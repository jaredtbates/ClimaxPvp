package net.climaxmc.KitPvp.Utils;// AUTHOR: gamer_000 (12/28/2015)

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

public class TextComponentMessages {

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


}
