package com.quac.bedstats.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;

public class ChatUtils {
    public static String prefix = "&a[Bedstats] ";

    public static void addMsg(String t) {
        Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(t));
    }

    public static String getBooleanMessage(String t) {
        return t.replaceAll("true", "&aENABLED").replaceAll("false", "&cDISABLED");
    }

    public static String getMessageWithoutColorCodes(String t) {
        return t
                .replaceAll("§0", "")
                .replaceAll("§1", "")
                .replaceAll("§2", "")
                .replaceAll("§3", "")
                .replaceAll("§4", "")
                .replaceAll("§5", "")
                .replaceAll("§6", "")
                .replaceAll("§7", "")
                .replaceAll("§8", "")
                .replaceAll("§9", "")
                .replaceAll("§a", "")
                .replaceAll("§b", "")
                .replaceAll("§c", "")
                .replaceAll("§d", "")
                .replaceAll("§e", "")
                .replaceAll("§f", "")
                .replaceAll("§k", "")
                .replaceAll("§l", "")
                .replaceAll("§m", "")
                .replaceAll("§n", "")
                .replaceAll("§o", "")
                .replaceAll("§r", "");
    }
}
