package growthcraft.cellar.common.block;

import growthcraft.cellar.shared.Reference;
import growthcraft.cellar.shared.init.GrowthcraftCellarBlocks;
import growthcraft.cellar.shared.init.GrowthcraftCellarItems;
import growthcraft.core.shared.block.GrowthcraftBlockLog;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockCorkLog extends GrowthcraftBlockLog {
    public BlockCorkLog(String unlocalizedName) {
        super(Reference.MODID, unlocalizedName);
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if ( !worldIn.isRemote ) {
            ItemStack stack = playerIn.getHeldItem(hand);
            if ( stack.getItem() instanceof ItemAxe ) {
                // drop ItemCorkBark
                dropItemCorkBark(worldIn, pos);
                // Replace this block with a blockCorkLogStripped
                worldIn.setBlockState(pos, GrowthcraftCellarBlocks.blockCorkLogStripped.getDefaultState());
                return true;
            }
        }
        return super.onBlockActivated(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ);
    }

    private void dropItemCorkBark(World worldIn, BlockPos pos) {
        if (!worldIn.isRemote && !worldIn.restoringBlockSnapshots) {
            Block.spawnAsEntity(
                    worldIn,
                    pos,
                    new ItemStack(GrowthcraftCellarItems.itemCorkBark.getItem(), RANDOM.nextInt(4) + 1)
            );
        }


    }

}
