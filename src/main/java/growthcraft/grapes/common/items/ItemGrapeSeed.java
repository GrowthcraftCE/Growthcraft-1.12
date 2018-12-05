package growthcraft.grapes.common.items;

import growthcraft.grapes.common.blocks.BlockGrapeVineBase;
import growthcraft.grapes.common.utils.GrapeTypeUtils;
import growthcraft.grapes.shared.Reference;
import growthcraft.grapes.shared.definition.IGrapeType;
import growthcraft.grapes.shared.init.GrowthcraftGrapesBlocks;
import growthcraft.grapes.shared.init.GrowthcraftGrapesItems.GrapeTypes;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSeeds;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;

public class ItemGrapeSeed extends ItemSeeds implements IPlantable {

    public ItemGrapeSeed(String unlocalizedName) {
        super(GrowthcraftGrapesBlocks.grapeVine0.getBlock(), Blocks.FARMLAND);
        this.setUnlocalizedName(unlocalizedName);
        this.setRegistryName(new ResourceLocation(Reference.MODID, unlocalizedName));
        this.setHasSubtypes(true);
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
    for (int i = 0; i < GrapeTypes.values().length; i++ ) {
    	GrapeTypes type = GrapeTypes.values()[i];
        if ( stack.getItemDamage() == type.getVariantID() ) {
            return  this.getUnlocalizedName() + "." + type.getName();
        } else {
            continue;
        }
    }
    return super.getUnlocalizedName() + "." + GrapeTypes.PURPLE.getName();
}

    /**
     * Called when a Block is right-clicked with this Item
     */
    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        ItemStack itemstack = player.getHeldItem(hand);
        net.minecraft.block.state.IBlockState state = worldIn.getBlockState(pos);
        if (facing == EnumFacing.UP && player.canPlayerEdit(pos.offset(facing), facing, itemstack) && state.getBlock().canSustainPlant(state, worldIn, pos, EnumFacing.UP, this) && worldIn.isAirBlock(pos.up()))
        {
        	IGrapeType type = GrapeTypeUtils.getTypeByVariantID(GrapeTypes.values(), itemstack.getMetadata());
        	if( type == null )
        		return EnumActionResult.FAIL;
        	IBlockState plantState = getPlant(worldIn, pos).withProperty(BlockGrapeVineBase.SUBTYPE, type.getPlantSubTypeID());

        	worldIn.setBlockState(pos.up(), plantState);
            itemstack.shrink(1);
            return EnumActionResult.SUCCESS;
        }
        else
        {
            return EnumActionResult.FAIL;
        }
    }

    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> subItems) {
		if( !this.isInCreativeTab(tab) )
			return;
        for ( int i = 0; i < GrapeTypes.values().length; i++ ) {
        	GrapeTypes type = GrapeTypes.values()[i];
            subItems.add(new ItemStack(this, 1, type.getVariantID()));
        }
    }

}
