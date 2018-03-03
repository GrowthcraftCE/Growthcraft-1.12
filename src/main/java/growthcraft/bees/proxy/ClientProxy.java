package growthcraft.bees.proxy;

import growthcraft.bees.GrowthcraftBees;
import growthcraft.bees.client.GuiBeeBox;
import growthcraft.bees.init.GrowthcraftBeesBlocks;
import growthcraft.bees.init.GrowthcraftBeesFluids;
import growthcraft.bees.init.GrowthcraftBeesItems;

public class ClientProxy extends CommonProxy {

	@Override
	public void init() {
		GrowthcraftBees.guiProvider.register("growthcraft_bees:bee_box", GuiBeeBox.class);
		GrowthcraftBeesFluids.registerItemColorHandlers();
	}
	
    @Override
    public void registerRenders() {
    	GrowthcraftBeesItems.registerRenders();
    	GrowthcraftBeesBlocks.registerRender();
    	GrowthcraftBeesFluids.registerRender();
    }

    @Override
    public void registerModelBakeryVariants() {
    	GrowthcraftBeesItems.registerItemVariants();
    }

    @Override
    public void registerSpecialRenders() {
    }
}
