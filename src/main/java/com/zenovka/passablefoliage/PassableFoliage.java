package com.zenovka.passablefoliage;

import java.util.ArrayList;
import java.util.List;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.common.config.Configuration;

@Mod(modid = "passablefoliage", name = "Passable Foliage", version = "0.4.1", acceptableRemoteVersions = "*")
public class PassableFoliage {
    public static boolean naturalOnly = true;
    public static boolean unknownLeavesNatural = true;
    public static boolean sneakToDescend = false;
    public static double foliageSlowdown = 1.85D;
    public static volatile List<LeafEntry> additionalLeaves = new ArrayList<LeafEntry>();

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        Configuration config = new Configuration(event.getSuggestedConfigurationFile());
        config.load();
        naturalOnly = config.getBoolean("naturalLeavesOnly", "general", true, "Only pass through naturally generated vanilla leaves.");
        unknownLeavesNatural = config.getBoolean("unknownModdedLeavesNatural", "general", true, "Treat modded leaves without known placement metadata as natural.");
        sneakToDescend = config.getBoolean("sneakToDescend", "general", true, "Sneak to drop through eligible leaf blocks.");
        foliageSlowdown = config.getFloat("foliageSlowdown", "general", 1.85F, 0.0F, 2.0F,
            "Horizontal velocity resistance inside foliage (0 = off, 1 = full damping, above 1 = rebound, 2 = full reversal).");
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
        FMLCommonHandler.instance().bus().register(new FoliageMovement());
    }
}
