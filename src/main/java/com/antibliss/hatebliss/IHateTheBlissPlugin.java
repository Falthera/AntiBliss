package com.antibliss.hatebliss;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.network.chat.Component;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.Commands;
import net.minecraft.commands.CommandSourceStack;
import com.antibliss.hatebliss.exploit.*;

public class IHateTheBlissPlugin implements ModInitializer {

    @Override
    public void onInitialize() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            dispatcher.register(Commands.literal("antibliss")
                .then(Commands.argument("exploit", StringArgumentType.word())
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
                        .then(Commands.argument("state", StringArgumentType.word())
                        .suggests((context, builder) -> builder
                            .suggest("on")
                            .suggest("off")
                            .buildFuture())
                        .executes(context -> toggleExploit(context)))));
        });
    }

    private static int toggleExploit(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        String exploit = context.getArgument("exploit", String.class);
        String state = context.getArgument("state", String.class);
        ServerPlayer player = context.getSource().getPlayerOrThrow();
        boolean enable = state.equalsIgnoreCase("on");

        switch (exploit.toLowerCase()) {
            case "pdc_bypass":
                player.sendMessage(Component.literal(enable ? "§aPDC Bypass: ENABLED" : "§cPDC Bypass: DISABLED"));
                if (enable) PdcBypassExploit.giveGem(player, "astra", 1);
                return 1;
            case "item_dup":
                player.sendMessage(Component.literal(enable ? "§aItem Duplication: ENABLED" : "§cItem Duplication: DISABLED"));
                if (enable) ItemDuplicationExploit.duplicateInventoryGems(player);
                return 1;
            case "ability_abuse":
                player.sendMessage(Component.literal(enable ? "§aAbility Abuse: ENABLED" : "§cAbility Abuse: DISABLED"));
                if (enable) AbilityAbuseExploit.triggerAbilities(player);
                return 1;
            case "energy_overflow":
                player.sendMessage(Component.literal(enable ? "§aEnergy Overflow: ENABLED (999 energy)" : "§cEnergy Overflow: DISABLED"));
                if (enable) EnergyExploit.setMaxEnergy(player, 999);
                return 1;
            case "gem_unlock":
                player.sendMessage(Component.literal(enable ? "§aGem Unlock: ENABLED (removing lock tags)" : "§cGem Unlock: DISABLED"));
                if (enable) GemUnlockExploit.unlockAllGems(player);
                return 1;
            case "forge_upgrader":
                player.sendMessage(Component.literal(enable ? "§aForged: Gem Upgrader (CMD 3001)" : "§cForge: Disabled"));
                if (enable) ItemForgeryExploit.giveUpgrader(player);
                return 1;
            case "forge_energy":
                player.sendMessage(Component.literal(enable ? "§aForged: Energy Bottle (CMD 4001)" : "§cForge: Disabled"));
                if (enable) ItemForgeryExploit.giveEnergyBottle(player);
                return 1;
            case "forge_repair":
                player.sendMessage(Component.literal(enable ? "§aForged: Repair Kit (CMD 4003)" : "§cForge: Disabled"));
                if (enable) ItemForgeryExploit.giveRepairKit(player);
                return 1;
            case "forge_fragment":
                player.sendMessage(Component.literal(enable ? "§aForged: Gem Fragment (CMD 4004)" : "§cForge: Disabled"));
                if (enable) ItemForgeryExploit.giveGemFragment(player);
                return 1;
            case "forge_trader":
                player.sendMessage(Component.literal(enable ? "§aForged: Gem Trader (CMD 4002)" : "§cForge: Disabled"));
                if (enable) ItemForgeryExploit.giveGemTrader(player);
                return 1;
            case "forge_revive":
                player.sendMessage(Component.literal(enable ? "§aForged: Revive Beacon (CMD 4005)" : "§cForge: Disabled"));
                if (enable) ItemForgeryExploit.giveReviveBeacon(player);
                return 1;
            case "forge_all_gems":
                ItemForgeryExploit.giveAllGems(player);
                player.sendMessage(Component.literal("§aForged ALL gem types Tier 1"));
                return 1;
            case "dup_all_gems":
                ItemDuplicationExploit.duplicateAllGems(player, 64);
                player.sendMessage(Component.literal("§cDuplicated ALL gems x64"));
                return 1;
            case "giveitem":
                GiveItemExploit.unlimitedGiveitem(player);
                return 1;
            case "unlimited_all":
                GiveItemExploit.unlimitedGiveitem(player);
                ItemForgeryExploit.giveAllGems(player);
                EnergyExploit.setMaxEnergy(player, 999);
                player.sendMessage(Component.literal("§cFull exploit combo: giveitem bypass + all gems + energy overflow"));
                return 1;
            default:
                player.sendMessage(Component.literal("§cUnknown exploit"));
                return 0;
        }
    }
}
