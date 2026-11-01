package com.zenovka.passablefoliage;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.common.config.Configuration;

@Mod(modid = "passablefoliage", name = "Passable Foliage", version = "0.1.0", acceptableRemoteVersions = "*")
public class PassableFoliage {
    public static boolean naturalOnly = true;
    public static boolean unknownLeavesNatural = true;
    public static boolean sneakToDescend = false;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        Configuration config = new Configuration(event.getSuggestedConfigurationFile());
        config.load();
        naturalOnly = config.getBoolean("naturalLeavesOnly", "general", true, "Only pass through naturally generated vanilla leaves.");
        unknownLeavesNatural = config.getBoolean("unknownModdedLeavesNatural", "general", true, "Treat modded leaves without known placement metadata as natural.");
        sneakToDescend = config.getBoolean("sneakToDescend", "general", false, "Sneaking permits descending through the canopy.");
        if (config.hasChanged()) config.save();
    }
}
