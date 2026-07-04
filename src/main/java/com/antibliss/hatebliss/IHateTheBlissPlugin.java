package com.antibliss.hatebliss;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import com.antibliss.hatebliss.exploit.*;

public class IHateTheBlissPlugin implements ModInitializer {

    private static boolean pdcBypassEnabled = false;
    private static boolean itemDupEnabled = false;
    private static boolean abilityAbuseEnabled = false;
    private static boolean energyOverflowEnabled = false;
    private static boolean gemUnlockEnabled = false;
    private static boolean forgeUpgraderEnabled = false;
    private static boolean forgeEnergyEnabled = false;
    private static boolean forgeRepairEnabled = false;
    private static boolean forgeFragmentEnabled = false;
    private static boolean forgeTraderEnabled = false;
    private static boolean forgeReviveEnabled = false;

    @Override
    public void onInitialize() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registry, environment) -> {
            dispatcher.register(CommandManager.literal("antibliss")
                .then(CommandManager.argument("exploit", StringArgumentType.word())
                    .suggests((context, builder) -> builder
                        .suggest("pdc_bypass")
                        .suggest("item_dup")
                        .suggest("ability_abuse")
                        .suggest("energy_overflow")
                        .suggest("gem_unlock")
                        .suggest("forge_upgrader")
                        .suggest("forge_energy")
                        .suggest("forge_repair")
                        .suggest("forge_fragment")
                        .suggest("forge_trader")
                        .suggest("forge_revive")
                        .suggest("forge_all_gems")
                        .suggest("dup_all_gems")
                        .suggest("giveitem")
                        .suggest("unlimited_all")
                        .buildFuture())
                    .then(CommandManager.argument("state", StringArgumentType.word())
                        .suggests((context, builder) -> builder
                            .suggest("on")
                            .suggest("off")
                            .buildFuture())
                        .executes(context -> toggleExploit(context))));
        });
    }

    private static int toggleExploit(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        String exploit = context.getArgument("exploit", String.class);
        String state = context.getArgument("state", String.class);
        ServerPlayerEntity player = context.getSource().getPlayerOrThrow();
        boolean enable = state.equalsIgnoreCase("on");

        switch (exploit.toLowerCase()) {
            case "pdc_bypass":
                pdcBypassEnabled = enable;
                player.sendMessage(Text.literal(enable ? "§aPDC Bypass: ENABLED" : "§cPDC Bypass: DISABLED"));
                if (enable) PdcBypassExploit.giveGem(player, "astra", 1);
                return 1;
            case "item_dup":
                itemDupEnabled = enable;
                player.sendMessage(Text.literal(enable ? "§aItem Duplication: ENABLED" : "§cItem Duplication: DISABLED"));
                if (enable) ItemDuplicationExploit.duplicateInventoryGems(player);
                return 1;
            case "ability_abuse":
                abilityAbuseEnabled = enable;
                player.sendMessage(Text.literal(enable ? "§aAbility Abuse: ENABLED" : "§cAbility Abuse: DISABLED"));
                if (enable) AbilityAbuseExploit.triggerAbilities(player);
                return 1;
            case "energy_overflow":
                energyOverflowEnabled = enable;
                player.sendMessage(Text.literal(enable ? "§aEnergy Overflow: ENABLED (999 energy)" : "§cEnergy Overflow: DISABLED"));
                if (enable) EnergyExploit.setMaxEnergy(player, 999);
                return 1;
            case "gem_unlock":
                gemUnlockEnabled = enable;
                player.sendMessage(Text.literal(enable ? "§aGem Unlock: ENABLED (removing lock tags)" : "§cGem Unlock: DISABLED"));
                if (enable) GemUnlockExploit.unlockAllGems(player);
                return 1;
            case "forge_upgrader":
                forgeUpgraderEnabled = enable;
                player.sendMessage(Text.literal(enable ? "§aForged: Gem Upgrader (CMD 3001)" : "§cForge: Disabled"));
                if (enable) ItemForgeryExploit.giveUpgrader(player);
                return 1;
            case "forge_energy":
                forgeEnergyEnabled = enable;
                player.sendMessage(Text.literal(enable ? "§aForged: Energy Bottle (CMD 4001)" : "§cForge: Disabled"));
                if (enable) ItemForgeryExploit.giveEnergyBottle(player);
                return 1;
            case "forge_repair":
                forgeRepairEnabled = enable;
                player.sendMessage(Text.literal(enable ? "§aForged: Repair Kit (CMD 4003)" : "§cForge: Disabled"));
                if (enable) ItemForgeryExploit.giveRepairKit(player);
                return 1;
            case "forge_fragment":
                forgeFragmentEnabled = enable;
                player.sendMessage(Text.literal(enable ? "§aForged: Gem Fragment (CMD 4004)" : "§cForge: Disabled"));
                if (enable) ItemForgeryExploit.giveGemFragment(player);
                return 1;
            case "forge_trader":
                forgeTraderEnabled = enable;
                player.sendMessage(Text.literal(enable ? "§aForged: Gem Trader (CMD 4002)" : "§cForge: Disabled"));
                if (enable) ItemForgeryExploit.giveGemTrader(player);
                return 1;
            case "forge_revive":
                forgeReviveEnabled = enable;
                player.sendMessage(Text.literal(enable ? "§aForged: Revive Beacon (CMD 4005)" : "§cForge: Disabled"));
                if (enable) ItemForgeryExploit.giveReviveBeacon(player);
                return 1;
            case "forge_all_gems":
                ItemForgeryExploit.giveAllGems(player);
                player.sendMessage(Text.literal("§aForged ALL gem types Tier 1"));
                return 1;
            case "dup_all_gems":
                ItemDuplicationExploit.duplicateAllGems(player, 64);
                player.sendMessage(Text.literal("§cDuplicated ALL gems x64"));
                return 1;
            case "giveitem":
                GiveItemExploit.unlimitedGiveitem(player);
                return 1;
            case "unlimited_all":
                GiveItemExploit.unlimitedGiveitem(player);
                ItemForgeryExploit.giveAllGems(player);
                EnergyExploit.setMaxEnergy(player, 999);
                player.sendMessage(Text.literal("§cFull exploit combo: giveitem bypass + all gems + energy overflow"));
                return 1;
            default:
                player.sendMessage(Text.literal("§cUnknown exploit"));
                return 0;
        }
    }
}