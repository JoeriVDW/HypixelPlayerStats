package com.quac.bedstats.commands;

import com.google.gson.JsonObject;
import com.quac.bedstats.utils.ApiUtils;
import com.quac.bedstats.utils.ChatUtils;
import com.quac.bedstats.utils.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import static com.quac.bedstats.utils.Color.stringToColor;
import static com.quac.bedstats.utils.HypixelPlayerUtils.rank;
import static javafx.scene.input.KeyCode.U;

public class MainCommand extends CommandBase {
    @Override
    public String getCommandName() {
        return "qtbedstats";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "qtbedstats";
    }

    @Override
    public List<String> getCommandAliases() {
        List<String> aliases = new ArrayList<>();
        aliases.add("bedstats");
        return aliases;
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender sender) {
        return true;
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
        if(args.length == 0) {
            try {
                ChatUtils.addMsg(msg(Minecraft.getMinecraft().thePlayer.getName(), "overall"));
            } catch (MalformedURLException e) {
                ChatUtils.addMsg(Color.translate(ChatUtils.prefix + "There was an error getting the api! Is your api key set?"));
                e.printStackTrace();
            }
        }
    }

    public static String msg(String username, String gamemode) throws MalformedURLException {
        JsonObject player = ApiUtils.getJson(username);
        JsonObject achievements = player.get("achievements").getAsJsonObject();

        String topline =
                prestige(achievements.get("bedwars_level").getAsInt()) +
                rank(player.get("newPackageRank").getAsString(), player.get("rankPlusColor").getAsString()) + player.get("displayname").getAsString();


        String msg = null;
        switch (gamemode.toLowerCase()) {
            case "overall":
                msg = Color.translate(
                        "&c================================\n" +
                        topline + "\n" +
                        "Work yes yes????   \n" +
                        "&c================================\n"
                );
                break;
        }
        return msg;
    }

    public static String prestige(int lvl) {
        String level = "" + lvl;

        if(lvl < 100) { // 0 - 99
            return "&7[" + level + "\u272B]&r ";
        } else if(lvl < 200) { // 100 - 199
            return "&f[" + level + "\u272B]&r ";
        } else if(lvl < 300) { // 200 - 299
            return "&6[" + level + "\u272B]&r ";
        } else if(lvl < 400) { // 300 - 399
            return "&b[" + level + "\u272B]&r ";
        } else if(lvl < 500) { // 400 - 499
            return "&2[" + level + "\u272B]&r ";
        } else if(lvl < 600) { // 500 - 599
            return "&3[" + level + "\u272B]&r ";
        } else if(lvl < 700) { // 600 - 699
            return "&c[" + level + "\u272B]&r ";
        } else if(lvl < 800) { // 700 - 799
            return "&d[" + level + "\u272B]&r ";
        } else if(lvl < 900) { // 800 - 899
            return "&9[" + level + "\u272B]&r ";
        } else if(lvl < 1000) { // 900 - 999
            return "&5[" + level + "\u272B]&r ";
        } else if(lvl < 1100) { // 1000 - 1099
            return "&c[&6" + level.charAt(0) + "&e" + level.charAt(1) + "&a" + level.charAt(2) + "&b" + level.charAt(3) + "&d\u272B&5]&r ";
        } else if(lvl < 1200) { // 1100 - 1199
            return "&8[&7" + level + "&8\u272A]&r ";
        } else if(lvl < 1300) { // 1200 - 1299
            return "&8[&6" + level + "\u272A]&r ";
        } else if(lvl < 1400) { // 1300 - 1399
            return "&8[&b" + level + "&3\u272A]&r ";
        } else if(lvl < 1500) { // 1400 - 1499
            return "&8[&a" + level + "&2\u272A]&r ";
        } else if(lvl < 1600) { // 1500 - 1599
            return "&8[&3" + level + "&9\u272A]&r ";
        } else if(lvl < 1700) { // 1600 - 1699
            return "&8[&c" + level + "&4\u272A]&r ";
        } else if(lvl < 1800) { // 1700 - 1799
            return "&8[&d" + level + "&5\u272A]&r ";
        } else if(lvl < 1900) { // 1800 - 1899
            return "&8[&9" + level + "&1\u272A]&r ";
        } else if(lvl < 2000) { // 1900 - 1999
            return "&8[&5" + level + "&8\u272A]&r ";
        } else { // 2000+
            return "&6&l[" + level + "\u272A]&r ";
        }
    }
}
