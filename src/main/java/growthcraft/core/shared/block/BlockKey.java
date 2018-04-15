package growthcraft.core.shared.block;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import growthcraft.core.shared.item.ItemKey;
import growthcraft.core.shared.utils.HashKey;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;

/**
 * As the name implies, this class is used in place of a List for Block keys
 */
public class BlockKey extends HashKey
{
	public final Block block;
	public final int meta;

	public BlockKey(@Nonnull Block pBlock, int pMeta)
	{
		super();
		this.block = pBlock;
		this.meta = pMeta;
		generateHashCode();
	}
	
	public BlockKey(@Nonnull IBlockState pBlockState)
	{
		this(pBlockState.getBlock(), pBlockState.getBlock().getMetaFromState(pBlockState));
	}

	public BlockKey(@Nonnull Block pBlock)
	{
		this(pBlock, 0);
	}

	public Block getBlock()
	{
		return block;
	}

	public int getMetadata()
	{
		return meta;
	}

	public boolean matches(@Nullable Block pBlock, int pMeta)
	{
		if (pBlock == null) return false;
		return pBlock == block && (meta == ItemKey.WILDCARD_VALUE || pMeta == meta);
	}

	public boolean matches(@Nonnull BlockKey key)
	{
		return matches(key.getBlock(), key.getMetadata());
	}

	private void generateHashCode()
	{
		this.hash = block.hashCode();
		this.hash = 31 * hash + meta;
	}
}
