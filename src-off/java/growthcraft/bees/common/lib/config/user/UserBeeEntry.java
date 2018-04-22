package growthcraft.bees.common.lib.config.user;

import growthcraft.core.shared.config.schema.ICommentable;
import growthcraft.core.shared.config.schema.ItemKeySchema;
import net.minecraft.item.ItemStack;

public class UserBeeEntry implements ICommentable
{
	public String comment = "";
	public ItemKeySchema item;

	public UserBeeEntry(ItemStack stack)
	{
		this.item = new ItemKeySchema(stack);
	}

	public UserBeeEntry() {}

	@Override
	public String getComment()
	{
		return comment;
	}

	@Override
	public void setComment(String com)
	{
		this.comment = com;
	}

	@Override
	public String toString()
	{
		return String.format("UserBeeEntry(block: `%s`)", item);
	}
}
