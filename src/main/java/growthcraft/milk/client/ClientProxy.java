package growthcraft.milk.client;

import growthcraft.milk.common.CommonProxy;
import growthcraft.milk.common.tileentity.TileEntityPancheon;
import growthcraft.milk.shared.Reference;
import growthcraft.milk.shared.init.GrowthcraftMilkBlocks;
import growthcraft.milk.shared.init.GrowthcraftMilkFluids;
import growthcraft.milk.shared.init.GrowthcraftMilkItems;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ClientProxy extends CommonProxy {

    @Override
    public void init() {
    	super.init();
    	GrowthcraftMilkFluids.registerColorHandlers();
    }

    @Override
    public void registerRenders() {
        GrowthcraftMilkBlocks.registerRenders();
        GrowthcraftMilkFluids.registerRenders();
        GrowthcraftMilkItems.registerRenders();
    }

    @Override
    public void registerModelBakeryVariants() {
    	GrowthcraftMilkItems.registerModelBakeryVariants();
    }

    @Override
    public void registerSpecialRenders() {
    	GrowthcraftMilkBlocks.registerSpecialRenders();
    }

}
