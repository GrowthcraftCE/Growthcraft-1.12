package growthcraft.apples.common.worldgen;

import growthcraft.apples.shared.init.GrowthcraftApplesBlocks;
import growthcraft.core.shared.worldgen.GrowthcraftTreeWorldGen;
import net.minecraft.block.Block;

public class WorldGenAppleTree extends GrowthcraftTreeWorldGen {

    private static int minTreeHeight = 5;
    private static int maxTreeHeight = 6;

    private static Block blockLog = GrowthcraftApplesBlocks.blockAppleLog.getBlock();
    private static Block blockLeaves = GrowthcraftApplesBlocks.blockAppleLeaves.getBlock();

    public WorldGenAppleTree(boolean notify) {
        super(blockLog, blockLeaves, minTreeHeight, maxTreeHeight, notify);
    }

}
