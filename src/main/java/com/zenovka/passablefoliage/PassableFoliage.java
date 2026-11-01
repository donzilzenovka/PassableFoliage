package com.zenovka.passablefoliage;

import java.util.ArrayList;
import java.util.List;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.MinecraftForge;

@Mod(modid = "passablefoliage", name = "Passable Foliage", version = "0.2.2", acceptableRemoteVersions = "*")
public class PassableFoliage {
    public static SimpleNetworkWrapper network;
    public static boolean naturalOnly = true;
    public static boolean unknownLeavesNatural = true;
    public static boolean sneakToDescend = false;
    public static volatile List<LeafEntry> additionalLeaves = new ArrayList<LeafEntry>();

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        Configuration config = new Configuration(event.getSuggestedConfigurationFile());
        config.load();
        naturalOnly = config.getBoolean("naturalLeavesOnly", "general", true, "Only pass through naturally generated vanilla leaves.");
        unknownLeavesNatural = config.getBoolean("unknownModdedLeavesNatural", "general", true, "Treat modded leaves without known placement metadata as natural.");
        sneakToDescend = false; // Replaced by deliberate Sneak + Jump input.
        String[] entries = config.getStringList("additionalLeaves", "general", new String[0],
            "Additional passable foliage: modid:blockname:meta or modid:blockname:* (all metadata). Entries override automatic leaf detection.");
        List<LeafEntry> parsed = new ArrayList<LeafEntry>();
        for (String entry : entries) {
            LeafEntry rule = LeafEntry.parse(entry);
            if (rule != null) parsed.add(rule);
            else System.err.println("[PassableFoliage] Invalid additionalLeaves entry: " + entry);
        }
        additionalLeaves = parsed;
        if (config.hasChanged()) config.save();
        network = NetworkRegistry.INSTANCE.newSimpleChannel("passfoliage");
        network.registerMessage(FoliagePacket.Handler.class, FoliagePacket.class, 0, Side.SERVER);
        FMLCommonHandler.instance().bus().register(new FoliageServerEvents());
        if (FMLCommonHandler.instance().getSide().isClient()) FMLCommonHandler.instance().bus().register(new FoliageClientEvents());
    }
}
