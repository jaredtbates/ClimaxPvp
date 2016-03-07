package net.climaxmc.Donations;

import net.climaxmc.ClimaxPvp;
import net.gpedro.integrations.slack.SlackMessage;
import org.bukkit.scheduler.BukkitRunnable;
import org.json.JSONArray;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class DonationsChecker extends BukkitRunnable {
    private ClimaxPvp plugin;

    public DonationsChecker(ClimaxPvp plugin) {
        runTaskTimerAsynchronously(plugin, 200, 200);
        this.plugin = plugin;
    }

    @Override
    public void run() {
        String apiKey = "206b80e617a83fd634ac02fe03bdaec5";
        String pending = getJSON("http://www.minecraftmarket.com/api/" + apiKey + "/pending");
        String expiry = getJSON("http://www.minecraftmarket.com/api/" + apiKey + "/expiry");

        if (pending != null) {
            JSONArray pendingArray = new JSONObject(pending).optJSONArray("result");
            for (int i = 0; i < pendingArray.length(); i++) {
                String username = pendingArray.getJSONObject(i).getString("username");
                JSONArray commands = pendingArray.getJSONObject(i).getJSONArray("commands");
                if (plugin.getServer().getPlayer(username) != null) {
                    for (int c = 0; c < commands.length(); c++) {
                        String cmd = commands.getJSONObject(c).getString("command");
                        int cmdID = commands.getJSONObject(c).getInt("id");
                        if (getJSON("http://www.minecraftmarket.com/api/" + apiKey + "/executed/" + cmdID) != null) {
                            if (!cmd.equals("")) {
                                plugin.getLogger().info("Executing \"/" + cmd + "\" on behalf of " + username);
                                executeCommand(cmd);
                                plugin.getSlackDonations().call(new SlackMessage(">>>*" + username + "* just donated for *" + cmd.substring(7 + username.length(), cmd.length()) + "*!"));
                            }
                        }
                    }
                }
            }
        }
        if (expiry != null) {
            JSONArray expiryArray = new JSONObject(expiry).optJSONArray("result");
            for (int i = 0; i < expiryArray.length(); i++) {
                String username = expiryArray.getJSONObject(i).getString("username");
                JSONArray commands = expiryArray.getJSONObject(i).getJSONArray("commands");
                if (plugin.getServer().getPlayer(username) != null) {
                    for (int c = 0; c < commands.length(); c++) {
                        String cmd = commands.getJSONObject(c).getString("command");
                        int cmdID = commands.getJSONObject(c).getInt("id");
                        if (getJSON("http://www.minecraftmarket.com/api/" + apiKey + "/executed/" + cmdID) != null) {
                            plugin.getLogger().info("Executing \"/" + cmd + "\" on behalf of " + username);
                            executeCommand(cmd);
                            plugin.getSlackDonations().call(new SlackMessage(">>>*" + username + "* just donated for *" + cmd.substring(7 + username.length(), cmd.length()) + "*!"));
                        }
                    }
                }
            }
        }
    }

    private void executeCommand(String cmd) {
        plugin.getServer().getScheduler().runTask(plugin, () -> plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), cmd));
    }

    private String getJSON(String url) {
        try {
            URL u = new URL(url);
            HttpURLConnection c = (HttpURLConnection) u.openConnection();
            c.setRequestMethod("GET");
            c.setRequestProperty("Content-length", "0");
            c.setRequestProperty("Accept", "application/json");
            c.setUseCaches(false);
            c.setAllowUserInteraction(false);
            c.setConnectTimeout(10000);
            c.setReadTimeout(10000);
            c.connect();
            int status = c.getResponseCode();
            switch (status) {
                case 200:
                case 201:
                    Scanner s = new Scanner(c.getInputStream());
                    s.useDelimiter("\\Z");
                    return s.next();
            }
        } catch (Exception ignored) {
        }
        return "";
    }
}