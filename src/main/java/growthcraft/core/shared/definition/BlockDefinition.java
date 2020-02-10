package growthcraft.core.shared.definition;

import net.minecraft.block.Block;

import javax.annotation.Nonnull;

public class BlockDefinition extends BlockTypeDefinition<Block> {
    public BlockDefinition(@Nonnull Block block) {
        super(block);
    }
}
