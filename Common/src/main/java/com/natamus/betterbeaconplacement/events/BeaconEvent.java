package com.natamus.betterbeaconplacement.events;

import com.natamus.betterbeaconplacement.config.ConfigHandler;
import com.natamus.betterbeaconplacement.util.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class BeaconEvent {
	public static boolean onBeaconClick(Level world, Player player, InteractionHand hand, BlockPos cpos, BlockHitResult hitVec) {
		if (world.isClientSide) {
			return true;
		}
		
		if (!world.getBlockState(cpos).getBlock().equals(Blocks.BEACON)) {
			return true;
		}

		ItemStack handstack = player.getItemInHand(hand);
		if (!Block.byItem(handstack.getItem()).defaultBlockState().is(BlockTags.BEACON_BASE_BLOCKS)) {
			return true;
		}
		
		boolean set = false;
		while (handstack.getCount() > 0) {
			BlockPos nextpos = Util.getNextLocation(world, cpos);
			if (nextpos == null) {
				return !set;
			}
			
			Block block = world.getBlockState(nextpos).getBlock();
			if (ConfigHandler.dropReplacedBlockTopBeacon) {
				if (!block.equals(Blocks.AIR) && !player.isCreative()) {
					ItemEntity ei = new ItemEntity(world, cpos.getX(), cpos.getY()+2, cpos.getZ(), new ItemStack(block, 1));
					world.addFreshEntity(ei);
				}
			}
			
			world.setBlockAndUpdate(nextpos, Block.byItem(handstack.getItem()).defaultBlockState());

			if (!player.isCreative()) {
				handstack.shrink(1);
			}
			
			set = true;
			if (!player.isCrouching()) {
				break;
			}
		}
		
		return false;
	}
	
	public static void onBlockBreak(Level world, Player player, BlockPos bpos, BlockState state, BlockEntity blockEntity) {
		if (world.isClientSide) {
			return;
		}
		
		if (!ConfigHandler.breakBeaconBaseBlocks) {
			return;
		}
		
		if (!state.getBlock().equals(Blocks.BEACON)) {
			return;
		}
		
		Util.processAllBaseBlocks(world, bpos, player.isCreative());
	}
}