package growthcraft.core.shared.io.nbt;

import net.minecraft.nbt.NBTTagCompound;

public interface INBTItemSerializable {
    /**
     * @param data - tag compound to read from
     */
    void readFromNBTForItem(NBTTagCompound data);

    /**
     * @param data - tag compound to write to
     */
    void writeToNBTForItem(NBTTagCompound data);
}
