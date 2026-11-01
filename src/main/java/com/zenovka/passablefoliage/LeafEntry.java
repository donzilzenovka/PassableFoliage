package com.zenovka.passablefoliage;

import net.minecraft.block.Block;

public final class LeafEntry {
    private final String blockName;
    private final int metadata;

    private LeafEntry(String blockName, int metadata) {
        this.blockName = blockName;
        this.metadata = metadata;
    }

    public static LeafEntry parse(String entry) {
        if (entry == null) return null;
        String trimmed = entry.trim();
        int lastColon = trimmed.lastIndexOf(':');
        int firstColon = trimmed.indexOf(':');
        if (firstColon <= 0 || lastColon <= firstColon + 1 || lastColon == trimmed.length() - 1) return null;
        String blockName = trimmed.substring(0, lastColon);
        String meta = trimmed.substring(lastColon + 1);
        if (blockName.indexOf(' ') >= 0) return null;
        if ("*".equals(meta)) return new LeafEntry(blockName, -1);
        try {
            int value = Integer.parseInt(meta);
            return value >= 0 && value <= 15 ? new LeafEntry(blockName, value) : null;
        } catch (NumberFormatException ignored) {
            return null;
        }
    }

    public boolean matches(Block block, int meta) {
        Object name = Block.blockRegistry.getNameForObject(block);
        return name != null && blockName.equals(name.toString()) && (metadata == -1 || metadata == meta);
    }
}
