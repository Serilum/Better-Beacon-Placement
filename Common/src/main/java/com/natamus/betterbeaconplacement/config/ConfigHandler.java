package com.natamus.betterbeaconplacement.config;

import com.natamus.collective.config.DuskConfig;
import com.natamus.betterbeaconplacement.util.Reference;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class ConfigHandler extends DuskConfig {
	public static HashMap<String, List<String>> configMetaData = new HashMap<String, List<String>>();

	@Entry public static boolean breakBeaconBaseBlocks = true;
	@Entry public static boolean dropReplacedBlockTopBeacon = true;

	public static void initConfig() {
		configMetaData.put("breakBeaconBaseBlocks", Arrays.asList(
			"If enabled, drops all beacon base blocks when the beacon itself is broken."
		));
		configMetaData.put("dropReplacedBlockTopBeacon", Arrays.asList(
			"If enabled, when a mineral block replaces a normal block that block is dropped on top of the beacon."
		));

		DuskConfig.init(Reference.NAME, Reference.MOD_ID, ConfigHandler.class);
	}
}