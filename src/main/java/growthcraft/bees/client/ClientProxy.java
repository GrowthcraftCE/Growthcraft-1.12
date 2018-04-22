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
		NetworkRegistry.INSTANCE.registerGuiHandler(Reference.MODID, GrowthcraftBees.guiProvider);
		
		GrowthcraftBees.guiProvider.register("growthcraft_bees:bee_box", GuiBeeBox.class);
		Init.registerFluidColorHandlers();
	}
	
    @Override
    public void registerRenders() {
    	Init.registerItemRenders();
    	Init.registerBlockRender();
    	Init.registerFluidRender();
    }

    @Override
    public void registerModelBakeryVariants() {
    	Init.registerItemVariants();
    }

    @Override
    public void registerSpecialRenders() {
    }
}
