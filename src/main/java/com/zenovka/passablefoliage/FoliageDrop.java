package com.zenovka.passablefoliage;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public final class FoliageDrop {
    private static final Map<UUID, Long> until = new ConcurrentHashMap<UUID, Long>();
    private FoliageDrop() {}

    public static boolean eligible(EntityPlayer player) {
        if (player == null || player.worldObj == null || !player.onGround || player.isRiding()) return false;
        World w = player.worldObj;
        int x = (int)Math.floor(player.posX);
        int y = (int)Math.floor(player.boundingBox.minY - 0.08D);
        int z = (int)Math.floor(player.posZ);
        Block block = w.getBlock(x, y, z);
        return FoliageHooks.isEligible(block, w, x, y, z);
    }

    public static void start(EntityPlayer player) {
        until.put(player.getUniqueID(), player.worldObj.getTotalWorldTime() + 12L);
        player.motionY = -0.18D;
        player.onGround = false;
    }

    public static boolean active(EntityPlayer player) {
        Long end = until.get(player.getUniqueID());
        if (end == null) return false;
        if (player.worldObj.getTotalWorldTime() <= end.longValue()) return true;
        until.remove(player.getUniqueID());
        return false;
    }
}
