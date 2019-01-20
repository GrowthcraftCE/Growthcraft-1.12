package growthcraft.core.common;

import growthcraft.core.shared.Reference;
import growthcraft.core.common.worldgen.BlockSaltGeneration;
import growthcraft.core.shared.config.GrowthcraftCoreConfig;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class CommonProxy {

	public void preInit() {
        registerTitleEntities();
	}
	
    public void init() {
        if (GrowthcraftCoreConfig.blockSaltOreWorldGen) {
            GameRegistry.registerWorldGenerator(new BlockSaltGeneration(), 0);
        }
    }
    
	public void postInit() {

	}

	@SuppressWarnings("deprecation")
    public void registerTitleEntities() {
    }
    
    public void postRegisterItems() {
    	
    }


}
