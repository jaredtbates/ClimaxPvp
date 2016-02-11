package net.climaxmc.KitPvp.Utils;// AUTHOR: gamer_000 (12/28/2015)

import net.climaxmc.ClimaxPvp;
import net.climaxmc.KitPvp.Menus.PlayerProfile.PlayerProfileMenu;
import net.climaxmc.common.database.PlayerData;
import net.climaxmc.common.database.Rank;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.*;
import org.bukkit.entity.Player;

import static org.bukkit.ChatColor.*;

public class TextComponentMessages {
    private ClimaxPvp plugin;

    public TextComponentMessages(ClimaxPvp plugin) {
        this.plugin = plugin;
    }

    private String playerStats(Player player) {
        PlayerData playerData = plugin.getPlayerData(player);
        String message = null;
        if (playerData.getNickname() != null) {
            message =
                    WHITE + "Nickname: " + playerData.getNickname() + "\n \n"
                            + YELLOW + "" + UNDERLINE + "                 STATS                 \n \n"
                            + WHITE + "" + BOLD + "Total Kills: " + ChatColor.GREEN + ChatColor.BOLD + (playerData.getKills() + playerData.getDuelKills()) + "\n"
                            + GRAY + "   Regular Kills: " + playerData.getKills() + "\n"
                            + GRAY + "   Duel Kills: " + playerData.getDuelKills() + "\n"
                            + WHITE + "" + BOLD + "Total Deaths: " + ChatColor.RED + ChatColor.BOLD + (playerData.getDeaths() + playerData.getDuelDeaths()) + "\n"
                            + GRAY + "   Regular Deaths: " + playerData.getDeaths() + "\n"
                            + GRAY + "   Duel Deaths: " + playerData.getDuelDeaths() + "\n \n"
                            + YELLOW + "" + UNDERLINE + "                  KDR's                 \n \n"
                            + AQUA + "REGULAR   " + LIGHT_PURPLE + "  DUELS   " + GOLD + "  TOTAL\n \n"
                            + AQUA + "" + PlayerProfileMenu.getRegularRatio(playerData) + "      " + LIGHT_PURPLE + "        "
                            + PlayerProfileMenu.getDuelsRatio(playerData) + "   " + GOLD + "        " + PlayerProfileMenu.getTotalRatio(playerData) + "\n \n"
                            + GREEN + "Click to open options for this player!";
        } else {
            message =
                    YELLOW + "" + UNDERLINE + "                 STATS                 \n \n"
                            + WHITE + "" + BOLD + "Total Kills: " + ChatColor.GREEN + ChatColor.BOLD + (playerData.getKills() + playerData.getDuelKills()) + "\n"
                            + GRAY + "   Regular Kills: " + playerData.getKills() + "\n"
                            + GRAY + "   Duel Kills: " + playerData.getDuelKills() + "\n"
                            + WHITE + "" + BOLD + "Total Deaths: " + ChatColor.RED + ChatColor.BOLD + (playerData.getDeaths() + playerData.getDuelDeaths()) + "\n"
                            + GRAY + "   Regular Deaths: " + playerData.getDeaths() + "\n"
                            + GRAY + "   Duel Deaths: " + playerData.getDuelDeaths() + "\n \n"
                            + YELLOW + "" + UNDERLINE + "                  KDR's                 \n \n"
                            + AQUA + "REGULAR   " + LIGHT_PURPLE + "  DUELS   " + GOLD + "  TOTAL\n"
                            + AQUA + "" + PlayerProfileMenu.getRegularRatio(playerData) + "      " + LIGHT_PURPLE + "        "
                            + PlayerProfileMenu.getDuelsRatio(playerData) + "   " + GOLD + "        " + PlayerProfileMenu.getTotalRatio(playerData) + "\n \n"
                            + GREEN + "Click to open options for this player!";
        }
        return message;
    }

    public TextComponent centerTextSpacesLeft() {
        TextComponent spaces = new TextComponent("                           ");
        return spaces;
    }

    public TextComponent centerTextSpacesMiddle() {
        TextComponent spaces = new TextComponent("         ");
        return spaces;
    }

    public TextComponent duelAcceptButton() {
        TextComponent message = new TextComponent("[ACCEPT]");
        message.setBold(true);
        message.setColor(ChatColor.GREEN);
        message.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/duel accept"));
        message.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ChatColor.GRAY
                + "Click to" + ChatColor.GREEN + " accept " + ChatColor.GRAY + "the request!").create()));
        return message;
    }

    public TextComponent duelDenyButton() {
        TextComponent message = new TextComponent("[DENY]");
        message.setBold(true);
        message.setColor(ChatColor.RED);
        message.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/duel deny"));
        message.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ChatColor.GRAY
                + "Click to" + ChatColor.RED + " deny " + ChatColor.GRAY + "the request!").create()));
        return message;
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

    public void sendChatMessage(Player player, String message) {
        PlayerData playerData = plugin.getPlayerData(player);

        TextComponent rank = new TextComponent(" " + (playerData.hasRank(Rank.NINJA) ? ChatColor.BOLD + playerData.getRank().getPrefix() + " " : ""));
        rank.setColor(playerData.getRank().getColor());

        TextComponent name;
        if(playerData.getNickname() != null) {
            name = new TextComponent(playerData.getNickname());
        } else {
            name = new TextComponent(player.getName());
            name.setColor(ChatColor.getByChar(playerData.getLevelColor().charAt(1)));
        }
        name.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/options " + player.getName()));
        name.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(playerStats(player)).create()));

        BaseComponent component = rank;
        component.addExtra(name);
        component.addExtra(ChatColor.WHITE + " \u00BB " + (playerData.hasRank(Rank.NINJA) ? ChatColor.WHITE : ChatColor.GRAY) + message);

        for (Player players : plugin.getServer().getOnlinePlayers()) {
            players.spigot().sendMessage(component);
        }
    }
}
