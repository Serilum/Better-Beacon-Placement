package com.natamus.betterbeaconplacement.forge.events;

import com.natamus.betterbeaconplacement.events.BeaconEvent;
import com.natamus.collective.functions.WorldFunctions;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.bus.BusGroup;
import net.minecraftforge.eventbus.api.listener.SubscribeEvent;

import java.lang.invoke.MethodHandles;

public class ForgeBeaconEvent {
	public static void registerEventsInBus() {
		BusGroup.DEFAULT.register(MethodHandles.lookup(), ForgeBeaconEvent.class);
	}

	@SubscribeEvent
	public static boolean onBeaconClick(PlayerInteractEvent.RightClickBlock e) {
		if (!BeaconEvent.onBeaconClick(e.getLevel(), e.getEntity(), e.getHand(), e.getPos(), e.getHitVec())) {
			return true;
		}
		return false;
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
