package growthcraft.core.common;

import growthcraft.core.shared.Reference;
import growthcraft.core.common.tileentity.TileEntityRopeKnot;
import growthcraft.core.common.worldgen.BlockSaltGeneration;
import growthcraft.core.shared.config.GrowthcraftCoreConfig;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class CommonProxy {

	public void preInit() {
        registerTitleEntities();
	}
	
    public void init() {
        if (GrowthcraftCoreConfig.worldgenBlockSaltOre) {
            GameRegistry.registerWorldGenerator(new BlockSaltGeneration(), 0);
        }
    }
    
	public void postInit() {

	}

	@SuppressWarnings("deprecation")
    public void registerTitleEntities() {
        GameRegistry.registerTileEntity(TileEntityRopeKnot.class, Reference.MODID + ":rope_knot");
    }
    
    public void postRegisterItems() {
    	
    }


}
