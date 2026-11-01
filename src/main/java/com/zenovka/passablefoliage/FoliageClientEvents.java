package com.zenovka.passablefoliage;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.InputUpdateEvent;

/** Intercept movement input before EntityPlayerSP applies vanilla jump velocity. */
public class FoliageClientEvents {
    private boolean held;

    @SubscribeEvent
    public void inputUpdate(InputUpdateEvent event) {
        Minecraft mc = Minecraft.getMinecraft();
        EntityPlayer player = mc.thePlayer;
        if (player == null || event.entityPlayer != player || mc.currentScreen != null) {
            held = false;
            return;
        }
        boolean combo = mc.gameSettings.keyBindSneak.getIsKeyPressed()
            && mc.gameSettings.keyBindJump.getIsKeyPressed();
        if (!combo) {
            held = false;
            return;
        }
        // Suppress vanilla jump on eligible foliage even while the drop is in progress.
        if (FoliageDrop.active(player)) {
            event.movementInput.jump = false;
            return;
        }
        if (!held && FoliageDrop.eligible(player)) {
            event.movementInput.jump = false;
            FoliageDrop.start(player);
            PassableFoliage.network.sendToServer(new FoliagePacket());
        }
        held = true;
    }
}
