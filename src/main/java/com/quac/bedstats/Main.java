package com.quac.bedstats;

import com.google.gson.JsonObject;
import com.quac.bedstats.commands.MainCommand;
import com.quac.bedstats.core.structure.Config;
import com.quac.bedstats.eventhandlers.PlayerEvents;
import com.quac.bedstats.utils.ApiUtils;
import com.quac.bedstats.utils.ChatUtils;
import gg.essential.vigilance.Vigilance;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.init.Blocks;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.io.File;
import java.net.MalformedURLException;

@Mod(modid = Main.MODID, version = Main.VERSION)
public class Main
{
    public static final String MODID = "bedstats";
    public static final String VERSION = "0.1";
    public static Config config;
    private static GuiScreen guiToOpen;

    public static JsonObject apiJson;
    int ticks = 0;

    public static void setGui(GuiScreen gui) {
        guiToOpen = gui;
    }
    
    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        Vigilance.initialize();
        config = new Config(new File("./config/bedwarsstats.toml"));
        config.preload();

        ClientCommandHandler.instance.registerCommand(new MainCommand());

        MinecraftForge.EVENT_BUS.register(new PlayerEvents());
        MinecraftForge.EVENT_BUS.register(new Main());
    }

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) throws MalformedURLException {
        if(!event.phase.equals(TickEvent.Phase.START)) return;

        if(ticks == 1) { refreshApi(); }
        if(ticks == 20 * 60 * 3) { ticks = 0; }

        if (guiToOpen != null) {
            try {
                System.out.println("Opening GUI...");
                Minecraft.getMinecraft().displayGuiScreen(guiToOpen);
            } catch (Exception e) {
                e.printStackTrace();
                ChatUtils.addMsg("&cError while opening GUI. Check log for more details");
            }
            guiToOpen = null;
        }

        ticks++;
    }

    public static void refreshApi() throws MalformedURLException {
        //apiJson = ApiUtils.getJson(Minecraft.getMinecraft().get);
    }
}
