package growthcraft.cellar.common.init;

import growthcraft.cellar.Reference;
import growthcraft.cellar.client.render.RenderBrewKettle;
import growthcraft.cellar.client.render.RenderCultureJar;
import growthcraft.cellar.client.render.RenderFruitPress;
import growthcraft.cellar.common.block.*;
import growthcraft.cellar.common.tileentity.*;
import growthcraft.core.shared.definition.BlockDefinition;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import static growthcraft.core.GrowthcraftCore.tabGrowthcraft;

public class GrowthcraftCellarBlocks {

    public static BlockDefinition brewKettle;
    public static BlockDefinition fermentBarrel;
    public static BlockDefinition cultureJar;
    public static BlockDefinition fruitPress;
    public static BlockDefinition fruitPresser;

    public static void init() {
        brewKettle = new BlockDefinition(new PREVBlockBrewKettle("brew_kettle"));
        fermentBarrel = new BlockDefinition(new BlockFermentBarrel("ferment_barrel"));
        cultureJar = new BlockDefinition(new PREVBlockCultureJar("culture_jar"));
        fruitPress = new BlockDefinition(new BlockFruitPress("fruit_press"));
        fruitPresser = new BlockDefinition(new BlockFruitPresser("fruit_presser"));
    }

    public static void register() {
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
        brewKettle.registerItemRender();
        fermentBarrel.registerItemRender();
        cultureJar.registerItemRender();
        fruitPress.registerItemRender();
    }

    @SideOnly(Side.CLIENT)
    public static void registerSpecialRenders() {
        ClientRegistry.bindTileEntitySpecialRenderer(PREVTileEntityBrewKettle.class, new RenderBrewKettle());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCultureJar.class, new RenderCultureJar());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityFruitPress.class, new RenderFruitPress());
    }
    
    public static void registerTileEntities() {
      GameRegistry.registerTileEntity(PREVTileEntityBrewKettle.class, Reference.MODID + ":brew_kettle");
      GameRegistry.registerTileEntity(TileEntityFermentBarrel.class, Reference.MODID + ":ferment_barrel");
      GameRegistry.registerTileEntity(TileEntityCultureJar.class, Reference.MODID + ":culture_jar");
      GameRegistry.registerTileEntity(TileEntityFruitPress.class, Reference.MODID + ":fruit_press");
      GameRegistry.registerTileEntity(TileEntityFruitPresser.class, Reference.MODID + ":fruit_presser");
    }
}
