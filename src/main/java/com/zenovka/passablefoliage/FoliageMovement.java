package com.zenovka.passablefoliage;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public final class FoliageMovement {
    @SubscribeEvent
    public void playerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;
        EntityPlayer player = event.player;
        if (player == null || player.boundingBox == null || player.isRiding()) return;
        double reduction = PassableFoliage.foliageSlowdown;
        if (reduction <= 0.0D || !insideFoliage(player)) return;
        double factor = 1.0D - reduction;
        player.motionX *= factor;
        player.motionZ *= factor;
    }

    private boolean insideFoliage(EntityPlayer player) {
        World world = player.worldObj;
        AxisAlignedBB box = player.boundingBox;
        // Small epsilon excludes blocks merely touching the player's feet or sides.
        int minX = (int)Math.floor(box.minX + 0.001D);
        int maxX = (int)Math.floor(box.maxX - 0.001D);
        int minY = (int)Math.floor(box.minY + 0.001D);
        int maxY = (int)Math.floor(box.maxY - 0.001D);
        int minZ = (int)Math.floor(box.minZ + 0.001D);
        int maxZ = (int)Math.floor(box.maxZ - 0.001D);
        for (int x = minX; x <= maxX; x++)
            for (int y = minY; y <= maxY; y++)
                for (int z = minZ; z <= maxZ; z++)
                    if (FoliageHooks.isEligible(world.getBlock(x, y, z), world, x, y, z))
                        return true;
        return false;
    }
}
