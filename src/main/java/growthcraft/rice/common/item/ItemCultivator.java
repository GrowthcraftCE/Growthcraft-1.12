package growthcraft.rice.common.item;

import growthcraft.core.shared.block.BlockFlags;
import growthcraft.rice.shared.Reference;
import growthcraft.rice.shared.init.GrowthcraftRiceBlocks;
import net.minecraft.block.BlockFarmland;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemCultivator extends ItemHoe {

    public ItemCultivator(String unlocalizedName) {
        super(ToolMaterial.IRON);
        this.setUnlocalizedName(unlocalizedName);
        this.setRegistryName(Reference.MODID, unlocalizedName);
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {

        ItemStack itemstack = player.getHeldItem(hand);
        IBlockState state = worldIn.getBlockState(pos);

        if ( state.getBlock() instanceof BlockFarmland) {
            worldIn.setBlockState(pos, GrowthcraftRiceBlocks.ricePaddy.getDefaultState(), BlockFlags.UPDATE_AND_SYNC);
            itemstack.damageItem(1, player);
            // Check for secondary output
            checkSecondaryOutput(player, worldIn, pos);

            return EnumActionResult.PASS;
        }

        return super.onItemUse(player, worldIn, pos, hand, facing, hitX, hitY, hitZ);
    }

    private void checkSecondaryOutput(EntityPlayer player, World worldIn, BlockPos pos) {

    }
}
