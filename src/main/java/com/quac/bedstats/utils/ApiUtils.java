package com.quac.bedstats.utils;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.quac.bedstats.core.structure.Config;
import net.minecraft.client.Minecraft;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class ApiUtils {
    public static JsonObject getJson(String uuid) throws MalformedURLException {
        try {
            if(Config.apiKey == null || Config.apiKey.equals("Not Found")) return null;
            String url = "https://api.hypixel.net/player?key=" + Config.apiKey + "&uuid=" + uuid;
            System.out.println(url);
            InputStream input = new URL(url).openStream();
            BufferedReader streamReader = new BufferedReader(new InputStreamReader(input, "UTF-8"));
            StringBuilder builder = new StringBuilder();
            String inputString;
            while ((inputString = streamReader.readLine()) != null) {
                builder.append(inputString);
            }
            JsonObject json = new JsonParser().parse(builder.toString()).getAsJsonObject();
            boolean succes = json.get("success").getAsBoolean();
            if(!succes) return null;
            JsonObject json2 = json.get("player").getAsJsonObject();
            return json2;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getUUIDByName(String username) {
        try {
            String url = "https://api.mojang.com/users/profiles/minecraft/" + username;
            System.out.println(url);
            InputStream input = new URL(url).openStream();
            BufferedReader streamReader = new BufferedReader(new InputStreamReader(input, "UTF-8"));
            StringBuilder builder = new StringBuilder();
            String inputString;
            while ((inputString = streamReader.readLine()) != null) {
                builder.append(inputString);
            }
            JsonObject json = new JsonParser().parse(builder.toString()).getAsJsonObject();
            return json.get("id").getAsString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
