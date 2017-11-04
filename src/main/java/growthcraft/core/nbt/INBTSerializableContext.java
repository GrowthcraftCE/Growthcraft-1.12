package growthcraft.core.nbt;

import net.minecraft.nbt.NBTTagCompound;

public interface INBTSerializableContext
{
	// REVISE_ME 0: Move to utils
	
	void readFromNBT(NBTTagCompound data, String name);
	void writeToNBT(NBTTagCompound data, String name);
}
