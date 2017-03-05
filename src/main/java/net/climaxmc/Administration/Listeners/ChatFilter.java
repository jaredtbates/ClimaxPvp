package net.climaxmc.Administration.Listeners;

import net.climaxmc.ClimaxPvp;
import org.apache.commons.lang.StringUtils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatFilter implements Listener {
    private ClimaxPvp plugin;

    public ChatFilter(ClimaxPvp plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onAsyncPlayerChat(final AsyncPlayerChatEvent event) {
        if (event.isCancelled()) {
            return;
        }

        String[] message = event.getMessage().split(" ");
        String finalMessage = "";

        for (String word : message) {
            for (String filteredWord : plugin.getFilteredWords()) {
                if (word.toLowerCase().contains(filteredWord.toLowerCase().trim())) {
                    word = StringUtils.repeat("*", word.length());
                }
            }

            finalMessage += word + " ";
        }

        double count = 0;
        for (char character : finalMessage.toCharArray()) {
            if (Character.isUpperCase(character)) {
                count++;
            }
        }

        if (finalMessage.length() >= 13) {
            if (count / finalMessage.length() > 0.60) {
                finalMessage = finalMessage.toLowerCase();
            }
        }

        event.setMessage(finalMessage.trim());
    }
}
