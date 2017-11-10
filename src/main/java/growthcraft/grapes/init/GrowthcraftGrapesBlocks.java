package growthcraft.grapes.init;

import growthcraft.grapes.Reference;
import growthcraft.grapes.blocks.BlockGrapeVine;
import growthcraft.grapes.blocks.BlockGrapeVineBush;
import growthcraft.grapes.blocks.BlockGrapeVineFruit;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;

import static growthcraft.core.GrowthcraftCore.tabGrowthcraft;

public class GrowthcraftGrapesBlocks {

    public static BlockGrapeVine grape_vine;
    public static BlockGrapeVineBush grape_vine_bush;
    public static BlockGrapeVineFruit grape_vine_fruit;

    public static void init() {
        grape_vine = new BlockGrapeVine("grape_vine");
        grape_vine_bush = new BlockGrapeVineBush("grape_vine_bush");
        grape_vine_fruit = new BlockGrapeVineFruit();
    }

    public static void register() {
        registerBlock(grape_vine, false, true);
        registerBlock(grape_vine_bush, false, true);
        registerBlock(grape_vine_fruit, false, true);
    }

    public static void  registerRenders() {
        registerRender(grape_vine);
        registerRender(grape_vine_bush);
        registerRender(grape_vine_fruit);
    }

    public static void registerBlock(Block block, boolean setCreativeTab, boolean registerItemBlock ) {
        GameRegistry.register(block);

        if(setCreativeTab) {
            block.setCreativeTab(tabGrowthcraft);
        }
        if(registerItemBlock) {
            GameRegistry.register(new ItemBlock(block).setRegistryName(block.getRegistryName()));
        }
    }

    public static void registerRender(Block block) {
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), 0, new ModelResourceLocation(
                new ResourceLocation(Reference.MODID, block.getUnlocalizedName().substring(5)), "inventory"));
    }

    public static void  registerRender(Block block, int meta, String fileName){
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), meta,
                new ModelResourceLocation(new ResourceLocation(Reference.MODID, fileName), "inventory"));
    }


}
