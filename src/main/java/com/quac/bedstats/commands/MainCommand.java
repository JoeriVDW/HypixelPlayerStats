package com.quac.bedstats.commands;

import com.google.gson.JsonObject;
import com.quac.bedstats.Main;
import com.quac.bedstats.core.structure.Config;
import com.quac.bedstats.utils.ApiUtils;
import com.quac.bedstats.utils.ChatUtils;
import com.quac.bedstats.utils.Color;
import com.quac.bedstats.utils.HypixelPlayerUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.net.MalformedURLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import static com.quac.bedstats.utils.Color.stringToColor;
import static com.quac.bedstats.utils.HypixelPlayerUtils.getOnlineDot;
import static com.quac.bedstats.utils.HypixelPlayerUtils.rank;
import static javafx.scene.input.KeyCode.U;

public class MainCommand extends CommandBase {
    @Override
    public String getCommandName() {
        return "qtbedstats";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/qtbedstats";
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
        try {
            switch (args.length) {
                case 0:
                    ChatUtils.addMsg(msg(Minecraft.getMinecraft().thePlayer.getName(), "overall"));
                    break;
                case 1:
                    if(args[0].equalsIgnoreCase("config")) {
                        Main.setGui(Main.config.gui());
                        return;
                    } else if(args[0].equalsIgnoreCase("help")) {
                        ChatUtils.addMsg(Color.translate(ChatUtils.prefix + "By Quac - Help menu\n"
                        +"&aCommand Usages, (argument) = Optional\n"
                        +"&aMain usage: '/qtbedstats (username) (gamemode)'\n" +
                                        "&a- If u want to enter a gamemode for your own stats u have to enter your own username!" +
                                        "&aAlso if u enter a gamemode that doesn't exist it will automatically get your global stats \n\n"
                        +"&aOther commands\n"
                        +"&a'/qtbedstats help' - Sends this message\n"
                        +"&a'/qtbedstats config' - Opens the config gui\n\n"
                        +"&a(Current) Gamemodes: 'global', 'solos', 'doubles', 'threes', 'fours' and '4v4'\n"
                        ));
                        return;
                    }
                    ChatUtils.addMsg(msg(args[0], "overall"));
                    break;
                case 2:
                    ChatUtils.addMsg(msg(args[0], args[1]));
                    break;
            }
        } catch (MalformedURLException e) {
            ChatUtils.addMsg(Color.translate(ChatUtils.prefix + "There was an error getting the api! Is your api key set?"));
            e.printStackTrace();
        }
    }

    public static String msg(String username, String gamemode) throws MalformedURLException {
        String uuid = ApiUtils.getUUIDByName(username);
        if(uuid == null) return Color.translate(ChatUtils.prefix + "There was an error whilst trying to get the player's uuid");

        JsonObject player = ApiUtils.getJson(uuid);
        JsonObject achievements = player.get("achievements").getAsJsonObject();
        JsonObject stats = player.get("stats").getAsJsonObject();
        JsonObject bw = stats.get("Bedwars").getAsJsonObject();

        String rank;
        if(player.get("newPackageRank") == null) { rank = "&a"; }
        else if(player.get("rankPlusColor") == null && player.get("newPackageRank") != null) { rank = rank(player.get("newPackageRank").getAsString()); }
        else if(player.get("rankPlusColor") != null && player.get("newPackageRank") != null) { rank = rank(player.get("newPackageRank").getAsString(), player.get("rankPlusColor").getAsString()); }
        else { rank = "&a"; }

        boolean isOnline;
        String isOnlineDisplay;
        if(player.get("lastLogin") != null && player.get("lastLogout") != null ) {
            isOnline = player.get("lastLogin").getAsLong() > player.get("lastLogout").getAsLong();
            isOnlineDisplay = HypixelPlayerUtils.getOnlineDot(isOnline);
        } else {
            isOnlineDisplay = "&7•";
        }

        String topline =
                prestige(achievements.get("bedwars_level").getAsInt())
                + rank
                + player.get("displayname").getAsString() + " "
                + isOnlineDisplay;


        String msg = null;

        // String.format("&7Wins: &6&l%d   \n", achievements.get("bedwars_wins").getAsInt()) +

        msg = getStatsFromGamemode(gamemode, bw, topline);

        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        if(msg != null) {
            if(Config.autoCopyStats) {
                clipboard.setContents(new StringSelection(ChatUtils.getMessageWithoutColorCodes(msg)), null);
            }
        }
        return msg;
    }

