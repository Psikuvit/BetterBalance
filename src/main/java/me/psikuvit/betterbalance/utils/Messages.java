package me.psikuvit.betterbalance.utils;

import org.bukkit.ChatColor;

public class Messages {

    public static String color(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }
}
