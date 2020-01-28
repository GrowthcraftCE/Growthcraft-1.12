package growthcraft.milk.shared.definition;

import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IStringSerializable;

public enum EnumCheeseStage implements IStringSerializable {
    UNAGED("unaged"),
    AGED("aged"),
    CUT("cut"),
    UNWAXED("unwaxed");

    public static final EnumCheeseStage[] VALUES = values();

    public final String name;
    public final int index;

    private EnumCheeseStage(String s) {
        this.name = s;
        this.index = ordinal();
    }

    public NBTTagCompound writeToNBT(NBTTagCompound tag) {
        tag.setInteger("cheese_stage_id", index);
        return tag;
    }

    public boolean writeToStream(ByteBuf stream) {
        stream.writeInt(index);
        return false;
    }

    /**
     * Returns a EnumCheeseStage given an id, if the id is invalid, returns CHEDDAR
     *
     * @param id cheese id
     * @return cheese
     */
    public static EnumCheeseStage getSafeById(int id) {
        if (id >= 0 && id < VALUES.length) return VALUES[id];
        return UNAGED;
    }

    public static EnumCheeseStage loadFromStream(ByteBuf stream) {
        final int id = stream.readInt();
        return getSafeById(id);
    }

    public static EnumCheeseStage loadFromNBT(NBTTagCompound nbt) {
        final int id = nbt.getInteger("cheese_stage_id");
        return getSafeById(id);
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String toString() {
        return getName();
    }
}