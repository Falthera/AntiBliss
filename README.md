# IHateTheBlissPlugin

A Fabric mod (Minecraft 26.1.2) demonstrating BlissGems vulnerabilities for educational purposes.

## Command Usage

`/antibliss <exploit> <on/off>`

### Available Exploits

| Command | Advantage |
|---------|-----------|
| `pdc_bypass` | Forge Astra gem via PDC manipulation |
| `forge_upgrader` | Gem Upgrader (CMD 3001) - upgrades any gem to T2 |
| `forge_energy` | Energy Bottle (CMD 4001) - 64 stack |
| `forge_repair` | Repair Kit (CMD 4003) - rare survival item |
| `forge_fragment` | Gem Fragment (CMD 4004) - crafting material |
| `forge_trader` | Gem Trader (CMD 4002) |
| `forge_revive` | Revive Beacon (CMD 4005) - resurrects players |
| `forge_all_gems` | All 8 gems (astra/fire/flux/life/puff/speed/strength/wealth) |
| `dup_all_gems` | Multiply gem stacks by 64x |
| `gem_unlock` | Removes protection to drop/sell gems |
| `energy_overflow` | Set energy to 999 (bypasses max 10) |

### Recipe Exploit (No Command)

Craft Repair Kit using vanilla prismarine shards:
```
F A F
N T N  
F A F
```
F = Prismarine Shard (vanilla), A = Anvil, N = Netherite Ingot, T = Smithing Template

**Why it works:** BlissGems RecipeManager.java:132 checks `Material.PRISMARINE_SHARD` without PDC validation!

## Building

```bash
./gradlew build
```

JAR output: `build/libs/IHateTheBlissPlugin.jar`