package growthcraft.core.shared.block;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * Interface used by PlayerInteractAmazingStick to act like a wrench,
 * use onBlockActivated instead for regular wrench actions
 */
public interface IWrenchable {
    public boolean wrenchBlock(World world, BlockPos pos, EntityPlayer player, ItemStack wrench);
}
