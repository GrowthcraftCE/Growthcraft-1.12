package growthcraft.milk.handlers;

import net.minecraft.util.IStringSerializable;

public class EnumHandler {

    public enum ButterTypes implements IStringSerializable {
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

        public int getID() {
            return this.ID;
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

    public enum WaxedCheeseTypes implements IStringSerializable {
        CHEDDAR(0, "cheddar"),
        MONTEREY(1, "monterey");

        private int ID;
        private String NAME;

        WaxedCheeseTypes(int id, String name) {
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

    public enum SimpleCheeseTypes implements IStringSerializable {
        RICOTTA(0, "ricotta");

        private int ID;
        private String NAME;

        SimpleCheeseTypes(int id, String name) {
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

    public enum AgedCheeseTypes implements IStringSerializable {
        GORGONZOLA(0, "gorgonzola"),
        EMMENTALER(1, "emmentaler"),
        APPENZELLER(2, "appenzeller"),
        ASIAGO(3, "asiago"),
        PARMESAN(4, "parmesan");

        private int ID;
        private String NAME;

        AgedCheeseTypes(int id, String name) {
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
}
