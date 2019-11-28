package growthcraft.cellar.common.block;

import growthcraft.cellar.shared.Reference;
import growthcraft.cellar.shared.init.GrowthcraftCellarBlocks;
import growthcraft.core.shared.block.GrowthcraftBlockLeaves;

public class BlockCorkLeaves extends GrowthcraftBlockLeaves {
    public static final int LEAVES_COLOR = 0x013220;

    public BlockCorkLeaves(String unlocalizedName) {
        super(Reference.MODID, unlocalizedName, GrowthcraftCellarBlocks.blockCorkSapling);
    }

}
