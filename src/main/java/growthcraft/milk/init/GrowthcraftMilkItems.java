package growthcraft.milk.init;

import growthcraft.milk.api.cheese.CheeseUtils;
import growthcraft.milk.api.definition.EnumCheeseStage;
import growthcraft.milk.api.definition.ICheeseBlockStackFactory;
import growthcraft.milk.api.definition.ICheeseType;
import growthcraft.milk.common.item.ItemBlockCheeseBlock;
import growthcraft.milk.common.item.ItemBlockHangingCurds;
import growthcraft.milk.handlers.EnumHandler;
import growthcraft.milk.handlers.EnumHandler.AgedCheeseTypes;
import growthcraft.milk.handlers.EnumHandler.IceCreamTypes;
import growthcraft.milk.handlers.EnumHandler.SimpleCheeseTypes;
import growthcraft.milk.handlers.EnumHandler.WaxedCheeseTypes;
import growthcraft.milk.handlers.EnumHandler.YogurtTypes;
import growthcraft.milk.items.*;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraftforge.oredict.OreDictionary;
import static growthcraft.core.GrowthcraftCore.tabGrowthcraft;

import growthcraft.core.GrowthcraftCore;
import growthcraft.core.api.definition.IObjectVariant;
import growthcraft.core.common.definition.ItemDefinition;

public class GrowthcraftMilkItems {

    public static ItemDefinition stomach;
    public static ItemDefinition thistleSeed;
    public static ItemDefinition thistle;
    public static ItemDefinition cheeseCloth;
    public static ItemDefinition butter;
    public static ItemDefinition iceCream;
    public static ItemDefinition yogurt;
    public static ItemDefinition starterCulture;
    public static ItemDefinition agedCheeseSlice;
    public static ItemDefinition agedCheeseBlockItem;
    public static ItemDefinition waxedCheeseBlockItem;
    public static ItemDefinition waxedCheeseSlice;
    public static ItemDefinition simpleCheeseSlice;
    public static ItemDefinition agedCheeseCurdsItem;
    public static ItemDefinition waxedCheeseCurdsItem;
    public static ItemDefinition simpleCheeseCurdsItem;

    public static void preInit() {
        thistle = new ItemDefinition( new ItemThistle("thistle") );
        thistleSeed = new ItemDefinition( new ItemSeedThistle("thistle_seed") );
        stomach = new ItemDefinition(new ItemStomach("stomach") );
        cheeseCloth = new ItemDefinition( new ItemCheeseCloth("cheese_cloth") );
        starterCulture = new ItemDefinition( new ItemStarterCulture("starter_culture") );
        butter = new ItemDefinition( new ItemButter("butter", 2, 0.3F, false) );
        iceCream = new ItemDefinition( new ItemIceCream("ice_cream", 2, 0.3F, false) );
        yogurt = new ItemDefinition( new ItemYogurt("yogurt", 2, 0.3F, false) );
        agedCheeseSlice = new ItemDefinition( new ItemAgedCheeseSlice("cheese_aged_slice", 2, 0.3F, false) );
        agedCheeseBlockItem = new ItemDefinition( new ItemBlockCheeseBlock<AgedCheeseTypes>(GrowthcraftMilkBlocks.agedCheeseBlock.getBlock(), AgedCheeseTypes.values()) );
        waxedCheeseBlockItem = new ItemDefinition( new ItemBlockCheeseBlock<WaxedCheeseTypes>(GrowthcraftMilkBlocks.waxedCheeseBlock.getBlock(), WaxedCheeseTypes.values()) );
        waxedCheeseSlice = new ItemDefinition( new ItemWaxedCheeseSlice("cheese_waxed_slice", 2, 0.3F, false) );
        simpleCheeseSlice = new ItemDefinition( new ItemSimpleCheeseSlice("cheese_simple_slice", 2, 0.3F, false) );
        agedCheeseCurdsItem = new ItemDefinition( new ItemBlockHangingCurds<AgedCheeseTypes>(GrowthcraftMilkBlocks.agedCheeseCurds.getBlock(), AgedCheeseTypes.values()) );
        waxedCheeseCurdsItem = new ItemDefinition( new ItemBlockHangingCurds<WaxedCheeseTypes>(GrowthcraftMilkBlocks.waxedCheeseCurds.getBlock(), WaxedCheeseTypes.values()) );
        simpleCheeseCurdsItem = new ItemDefinition( new ItemBlockHangingCurds<SimpleCheeseTypes>(GrowthcraftMilkBlocks.simpleCheeseCurds.getBlock(), SimpleCheeseTypes.values()) );
    }

