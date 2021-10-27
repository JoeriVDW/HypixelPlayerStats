package com.quac.bedstats.eventhandlers;

import com.google.gson.JsonObject;
import com.quac.bedstats.Main;
import com.quac.bedstats.core.structure.Config;
import com.quac.bedstats.utils.ChatUtils;
import com.quac.bedstats.utils.Color;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.net.MalformedURLException;

public class PlayerEvents {
    @SubscribeEvent
    public void onMessageRecieve(ClientChatReceivedEvent e) throws MalformedURLException {
        String msg = e.message.getUnformattedText();
        if(isFromPlayer(msg)) return;

        // API GRABBER
        if(cfm(Color.translate("Your new API key is"), e)) {
            String apiKey = msg.substring(20);
            System.out.println(Color.translate(ChatUtils.prefix + "found api key! '" + apiKey + "'"));
            Config.apiKey = apiKey;
            ChatUtils.addMsg(Color.translate(ChatUtils.prefix + "found api key! '" + apiKey + "'"));
            Main.config.markDirty();
            Main.config.writeData();
        }

        JsonObject api = Main.apiJson;
    }

    public boolean isFromPlayer(String s) {
        return s.contains(":");
    }

    public boolean cfm(String s, ClientChatReceivedEvent e) {
        //System.out.println("Getting: " + e.message.getUnformattedText());
        return e.message.getUnformattedText().contains(s);
    }
}
