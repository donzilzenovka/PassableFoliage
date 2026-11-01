package com.zenovka.passablefoliage;

import java.util.UUID;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;

public class FoliageServerEvents {
    @SubscribeEvent
    public void serverTick(TickEvent.ServerTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;
        MinecraftServer server = MinecraftServer.getServer();
        if (server == null) return;
        UUID id;
        while ((id = FoliagePacket.Handler.requests.poll()) != null) {
            for (Object obj : server.getConfigurationManager().playerEntityList) {
                EntityPlayerMP player = (EntityPlayerMP)obj;
                if (!id.equals(player.getUniqueID())) continue;
                if (FoliageDrop.eligible(player)) FoliageDrop.start(player);
                break;
            }
        }
    }
}
