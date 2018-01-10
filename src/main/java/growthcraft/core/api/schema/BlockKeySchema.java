package growthcraft.core.api.schema;

import growthcraft.core.api.utils.BlockKey;
import net.minecraft.block.Block;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class BlockKeySchema implements ICommentable, IValidatable
{
	public String comment;
	public String mod_id;
	public String name;
	public int meta;

	public BlockKeySchema(String pmod_id, String pname, int pmeta)
	{
		this.mod_id = pmod_id;
		this.name = pname;
		this.meta = pmeta;
		this.comment = "";
	}

	public BlockKeySchema(Block block, int pmeta)
	{
//		final ResourceLocation resloc = new ResourceLocation(block.getUnlocalizedName());
//		final UniqueIdentifier uuid = GameRegistry.findUniqueIdentifierFor(block);
		final ResourceLocation resloc = block.getRegistryName();
		this.mod_id = resloc.getResourceDomain(); //uuid.modId;
		this.name = resloc.getResourcePath(); // uuid.name;
		this.meta = pmeta;
		this.comment = block.getLocalizedName();
	}

	public BlockKeySchema(BlockKey blockKey)
	{
		this(blockKey.block, blockKey.meta);
	}

	public BlockKeySchema()
	{
		this.comment = "";
	}

	public Block getBlock()
	{
		if (mod_id != null && name != null)
		{
			return Block.getBlockFromName(mod_id + ":" + name);
			// return GameRegistry.findBlock(mod_id, name);
		}
		return null;
	}

	public BlockKey toBlockKey()
	{
		final Block block = getBlock();
		if (block != null)
		{
			return new BlockKey(block, meta);
		}
		return null;
	}

	@Override
	public void setComment(String comm)
	{
		this.comment = comm;
	}

	@Override
	public String getComment()
	{
		return comment;
	}

	@Override
	public boolean isValid()
	{
		return getBlock() != null;
	}

	@Override
	public boolean isInvalid()
	{
		return !isValid();
	}

	@Override
	public String toString()
	{
		return String.format("Schema<BlockKey>(mod_id: '%s', name: '%s', meta: %d)", mod_id, name, meta);
	}
}
