package com.zenovka.passablefoliage;

import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

/** Explicit block/metadata or tile-entity-class foliage match. */
public final class LeafEntry {
    private final String blockName;
    private final int metadata;
    private final String tileClassName;

    private LeafEntry(String blockName, int metadata, String tileClassName) {
        this.blockName = blockName;
        this.metadata = metadata;
        this.tileClassName = tileClassName;
    }

    public static LeafEntry parse(String entry) {
        if (entry == null) return null;
        String trimmed = entry.trim();
        if (trimmed.startsWith("te:")) {
            String className = trimmed.substring(3).trim();
            if (className.length() == 0 || className.indexOf('.') < 0 ||
                className.indexOf(' ') >= 0 || className.indexOf('@') >= 0) return null;
            return new LeafEntry(null, -1, className);
        }
        int lastColon = trimmed.lastIndexOf(':');
        int firstColon = trimmed.indexOf(':');
        if (firstColon <= 0 || lastColon <= firstColon + 1 || lastColon == trimmed.length() - 1) return null;
        String blockName = trimmed.substring(0, lastColon);
        String meta = trimmed.substring(lastColon + 1);
        if (blockName.indexOf(' ') >= 0) return null;
        if ("*".equals(meta)) return new LeafEntry(blockName, -1, null);
        try {
            int value = Integer.parseInt(meta);
            return value >= 0 && value <= 15 ? new LeafEntry(blockName, value, null) : null;
        } catch (NumberFormatException ignored) {
            return null;
        }
    }

    public boolean matches(Block block, int meta, World world, int x, int y, int z) {
        if (tileClassName != null) {
            TileEntity tile = world.getTileEntity(x, y, z);
            return tile != null && tileClassName.equals(tile.getClass().getName());
        }
        Object name = Block.blockRegistry.getNameForObject(block);
        return name != null && blockName.equals(name.toString()) && (metadata == -1 || metadata == meta);
    }
}
