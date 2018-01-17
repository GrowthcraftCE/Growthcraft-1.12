package growthcraft.milk.proxy;

import growthcraft.milk.Reference;
import growthcraft.milk.common.tileentity.TileEntityPancheon;
import growthcraft.milk.init.GrowthcraftMilkBlocks;
import growthcraft.milk.init.GrowthcraftMilkFluids;
import growthcraft.milk.init.GrowthcraftMilkItems;
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
