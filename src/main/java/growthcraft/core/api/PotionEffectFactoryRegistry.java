package growthcraft.core.api;

import javax.annotation.Nonnull;

import growthcraft.core.api.effect.IPotionEffectFactory;
import net.minecraft.nbt.NBTTagCompound;

public class PotionEffectFactoryRegistry extends AbstractClassRegistry<IPotionEffectFactory>
{
	// REVISE_ME 0
	// INITIALIZE
	
//	@Override
	public IPotionEffectFactory loadPotionEffectFactoryFromNBT(@Nonnull NBTTagCompound data, @Nonnull String name)
	{
		return loadObjectFromNBT(data, name);
	}
}
