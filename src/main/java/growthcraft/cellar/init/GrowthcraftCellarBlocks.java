package growthcraft.cellar.init;

import growthcraft.cellar.Reference;
import growthcraft.cellar.blocks.BlockBrewKettle;
import growthcraft.cellar.blocks.BlockCultureJar;
import growthcraft.cellar.client.render.RenderBrewKettle;
import growthcraft.cellar.client.render.RenderCultureJar;
import growthcraft.cellar.common.block.BlockFermentBarrel;
import growthcraft.cellar.common.block.BlockFruitPress;
import growthcraft.cellar.common.block.BlockFruitPresser;
import growthcraft.cellar.common.block.PREVBlockBrewKettle;
import growthcraft.cellar.common.block.PREVBlockCultureJar;
import growthcraft.cellar.common.tileentity.PREVTileEntityBrewKettle;
import growthcraft.cellar.common.tileentity.TileEntityCultureJar;
import growthcraft.cellar.common.tileentity.TileEntityFermentBarrel;
import growthcraft.cellar.common.tileentity.TileEntityFruitPress;
import growthcraft.cellar.common.tileentity.TileEntityFruitPresser;
import growthcraft.core.common.definition.BlockDefinition;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;

import static growthcraft.core.GrowthcraftCore.tabGrowthcraft;

public class GrowthcraftCellarBlocks {

//    public static Block blockCultureJar;
//    public static Block blockBrewKettle;
    public static BlockDefinition brewKettle;
    public static BlockDefinition fermentBarrel;
    public static BlockDefinition cultureJar;
    public static BlockDefinition fruitPress;
    public static BlockDefinition fruitPresser;

    public static void init() {
//        blockCultureJar = new BlockCultureJar("culture_jar");
//        blockBrewKettle = new BlockBrewKettle("brew_kettle");
        brewKettle = new BlockDefinition(new PREVBlockBrewKettle("brew_kettle"));
        fermentBarrel = new BlockDefinition(new BlockFermentBarrel("ferment_barrel"));
        cultureJar = new BlockDefinition(new PREVBlockCultureJar("culture_jar"));
        fruitPress = new BlockDefinition(new BlockFruitPress("fruit_press"));
        fruitPresser = new BlockDefinition(new BlockFruitPresser("fruit_presser"));
    }

    public static void register() {
//        registerBlock(blockCultureJar, true, true);
//        registerBlock(blockBrewKettle, true, true);
        brewKettle.getBlock().setCreativeTab(tabGrowthcraft);
        brewKettle.register(true);
        fermentBarrel.getBlock().setCreativeTab(tabGrowthcraft);
        fermentBarrel.register(true);
        cultureJar.getBlock().setCreativeTab(tabGrowthcraft);
        cultureJar.register(true);
        fruitPress.getBlock().setCreativeTab(tabGrowthcraft);
        fruitPress.register(true);
        fruitPresser.register(false);
    }

    public static void registerRenders() {
//        registerRender(blockCultureJar);
//        registerRender(blockBrewKettle);
        brewKettle.registerRender();
        fermentBarrel.registerRender();
        cultureJar.registerRender();
        fruitPress.registerRender();
        fruitPresser.registerRender();
    }
    
    public static void registerSpecialRenders() {
        ClientRegistry.bindTileEntitySpecialRenderer(PREVTileEntityBrewKettle.class, new RenderBrewKettle());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCultureJar.class, new RenderCultureJar());
    }
    
    public static void registerTileEntities() {
      GameRegistry.registerTileEntity(PREVTileEntityBrewKettle.class, Reference.MODID + ":brew_kettle");
      GameRegistry.registerTileEntity(TileEntityFermentBarrel.class, Reference.MODID + ":ferment_barrel");
      GameRegistry.registerTileEntity(TileEntityCultureJar.class, Reference.MODID + ":culture_jar");
      GameRegistry.registerTileEntity(TileEntityFruitPress.class, Reference.MODID + ":fruit_press");
      GameRegistry.registerTileEntity(TileEntityFruitPresser.class, Reference.MODID + ":fruit_presser");
    }

    public static void registerBlock(Block block, boolean setCreativeTab, boolean registerItemBlock) {
        GameRegistry.register(block);

        if (setCreativeTab) {
            block.setCreativeTab(tabGrowthcraft);
        }
        if (registerItemBlock) {
            GameRegistry.register(new ItemBlock(block).setRegistryName(block.getRegistryName()));
        }
    }
}
