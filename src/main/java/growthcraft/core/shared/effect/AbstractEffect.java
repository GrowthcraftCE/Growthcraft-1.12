package growthcraft.core.shared.effect;

import java.util.List;

import growthcraft.core.shared.CoreRegistry;
import net.minecraft.nbt.NBTTagCompound;

/**
 * Because sometimes you want an Effect that does ABSOLUTELY NOTHING.
 */
public abstract class AbstractEffect implements IEffect
{
	// REVISE_ME 0
	
	protected boolean descriptionEnabled = true;

	@SuppressWarnings("unchecked")
	public <T extends AbstractEffect> T toggleDescription(boolean bool)
	{
		this.descriptionEnabled = bool;
		return (T)this;
	}

	protected abstract void readFromNBT(NBTTagCompound data);

	@Override
	public void readFromNBT(NBTTagCompound data, String name)
	{
		if (data.hasKey(name))
		{
			final NBTTagCompound effectData = data.getCompoundTag(name);
			readFromNBT(effectData);
		}
		else
		{
			// LOG error
		}
	}

	protected abstract void writeToNBT(NBTTagCompound data);

	@Override
	public void writeToNBT(NBTTagCompound data, String name)
	{
		final NBTTagCompound target = new NBTTagCompound();
		final String effectName = CoreRegistry.instance().getEffectsRegistry().getName(this.getClass());
		// This is a VERY important field, this is how the effects will reload their correct class.
		target.setString("__name__", effectName);
		writeToNBT(target);

		data.setTag(name, target);
	}

	protected abstract void getActualDescription(List<String> list);

	@Override
	public void getDescription(List<String> list)
	{
		if (descriptionEnabled)
		{
			getActualDescription(list);
		}
	}
}
