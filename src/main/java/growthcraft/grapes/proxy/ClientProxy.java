package growthcraft.grapes.proxy;

import growthcraft.grapes.Reference;
import growthcraft.grapes.init.GrowthcraftGrapesItems;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.util.ResourceLocation;

public class ClientProxy extends CommonProxy {

    @Override
    public void init() {
    }

    @Override
    public void registerRenders() {
        GrowthcraftGrapesItems.registerRenders();
    }

    @Override
    public void registerModelBakeryVariants() {
        ModelBakery.registerItemVariants(GrowthcraftGrapesItems.grape,
                new ResourceLocation(Reference.MODID, "grape_purple"),
                new ResourceLocation(Reference.MODID, "grape_green"),
                new ResourceLocation(Reference.MODID, "grape_red")
        );
    }
}
