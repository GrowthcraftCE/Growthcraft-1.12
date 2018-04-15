package growthcraft.core.common.lib.definition;

import javax.annotation.Nonnull;

import net.minecraft.block.Block;

public class BlockDefinition extends BlockTypeDefinition<Block>
{
	public BlockDefinition(@Nonnull Block block)
	{
		super(block);
	}
}
