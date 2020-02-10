package growthcraft.bees.common;

import growthcraft.bees.common.worldgen.BeeHiveWorldGen;
import growthcraft.bees.shared.config.GrowthcraftBeesConfig;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class CommonProxy {
    // REVISE_TEAM

    public void init() {
        if (GrowthcraftBeesConfig.worldgenBeeHive) {
            GameRegistry.registerWorldGenerator(new BeeHiveWorldGen(), 0);
        }
    }

    public void preInit() {
        registerTileEntities();
    }

    public void postInit() {

    }

    public void registerTileEntities() {
        Init.registerTileEntities();
    }

    public void postRegisterItems() {
    }

}
