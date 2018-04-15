package growthcraft.core.shared.tileentity.feature;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public interface IItemOperable
{
	public static enum Action
	{
		// Player used the right click (onBlockActivated)
		RIGHT,
		// Player used the left click (onBlockClicked)
		LEFT;
	}

	/**
	 * Tries to place the item from the player into the target
	 *
	 * @param action - whether it was a left click or right click
	 * @param player - the player placing the item
	 * @param stack - the item stack being placed
	 * @return true, if something happened, eg. item was inserted, false otherwise
	 */
	boolean tryPlaceItem(@Nonnull Action action, @Nonnull EntityPlayer player, @Nullable ItemStack stack);

	/**
	 * Tries to remove an item from the target
	 * The stack provided is the player's current item stack, this can be safely
	 * ignored, OR changes the behaviour of the target.
	 *
	 * @param action - whether it was a left click or right click
	 * @param player - player taking the item
	 * @param stack - stack on player's hand currently
	 * @return true, an item was taken, false otherwise
	 */
	boolean tryTakeItem(@Nonnull Action action, @Nonnull EntityPlayer player, @Nullable ItemStack stack);
}
