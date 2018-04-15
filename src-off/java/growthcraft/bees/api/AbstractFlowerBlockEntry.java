package growthcraft.bees.api;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;

public abstract class AbstractFlowerBlockEntry implements IFlowerBlockEntry
{
	private final Block block;
	private final int meta; 
	
	public AbstractFlowerBlockEntry(IBlockState blockState) {
		this(blockState.getBlock(), blockState.getBlock().getMetaFromState(blockState));
	}

	public AbstractFlowerBlockEntry(Block block, int meta)
	{
		this.block = block;
		this.meta = meta;
	}

	@Override
	public Block getBlock() {
		return block;
	}

	@Override
	public int getBlockMeta() {
		return meta;
	}
}
