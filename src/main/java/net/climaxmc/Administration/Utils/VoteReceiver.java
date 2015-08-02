/*
 * Copyright (C) 2012 Vex Software LLC
 * This file is part of Votifier.
 * 
 * Votifier is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * Votifier is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with Votifier.  If not, see <http://www.gnu.org/licenses/>.
 */

package net.climaxmc.Administration.Utils;

import net.climaxmc.ClimaxPvp;
import org.bukkit.ChatColor;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.xml.bind.DatatypeConverter;
import java.io.*;
import java.net.*;
import java.security.*;
import java.security.spec.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The vote receiving server.
 *
 * @author Blake Beaupain
 * @author Kramer Campbell
 */
public class VoteReceiver extends Thread {

    /**
     * The logger instance.
     */
    private static final Logger LOG = Logger.getLogger("Votifier");

    private final ClimaxPvp plugin;

    /**
     * The host to listen on.
     */
    private final String host;

    /**
     * The port to listen on.
     */
    private final int port;

    /**
     * The server socket.
     */
    private ServerSocket server;

    /**
     * The running flag.
     */
    private boolean running = true;

    private KeyPair keyPair;

    /**
     * Instantiates a new vote receiver.
     *
     * @param host The host to listen on
     * @param port The port to listen on
     */
    public VoteReceiver(final ClimaxPvp plugin, String host, int port)
            throws Exception {
        this.plugin = plugin;
        this.host = host;
        this.port = port;

        initialize();
        createKeys();
    }

    private void initialize() throws Exception {
        try {
            server = new ServerSocket();
            server.bind(new InetSocketAddress(host, port));
        } catch (Exception ex) {
            LOG.log(Level.SEVERE,
                    "Error initializing vote receiver. Please verify that the configured");
            LOG.log(Level.SEVERE,
                    "IP address and port are not already in use. This is a common problem");
            LOG.log(Level.SEVERE,
                    "with hosting services and, if so, you should check with your hosting provider.",
                    ex);
            throw new Exception(ex);
        }
    }

    /**
     * Shuts the vote receiver down cleanly.
     */
    public void shutdown() {
        running = false;
        if (server == null)
            return;
        try {
            server.close();
        } catch (Exception ex) {
            LOG.log(Level.WARNING, "Unable to shut down vote receiver cleanly.");
        }
    }

