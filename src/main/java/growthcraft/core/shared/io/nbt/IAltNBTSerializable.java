package growthcraft.core.shared.io.nbt;

import net.minecraft.nbt.NBTTagCompound;

public interface IAltNBTSerializable {
    /**
     * @param data - tag compound to read from
     */
    void readFromNBT(NBTTagCompound data);

    /**
     * @param data - tag compound to write to
     */
    NBTTagCompound writeToNBT(NBTTagCompound data);
}
