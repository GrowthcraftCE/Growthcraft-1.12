package growthcraft.core.api;

import javax.annotation.Nonnull;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

import growthcraft.core.api.nbt.INBTSerializableContext;
import net.minecraft.nbt.NBTTagCompound;

public class AbstractClassRegistry<T extends INBTSerializableContext> {
	// REVISE_ME 0
	
	/**
	 * Error raised when an attempt is made to register a class under an existing name
	 */
	public static class ClassRegisteredException extends RuntimeException
	{
		public static final long serialVersionUID = 1L;

		public ClassRegisteredException(@Nonnull String msg)
		{
			super(msg);
		}

		public ClassRegisteredException() {}
	}

	private BiMap<String, Class<? extends T>> effects = HashBiMap.create();

	public Class<? extends T> getClass(@Nonnull String name)
	{
		return effects.get(name);
	}

	public String getName(@Nonnull Class<?> klass)
	{
		return effects.inverse().get(klass);
	}

	public void register(@Nonnull String name, @Nonnull Class<? extends T> klass)
	{
		if (effects.containsKey(name))
		{
			final Class<? extends T> effect = getClass(name);
			throw new ClassRegisteredException("Cannot register " + klass + ", Effect " + effect + " is already registered to " + name);
		}
		else
		{
			effects.put(name, klass);
		}
	}

	/**
	 * Mother of hacks batman!
	 *
	 * @param data - nbt data to load from
	 * @param name - key to load data from
	 * @return T an instance of the class to reload
	 */
	public T loadObjectFromNBT(@Nonnull NBTTagCompound data, @Nonnull String name)
	{
		final NBTTagCompound effectData = data.getCompoundTag(name);
		final String factoryName = effectData.getString("__name__");
		final Class<? extends T> klass = getClass(factoryName);

		T instance = null;

		// This should be a utility method in the future or something, its used so much now...
		try
		{
			instance = klass.newInstance();
		}
		catch (InstantiationException e)
		{
			throw new IllegalStateException("Failed to create a new instance of an illegal class " + klass, e);
		}
		catch (IllegalAccessException e)
		{
			throw new IllegalStateException("Failed to create a new instance of " + klass + ", because lack of permissions", e);
		}

		instance.readFromNBT(data, name);

		return instance;
	}

}
