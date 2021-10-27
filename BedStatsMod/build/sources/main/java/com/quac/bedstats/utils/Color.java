package com.quac.bedstats.utils;

import net.minecraft.util.EnumChatFormatting;

public class Color {
    public static String translate(String s) {
        return EnumChatFormatting.getTextWithoutFormattingCodes(s).replaceAll("&", "\u00A7");
    }

    public static String stringToColor(String color) {
        switch (color.toUpperCase()) {
            case "BLACK":
                return "&0";
            case "DARK_GREEN":
                return "&2";
            case "DARK_RED":
                return "&4";
            case "GOLD":
                return "&6";
            case "DARK_GRAY":
                return "&8";
            case "GREEN":
                return "&a";
            case "RED":
                return "&c";
            case "YELLOW":
                return "&e";
            case "DARK_BLUE":
                return "&1";
            case "DARK_AQUA":
                return "&3";
            case "DARK_PURPLE":
                return "&5";
            case "GRAY":
                return "&7";
            case "BLUE":
                return "&9";
            case "AQUA":
                return "&b";
            case "LIGHT_PURPLE":
                return "&d";
            case "WHITE":
            default:
                return "&f";
        }
    }
}
