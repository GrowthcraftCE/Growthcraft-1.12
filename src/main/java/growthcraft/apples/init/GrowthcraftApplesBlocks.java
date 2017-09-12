package growthcraft.apples.init;

import growthcraft.apples.Reference;
import growthcraft.apples.blocks.BlockApple;
import growthcraft.apples.blocks.BlockAppleLeaves;
import growthcraft.apples.blocks.BlockAppleLog;
import growthcraft.apples.blocks.BlockAppleSapling;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;

import static growthcraft.core.GrowthcraftCore.tabGrowthcraft;

public class GrowthcraftApplesBlocks {

    public static BlockAppleSapling blockAppleSapling;
    public static BlockAppleLog blockAppleLog;
    public static BlockAppleLeaves blockAppleLeaves;
    public static BlockApple blockApple;

    public static void preInit() {
        blockAppleSapling = new BlockAppleSapling("apple_sapling");
        blockAppleLog = new BlockAppleLog("apple_wood");
        blockAppleLeaves = new BlockAppleLeaves("apple_leaves");
        blockApple = new BlockApple("apple_crop");
        register();
    }

    public static void register() {
        registerBlock(blockAppleSapling);
        registerBlock(blockAppleLog);
        registerBlock(blockAppleLeaves);
        registerBlock(blockApple, false, false);
    }

    public static void registerRenders() {
        registerRender(blockAppleSapling);
        registerRender(blockAppleLog);
        registerRender(blockAppleLeaves);
        registerRender(blockApple);
    }

    /* No need to edit below */

    /**
     * Simple block registration
     * @param block block
     */
    public static void registerBlock(Block block) {
        registerBlock(block, true, true);
    }

    /**
     * Block registration with a custom ItemBlock
     * @param block block
     * @param itemBlock itemBlock
     */
    public static void registerBlock(Block block, ItemBlock itemBlock) {
        block.setCreativeTab(tabGrowthcraft);
        GameRegistry.register(block);
        GameRegistry.register(itemBlock.setRegistryName(block.getRegistryName()));
    }

    /**
     * Advanced block registration
     * @param block block
     * @param setCreativeTab Add a creative tab entry.
     * @param registerItemBlock Add an ItemBlock entry.
     */
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
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), 0,
                new ModelResourceLocation(new ResourceLocation(Reference.MODID,
                        block.getUnlocalizedName().substring(5)), "inventory"));
    }

    public static void  registerRender(Block block, int meta, String fileName){
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), meta,
                new ModelResourceLocation(new ResourceLocation(Reference.MODID, fileName), "inventory"));
    }
}
