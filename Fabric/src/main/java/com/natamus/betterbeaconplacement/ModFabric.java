package com.natamus.betterbeaconplacement;

import com.natamus.betterbeaconplacement.events.BeaconEvent;
import com.natamus.betterbeaconplacement.util.Reference;
import com.natamus.collective.check.RegisterMod;
import com.natamus.collective.fabric.callbacks.CollectiveBlockEvents;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;

public class ModFabric implements ModInitializer {
	
	@Override
	public void onInitialize() {
		setGlobalConstants();
		ModCommon.init();

		loadEvents();

		RegisterMod.register(Reference.NAME, Reference.MOD_ID, Reference.VERSION, Reference.ACCEPTED_VERSIONS);
	}

	private void loadEvents() {
		CollectiveBlockEvents.BLOCK_RIGHT_CLICK.register((Level world, Player player, InteractionHand hand, BlockPos pos, BlockHitResult hitVec) -> {
			return BeaconEvent.onBeaconClick(world, player, hand, pos, hitVec);
		});

		PlayerBlockBreakEvents.AFTER.register((world, player, pos, state, entity) -> {
			BeaconEvent.onBlockBreak(world, player, pos, state, entity);
		});
	}

	private static void setGlobalConstants() {

	}
}
