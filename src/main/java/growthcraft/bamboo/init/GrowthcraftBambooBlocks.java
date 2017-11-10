package growthcraft.bamboo.init;

import growthcraft.bamboo.Reference;
import growthcraft.bamboo.blocks.*;
import growthcraft.bamboo.handler.ColorHandlerBlockBambooLeaves;
import growthcraft.core.utils.GrowthcraftLogger;
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

public class GrowthcraftBambooBlocks {

    public static BlockBambooPlank bambooPlank;
    public static BlockBambooSlabHalf bambooSlabHalf;
    public static BlockBambooSlabDouble bambooSlabDouble;
    public static BlockBambooStairs bambooStairs;
    public static BlockBambooFence bambooFence;
    public static BlockBambooFenceGate bambooFenceGate;
    public static BlockBambooLeaves bambooLeaves;
    public static BlockBambooStalk bambooStalk;
    public static BlockBambooShoot bambooShoot;
    public static BlockBambooDoor blockBambooDoor;

    public static void init() {
        bambooPlank = new BlockBambooPlank();
        bambooSlabHalf = new BlockBambooSlabHalf("bamboo_slab_half");
        bambooSlabDouble = new BlockBambooSlabDouble("bamboo_slab_double" );
        bambooStairs = new BlockBambooStairs("bamboo_stairs", bambooPlank.getDefaultState());
        bambooFence = new BlockBambooFence("bamboo_fence");
        bambooFenceGate = new BlockBambooFenceGate("bamboo_fence_gate");
        bambooLeaves = new BlockBambooLeaves("bamboo_leaves");
        bambooStalk = new BlockBambooStalk("bamboo_stalk");
        bambooShoot = new BlockBambooShoot("bamboo_shoot");
        blockBambooDoor = new BlockBambooDoor("bamboo_door");
    }

    public static void register() {
        registerBlock(bambooPlank);
        registerBlock(bambooSlabHalf, new ItemSlab(bambooSlabHalf, bambooSlabHalf, bambooSlabDouble));
        registerBlock(bambooSlabDouble, false, false);
        registerBlock(bambooStairs);
        registerBlock(bambooFence);
        registerBlock(bambooFenceGate);
        registerBlock(bambooLeaves);
        registerBlock(bambooStalk);
        registerBlock(bambooShoot);
        registerBlock(blockBambooDoor, false, false);
    }

    public static void  registerRenders() {
        registerRender(bambooPlank);
        registerRender(bambooSlabHalf);
        registerRender(bambooStairs);
        registerRender(bambooFence);
        registerRender(bambooFenceGate);
        registerRender(bambooLeaves);
        registerRender(bambooStalk);
        registerRender(bambooShoot);
        registerRender(blockBambooDoor);
    }

    public static void registerBlockColorHandlers() {
        registerBlockColorHandler(blockBambooDoor);
    }

    /*
     * Credit to CJMinecraft for identifying how to ignore properties.
     */
    @SideOnly(Side.CLIENT)
    public static void setCustomStateMappers() {
        ModelLoader.setCustomStateMapper(bambooFenceGate, (new StateMap.Builder().ignore(BlockFenceGate.POWERED)).build());
    }

    @SideOnly(Side.CLIENT)
    public static void registerBlockColorHandler(Block block) {
        GrowthcraftLogger.getLogger().info("[DEBUG::DEBUG] Registering " + block.getUnlocalizedName() + " color handlers.");
        BlockColors blockColors = Minecraft.getMinecraft().getBlockColors();
        blockColors.registerBlockColorHandler(new ColorHandlerBlockBambooLeaves(), block);
    }

    /**
     * Simple Block Resgistration.
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
