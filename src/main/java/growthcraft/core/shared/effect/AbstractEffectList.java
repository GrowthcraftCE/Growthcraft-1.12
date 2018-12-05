package growthcraft.core.shared.effect;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import growthcraft.core.shared.io.nbt.NBTHelper;
import net.minecraft.nbt.NBTTagCompound;

/**
 * Base class for defining Effect lists
 */
public abstract class AbstractEffectList extends AbstractEffect
{
	// REVISE_ME 0
	
	protected List<IEffect> effects = new ArrayList<IEffect>();

	/**
	 * Clears all the effects in this list
	 *
	 * @return this
	 */
	@SuppressWarnings("unchecked")
	public <T extends AbstractEffectList> T clear()
	{
		effects.clear();
		return (T)this;
	}

	/**
	 * Adds the given list of effects to this EffectList
	 *
	 * @return this
	 */
	@SuppressWarnings("unchecked")
	public <T extends AbstractEffectList> T concat(@Nonnull List<IEffect> otherEffects)
	{
		effects.addAll(otherEffects);
		return (T)this;
	}

	/**
	 * Adds the given EffectList to this EffectList
	 *
	 * @return this
	 */
	@SuppressWarnings("unchecked")
	public <T extends AbstractEffectList> T concat(@Nonnull AbstractEffectList list)
	{
		effects.addAll(list.effects);
		return (T)this;
	}

	/**
	 * @return this
	 */
	@SuppressWarnings("unchecked")
	public <T extends AbstractEffectList> T add(@Nonnull IEffect effect)
	{
		effects.add(effect);
		return (T)this;
	}

	/**
	 * Retrieves an effect at the given position
	 *
	 * @param index - position to place the effect
	 * @return effect
	 */
	public IEffect get(int index)
	{
		return effects.get(index);
	}

	/**
	 * Sets an effect at the given index
	 *
	 * @param index - position to place the effect
	 * @param effect - effect to set
	 * @return this
	 */
	@SuppressWarnings("unchecked")
	public <T extends AbstractEffectList> T set(int index, @Nonnull IEffect effect)
	{
		effects.set(index, effect);
		return (T)this;
	}

	/**
	 * Returns the size of the EffectList
	 *
	 * @return size of the effect list
	 */
	public int size()
	{
		return effects.size();
	}

	@Override
	protected void readFromNBT(NBTTagCompound data)
	{
		effects.clear();
		NBTHelper.loadEffectsList(effects, data);
	}

	@Override
	protected void writeToNBT(NBTTagCompound data)
	{
		NBTHelper.writeEffectsList(data, effects);
	}
}
