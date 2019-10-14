package growthcraft.cellar.common.block;

import growthcraft.cellar.shared.Reference;
import growthcraft.core.shared.block.GrowthcraftBlockContainer;
import growthcraft.core.shared.tileentity.feature.IInteractionObject;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

class GuiBindingBlockCellar {
    private GuiBindingBlockCellar() {
    }

    static boolean openGui(GrowthcraftBlockContainer block, EntityPlayer player, World world, BlockPos pos) {
        final TileEntity te = block.getTileEntity(world, pos);
        if (te instanceof IInteractionObject) {
            player.openGui(Reference.MODID, 0, world, pos.getX(), pos.getY(), pos.getZ());
            return true;
        }
        return false;
    }

    static boolean onBlockActivated(GrowthcraftBlockContainer block, World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (block.grcOnBlockActivated(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ)) return true;
        return !playerIn.isSneaking() && openGui(block, playerIn, worldIn, pos);
    }
}
