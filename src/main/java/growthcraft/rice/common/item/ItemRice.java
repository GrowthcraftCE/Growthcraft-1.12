package growthcraft.rice.common.item;

import growthcraft.core.shared.block.BlockPaddyBase;
import growthcraft.rice.shared.Reference;
import growthcraft.rice.shared.init.GrowthcraftRiceBlocks;
import net.minecraft.block.BlockAir;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemSeeds;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemRice extends ItemSeeds {
    public ItemRice(String unlocalizedName) {
        super(GrowthcraftRiceBlocks.riceCrop.getBlock(), GrowthcraftRiceBlocks.ricePaddy.getBlock());
        this.setUnlocalizedName(unlocalizedName);
        this.setRegistryName(new ResourceLocation(Reference.MODID, unlocalizedName));
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        ItemStack itemstack = player.getHeldItem(hand);

        IBlockState state = worldIn.getBlockState(pos);
        IBlockState stateBlockAbove = worldIn.getBlockState(pos.up());

        if (facing == EnumFacing.UP && state.getBlock() instanceof BlockPaddyBase && stateBlockAbove.getBlock() instanceof BlockAir) {
            IBlockState plantState = getPlant(worldIn, pos);
            worldIn.setBlockState(pos.up(), plantState);
            itemstack.shrink(1);

            return EnumActionResult.SUCCESS;
        } else {
            return EnumActionResult.FAIL;
        }
    }
}