    @Override
    public void run() {

        // Main loop.
        while (running) {
            try {
                Socket socket = server.accept();
                socket.setSoTimeout(5000); // Don't hang on slow connections.
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(socket.getOutputStream()));
                InputStream in = socket.getInputStream();

                // Send them our version.
                writer.write("ClimaxPvp Votifier");
                writer.newLine();
                writer.flush();

                // Read the 256 byte block.
                byte[] block = new byte[256];
                in.read(block, 0, block.length);

                // Decrypt the block.
                block = decrypt(block, keyPair.getPrivate());
                int position = 0;

                // Perform the opcode check.
                String opcode = readString(block, position);
                position += opcode.length() + 1;
                if (!opcode.equals("VOTE")) {
                    // Something went wrong in RSA.
                    throw new Exception("Unable to decode RSA");
                }

                // Parse the block.
                String serviceName = readString(block, position);
                position += serviceName.length() + 1;
                String username = readString(block, position);
                position += username.length() + 1;
                String address = readString(block, position);
                position += address.length() + 1;
                String timeStamp = readString(block, position);
                position += timeStamp.length() + 1;

                // Create the vote.
                final Vote vote = new Vote();
                vote.setServiceName(serviceName);
                vote.setUsername(username);
                vote.setAddress(address);
                vote.setTimeStamp(timeStamp);

                // Call event in a synchronized fashion to ensure that the
                // custom event runs in the
                // the main server thread, not this one.
                plugin.getServer().getScheduler()
                        .scheduleSyncDelayedTask(plugin, () -> {
                            plugin.getServer().broadcastMessage(ChatColor.GREEN + vote.getUsername() + " has voted for " + ChatColor.GOLD + "Climax" + ChatColor.RED + "MC" + ChatColor.GREEN + "!");
                            //TODO Add any applicable code
                        });

                // Clean up.
                writer.close();
                in.close();
                socket.close();
            } catch (SocketException ex) {
                LOG.log(Level.WARNING, "Protocol error. Ignoring packet - "
                        + ex.getLocalizedMessage());
            } catch (BadPaddingException ex) {
                LOG.log(Level.WARNING,
                        "Unable to decrypt vote record. Make sure that that your public key");
                LOG.log(Level.WARNING,
                        "matches the one you gave the server list.", ex);
            } catch (Exception ex) {
                LOG.log(Level.WARNING,
                        "Exception caught while receiving a vote notification",
                        ex);
            }
        }
    }

    /**
     * Reads a string from a block of data.
     *
     * @param data The data to read from
     * @return The string
     */
    private String readString(byte[] data, int offset) {
        StringBuilder builder = new StringBuilder();
        for (int i = offset; i < data.length; i++) {
            if (data[i] == '\n')
                break; // Delimiter reached.
            builder.append((char) data[i]);
        }
        return builder.toString();
    }

    /**
     * Encrypts a block of data.
     *
     * @param data The data to encrypt
     * @param key  The key to encrypt with
     * @return The encrypted data
     * @throws Exception If an error occurs
     */
    public static byte[] encrypt(byte[] data, PublicKey key) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        return cipher.doFinal(data);
    }

    /**
     * Decrypts a block of data.
     *
     * @param data The data to decrypt
     * @param key  The key to decrypt with
     * @return The decrypted data
     * @throws Exception If an error occurs
     */
    public static byte[] decrypt(byte[] data, PrivateKey key) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, key);
        return cipher.doFinal(data);
    }

    /**
     * A model for a vote.
     *
     * @author Blake Beaupain
     */
    public class Vote {

        /**
         * The name of the vote service.
         */
        private String serviceName;

        /**
         * The username of the voter.
         */
        private String username;

        /**
         * The address of the voter.
         */
        private String address;

        /**
         * The date and time of the vote.
         */
        private String timeStamp;

        @Override
        public String toString() {
            return "Vote (from:" + serviceName + " username:" + username
                    + " address:" + address + " timeStamp:" + timeStamp + ")";
        }

        /**
         * Sets the serviceName.
         *
         * @param serviceName The new serviceName
         */
        public void setServiceName(String serviceName) {
            this.serviceName = serviceName;
        }

        /**
         * Gets the serviceName.
         *
         * @return The serviceName
         */
        public String getServiceName() {
            return serviceName;
        }

        /**
         * Sets the username.
         *
         * @param username The new username
         */
        public void setUsername(String username) {
            this.username = username.length() <= 16 ? username : username.substring(0, 16);
        }

        /**
         * Gets the username.
         *
         * @return The username
         */
        public String getUsername() {
            return username;
        }

        /**
         * Sets the address.
         *
         * @param address The new address
         */
        public void setAddress(String address) {
            this.address = address;
        }

        /**
         * Gets the address.
         *
         * @return The address
         */
        public String getAddress() {
            return address;
        }

        /**
         * Sets the time stamp.
         *
         * @param timeStamp The new time stamp
         */
        public void setTimeStamp(String timeStamp) {
            this.timeStamp = timeStamp;
        }

        /**
         * Gets the time stamp.
         *
         * @return The time stamp
         */
        public String getTimeStamp() {
            return timeStamp;
        }
    }

    /**
     * Static utility methods for saving and loading RSA key pairs.
     *
     * @author Blake Beaupain
     */
    public static class RSAIO {

        /**
         * Saves the key pair to the disk.
         *
         * @param directory The directory to save to
         * @param keyPair   The key pair to save
         * @throws Exception If an error occurs
         */
        public static void save(File directory, KeyPair keyPair) throws Exception {
            PrivateKey privateKey = keyPair.getPrivate();
            PublicKey publicKey = keyPair.getPublic();

            // Store the public key.
            X509EncodedKeySpec publicSpec = new X509EncodedKeySpec(
                    publicKey.getEncoded());
            FileOutputStream out = new FileOutputStream(directory + "/public.key");
            out.write(DatatypeConverter.printBase64Binary(publicSpec.getEncoded())
                    .getBytes());
            out.close();

            // Store the private key.
            PKCS8EncodedKeySpec privateSpec = new PKCS8EncodedKeySpec(
                    privateKey.getEncoded());
            out = new FileOutputStream(directory + "/private.key");
            out.write(DatatypeConverter.printBase64Binary(privateSpec.getEncoded())
                    .getBytes());
            out.close();
        }

        /**
         * Loads an RSA key pair from a directory. The directory must have the files
         * "public.key" and "private.key".
         *
         * @param directory The directory to load from
         * @return The key pair
         * @throws Exception If an error occurs
         */
        public static KeyPair load(File directory) throws Exception {
            // Read the public key file.
            File publicKeyFile = new File(directory + "/public.key");
            FileInputStream in = new FileInputStream(directory + "/public.key");
            byte[] encodedPublicKey = new byte[(int) publicKeyFile.length()];
            in.read(encodedPublicKey);
            encodedPublicKey = DatatypeConverter.parseBase64Binary(new String(
                    encodedPublicKey));
            in.close();

            // Read the private key file.
            File privateKeyFile = new File(directory + "/private.key");
            in = new FileInputStream(directory + "/private.key");
            byte[] encodedPrivateKey = new byte[(int) privateKeyFile.length()];
            in.read(encodedPrivateKey);
            encodedPrivateKey = DatatypeConverter.parseBase64Binary(new String(
                    encodedPrivateKey));
            in.close();

            // Instantiate and return the key pair.
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(
                    encodedPublicKey);
            PublicKey publicKey = keyFactory.generatePublic(publicKeySpec);
            PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(
                    encodedPrivateKey);
            PrivateKey privateKey = keyFactory.generatePrivate(privateKeySpec);
            return new KeyPair(publicKey, privateKey);
        }

    }

    /**
     * Generates an RSA key pair.
     *
     * @param bits
     *            The amount of bits
     * @return The key pair
     */
    public static KeyPair generateKeyPair(int bits) throws Exception {
        LOG.info("Votifier is generating an RSA key pair...");
        KeyPairGenerator keygen = KeyPairGenerator.getInstance("RSA");
        RSAKeyGenParameterSpec spec = new RSAKeyGenParameterSpec(bits,
                RSAKeyGenParameterSpec.F4);
        keygen.initialize(spec);
        return keygen.generateKeyPair();
    }

    private void createKeys() {
        File rsaDirectory = new File(plugin.getDataFolder() + "/rsa");

        /*
		 * Create RSA directory and keys if it does not exist; otherwise, read
		 * keys.
		 */
        try {
            if (!rsaDirectory.exists()) {
                rsaDirectory.mkdir();
                keyPair = generateKeyPair(2048);
                RSAIO.save(rsaDirectory, keyPair);
            } else {
                keyPair = RSAIO.load(rsaDirectory);
            }
        } catch (Exception ex) {
            LOG.log(Level.SEVERE,
                    "Error reading configuration file or RSA keys", ex);
        }
    }
}

