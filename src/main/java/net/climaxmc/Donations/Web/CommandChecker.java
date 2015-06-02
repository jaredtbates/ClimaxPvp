package net.climaxmc.Donations.Web;

import net.climaxmc.ClimaxPvp;
import org.bukkit.scheduler.BukkitRunnable;
import org.json.JSONObject;
import org.json.JSONArray;

public class CommandChecker extends BukkitRunnable {
    private ClimaxPvp plugin;
    private String apiKey = "";

    public CommandChecker(ClimaxPvp plugin) {
        this.plugin = plugin;
        this.apiKey = plugin.getConfig().getString("Donations.APIKey");
    }

    @Override
    public void run() {
        String pending;
        String expiry;
        try {
            pending = JsonManager.getJSON("http://www.minecraftmarket.com/api/" + apiKey + "/pending");
            expiry = JsonManager.getJSON("http://www.minecraftmarket.com/api/" + apiKey + "/expiry");

            if (pending != null) {
                JSONArray pendingArray = new JSONObject(pending).optJSONArray("result");
                for (int i = 0; i < pendingArray.length(); i++) {
                    String username = pendingArray.getJSONObject(i).getString("username");
                    JSONArray commands = pendingArray.getJSONObject(i).getJSONArray("commands");
                    if (plugin.getServer().getPlayerExact(username) != null) {
                        for (int c = 0; c < commands.length(); c++) {
                            String cmd = commands.getJSONObject(c).getString("command");
                            int cmdID = commands.getJSONObject(c).getInt("id");
                            if (JsonManager.getJSON("http://www.minecraftmarket.com/api/" + apiKey + "/executed/" + cmdID) != null) {
                                if (!cmd.equals("")) {
                                    plugin.getLogger().info("Executing \"/" + cmd + "\" on behalf of " + username);
                                    executeCommand(cmd);
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
                    if (plugin.getServer().getPlayerExact(username) != null) {
                        for (int c = 0; c < commands.length(); c++) {
                            String cmd = commands.getJSONObject(c).getString("command");
                            int cmdID = commands.getJSONObject(c).getInt("id");
                            if (JsonManager.getJSON("http://www.minecraftmarket.com/api/" + apiKey + "/executed/" + cmdID) != null) {
                                plugin.getLogger().info("Executing \"/" + cmd + "\" on behalf of " + username);
                                executeCommand(cmd);
                            }
                        }
                    }
                }
            }

        } catch (Exception ignored) {
        }
    }

    private void executeCommand(final String cmd) {
        plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () -> plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), cmd));
    }
}