package growthcraft.cellar.common;

import static growthcraft.core.shared.GrowthcraftCoreApis.tabGrowthcraft;

import growthcraft.cellar.client.render.RenderBrewKettle;
import growthcraft.cellar.client.render.RenderCultureJar;
import growthcraft.cellar.client.render.RenderFruitPress;
import growthcraft.cellar.common.block.BlockBrewKettle;
import growthcraft.cellar.common.block.BlockCultureJar;
import growthcraft.cellar.common.block.BlockFermentBarrel;
import growthcraft.cellar.common.block.BlockFruitPress;
import growthcraft.cellar.common.block.BlockFruitPresser;
import growthcraft.cellar.common.item.ItemChievDummy;
import growthcraft.cellar.common.item.ItemYeast;
import growthcraft.cellar.common.potion.PotionCellar;
import growthcraft.cellar.common.stats.CellarAchievement;
import growthcraft.cellar.common.tileentity.TileEntityBrewKettle;
import growthcraft.cellar.common.tileentity.TileEntityCultureJar;
import growthcraft.cellar.common.tileentity.TileEntityFermentBarrel;
import growthcraft.cellar.common.tileentity.TileEntityFruitPress;
import growthcraft.cellar.common.tileentity.TileEntityFruitPresser;
import growthcraft.cellar.shared.CellarRegistry;
import growthcraft.cellar.shared.GrowthcraftCellarApis;
import growthcraft.cellar.shared.Reference;
import growthcraft.cellar.shared.booze.BoozeEffect;
import growthcraft.cellar.shared.booze.effect.EffectTipsy;
import growthcraft.cellar.shared.init.GrowthcraftCellarBlocks;
import growthcraft.cellar.shared.init.GrowthcraftCellarItems;
import growthcraft.cellar.shared.init.GrowthcraftCellarItems.EnumYeast;
import growthcraft.cellar.shared.init.GrowthcraftCellarPotions;
import growthcraft.cellar.shared.processing.heatsource.user.UserHeatSourceEntry;
import growthcraft.core.shared.CoreRegistry;
import growthcraft.core.shared.definition.BlockDefinition;
import growthcraft.core.shared.definition.ItemDefinition;
import growthcraft.core.shared.effect.EffectRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;

public class Init {
	private Init() {}
	
	///////////
	// Blocks
	///////////
	
    public static void preInitBlocks() {
        GrowthcraftCellarBlocks.brewKettle = new BlockDefinition(new BlockBrewKettle("brew_kettle"));
        GrowthcraftCellarBlocks.fermentBarrel = new BlockDefinition(new BlockFermentBarrel("ferment_barrel"));
        GrowthcraftCellarBlocks.cultureJar = new BlockDefinition(new BlockCultureJar("culture_jar"));
        GrowthcraftCellarBlocks.fruitPress = new BlockDefinition(new BlockFruitPress("fruit_press"));
        GrowthcraftCellarBlocks.fruitPresser = new BlockDefinition(new BlockFruitPresser("fruit_presser"));
    }

    public static void registerBlocks() {
    	GrowthcraftCellarBlocks.brewKettle.getBlock().setCreativeTab(tabGrowthcraft);
    	GrowthcraftCellarBlocks.brewKettle.register(true);
    	GrowthcraftCellarBlocks.fermentBarrel.getBlock().setCreativeTab(tabGrowthcraft);
    	GrowthcraftCellarBlocks.fermentBarrel.register(true);
    	GrowthcraftCellarBlocks.cultureJar.getBlock().setCreativeTab(tabGrowthcraft);
    	GrowthcraftCellarBlocks.cultureJar.register(true);
    	GrowthcraftCellarBlocks.fruitPress.getBlock().setCreativeTab(tabGrowthcraft);
    	GrowthcraftCellarBlocks.fruitPress.register(true);
    	GrowthcraftCellarBlocks.fruitPresser.register(false);
    }

    public static void registerBlockRenders() {
    	GrowthcraftCellarBlocks.brewKettle.registerItemRender();
    	GrowthcraftCellarBlocks.fermentBarrel.registerItemRender();
    	GrowthcraftCellarBlocks.cultureJar.registerItemRender();
    	GrowthcraftCellarBlocks.fruitPress.registerItemRender();
    }

