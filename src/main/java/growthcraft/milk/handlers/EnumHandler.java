package growthcraft.milk.handlers;

import growthcraft.core.api.definition.IFluidStackFactory;
import growthcraft.core.api.definition.IItemStackFactory;
import growthcraft.core.api.definition.IObjectVariant;
import growthcraft.milk.api.definition.ICheeseBlockStackFactory;
import growthcraft.milk.api.definition.ICheeseCurdStackFactory;
import growthcraft.milk.api.definition.ICheeseItemStackFactory;
import growthcraft.milk.api.definition.ICheeseType;
import growthcraft.milk.init.GrowthcraftMilkItems;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;

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

    public enum WaxedCheeseTypes implements IStringSerializable, ICheeseType {
        CHEDDAR(0, "cheddar", 0xed9200),
        MONTEREY(1, "monterey", 0xf5f5da);

        private int ID;
        private String NAME;
        private int color;

        WaxedCheeseTypes(int id, String name, int color) {
            this.ID = id;
            this.NAME = name;
            this.color = color;
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
					return new ItemStack(GrowthcraftMilkItems.itemAgedCheeseSlice,size,getID());
				}
			};
		}

		@Override
		public ICheeseBlockStackFactory getCheeseBlocks() {
			return null;
		}

		@Override
		public ICheeseCurdStackFactory getCurdBlocks() {
			return null;
		}

		@Override
		public IFluidStackFactory getFluids() {
			return null;
		}

    }

    public enum SimpleCheeseTypes implements IStringSerializable, ICheeseType {
        RICOTTA(0, "ricotta", 0xc8c8c5);

        private int ID;
        private String NAME;
        private int color;

        SimpleCheeseTypes(int id, String name, int color) {
            this.ID = id;
            this.NAME = name;
            this.color = color;
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
					return new ItemStack(GrowthcraftMilkItems.itemSimpleCheeseSlice,size,getID());
				}
			};
		}

		@Override
		public ICheeseBlockStackFactory getCheeseBlocks() {
			return null;
		}

		@Override
		public ICheeseCurdStackFactory getCurdBlocks() {
			return null;
		}

		@Override
		public IFluidStackFactory getFluids() {
			return null;
		}
    }

    public enum AgedCheeseTypes implements IStringSerializable, ICheeseType {
        GORGONZOLA(0, "gorgonzola", 0xeae7de),
        EMMENTALER(1, "emmentaler", 0xddddbc),
        APPENZELLER(2, "appenzeller", 0xf3e2a7),
        ASIAGO(3, "asiago", 0xbfb68d),
        PARMESAN(4, "parmesan", 0xd8d5c6);

        private int ID;
        private String NAME;
        private int color;

        AgedCheeseTypes(int id, String name, int color) {
            this.ID = id;
            this.NAME = name;
            this.color = color;
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
					return new ItemStack(GrowthcraftMilkItems.itemAgedCheeseSlice,size,getID());
				}
			};
		}

		@Override
		public ICheeseBlockStackFactory getCheeseBlocks() {
			return null;
		}

		@Override
		public ICheeseCurdStackFactory getCurdBlocks() {
			return null;
		}

		@Override
		public IFluidStackFactory getFluids() {
			return null;
		}
    }
}
