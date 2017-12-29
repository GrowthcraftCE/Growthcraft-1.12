package growthcraft.grapes.proxy;

import growthcraft.grapes.Reference;
import growthcraft.grapes.init.GrowthcraftGrapesFluids;
import growthcraft.grapes.init.GrowthcraftGrapesItems;
import growthcraft.grapes.tileentity.TileEntityGrapeVineFruit;
import growthcraft.grapes.tileentity.render.RendererGrapeVineFruit;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.ClientRegistry;

public class ClientProxy extends CommonProxy {

    @Override
    public void init() {
        registerModelBakeryVariants();
        registerSpecialRenders();
        GrowthcraftGrapesFluids.registerBoozeColorHandler();
    }

    @Override
    public void registerRenders() {
        GrowthcraftGrapesItems.registerRenders();
        GrowthcraftGrapesFluids.registerRenders();
    }

    @Override
    public void registerModelBakeryVariants() {
        ModelBakery.registerItemVariants(GrowthcraftGrapesItems.grape,
                new ResourceLocation(Reference.MODID, "grape_purple"),
                new ResourceLocation(Reference.MODID, "grape_green"),
                new ResourceLocation(Reference.MODID, "grape_red")
        );
        GrowthcraftGrapesFluids.registerItemVariants();
    }

    @Override
    public void registerSpecialRenders() {
        // TileEntitySpecialRenderer for showing the type of grape that is stored.
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityGrapeVineFruit.class, new RendererGrapeVineFruit());
    }
}
