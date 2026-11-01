# PassableFoliage

Standalone Minecraft Forge 1.7.10 foliage collision mod, inspired by the canopy movement of TreeClimbing. No DragonAPI dependency.

Players may enter leaf blocks from the side or below; leaves still support players standing on their upper surface. Other entities retain normal leaf collision.

## Configuration

Generated at `config/passablefoliage.cfg`:
- `naturalLeavesOnly=true` — vanilla player-placed leaves stay solid.
- `unknownModdedLeavesNatural=true` — modded leaves detected through Forge `isLeaves` are eligible.
- `sneakToDescend=false` — optional canopy drop-through.

## Build

Uses the EntityGovernor Gradle structure: RetroFuturaGradle 1.4.9, Gradle 8.14.3, Java 8 toolchain.

`gradlew.bat build` (Windows), or `./gradlew build` (Linux).

## Testing

**Unverified prototype:** Test in a disposable world before adding to a large modpack. Check horizontal traversal, jumping upward, standing on a canopy, player-placed vanilla leaves, modded leaves, multiplayer, and interactions with other coremods. The narrow ASM patch targets the vanilla Block collision method; mods overriding collision behavior may need additional support.
