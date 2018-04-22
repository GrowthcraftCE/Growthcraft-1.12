package growthcraft.hops.common.item;

import growthcraft.core.shared.block.BlockCheck;
import growthcraft.hops.shared.Reference;
import growthcraft.hops.shared.init.GrowthcraftHopsBlocks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemSeeds;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemSeedHops extends ItemSeeds {

    public ItemSeedHops(String unlocalizedName) {
        super(GrowthcraftHopsBlocks.hops.getBlock(), Blocks.FARMLAND);
        this.setUnlocalizedName(unlocalizedName);
        this.setRegistryName(new ResourceLocation(Reference.MODID, unlocalizedName));
    }
    
    /**
     * Called when a Block is right-clicked with this Item
     */
    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        ItemStack itemstack = player.getHeldItem(hand);
        net.minecraft.block.state.IBlockState state = worldIn.getBlockState(pos);
        if (facing == EnumFacing.UP && player.canPlayerEdit(pos.offset(facing), facing, itemstack) && state.getBlock().canSustainPlant(state, worldIn, pos, EnumFacing.UP, this) && BlockCheck.isRope(worldIn.getBlockState(pos.up()).getBlock()))
        {
//        	IGrapeType type = GrapeTypeUtils.getTypeByVariantID(GrapeTypes.values(), itemstack.getMetadata());
//        	if( type == null )
//        		return EnumActionResult.FAIL;
        	IBlockState plantState = getPlant(worldIn, pos);

        	worldIn.setBlockState(pos.up(), plantState);
            itemstack.shrink(1);
            return EnumActionResult.SUCCESS;
        }
        else
        {
            return EnumActionResult.FAIL;
        }
    }
}
