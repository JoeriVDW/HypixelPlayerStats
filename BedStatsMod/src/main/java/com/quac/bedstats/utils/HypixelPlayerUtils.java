package com.quac.bedstats.utils;

import static com.quac.bedstats.utils.Color.stringToColor;

public class HypixelPlayerUtils {
    public static String rank(String rank, String plusColor) {
        String plusColorPrefix = stringToColor(plusColor);

        switch (rank.toLowerCase()) {
            case "mvp_plus":
                return "&b[MVP" + plusColorPrefix + "+&b] ";
            case "mvp":
                return "&b[MVP] ";
            case "vip_plus":
                return "&a[VIP&6+&a] ";
            case "vip":
                return "&a[VIP] ";
            default:
                return "&7";
        }
    }
}
