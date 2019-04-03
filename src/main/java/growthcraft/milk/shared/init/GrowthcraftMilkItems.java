package growthcraft.milk.shared.init;

import growthcraft.cellar.shared.item.ItemBoozeBottle;
import growthcraft.core.shared.definition.IItemStackFactory;
import growthcraft.core.shared.definition.IObjectVariant;
import growthcraft.core.shared.definition.ItemDefinition;
import growthcraft.core.shared.definition.ItemTypeDefinition;
import growthcraft.milk.shared.Reference;
import growthcraft.milk.shared.cheese.CheeseUtils;
import growthcraft.milk.shared.definition.*;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;

public class GrowthcraftMilkItems {
	
	private GrowthcraftMilkItems() {}

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

    public static ItemTypeDefinition<ItemBoozeBottle> kumisBottle;

	private static boolean canWax(WaxedCheeseTypes type, ItemStack stack) {
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
    
    public enum ButterTypes implements IStringSerializable, IObjectVariant, IItemStackFactory {
        UNSALTED(0, "unsalted"),
        SALTED(1, "salted");

        private int ID;
        private String NAME;

        ButterTypes(int id, String name) {
            this.ID = id;
            this.NAME = name;
        }

        @Override
        public String toString() {
            return getName();
        }

        @Override
        public String getName() {
            return this.NAME;
        }

		@Override
		public int getVariantID() {
			return this.ID;
		}

		@Override
		public ItemStack asStack(int size) {
			return GrowthcraftMilkItems.butter.asStack(1, getVariantID());
		}

		@Override
		public ItemStack asStack() {
			return asStack(1);
		}
    }

    public enum IceCreamTypes implements IStringSerializable, IObjectVariant, IItemStackFactory {
        PLAIN(0, "plain"),
        CHOCOLATE(1, "chocolate"),
        GRAPEPURPLE(2, "grape_purple"),
        APPLE(3, "apple"),
        HONEY(4, "honey"),
        WATERMELON(5, "watermelon");

        private int ID;
        private String NAME;

        IceCreamTypes(int id, String name) {
            this.ID = id;
            this.NAME = name;
        }

        @Override
        public String toString() {
            return getName();
        }

        @Override
        public String getName() {
            return this.NAME;
        }

		@Override
        public int getVariantID() {
            return this.ID;
        }

		@Override
		public ItemStack asStack(int size) {
			return GrowthcraftMilkItems.iceCream.asStack(size, getVariantID());
		}

		@Override
		public ItemStack asStack() {
			return asStack(1);
		}
    }

    public enum YogurtTypes implements IStringSerializable, IObjectVariant, IItemStackFactory {
        PLAIN(0, "plain"),
        CHOCOLATE(1, "chocolate"),
        GRAPEPURPLE(2, "grape_purple"),
        APPLE(3, "apple"),
        HONEY(4, "honey"),
        WATERMELON(5, "watermelon");

        private int ID;
        private String NAME;

        YogurtTypes(int id, String name) {
            this.ID = id;
            this.NAME = name;
        }

        @Override
        public String toString() {
            return getName();
        }

        @Override
        public String getName() {
            return this.NAME;
        }

		@Override
        public int getVariantID() {
            return this.ID;
        }
		
		@Override
		public ItemStack asStack(int size) {
			return GrowthcraftMilkItems.yogurt.asStack(size, getVariantID());
		}

		@Override
		public ItemStack asStack() {
			return asStack(1);
		}
	}

    public enum WaxedCheeseTypes implements ICheeseType, IObjectVariant, IStringSerializable {
        CHEDDAR(0, "cheddar", 0xed9200),
        MONTEREY(1, "monterey", 0xf5f5da),
	    GOUDA(2, "gouda", 0xEEDC82),
	    PROVOLONE(3, "provolone", 0xFFFDD0);

        private int ID;
        private ResourceLocation name;
        private int color;

        WaxedCheeseTypes(int id, String name, int color) {
            this.ID = id;
            this.name = new ResourceLocation( Reference.MODID, name );
            this.color = color;
        }

        @Override
        public String toString() {
            return getName();
        }

        @Override
        public String getName() {
            return this.name.getResourcePath();
        }
        
		@Override
		public ResourceLocation getRegistryName() {
			return this.name;
		}

		@Override
        public int getVariantID() {
            return this.ID;
        }

		@Override
		public boolean canWax(ItemStack stack) {
			return GrowthcraftMilkItems.canWax(this, stack);
		}

		@Override
		public int getColor() {
			return this.color;
		}

		@Override
		public ICheeseItemStackFactory getCheeseItems() {
			return new ICheeseItemStackFactory() {
				@Override
				public ItemStack asStack(int size) {
					// return new ItemStack(GrowthcraftMilkItems.itemWaxedCheeseSlice,size,getVariantID());
					return GrowthcraftMilkItems.waxedCheeseSlice.asStack(size, getVariantID());
				}
			};
		}

		@Override
		public ICheeseBlockStackFactory getCheeseBlocks() {
			return new ICheeseBlockStackFactory() {
				@Override
				public ItemStack asStackForStage(int size, int slices, EnumCheeseStage stage) {
					return GrowthcraftMilkBlocks.waxedCheeseBlock.asStack(size, CheeseUtils.getItemMetaFor(WaxedCheeseTypes.this, slices, stage));
				}

				@Override
				public EnumCheeseStage getInitialStage() {
					return EnumCheeseStage.UNWAXED;
				}
			};
		}

		@Override
		public ICheeseCurdStackFactory getCurdBlocks() {
			return new ICheeseCurdStackFactory() {
				@Override
				public ItemStack asStack(int size) {
					return GrowthcraftMilkBlocks.waxedCheeseCurds.asStack(size, CheeseUtils.getItemMetaFor(WaxedCheeseTypes.this, 4, EnumCheeseStage.UNAGED));
				}
			};
		}

		@Override
		public ICheeseFluidStackFactory getFluids() {
			return new ICheeseFluidStackFactory() {
				@Override
				public FluidStack asFluidStack(int amount) {
					return GrowthcraftMilkFluids.cheesesWaxed.get(WaxedCheeseTypes.this).asFluidStack(amount);
				}
			};
		}

    }

    public enum SimpleCheeseTypes implements IStringSerializable, ICheeseType, IObjectVariant {
        RICOTTA(0, "ricotta", 0xc8c8c5);

        private int ID;
        private ResourceLocation name;
        private int color;

        SimpleCheeseTypes(int id, String name, int color) {
            this.ID = id;
            this.name = new ResourceLocation( Reference.MODID, name );
            this.color = color;
        }

        @Override
        public String toString() {
            return getName();
        }

        @Override
        public String getName() {
            return this.name.getResourcePath();
        }
        
		@Override
		public ResourceLocation getRegistryName() {
			return this.name;
		}

		@Override
        public int getVariantID() {
            return this.ID;
        }

		@Override
		public boolean canWax(ItemStack stack) {
			return false;
		}

		@Override
		public int getColor() {
			return this.color;
		}

		@Override
		public ICheeseItemStackFactory getCheeseItems() {
			return new ICheeseItemStackFactory() {
				@Override
				public ItemStack asStack(int size) {
//					return new ItemStack(GrowthcraftMilkItems.itemSimpleCheeseSlice,size,getVariantID());
					return GrowthcraftMilkItems.simpleCheeseSlice.asStack(size, getVariantID());
				}
			};
		}

		@Override
		public ICheeseBlockStackFactory getCheeseBlocks() {
			return null;
		}

		@Override
		public ICheeseCurdStackFactory getCurdBlocks() {
			return new ICheeseCurdStackFactory() {
				@Override
				public ItemStack asStack(int size) {
					return GrowthcraftMilkBlocks.simpleCheeseCurds.asStack(size, CheeseUtils.getItemMetaFor(SimpleCheeseTypes.this, 4, EnumCheeseStage.UNAGED));
				}
			};
		}

		@Override
		public ICheeseFluidStackFactory getFluids() {
			return new ICheeseFluidStackFactory() {
				@Override
				public FluidStack asFluidStack(int amount) {
					return GrowthcraftMilkFluids.cheesesSimple.get(SimpleCheeseTypes.this).asFluidStack(amount);
				}
			};
		}
    }

    public enum AgedCheeseTypes implements IStringSerializable, ICheeseType, IObjectVariant {
        GORGONZOLA(0, "gorgonzola", 0xeae7de),
        EMMENTALER(1, "emmentaler", 0xddddbc),
        APPENZELLER(2, "appenzeller", 0xf3e2a7),
        ASIAGO(3, "asiago", 0xbfb68d),
        PARMESAN(4, "parmesan", 0xd8d5c6),
	    CASU_MARZU(5, "casu_marzu", 0xF1E9D2);

        private int ID;
        private ResourceLocation name;
        private int color;

        AgedCheeseTypes(int id, String name, int color) {
            this.ID = id;
            this.name = new ResourceLocation( Reference.MODID, name );
            this.color = color;
        }

        @Override
        public String toString() {
            return getName();
        }

        @Override
        public String getName() {
            return this.name.getResourcePath();
        }
        
		@Override
		public ResourceLocation getRegistryName() {
			return this.name;
		}

		@Override
        public int getVariantID() {
            return this.ID;
        }

		@Override
		public boolean canWax(ItemStack stack) {
			return false;
		}

		@Override
		public int getColor() {
			return this.color;
		}

		@Override
		public ICheeseItemStackFactory getCheeseItems() {
			return new ICheeseItemStackFactory() {
				@Override
				public ItemStack asStack(int size) {
//					return new ItemStack(GrowthcraftMilkItems.itemAgedCheeseSlice,size,getVariantID());
					return GrowthcraftMilkItems.agedCheeseSlice.asStack(size, getVariantID());
				}
			};
		}

		@Override
		public ICheeseBlockStackFactory getCheeseBlocks() {
			return new ICheeseBlockStackFactory() {

				@Override
				public ItemStack asStackForStage(int size, int slices, EnumCheeseStage stage) {
					return GrowthcraftMilkBlocks.agedCheeseBlock.asStack(size, CheeseUtils.getItemMetaFor(AgedCheeseTypes.this, slices, stage));
				}

				@Override
				public EnumCheeseStage getInitialStage() {
					return EnumCheeseStage.UNAGED;
				}
			};
		}

		@Override
		public ICheeseCurdStackFactory getCurdBlocks() {
			return new ICheeseCurdStackFactory() {
				@Override
				public ItemStack asStack(int size) {
					return GrowthcraftMilkBlocks.agedCheeseCurds.asStack(size, CheeseUtils.getItemMetaFor(AgedCheeseTypes.this, 4, EnumCheeseStage.UNAGED));
				}
			};
		}

		@Override
		public ICheeseFluidStackFactory getFluids() {
			return new ICheeseFluidStackFactory() {
				@Override
				public FluidStack asFluidStack(int amount) {
					return GrowthcraftMilkFluids.cheesesAged.get(AgedCheeseTypes.this).asFluidStack(amount);
				}
			};
		}
    }
   

}
