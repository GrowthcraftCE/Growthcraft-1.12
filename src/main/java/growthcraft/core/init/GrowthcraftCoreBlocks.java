package growthcraft.core.init;

import growthcraft.core.Reference;
import growthcraft.core.blocks.BlockRopeFence;
import growthcraft.core.blocks.BlockRopeKnot;
import growthcraft.core.blocks.BlockSalt;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;

import static growthcraft.core.GrowthcraftCore.tabGrowthcraft;

public class GrowthcraftCoreBlocks {

    public static Block salt_block;
    public static Block rope_knot;
    public static Block rope_fence;

    public static void init() {
        rope_fence = new BlockRopeFence( "rope_fence" );
        salt_block = new BlockSalt("salt_block");
        rope_knot = new BlockRopeKnot("rope_knot");
    }

    public static void register() {
        registerBlock(salt_block);
        registerBlock(rope_fence, false, true);
        registerBlock(rope_knot, false, true);
    }

    public static void  registerRenders() {
        registerRender(salt_block);
        registerRender(rope_fence);
        registerRender(rope_knot);
    }

    public static void registerBlock(Block block) {
        registerBlock(block, true, true);
    }

    public static void registerBlock(Block block, boolean setCreativeTab, ItemBlock itemBlock) {
        GameRegistry.register(block);

        if(setCreativeTab) {
            block.setCreativeTab(tabGrowthcraft);
        }

        GameRegistry.register(itemBlock.setRegistryName(block.getRegistryName()));
    }

    public static void registerBlock(Block block, boolean setCreativeTab, boolean registerItemBlock) {
        GameRegistry.register(block);

        if(setCreativeTab) {
            block.setCreativeTab(tabGrowthcraft);
        }
        if(registerItemBlock) {
            GameRegistry.register(new ItemBlock(block).setRegistryName(block.getRegistryName()));
        }
    }

    public static void registerRender(Block block) {
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), 0, new ModelResourceLocation(new ResourceLocation(Reference.MODID, block.getUnlocalizedName().substring(5)), "inventory"));
    }

    public static void  registerRender(Block block, int meta, String fileName){
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), meta, new ModelResourceLocation(new ResourceLocation(Reference.MODID, fileName), "inventory"));
    }

}
