package growthcraft.apples.init;

import growthcraft.apples.Reference;
import growthcraft.apples.blocks.*;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFenceGate;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMap;
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

    public static BlockAppleSapling blockAppleSapling;
    public static BlockAppleLog blockAppleLog;
    public static BlockAppleLeaves blockAppleLeaves;
    public static BlockApple blockApple;
    public static BlockApplePlanks blockApplePlanks;

    public static BlockAppleFence blockAppleFence;
    public static BlockAppleFenceGate blockAppleFenceGate;

    public static BlockAppleSlabHalf blockAppleSlabHalf;
    public static BlockAppleSlabDouble blockAppleSlabDouble;

    public static BlockAppleStairs blockAppleStairs;

    public static BlockAppleDoor blockAppleDoor;


    public static void preInit() {
        blockAppleSapling = new BlockAppleSapling("apple_sapling");
        blockAppleLog = new BlockAppleLog("apple_log");
        blockAppleLeaves = new BlockAppleLeaves("apple_leaves");
        blockApple = new BlockApple("apple_crop");
        blockApplePlanks = new BlockApplePlanks("apple_planks");
        blockAppleFence = new BlockAppleFence("apple_fence");
        blockAppleFenceGate = new BlockAppleFenceGate("apple_fence_gate");

        blockAppleSlabHalf = new BlockAppleSlabHalf("apple_slab_half");
        blockAppleSlabDouble = new BlockAppleSlabDouble("apple_slab_double");

        blockAppleStairs = new BlockAppleStairs("apple_stairs", blockApplePlanks.getDefaultState());

        blockAppleDoor = new BlockAppleDoor("apple_door");

        register();
    }

    public static void register() {
        registerBlock(blockAppleSapling);
        registerBlock(blockAppleLog);
        registerBlock(blockAppleLeaves);
        registerBlock(blockApple, false, false);
        registerBlock(blockApplePlanks);
        registerBlock(blockAppleFence);
        registerBlock(blockAppleFenceGate);
        registerBlock(blockAppleSlabHalf,
                new ItemSlab(blockAppleSlabHalf, blockAppleSlabHalf, blockAppleSlabDouble));
        registerBlock(blockAppleSlabDouble, false, false);
        registerBlock(blockAppleStairs);
        registerBlock(blockAppleDoor, true, true);

    }

    public static void registerRenders() {
        registerRender(blockAppleSapling);
        registerRender(blockAppleLog);
        registerRender(blockAppleLeaves);
        registerRender(blockApple);
        registerRender(blockApplePlanks);
        registerRender(blockAppleFence);
        registerRender(blockAppleFenceGate);
        registerRender(blockAppleStairs);
        registerRender(blockAppleSlabHalf);
        registerRender(blockAppleDoor);
    }

    /*
     * Credit to CJMinecraft for identifying how to ignore properties.
     */
    @SideOnly(Side.CLIENT)
    public static void setCustomStateMappers() {
        ModelLoader.setCustomStateMapper(blockAppleFenceGate, (new StateMap.Builder().ignore(BlockFenceGate.POWERED)).build());
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
