package net.climaxmc.Administration.Runnables;

import net.climaxmc.Administration.Utils.PluginUtil;
import net.climaxmc.ClimaxPvp;
import net.md_5.bungee.api.ChatColor;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;

public class UpdateRunnable implements Runnable {
    private ClimaxPvp plugin;
    private File updateFile;

    public UpdateRunnable(ClimaxPvp plugin) {
        this.plugin = plugin;

        if (!plugin.getServer().getUpdateFolderFile().exists()) {
            plugin.getServer().getUpdateFolderFile().mkdir();
        }

        updateFile = new File(plugin.getServer().getUpdateFolderFile().getPath() + File.separator + "ClimaxPvp.jar");
    }

    @Override
    public void run() {
        if (updateFile == null || !updateFile.exists()) {
            return;
        }

        plugin.getServer().broadcastMessage(ChatColor.GRAY + "Reloading in 5 seconds for an update...");

        plugin.getServer().getScheduler().runTaskLater(plugin, () -> {
            PluginUtil.unload(plugin);

            try {
                Files.move(Paths.get(updateFile.getPath()), Paths.get(plugin.getServer().getUpdateFolderFile().getParentFile().getPath() + File.separator + "ClimaxPvp.jar"), StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                plugin.getLogger().severe("Could not copy update!");
            }

            PluginUtil.load("ClimaxPvp");

            plugin.getServer().broadcastMessage(ChatColor.GRAY + "Reload complete!");
        }, 100);
    }
}
