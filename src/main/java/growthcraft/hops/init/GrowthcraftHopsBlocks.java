package growthcraft.hops.init;

import growthcraft.hops.Reference;
import growthcraft.hops.blocks.BlockHopsBush;
import growthcraft.hops.blocks.BlockHopsTrunk;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;

import static growthcraft.core.GrowthcraftCore.tabGrowthcraft;

public class GrowthcraftHopsBlocks {
    public static Block block_hops;
    public static Block hops_bush;

    public static void init() {
        block_hops = new BlockHopsTrunk("hops_vine");
        hops_bush = new BlockHopsBush();
    }

    public static void register() {
        registerBlock(block_hops, false, true);
        registerBlock(hops_bush, false, true);
    }

    public static void  registerRenders() {
        registerRender(block_hops);
        registerRender(hops_bush);
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
