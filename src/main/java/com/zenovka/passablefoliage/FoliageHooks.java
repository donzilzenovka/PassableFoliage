package com.zenovka.passablefoliage;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.AxisAlignedBB;
import java.util.List;
import net.minecraft.world.World;

public final class FoliageHooks {
    private FoliageHooks() {}

    @SuppressWarnings({"rawtypes", "unchecked"})
    public static boolean handleCollision(Block block, World world, int x, int y, int z,
                                          AxisAlignedBB query, List boxes, Entity entity) {
        if (!(entity instanceof EntityPlayer) || world == null || block == null
            || entity.boundingBox == null || !isEligible(block, world, x, y, z)) return false;
        if (PassableFoliage.sneakToDescend && entity.isSneaking()) return true;
        double surface = y + 1.0D - PassableFoliage.foliageSinkDepth;
        if (entity.boundingBox.minY < surface - 0.01D) return true;
        AxisAlignedBB support = AxisAlignedBB.getBoundingBox(x, surface - 0.01D, z,
            x + 1.0D, surface, z + 1.0D);
        if (query != null && support.intersectsWith(query)) boxes.add(support);
        return true;
    }

    @SuppressWarnings("rawtypes")
    public static boolean omitCollision(Block block, World world, int x, int y, int z,
                                        AxisAlignedBB query, List boxes, Entity entity) {
        return handleCollision(block, world, x, y, z, query, boxes, entity);
    }

    public static boolean omitCollision(Block block, World world, int x, int y, int z, Entity entity) {
        if (!(entity instanceof EntityPlayer) || world == null || block == null || entity.boundingBox == null) return false;
        if (!isEligible(block, world, x, y, z)) return false;
        if (PassableFoliage.sneakToDescend && entity.isSneaking()) return true;
        return entity.boundingBox.minY < y + 0.99D;
    }

    public static boolean isEligible(Block block, World world, int x, int y, int z) {
        if (block == null || world == null) return false;
        int meta = world.getBlockMetadata(x, y, z);
        boolean explicit = false;
        for (LeafEntry entry : PassableFoliage.additionalLeaves) {
            if (entry.matches(block, meta)) { explicit = true; break; }
        }
        if (!explicit && !block.isLeaves(world, x, y, z)) return false;
        if (!explicit && PassableFoliage.naturalOnly) {
            if (block == Blocks.leaves || block == Blocks.leaves2) {
                if ((meta & 4) != 0) return false;
            } else if (!PassableFoliage.unknownLeavesNatural) {
                return false;
            }
        }
        return true;
    }
}
