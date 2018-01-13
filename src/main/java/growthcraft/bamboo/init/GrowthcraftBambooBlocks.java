package growthcraft.bamboo.init;

import growthcraft.bamboo.blocks.*;
import growthcraft.bamboo.handler.ColorHandlerBlockBambooLeaves;
import growthcraft.core.common.definition.BlockDefinition;
import growthcraft.core.common.definition.BlockTypeDefinition;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFenceGate;
import net.minecraft.block.BlockSlab;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.item.ItemSlab;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import static growthcraft.core.GrowthcraftCore.tabGrowthcraft;

public class GrowthcraftBambooBlocks {

    public static BlockDefinition bambooPlank;
    public static BlockTypeDefinition<BlockSlab> bambooSlabHalf;
    public static BlockTypeDefinition<BlockSlab> bambooSlabDouble;
    public static BlockDefinition bambooStairs;
    public static BlockDefinition bambooFence;
    public static BlockDefinition bambooFenceGate;
    public static BlockDefinition bambooLeaves;
    public static BlockDefinition bambooStalk;
    public static BlockDefinition bambooShoot;
    public static BlockDefinition blockBambooDoor;

    public static void init() {
        bambooPlank = new BlockDefinition( new BlockBambooPlank() );
        bambooSlabHalf = new BlockTypeDefinition<BlockSlab>( new BlockBambooSlabHalf("bamboo_slab_half") );
        bambooSlabDouble = new BlockTypeDefinition<BlockSlab>( new BlockBambooSlabDouble("bamboo_slab_double" ) );
        bambooStairs = new BlockDefinition( new BlockBambooStairs("bamboo_stairs", bambooPlank.getDefaultState()) );
        bambooFence = new BlockDefinition( new BlockBambooFence("bamboo_fence") );
        bambooFenceGate = new BlockDefinition( new BlockBambooFenceGate("bamboo_fence_gate") );
        bambooLeaves = new BlockDefinition( new BlockBambooLeaves("bamboo_leaves") );
        bambooStalk = new BlockDefinition( new BlockBambooStalk("bamboo_stalk") );
        bambooShoot = new BlockDefinition( new BlockBambooShoot("bamboo_shoot") );
        blockBambooDoor = new BlockDefinition( new BlockBambooDoor("bamboo_door") );
    }

    public static void register() {
    	bambooPlank.getBlock().setCreativeTab(tabGrowthcraft);
    	bambooPlank.register(true);
    	bambooSlabHalf.getBlock().setCreativeTab(tabGrowthcraft);
    	bambooSlabHalf.register(new ItemSlab(bambooSlabHalf.getBlock(), bambooSlabHalf.getBlock(), bambooSlabDouble.getBlock()));
    	bambooSlabDouble.register(false);
    	bambooStairs.getBlock().setCreativeTab(tabGrowthcraft);
    	bambooStairs.register(true);
    	bambooFence.getBlock().setCreativeTab(tabGrowthcraft);
    	bambooFence.register(true);
    	bambooFenceGate.getBlock().setCreativeTab(tabGrowthcraft);
    	bambooFenceGate.register(true);
    	bambooLeaves.getBlock().setCreativeTab(tabGrowthcraft);
    	bambooLeaves.register(true);
    	bambooStalk.getBlock().setCreativeTab(tabGrowthcraft);
    	bambooStalk.register(true);
    	bambooShoot.getBlock().setCreativeTab(tabGrowthcraft);
    	bambooShoot.register(true);
    	blockBambooDoor.register(false);
    }

    public static void  registerRenders() {
    	bambooPlank.registerRender();
    	bambooSlabHalf.registerRender();
    	bambooStairs.registerRender();
    	bambooFence.registerRender();
    	bambooFenceGate.registerRender();
    	bambooLeaves.registerRender();
    	bambooStalk.registerRender();
    	bambooShoot.registerRender();
    	blockBambooDoor.registerRender();
    }

    public static void registerBlockColorHandlers() {
        registerBlockColorHandler(blockBambooDoor.getBlock());
    }

    /*
     * Credit to CJMinecraft for identifying how to ignore properties.
     */
    @SideOnly(Side.CLIENT)
    public static void setCustomStateMappers() {
        ModelLoader.setCustomStateMapper(bambooFenceGate.getBlock(), (new StateMap.Builder().ignore(BlockFenceGate.POWERED)).build());
    }

    @SideOnly(Side.CLIENT)
    public static void registerBlockColorHandler(Block block) {
        BlockColors blockColors = Minecraft.getMinecraft().getBlockColors();
        blockColors.registerBlockColorHandler(new ColorHandlerBlockBambooLeaves(), block);
    }

    /**
     * Simple Block Resgistration.
     * @param block block
     */
/*    public static void registerBlock(Block block) {
        registerBlock(block, true, true);
    }*/

    /**
     * Block registration with a custom ItemBlock
     * @param block block
     * @param itemBlock itemBlock
     */
/*    public static void registerBlock(Block block, ItemBlock itemBlock) {
        block.setCreativeTab(tabGrowthcraft);
        GameRegistry.register(block);
        GameRegistry.register(itemBlock.setRegistryName(block.getRegistryName()));
    } */

    /**
     * Advanced block registration
     * @param block block
     * @param setCreativeTab Add a creative tab entry.
     * @param registerItemBlock Add an ItemBlock entry.
     */
/*    public static void registerBlock(Block block, boolean setCreativeTab, boolean registerItemBlock ) {
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
    } */
}
