package growthcraft.core.shared.tileentity.feature;

import net.minecraft.nbt.NBTTagCompound;

public interface ITileNamedFluidTanks {
    /**
     * This should write a NBTTagList to the provided tag under the key "tank_names"
     *
     * @param tag - tag to write to
     */
    void writeFluidTankNamesToTag(NBTTagCompound tag);
}
