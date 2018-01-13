package growthcraft.cellar.proxy;

import growthcraft.cellar.init.GrowthcraftCellarItems;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.util.ResourceLocation;
import growthcraft.cellar.GrowthcraftCellar;
import growthcraft.cellar.Reference;
import growthcraft.cellar.client.gui.GuiCultureJar;
import growthcraft.cellar.client.gui.GuiFermentBarrel;
import growthcraft.cellar.client.gui.GuiFruitPress;
import growthcraft.cellar.client.gui.PREVGuiBrewKettle;
import growthcraft.cellar.handlers.EnumHandler.EnumYeast;
import growthcraft.cellar.init.GrowthcraftCellarBlocks;

public class ClientProxy extends CommonProxy {
    @Override
    public void preInit() {
        super.preInit();
    	registerRenders();
        registerModelBakeryVariants();
        registerSpecialRenders();
    }
    
    @Override
    public void init() {
    	super.init();
    	GrowthcraftCellar.guiProvider.register("growthcraft_cellar:fruit_press", GuiFruitPress.class);
    	GrowthcraftCellar.guiProvider.register("growthcraft_cellar:brew_kettle", PREVGuiBrewKettle.class);
    	GrowthcraftCellar.guiProvider.register("growthcraft_cellar:ferment_barrel", GuiFermentBarrel.class);
    	GrowthcraftCellar.guiProvider.register("growthcraft_cellar:culture_jar", GuiCultureJar.class);
    }

    @Override
    public void registerRenders() {
    	super.registerRenders();
    	GrowthcraftCellarItems.registerRenders();
    	GrowthcraftCellarBlocks.registerRenders();
    }

    @Override
    public void registerModelBakeryVariants() {
/*    	ResourceLocation yeastReslocs[] = new ResourceLocation[EnumYeast.values().length];
    	
    	for( int i = 0; i < yeastReslocs.length; i ++ ) {
    		EnumYeast type = EnumYeast.values()[i]; 
    		yeastReslocs[i] = new ResourceLocation(Reference.MODID, "yeast_" + type.toString().toLowerCase() );	
    	}
    	
    	ModelBakery.registerItemVariants(GrowthcraftCellarItems.yeast.getItem(), yeastReslocs);*/
    	GrowthcraftCellarItems.yeast.registerModelBakeryVariants(EnumYeast.values());
    }

    @Override
    public void registerSpecialRenders() {
    	super.registerSpecialRenders();
    	GrowthcraftCellarBlocks.registerSpecialRenders();
    }
}
