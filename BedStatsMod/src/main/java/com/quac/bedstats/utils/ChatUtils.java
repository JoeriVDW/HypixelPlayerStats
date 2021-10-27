package com.quac.bedstats.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;

public class ChatUtils {
    public static String prefix = "&a[QuacTils] ";

    public static void addMsg(String t) {
        Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(t));
    }

    public static String getBooleanMessage(String t) {
        return t.replaceAll("true", "&aENABLED").replaceAll("false", "&cDISABLED");
    }
}
