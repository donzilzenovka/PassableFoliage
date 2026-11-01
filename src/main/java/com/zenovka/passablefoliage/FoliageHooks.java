package com.zenovka.passablefoliage;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public final class FoliageHooks {
    private FoliageHooks() {}

    // Called only by the patched Block collision method.
    public static boolean omitCollision(Block block, World world, int x, int y, int z, Entity entity) {
        if (!(entity instanceof EntityPlayer) || world == null || block == null) return false;
        if (!block.isLeaves(world, x, y, z)) return false;
        if (PassableFoliage.naturalOnly) {
            if (block == Blocks.leaves || block == Blocks.leaves2) {
                if ((world.getBlockMetadata(x, y, z) & 4) != 0) return false;
            } else if (!PassableFoliage.unknownLeavesNatural) {
                return false;
            }
        }
        if (PassableFoliage.sneakToDescend && entity.isSneaking()) return true;
        // A player whose feet are at canopy height must retain the full collision box.
        // Below the top surface, horizontal and upward movement may pass through.
        return entity.boundingBox.minY < y + 0.99D;
    }
}
