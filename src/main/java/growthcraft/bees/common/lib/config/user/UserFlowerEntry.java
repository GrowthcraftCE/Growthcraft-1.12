package growthcraft.bees.common.lib.config.user;

import growthcraft.core.shared.config.schema.BlockKeySchema;
import growthcraft.core.shared.config.schema.ICommentable;
import net.minecraft.block.Block;

public class UserFlowerEntry implements ICommentable
{
	public String comment = "";
	public String entry_type = "generic";
	public BlockKeySchema block;

	public UserFlowerEntry(String modId, String name, int meta)
	{
		this.block = new BlockKeySchema(modId, name, meta);
	}

	public UserFlowerEntry(Block pBlock, int meta)
	{
		this.block = new BlockKeySchema(pBlock, meta);
	}

	public UserFlowerEntry() {}

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

	public String getEntryType()
	{
		return entry_type;
	}

	public UserFlowerEntry setEntryType(String type)
	{
		this.entry_type = type;
		return this;
	}

	@Override
	public String toString()
	{
		return String.format("UserFlowerEntry(block: `%s`)", block);
	}
}
