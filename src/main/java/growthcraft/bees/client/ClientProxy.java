package growthcraft.bees.client;

import growthcraft.bees.GrowthcraftBees;
import growthcraft.bees.client.gui.GuiBeeBox;
import growthcraft.bees.common.CommonProxy;
import growthcraft.bees.common.Init;
import growthcraft.bees.shared.Reference;
import growthcraft.core.shared.inventory.GrowthcraftGuiProvider;
import net.minecraftforge.fml.common.network.NetworkRegistry;

public class ClientProxy extends CommonProxy {

	@Override
	public void init() {
		super.init();
		
		GrowthcraftBees.guiProvider.register("growthcraft_bees:bee_box", GuiBeeBox.class);
		Init.registerFluidColorHandlers();
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
