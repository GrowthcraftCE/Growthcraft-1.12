package growthcraft.milk.common.block;

import growthcraft.milk.shared.Reference;
import growthcraft.milk.shared.init.GrowthcraftMilkItems;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.IGrowable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

import javax.annotation.Nullable;

public class BlockThistle extends BlockCrops implements IGrowable, IPlantable {

	// SYNC: Must be kept consistent with thistle textures
    private static final AxisAlignedBB BOUNDING_BOX_0 = new AxisAlignedBB(0.0625 * 5, 0.0625 * 0, 0.0625 * 5, 0.0625 * 11, 0.0625 * 4, 0.0625 * 11);
    private static final AxisAlignedBB BOUNDING_BOX_1 = new AxisAlignedBB(0.0625 * 5, 0.0625 * 0, 0.0625 * 5, 0.0625 * 11, 0.0625 * 9, 0.0625 * 11);
    private static final AxisAlignedBB BOUNDING_BOX_2 = new AxisAlignedBB(0.0625 * 4, 0.0625 * 0, 0.0625 * 4, 0.0625 * 12, 0.0625 * 15, 0.0625 * 12);
    private static final AxisAlignedBB BOUNDING_BOX_3 = new AxisAlignedBB(0.0625 * 2, 0.0625 * 0, 0.0625 * 2, 0.0625 * 14, 0.0625 * 16, 0.0625 * 14);

    public BlockThistle(String unlocalizedName) {
        this.setUnlocalizedName(unlocalizedName);
        this.setRegistryName(new ResourceLocation(Reference.MODID, unlocalizedName));
    }

    protected Item getSeed() {
        return GrowthcraftMilkItems.thistleSeed.getItem();
    }

    protected Item getCrop() {
        return GrowthcraftMilkItems.thistle.getItem();
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
    	// SYNC: Must be kept consistent with thistle.json blockstate configuration
    	int age = state.getValue(AGE);
    	if( age <= 2 )
    		return BOUNDING_BOX_0;
    	if( age <= 4 )
    		return BOUNDING_BOX_1;
    	if( age <= 6 )
    		return BOUNDING_BOX_2;
    	return BOUNDING_BOX_3;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        tooltip.add(TextFormatting.BLUE + I18n.format(this.getUnlocalizedName() + ".tooltip"));
    }

    @Override
    public boolean isFlammable(IBlockAccess world, BlockPos pos, EnumFacing face) {
        return true;
    }
    
    @Override
    public EnumPlantType getPlantType(IBlockAccess world, BlockPos pos)
    {
        return EnumPlantType.Crop;
    }

}
