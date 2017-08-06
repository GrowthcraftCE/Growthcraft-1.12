package growthcraft.core.proxy;

import growthcraft.core.Reference;
import growthcraft.core.init.GrowthcraftCoreBlocks;
import growthcraft.core.init.GrowthcraftCoreItems;
import growthcraft.core.tileentity.TileEntityRopeKnot;
import growthcraft.core.tileentity.renderer.TileEntityRopeKnotRenderer;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.ClientRegistry;

public class ClientProxy extends CommonProxy {
    @Override
    public void registerRenders() {
        GrowthcraftCoreItems.registerRenders();
        GrowthcraftCoreBlocks.registerRenders();
    }

    @Override
    public void registerModelBakeryVariants() {
        ModelBakery.registerItemVariants(GrowthcraftCoreItems.crowbar,
                new ResourceLocation(Reference.MODID, "crowbar_white"),
                new ResourceLocation(Reference.MODID, "crowbar_orange"),
                new ResourceLocation(Reference.MODID, "crowbar_magenta"),
                new ResourceLocation(Reference.MODID, "crowbar_lightblue"),
                new ResourceLocation(Reference.MODID, "crowbar_yellow"),
                new ResourceLocation(Reference.MODID, "crowbar_lime"),
                new ResourceLocation(Reference.MODID, "crowbar_pink"),
                new ResourceLocation(Reference.MODID, "crowbar_gray"),
                new ResourceLocation(Reference.MODID, "crowbar_silver"),
                new ResourceLocation(Reference.MODID, "crowbar_cyan"),
                new ResourceLocation(Reference.MODID, "crowbar_purple"),
                new ResourceLocation(Reference.MODID, "crowbar_blue"),
                new ResourceLocation(Reference.MODID, "crowbar_brown"),
                new ResourceLocation(Reference.MODID, "crowbar_green"),
                new ResourceLocation(Reference.MODID, "crowbar_red"),
                new ResourceLocation(Reference.MODID, "crowbar_black")
        );
    }

    @Override
    public void registerSpecialRenders() {
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityRopeKnot.class, new TileEntityRopeKnotRenderer());
    }

}
