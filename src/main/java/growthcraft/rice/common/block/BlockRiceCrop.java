package growthcraft.rice.common.block;

import growthcraft.core.shared.block.BlockPaddyBase;
import growthcraft.rice.shared.Reference;
import growthcraft.rice.shared.init.GrowthcraftRiceItems;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.IGrowable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

import static growthcraft.core.shared.block.BlockPaddyBase.IS_RADIOACTIVE;

public class BlockRiceCrop extends BlockCrops implements IGrowable, IPlantable {

    private static final AxisAlignedBB[] BOUNDING_BOXES = new AxisAlignedBB[]{
            new AxisAlignedBB(0.0625 * 2, 0.0625 * 0, 0.0625 * 2, 0.0625 * 14, 0.0625 * 6, 0.0625 * 14),
            new AxisAlignedBB(0.0625 * 2, 0.0625 * 0, 0.0625 * 2, 0.0625 * 14, 0.0625 * 11, 0.0625 * 14),
            new AxisAlignedBB(0.0625 * 2, 0.0625 * 0, 0.0625 * 2, 0.0625 * 14, 0.0625 * 15, 0.0625 * 14),
            new AxisAlignedBB(0.0625 * 2, 0.0625 * 0, 0.0625 * 2, 0.0625 * 14, 0.0625 * 15, 0.0625 * 14),
            new AxisAlignedBB(0.0625 * 2, 0.0625 * 0, 0.0625 * 2, 0.0625 * 14, 0.0625 * 14, 0.0625 * 14),
    };

    public static class RiceStage
    {
        public static final int SPROUTING = 0;
        public static final int SPROUT = 1;
        public static final int SAPLING = 2;
        public static final int FLOWERING = 3;
        public static final int MATURE = 4;

        private RiceStage() {}
    }

    public BlockRiceCrop(String unlocalizedName) {
        this.setUnlocalizedName(unlocalizedName);
        this.setRegistryName(Reference.MODID, unlocalizedName);
    }

    @Override
    protected Item getSeed() {
        return GrowthcraftRiceItems.rice.getItem();
    }

    @Override
    protected Item getCrop() {
        return GrowthcraftRiceItems.rice.getItem();
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {

        switch (state.getValue(AGE)) {
            case 0:
                return BOUNDING_BOXES[RiceStage.SPROUTING];
            case 1:
            case 2:
                return BOUNDING_BOXES[RiceStage.SPROUT];
            case 3:
            case 4:
                return BOUNDING_BOXES[RiceStage.SAPLING];
            case 5:
            case 6:
                return BOUNDING_BOXES[RiceStage.FLOWERING];
            case 7:
                return BOUNDING_BOXES[RiceStage.MATURE];
            default:
                break;
        }

        return BOUNDING_BOXES[0];
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World player, List<String> tooltip, ITooltipFlag advanced) {
        super.addInformation(stack, player, tooltip, advanced);
    }

    @Override
    public boolean isFlammable(IBlockAccess world, BlockPos pos, EnumFacing face) {
        return false;
    }

    @Override
    public EnumPlantType getPlantType(IBlockAccess world, BlockPos pos) {
        return EnumPlantType.Crop;
    }

    @Override
    public void grow(World worldIn, Random random, BlockPos pos, IBlockState state) {
        this.grow(worldIn, pos, state);

        BlockPos posDown = pos.down();
        IBlockState downBlockState = worldIn.getBlockState(posDown);

        // IS_RADIOACTIVE causes a double growth.
        if ( downBlockState.getBlock() instanceof BlockPaddyBase && downBlockState.getValue(IS_RADIOACTIVE) ) {
            this.grow(worldIn, pos, state);
        }

    }

    @Override
    public boolean canBlockStay(World world, BlockPos pos, IBlockState state) {
        BlockPos dwn = pos.down();
        IBlockState dwnState = world.getBlockState(dwn);
        return dwnState.getBlock() instanceof BlockPaddyBase;
    }

    @Override
    public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
        super.getDrops(drops, world, pos, state, fortune);

        BlockPos posDown = pos.down();
        IBlockState downBlockState = world.getBlockState(posDown);

        if ( downBlockState.getBlock() instanceof BlockPaddyBase && downBlockState.getValue(IS_RADIOACTIVE) ) {
            drops.add(new ItemStack(GrowthcraftRiceItems.rice.getItem()));
        }

    }

}
