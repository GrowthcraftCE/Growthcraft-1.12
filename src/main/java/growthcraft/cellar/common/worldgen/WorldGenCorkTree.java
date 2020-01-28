package growthcraft.cellar.common.worldgen;

import growthcraft.cellar.shared.init.GrowthcraftCellarBlocks;
import growthcraft.core.shared.worldgen.GrowthcraftTreeWorldGen;
import net.minecraft.block.Block;

public class WorldGenCorkTree extends GrowthcraftTreeWorldGen {

    private static int minTreeHeight = 5;
    private static int maxTreeHeight = 7;

    private static Block blockLog = GrowthcraftCellarBlocks.blockCorkLog.getBlock();
    private static Block blockLeaves = GrowthcraftCellarBlocks.blockCorkLeaves.getBlock();

    public WorldGenCorkTree(boolean notify) {
        super(blockLog, blockLeaves, minTreeHeight, maxTreeHeight, notify);
    }

}
