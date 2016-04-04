package net.climaxmc.KitPvp.Utils;// AUTHOR: gamer_000 (12/28/2015)

import net.climaxmc.ClimaxPvp;
import net.climaxmc.common.database.PlayerData;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.*;
import org.bukkit.entity.Player;

public class TextComponentMessages {

    private ClimaxPvp plugin;

    public TextComponentMessages(ClimaxPvp plugin) {
        this.plugin = plugin;
    }

    public TextComponent centerTextSpacesLeft() {
        TextComponent spaces = new TextComponent("                           ");
        return spaces;
    }

    public TextComponent centerTextSpacesMiddle() {
        TextComponent spaces = new TextComponent("         ");
        return spaces;
    }

    public TextComponent teamAcceptButton() {
        TextComponent message = new TextComponent("[ACCEPT]");
        message.setBold(true);
        message.setColor(ChatColor.GREEN);
        message.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/team accept"));
        message.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ChatColor.GRAY
                + "Click to" + ChatColor.GREEN + " accept " + ChatColor.GRAY + "the request!").create()));
        return message;
    }

    public TextComponent teamDenyButton() {
        TextComponent message = new TextComponent("[DENY]");
        message.setBold(true);
        message.setColor(ChatColor.RED);
        message.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/team deny"));
        message.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ChatColor.GRAY
                + "Click to" + ChatColor.RED + " deny " + ChatColor.GRAY + "the request!").create()));
        return message;
    }

    public BaseComponent[] playerStats(Player player) {
        PlayerData playerData = plugin.getPlayerData(player);
        ChatColor ratioColor = ChatColor.GRAY;
        double ratio = ((double) playerData.getKills() / (double) playerData.getDeaths());
        ratio = Math.round(ratio * 100.00D) / 100.00D;
        if (playerData.getDeaths() == 0) {
            ratio = playerData.getKills();
        }
        if (ratio < 0.25D) {
            ratioColor = ChatColor.DARK_RED;
        } else if (ratio >= 0.25D && ratio < 0.50D) {
            ratioColor = ChatColor.RED;
        } else if (ratio >= 0.50D && ratio < 0.75D) {
            ratioColor = ChatColor.GOLD;
        } else if (ratio >= 0.75D && ratio < 1.00D) {
            ratioColor = ChatColor.YELLOW;
        } else if (ratio >= 1.00D && ratio < 1.50D) {
            ratioColor = ChatColor.DARK_GREEN;
        } else if (ratio >= 1.50D && ratio < 2.50D) {
            ratioColor = ChatColor.GREEN;
        } else if (ratio >= 2.50D && ratio < 3.50D) {
            ratioColor = ChatColor.AQUA;
        } else if (ratio >= 3.50D) {
            ratioColor = ChatColor.LIGHT_PURPLE;
        }
        BaseComponent[] component;
        if (playerData.getNickname() != null) {
            component = new ComponentBuilder(
                    ChatColor.WHITE + "Name: " + playerData.getLevelColor() + player.getName() + "\n"
                            + ChatColor.GRAY + "Nickname: " + playerData.getNickname() + "\n\n"
                            + ChatColor.YELLOW + "" + ChatColor.UNDERLINE + "                 STATS                  \n\n"
                            + ChatColor.WHITE + "Kills: " + ChatColor.GREEN + playerData.getKills() + "\n"
                            + ChatColor.WHITE + "Deaths: " + ChatColor.RED + playerData.getDeaths() + "\n"
                            + ChatColor.WHITE + "Kill Death Ratio: " + ratioColor + ratio
                            + "\n\n" + ChatColor.AQUA + "Click to open player options...."
            ).create();
        } else {
            component = new ComponentBuilder(
                    ChatColor.WHITE + "Name: " + playerData.getLevelColor() + player.getName() + "\n\n"
                            + ChatColor.YELLOW + "" + ChatColor.UNDERLINE + "                 STATS                  \n\n"
                            + ChatColor.WHITE + "Kills: " + ChatColor.GREEN + playerData.getKills() + "\n"
                            + ChatColor.WHITE + "Deaths: " + ChatColor.RED + playerData.getDeaths() + "\n"
                            + ChatColor.WHITE + "Kill Death Ratio: " + ratioColor + ratio
                            + "\n\n" + ChatColor.AQUA + "Click to open player options...."
            ).create();
        }

        return component;
    }

}
