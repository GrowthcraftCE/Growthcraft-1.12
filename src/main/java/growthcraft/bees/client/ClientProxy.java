package growthcraft.bees.client;

import growthcraft.bees.GrowthcraftBees;
import growthcraft.bees.client.gui.GuiBeeBox;
import growthcraft.bees.common.CommonProxy;
import growthcraft.bees.common.Init;
import growthcraft.bees.shared.Reference;
import net.minecraftforge.fml.common.network.NetworkRegistry;

public class ClientProxy extends CommonProxy {

	@Override
	public void init() {
		super.init();
		NetworkRegistry.INSTANCE.registerGuiHandler(Reference.MODID, GrowthcraftBees.guiProvider);
		
		GrowthcraftBees.guiProvider.register("growthcraft_bees:bee_box", GuiBeeBox.class);
		Init.registerFluidColorHandlers();
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
