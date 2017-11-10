package growthcraft.apples.init;

import growthcraft.apples.Reference;
import growthcraft.apples.blocks.*;
import growthcraft.apples.handlers.GrowthcraftApplesColorHandler;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFenceGate;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemSlab;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import static growthcraft.core.GrowthcraftCore.tabGrowthcraft;

public class GrowthcraftApplesBlocks {

    public static BlockApple blockApple;
    public static BlockAppleDoor blockAppleDoor;
    public static BlockAppleFence blockAppleFence;
    public static BlockAppleFenceGate blockAppleFenceGate;
    public static BlockAppleLeaves blockAppleLeaves;
    public static BlockAppleLog blockAppleLog;
    public static BlockApplePlanks blockApplePlanks;
    public static BlockAppleSapling blockAppleSapling;
    public static BlockAppleSlabHalf blockAppleSlabHalf;
    public static BlockAppleSlabDouble blockAppleSlabDouble;
    public static BlockAppleStairs blockAppleStairs;

    public static void preInit() {
        blockApple = new BlockApple("apple_crop");
        blockAppleDoor = new BlockAppleDoor("apple_door");
        blockAppleFence = new BlockAppleFence("apple_fence");
        blockAppleFenceGate = new BlockAppleFenceGate("apple_fence_gate");
        blockAppleLeaves = new BlockAppleLeaves("apple_leaves");
        blockAppleLog = new BlockAppleLog("apple_log");
        blockApplePlanks = new BlockApplePlanks("apple_planks");
        blockAppleSapling = new BlockAppleSapling("apple_sapling");
        blockAppleSlabHalf = new BlockAppleSlabHalf("apple_slab_half");
        blockAppleSlabDouble = new BlockAppleSlabDouble("apple_slab_double");
        blockAppleStairs = new BlockAppleStairs("apple_stairs", blockApplePlanks.getDefaultState());
        // Register the blocks
        register();
    }

    public static void register() {
        registerBlock(blockApple, false, false);
        registerBlock(blockAppleDoor, false, false);
        registerBlock(blockAppleFence);
        registerBlock(blockAppleFenceGate);
        registerBlock(blockAppleLeaves);
        registerBlock(blockAppleLog);
        registerBlock(blockApplePlanks);
        registerBlock(blockAppleSapling);
        registerBlock(blockAppleSlabHalf,
                new ItemSlab(blockAppleSlabHalf, blockAppleSlabHalf, blockAppleSlabDouble));
        registerBlock(blockAppleSlabDouble, false, false);
        registerBlock(blockAppleStairs);
    }

    public static void registerRenders() {
        registerRender(blockApple);
        registerRender(blockAppleDoor);
        registerRender(blockAppleFence);
        registerRender(blockAppleFenceGate);
        registerRender(blockAppleLeaves);
        registerRender(blockAppleLog);
        registerRender(blockAppleSapling);
        registerRender(blockApplePlanks);
        registerRender(blockAppleStairs);
        registerRender(blockAppleSlabHalf);
    }

    public static void registerBlockColorHandlers() {
        registerBlockColorHandler(blockAppleLeaves);
    }
    /*
     * Credit to CJMinecraft for identifying how to ignore properties.
     */
    @SideOnly(Side.CLIENT)
    public static void setCustomStateMappers() {
        ModelLoader.setCustomStateMapper(blockAppleFenceGate, (new StateMap.Builder().ignore(BlockFenceGate.POWERED)).build());
    }

    @SideOnly(Side.CLIENT)
    public static void registerBlockColorHandler(Block block) {
        BlockColors blockColors = Minecraft.getMinecraft().getBlockColors();
        blockColors.registerBlockColorHandler(new GrowthcraftApplesColorHandler(), block);
    }

    /* No need to edit below */

    public static void registerBlock(Block block) {
        registerBlock(block, true, true);
    }

    public static void registerBlock(Block block, ItemBlock itemBlock) {
        block.setCreativeTab(tabGrowthcraft);
        GameRegistry.register(block);
        GameRegistry.register(itemBlock.setRegistryName(block.getRegistryName()));
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
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), 0,
                new ModelResourceLocation(new ResourceLocation(Reference.MODID,
                        block.getUnlocalizedName().substring(5)), "inventory"));
    }

    public static void  registerRender(Block block, int meta, String fileName){
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), meta,
                new ModelResourceLocation(new ResourceLocation(Reference.MODID, fileName), "inventory"));
    }
}
