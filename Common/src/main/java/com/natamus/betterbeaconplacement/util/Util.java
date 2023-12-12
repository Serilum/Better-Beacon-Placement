package com.natamus.betterbeaconplacement.util;

import com.natamus.collective.functions.BlockFunctions;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Iterator;

public class Util {
	public static BlockPos getNextLocation(Level world, BlockPos beaconpos) {
		int x = beaconpos.getX();
		int y = beaconpos.getY();
		int z = beaconpos.getZ();

		for (int n = 1; n <= 4; n++) {
			Iterator<BlockPos> layer = BlockPos.betweenClosedStream(x-n, y-n, z-n, x+n, y-n, z+n).iterator();
			BlockPos result = checkIterator(world, layer);
			if (result != null) {
				if (BlockFunctions.isSpecificBlock(Blocks.BEDROCK, world, result)) {
					return null;
				}
				return result;
			}
		}

		return null;
	}

	public static void processAllBaseBlocks(Level world, BlockPos beaconpos, boolean iscreative) {
		int x = beaconpos.getX();
		int y = beaconpos.getY();
		int z = beaconpos.getZ();

		for (int n = 1; n <= 4; n++) {
			breakBase(world, beaconpos, BlockPos.betweenClosedStream(x-n, y-n, z-n, x+n, y-n, z+n).iterator(), iscreative);
		}
	}

	public static void breakBase(Level world, BlockPos beaconpos, Iterator<BlockPos> it, boolean iscreative) {
		while (it.hasNext()) {
			BlockPos np = it.next();
			BlockState blockState = world.getBlockState(np);
			if (blockState.is(BlockTags.BEACON_BASE_BLOCKS)) {
				world.setBlockAndUpdate(np, Blocks.AIR.defaultBlockState());
				if (!iscreative) {
					ItemEntity ei = new ItemEntity(world, beaconpos.getX(), beaconpos.getY()+2, beaconpos.getZ(), new ItemStack(blockState.getBlock(), 1));
					world.addFreshEntity(ei);
				}
			}
		}
	}

	private static BlockPos checkIterator(Level world, Iterator<BlockPos> it) {
		while (it.hasNext()) {
			BlockPos np = it.next();
			BlockState blockState = world.getBlockState(np);
			if (!blockState.is(BlockTags.BEACON_BASE_BLOCKS)) {
				return np.immutable();
			}
		}
		return null;
	}
}