package com.zenovka.passablefoliage;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;

public class FoliageClientEvents {
    private boolean held;

    @SubscribeEvent
    public void clientTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.START) return;
        Minecraft mc = Minecraft.getMinecraft();
        EntityPlayerSP player = mc.thePlayer;
        if (player == null || mc.currentScreen != null) { held = false; return; }
        boolean combo = mc.gameSettings.keyBindSneak.getIsKeyPressed()
            && mc.gameSettings.keyBindJump.getIsKeyPressed();
        if (!combo) { held = false; return; }
        if (FoliageDrop.active(player)) {
            mc.gameSettings.keyBindJump.pressed = false;
            if (player.movementInput != null) player.movementInput.jump = false;
            return;
        }
        if (!held && FoliageDrop.eligible(player)) {
            mc.gameSettings.keyBindJump.pressed = false;
            if (player.movementInput != null) player.movementInput.jump = false;
            FoliageDrop.start(player);
            PassableFoliage.network.sendToServer(new FoliagePacket());
        }
        held = true;
    }
}
