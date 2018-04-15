package growthcraft.core.common.lib.effect;

import javax.annotation.Nonnull;

import growthcraft.core.common.lib.AbstractClassRegistry;
import net.minecraft.nbt.NBTTagCompound;

public class PotionEffectFactoryRegistry extends AbstractClassRegistry<IPotionEffectFactory>
{
	// REVISE_ME 0
	
//	@Override
	public IPotionEffectFactory loadPotionEffectFactoryFromNBT(@Nonnull NBTTagCompound data, @Nonnull String name)
	{
		return loadObjectFromNBT(data, name);
	}
}