    @SideOnly(Side.CLIENT)
    public static void registerBlockSpecialRenders() {
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityBrewKettle.class, new RenderBrewKettle());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCultureJar.class, new RenderCultureJar());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityFruitPress.class, new RenderFruitPress());
    }
    
    public static void registerTileEntities() {
        GameRegistry.registerTileEntity(TileEntityBrewKettle.class, Reference.MODID + ":brew_kettle");
        GameRegistry.registerTileEntity(TileEntityFermentBarrel.class, Reference.MODID + ":ferment_barrel");
        GameRegistry.registerTileEntity(TileEntityCultureJar.class, Reference.MODID + ":culture_jar");
        GameRegistry.registerTileEntity(TileEntityFruitPress.class, Reference.MODID + ":fruit_press");
        GameRegistry.registerTileEntity(TileEntityFruitPresser.class, Reference.MODID + ":fruit_presser");
    }
    
    ///////////
    // Items
    ///////////
    
    public static void preInitItems() {
    	GrowthcraftCellarItems.chievItemDummy = new ItemDefinition( new ItemChievDummy("achievement_dummy") );
    	GrowthcraftCellarItems.yeast = new ItemDefinition( new ItemYeast("yeast") );
    }

    public static void registerItems() {
    	GrowthcraftCellarItems.chievItemDummy.register();
    	GrowthcraftCellarItems.yeast.getItem().setCreativeTab(tabGrowthcraft);
    	GrowthcraftCellarItems.yeast.register();
    }

    public static void registerItemRenders() {
    	GrowthcraftCellarItems.chievItemDummy.registerRender();
    	GrowthcraftCellarItems.yeast.registerRenders(EnumYeast.class);
    }

    ///////////
    // Effects
    ///////////
    
	public static void preInitEffects()
	{
		final EffectRegistry reg = CoreRegistry.instance().getEffectsRegistry();
		reg.register("booze_effect", BoozeEffect.class);
		reg.register("booze_effect_list", BoozeEffect.BoozeEffectList.class);
		reg.register("tipsy", EffectTipsy.class);
	}

	///////////
	// Heat sources
	///////////
	
	public static void preInitHeatSources() {
	}
	
	public static void registerHeatSources() {
		GrowthcraftCellarApis.userApis.getUserHeatSources().addDefault("minecraft", "fire", UserHeatSourceEntry.newWildcardHeat(1.0f))
			.setComment("Fire!");
		GrowthcraftCellarApis.userApis.getUserHeatSources().addDefault("minecraft", "flowing_lava", UserHeatSourceEntry.newWildcardHeat(0.7f))
			.setComment("We need to register both states of lava, this when its flowing");
		GrowthcraftCellarApis.userApis.getUserHeatSources().addDefault("minecraft", "lava", UserHeatSourceEntry.newWildcardHeat(0.7f))
			.setComment("And when its a still pool.");
		GrowthcraftCellarApis.userApis.getUserHeatSources().addDefault(Blocks.MAGMA.getRegistryName(), UserHeatSourceEntry.newWildcardHeat(0.7f))
		    .setComment("Magma Block. Something like lava.");
	}
	
	///////////
	// Yeasts
	///////////
	
	public static void initYeasts() {
    }
	
	public static void registerYeasts() {
		OreDictionary.registerOre("materialYeast", GrowthcraftCellarItems.yeast.getItem());
		for( EnumYeast type : EnumYeast.values() ) {
			ItemStack yeastItemStack = type.asStack();
			OreDictionary.registerOre(type.toOreName(), yeastItemStack);
			CellarRegistry.instance().yeast().addYeast(yeastItemStack);
		}
    }

	///////////
	// Potions
	///////////
	
	public static void registerPotions() {
		// TODO: Add some class PotionDefinition like BlockDefinition or ItemDefinition
		GrowthcraftCellarPotions.potionTipsy = new PotionCellar(false, 0, 1, 0).setPotionName("potion.tipsy");
		registerPotion(GrowthcraftCellarPotions.potionTipsy);
		
		EffectTipsy.achievement = CellarAchievement.GET_DRUNK;
	}
	
	protected static void registerPotion(Potion potion) {
		GameRegistry.register(potion, new ResourceLocation(Reference.MODID, potion.getName()));
	}
	
	///////////
	// Recipes
	///////////
	
    public static void registerRecipes() {
        registerCraftingRecipes();
    }

    private static void registerCraftingRecipes() {


        GameRegistry.addRecipe(GrowthcraftCellarBlocks.cultureJar.asStack(1),
                "BAB", "B B", "BBB",
                'A', Blocks.PLANKS,
                'B', Blocks.GLASS_PANE
        );

        GameRegistry.addShapelessRecipe(
        		GrowthcraftCellarBlocks.brewKettle.asStack(1),
                Items.CAULDRON
        );
        
		GameRegistry.addRecipe(new ShapedOreRecipe(GrowthcraftCellarBlocks.fruitPress.asStack(), "ABA", "CCC", "AAA", 'A', "plankWood", 'B', Blocks.PISTON,'C', "ingotIron"));
		GameRegistry.addRecipe(new ShapedOreRecipe(GrowthcraftCellarBlocks.brewKettle.asStack(), "A", 'A', Items.CAULDRON));
		GameRegistry.addRecipe(new ShapedOreRecipe(GrowthcraftCellarBlocks.fermentBarrel.asStack(), "AAA", "BBB", "AAA", 'B', "plankWood", 'A', "ingotIron"));
		GameRegistry.addRecipe(new ShapedOreRecipe(GrowthcraftCellarBlocks.cultureJar.asStack(), "GAG", "G G", "GGG", 'A', "plankWood", 'G', "paneGlass"));
    }
	
}
