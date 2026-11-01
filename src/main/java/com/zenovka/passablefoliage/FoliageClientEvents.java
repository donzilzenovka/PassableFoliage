package com.zenovka.passablefoliage;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;

public class FoliageClientEvents {
    private boolean held;
    @SubscribeEvent
    public void clientTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;
        Minecraft mc = Minecraft.getMinecraft();
        EntityPlayer player = mc.thePlayer;
        if (player == null || mc.currentScreen != null) { held = false; return; }
        boolean combo = mc.gameSettings.keyBindSneak.getIsKeyPressed() && mc.gameSettings.keyBindJump.getIsKeyPressed();
        if (combo && !held && FoliageDrop.eligible(player)) {
            FoliageDrop.start(player);
            PassableFoliage.network.sendToServer(new FoliagePacket());
        }
        held = combo;
    }
}
