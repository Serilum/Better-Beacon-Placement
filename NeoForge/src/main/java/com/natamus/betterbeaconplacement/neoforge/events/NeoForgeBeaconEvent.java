package com.natamus.betterbeaconplacement.neoforge.events;

import com.natamus.betterbeaconplacement.events.BeaconEvent;
import com.natamus.collective.functions.WorldFunctions;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.level.BlockEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;

@EventBusSubscriber
public class NeoForgeBeaconEvent {
	@SubscribeEvent
	public static void onBeaconClick(PlayerInteractEvent.RightClickBlock e) {
		if (!BeaconEvent.onBeaconClick(e.getLevel(), e.getEntity(), e.getHand(), e.getPos(), e.getHitVec())) {
			e.setCanceled(true);
		}
	}
	
	@SubscribeEvent
	public static void onBlockBreak(BlockEvent.BreakEvent e) {
		Level level = WorldFunctions.getWorldIfInstanceOfAndNotRemote(e.getLevel());
		if (level == null) {
			return;
		}

		BeaconEvent.onBlockBreak(level, e.getPlayer(), e.getPos(), e.getState(), null);
	}
}