    private static String getStatsFromGamemode(String gamemode, JsonObject bw, String topline) {
        String pre = "_";
        String gamemodeName;

        switch (gamemode.toLowerCase()) {
            case "solo":
            case "solos":
                pre = "eight_one_";
                gamemodeName = "Solo";
                break;
            case "double":
            case "doubles":
                pre = "eight_two_";
                gamemodeName = "Doubles";
                break;
            case "threes":
            case "triple":
            case "triples":
                pre = "four_three_";
                gamemodeName = "Threes";
                break;
            case "four":
            case "fours":
                pre = "four_four_";
                gamemodeName = "Fours";
                break;
            case "4v4":
            case "fourvfour":
                pre = "two_four_";
                gamemodeName = "4v4";
                break;
            case "global":
            case "overall":
            default:
                pre = "";
                gamemodeName = "Global";
                break;
        }

        double a = (((bw.get(pre+"wins_bedwars") == null)) ? 0 : bw.get(pre+"wins_bedwars").getAsDouble()) / (((bw.get(pre+"losses_bedwars") == null)) ? 0 : bw.get(pre+"losses_bedwars").getAsDouble());
        double wlRate = Math.round(a*100.0)/100.0;

        double b = (((bw.get(pre+"final_kills_bedwars") == null)) ? 0 : bw.get(pre+"final_kills_bedwars").getAsDouble()) / (((bw.get(pre+"final_deaths_bedwars") == null)) ? 0 : bw.get(pre+"final_deaths_bedwars").getAsDouble());
        double fkdRate = Math.round(b*100.0)/100.0;

        double c = (((bw.get(pre+"kills_bedwars") == null)) ? 0 : bw.get(pre+"kills_bedwars").getAsDouble()) / (((bw.get(pre+"losses_bedwars") == null)) ? 0 : bw.get(pre+"losses_bedwars").getAsDouble());
        double kdRate = Math.round(c*100.0)/100.0;

        double d = (((bw.get(pre+"beds_broken_bedwars") == null)) ? 0 : bw.get(pre+"beds_broken_bedwars").getAsDouble()) / (((bw.get(pre+"beds_lost_bedwars") == null)) ? 0 : bw.get(pre+"beds_lost_bedwars").getAsDouble());
        double bblRate = Math.round(d*100.0)/100.0;

        String msg = Color.translate(
                "\n&c================================\n" +
                        topline + "\n" +
                        "&f&lBedwars &fStats, &a" + gamemodeName + " - By Quac\n" +
                        "\n" +
                        String.format("&7Wins: &a&l%d   \n", ((bw.get(pre+"wins_bedwars") == null)) ? 0 : bw.get(pre+"wins_bedwars").getAsInt()) +
                        String.format("&7Losses: &c&l%d   \n", ((bw.get(pre+"losses_bedwars") == null)) ? 0 : bw.get(pre+"losses_bedwars").getAsInt()) +
                        String.format("&7WLR: &6&l%s   \n", wlRate) +
                        "\n" +
                        String.format("&7Final Kills: &a&l%d   \n", ((bw.get(pre+"final_kills_bedwars") == null)) ? 0 : bw.get(pre+"final_kills_bedwars").getAsInt()) +
                        String.format("&7Final Deaths: &c&l%d   \n", ((bw.get(pre+"final_deaths_bedwars") == null)) ? 0 : bw.get(pre+"final_deaths_bedwars").getAsInt()) +
                        String.format("&7FKDR: &6&l%s   \n", fkdRate) +
                        "\n" +
                        String.format("&7Kills: &a&l%d   \n", ((bw.get(pre+"kills_bedwars") == null)) ? 0 : bw.get(pre+"kills_bedwars").getAsInt()) +
                        String.format("&7Deaths: &c&l%d   \n", ((bw.get(pre+"deaths_bedwars") == null)) ? 0 : bw.get(pre+"deaths_bedwars").getAsInt()) +
                        String.format("&7KDR: &6&l%s   \n", kdRate) +
                        "\n" +
                        String.format("&7Beds Broken: &a&l%d   \n", ((bw.get(pre+"beds_broken_bedwars") == null)) ? 0 : bw.get(pre+"beds_broken_bedwars").getAsInt()) +
                        String.format("&7Beds Lost: &c&l%d   \n", ((bw.get(pre+"beds_lost_bedwars") == null)) ? 0 : bw.get(pre+"beds_lost_bedwars").getAsInt()) +
                        String.format("&7BBLR: &6&l%s   \n", bblRate) +
                        "&c================================\n"
        );

        return msg;
    }

    public static String prestige(int lvl) {
        String level = "" + lvl;

        if(lvl < 100) { // 0 - 99
            return "&7[" + level + "✫]&r ";
        } else if(lvl < 200) { // 100 - 199
            return "&f[" + level + "✫]&r ";
        } else if(lvl < 300) { // 200 - 299
            return "&6[" + level + "✫]&r ";
        } else if(lvl < 400) { // 300 - 399
            return "&b[" + level + "✫]&r ";
        } else if(lvl < 500) { // 400 - 499
            return "&2[" + level + "✫]&r ";
        } else if(lvl < 600) { // 500 - 599
            return "&3[" + level + "✫]&r ";
        } else if(lvl < 700) { // 600 - 699
            return "&c[" + level + "✫]&r ";
        } else if(lvl < 800) { // 700 - 799
            return "&d[" + level + "✫]&r ";
        } else if(lvl < 900) { // 800 - 899
            return "&9[" + level + "✫]&r ";
        } else if(lvl < 1000) { // 900 - 999
            return "&5[" + level + "✫]&r ";
        } else if(lvl < 1100) { // 1000 - 1099
            return "&c[&6" + level.charAt(0) + "&e" + level.charAt(1) + "&a" + level.charAt(2) + "&b" + level.charAt(3) + "&d✫&5]&r ";
        } else if(lvl < 1200) { // 1100 - 1199
            return "&7[&f" + level + "&7✪]&r ";
        } else if(lvl < 1300) { // 1200 - 1299
            return "&7[&e" + level + "&6✪]&r ";
        } else if(lvl < 1400) { // 1300 - 1399
            return "&7[&b" + level + "&3✪]&r ";
        } else if(lvl < 1500) { // 1400 - 1499
            return "&7[&a" + level + "&2✪]&r ";
        } else if(lvl < 1600) { // 1500 - 1599
            return "&7[&3" + level + "&9✪]&r ";
        } else if(lvl < 1700) { // 1600 - 1699
            return "&7[&c" + level + "&4✪]&r ";
        } else if(lvl < 1800) { // 1700 - 1799
            return "&7[&d" + level + "&5✪]&r ";
        } else if(lvl < 1900) { // 1800 - 1899
            return "&7[&9" + level + "&1✪]&r ";
        } else if(lvl < 2000) { // 1900 - 1999
            return "&7[&5" + level + "&8✪&7]&r ";
        } else { // 2000+
            return "&6&l[" + level + "✪]&r ";
        }
    }
}
