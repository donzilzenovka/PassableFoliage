# PassableFoliage

Standalone Minecraft Forge 1.7.10 foliage collision mod, inspired by the canopy movement of TreeClimbing. No DragonAPI dependency.

Players may enter leaf blocks from the side or below; leaves still support players standing on their upper surface. Other entities retain normal leaf collision.

## Configuration

Generated at `config/passablefoliage.cfg`:
- `naturalLeavesOnly=true` — vanilla player-placed leaves stay solid.
- `unknownModdedLeavesNatural=true` — modded leaves detected through Forge `isLeaves` are eligible.
- Sneak + Jump while standing on eligible leaves triggers a short downward drop. The server validates requests for SMP.
- `additionalLeaves` — extra blocks to treat as passable foliage, even if Forge does not report them as leaves.

Example Forge configuration:

```properties
S:additionalLeaves <
    BiomesOPlenty:leaves:*
    examplemod:decorative_leaves:2
    examplemod:decorative_leaves:5
>
```

Each entry uses `modid:blockname:meta`. Metadata may be 0–15 or `*` for all variants. These are example names; use actual registered block IDs. Explicit entries override natural-leaf restrictions, including vanilla placed-leaf metadata. Restart Minecraft after changing the list.

## Build

Uses the EntityGovernor Gradle structure: RetroFuturaGradle 1.4.9, Gradle 8.14.3, Java 8 toolchain.

`gradlew.bat build` (Windows), or `./gradlew build` (Linux).

## Testing

**Unverified prototype:** Test in a disposable world before adding to a large modpack. Check horizontal traversal, jumping upward, standing on a canopy, player-placed vanilla leaves, modded leaves, multiplayer, and interactions with other coremods. The narrow ASM patch targets the vanilla Block collision method; mods overriding collision behavior may need additional support.
