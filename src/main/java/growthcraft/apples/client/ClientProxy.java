package growthcraft.apples.client;

import growthcraft.apples.common.CommonProxy;
import growthcraft.apples.common.Init;
import growthcraft.apples.shared.init.GrowthcraftApplesBlocks;
import growthcraft.apples.shared.init.GrowthcraftApplesItems;

public class ClientProxy extends CommonProxy {

    @Override
    public void preInit() {
    	super.preInit();
        Init.setCustomBlockStateMappers();
    }

	@Override
	public void init() {
		super.init();
		Init.registerBlockColorHandlers();
		Init.registerItemColorHandlers();
        registerSpecialRenders();
	}
	
	@Override
    public void postRegisterItems() {
    	super.postRegisterItems();
    	registerModelBakeryVariants();
    }
	
    public void registerModelBakeryVariants() {
    	Init.registerItemVariants();
    }

    public void registerSpecialRenders() {
    }

}
