package com.natamus.betterbeaconplacement.forge.events;

import com.natamus.betterbeaconplacement.events.BeaconEvent;
import com.natamus.collective.functions.WorldFunctions;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ForgeBeaconEvent {
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
