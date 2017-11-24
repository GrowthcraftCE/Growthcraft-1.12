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
}