    public static void register() {
    	thistle.getItem().setCreativeTab(tabGrowthcraft);
    	thistle.register();
    	thistleSeed.getItem().setCreativeTab(tabGrowthcraft);
    	thistleSeed.register();
        stomach.getItem().setCreativeTab(tabGrowthcraft);
        stomach.register();
        cheeseCloth.getItem().setCreativeTab(tabGrowthcraft);
        cheeseCloth.register();
        starterCulture.getItem().setCreativeTab(GrowthcraftCore.tabGrowthcraft);
        starterCulture.register();
        butter.getItem().setCreativeTab(GrowthcraftCore.tabGrowthcraft);
        butter.register();
        iceCream.getItem().setCreativeTab(tabGrowthcraft);
        iceCream.register();
        yogurt.getItem().setCreativeTab(tabGrowthcraft);
        yogurt.register();
        agedCheeseSlice.getItem().setCreativeTab(tabGrowthcraft);
        agedCheeseSlice.register();
        waxedCheeseSlice.getItem().setCreativeTab(tabGrowthcraft);
        waxedCheeseSlice.register();
        simpleCheeseSlice.getItem().setCreativeTab(tabGrowthcraft);
        simpleCheeseSlice.register();
        agedCheeseBlockItem.register(GrowthcraftMilkBlocks.agedCheeseBlock.getBlock().getRegistryName() /*new ResourceLocation(Reference.MODID, "cheese_aged")*/);
        waxedCheeseBlockItem.register(GrowthcraftMilkBlocks.waxedCheeseBlock.getBlock().getRegistryName());
        agedCheeseCurdsItem.register(GrowthcraftMilkBlocks.agedCheeseCurds.getBlock().getRegistryName());
        waxedCheeseCurdsItem.register(GrowthcraftMilkBlocks.waxedCheeseCurds.getBlock().getRegistryName());
        simpleCheeseCurdsItem.register(GrowthcraftMilkBlocks.simpleCheeseCurds.getBlock().getRegistryName());
        
        registerOres();
    }
    
	private static void registerOres()
	{
		OreDictionary.registerOre("rennetSource", thistle.getItem());
		OreDictionary.registerOre("rennetSource", stomach.asStack());
		OreDictionary.registerOre("materialStarterCulture", starterCulture.asStack());
		OreDictionary.registerOre("materialCheeseCloth", cheeseCloth.asStack());
		OreDictionary.registerOre("foodFruit", Items.MELON);
		OreDictionary.registerOre("foodFruit", Items.CHORUS_FRUIT);
	}
	
	public static <ET extends ICheeseType & IStringSerializable> void registerCheeseWheelItemRenders( ItemDefinition itemDef, ET[] types ) {
        for( ET type : types ) {
        	ICheeseBlockStackFactory blockStackFactory = type.getCheeseBlocks();
        	for( EnumCheeseStage stage : EnumCheeseStage.values() ) {
        		if( blockStackFactory.getInitialStage() != EnumCheeseStage.UNWAXED &&
        			stage == EnumCheeseStage.UNWAXED )
        			continue;	// ignore unwaxed stage for non waxable items
        		
        		if( stage == EnumCheeseStage.CUT ) {
        			for( int i = 1; i <= CheeseUtils.MAX_CUTS; i ++ ) {
        				int meta = CheeseUtils.getItemMetaFor(type, i, stage);
        				itemDef.registerRender(meta, type.getName() + "_" + stage.getName() );
        			}
        		}
        		else {
    				int meta = CheeseUtils.getItemMetaFor(type, CheeseUtils.MAX_CUTS, stage);
    				itemDef.registerRender(meta, type.getName() + "_" + stage.getName());
        		}
        	}
        }
	}
	
	public static <ET extends ICheeseType & IStringSerializable> void registerCheeseCurdsItemRenders( ItemDefinition itemDef, ET[] types ) {
        for( ET type : types ) {
        	int meta = CheeseUtils.getItemMetaFor(type, 4, EnumCheeseStage.UNAGED);
        	itemDef.registerRender(meta, type.getName() + "_curds");
        }
	}

    public static void registerRenders() {
    	thistle.registerRender();
    	thistleSeed.registerRender();
        stomach.registerRender();
        cheeseCloth.registerRender();
        starterCulture.registerRender();

        butter.registerRenders(EnumHandler.ButterTypes.class);
        iceCream.registerRenders(IceCreamTypes.class);
        yogurt.registerRenders(YogurtTypes.class);
        agedCheeseSlice.registerRenders(AgedCheeseTypes.class);
        waxedCheeseSlice.registerRenders(WaxedCheeseTypes.class);
        simpleCheeseSlice.registerRenders(SimpleCheeseTypes.class);
        
        registerCheeseWheelItemRenders(agedCheeseBlockItem, AgedCheeseTypes.values());
        registerCheeseWheelItemRenders(waxedCheeseBlockItem, WaxedCheeseTypes.values());
        
        registerCheeseCurdsItemRenders(agedCheeseCurdsItem, AgedCheeseTypes.values());
        registerCheeseCurdsItemRenders(waxedCheeseCurdsItem, WaxedCheeseTypes.values());
    }
    
	public static void registerModelBakeryVariants() {
		butter.registerModelBakeryVariants(EnumHandler.ButterTypes.class);
//		agedCheeseBlockItem.registerModelBakeryVariants(AgedCheeseTypes.class);
	}

	public static boolean canWax(WaxedCheeseTypes type, ItemStack stack) {
		String oreName;
		switch( type ) {
		case MONTEREY:
			oreName = "materialBeeswaxBlack";
			break;
		default:
		case CHEDDAR:
			oreName = "materialBeeswaxRed";
			break;
		}
		
		int[] oreIDsStack = OreDictionary.getOreIDs(stack);
		int oreIDWax = OreDictionary.getOreID(oreName);
		
		for( int id : oreIDsStack ) {
			if( id == oreIDWax )
				return true;
		}
		return false;
	}

}
