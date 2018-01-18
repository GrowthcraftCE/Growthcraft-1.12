package growthcraft.milk.handlers;

import growthcraft.core.api.definition.IItemStackFactory;
import growthcraft.core.api.definition.IObjectVariant;
import growthcraft.milk.Reference;
import growthcraft.milk.api.cheese.CheeseUtils;
import growthcraft.milk.api.definition.EnumCheeseStage;
import growthcraft.milk.api.definition.ICheeseBlockStackFactory;
import growthcraft.milk.api.definition.ICheeseCurdStackFactory;
import growthcraft.milk.api.definition.ICheeseFluidStackFactory;
import growthcraft.milk.api.definition.ICheeseItemStackFactory;
import growthcraft.milk.api.definition.ICheeseType;
import growthcraft.milk.init.GrowthcraftMilkBlocks;
import growthcraft.milk.init.GrowthcraftMilkFluids;
import growthcraft.milk.init.GrowthcraftMilkItems;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;

public class EnumHandler {

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

    public enum IceCreamTypes implements IStringSerializable {
        PLAIN(0, "plain"),
        CHOCOLATE(1, "chocolate"),
        GRAPE(2, "grape"),
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

        public int getID() {
            return this.ID;
        }
    }

    public enum YogurtTypes implements IStringSerializable {
        PLAIN(0, "plain"),
        CHOCOLATE(1, "chocolate"),
        GRAPE(2, "grape"),
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

        public int getID() {
            return this.ID;
        }
    }

    public enum WaxedCheeseTypes implements ICheeseType, IObjectVariant, IStringSerializable {
        CHEDDAR(0, "cheddar", 0xed9200),
        MONTEREY(1, "monterey", 0xf5f5da);

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
					return new ItemStack(GrowthcraftMilkItems.itemAgedCheeseSlice,size,getVariantID());
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
					return GrowthcraftMilkBlocks.waxedCheeseCurds.asStack(size, CheeseUtils.getItemMetaFor(WaxedCheeseTypes.this, 0, EnumCheeseStage.UNAGED));
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
					return new ItemStack(GrowthcraftMilkItems.itemSimpleCheeseSlice,size,getVariantID());
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
					return GrowthcraftMilkBlocks.simpleCheeseCurds.asStack(size, CheeseUtils.getItemMetaFor(SimpleCheeseTypes.this, 0, EnumCheeseStage.UNAGED));
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
        PARMESAN(4, "parmesan", 0xd8d5c6);

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
					return new ItemStack(GrowthcraftMilkItems.itemAgedCheeseSlice,size,getVariantID());
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
					return GrowthcraftMilkBlocks.agedCheeseCurds.asStack(size, CheeseUtils.getItemMetaFor(AgedCheeseTypes.this, 0, EnumCheeseStage.UNAGED));
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
