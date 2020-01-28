package growthcraft.milk.common.block;

import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import growthcraft.core.shared.block.BlockFlags;
import growthcraft.core.shared.block.GrowthcraftBlockContainer;
import growthcraft.core.shared.block.GrowthcraftRotatableBlockContainer;
import growthcraft.milk.common.tileentity.TileEntityButterChurn;
import growthcraft.milk.shared.Reference;
import growthcraft.milk.shared.init.GrowthcraftMilkBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockButterChurn extends GrowthcraftRotatableBlockContainer {
    private static final AxisAlignedBB BOUNDING_BOX = new AxisAlignedBB(
            0.0625 * 2, 0.0625 * 0, 0.0625 * 2,
            0.0625 * 14, 0.0625 * 16, 0.0625 * 14);

    public BlockButterChurn(String unlocalizedName) {
        super(Material.WOOD);
        this.setUnlocalizedName(unlocalizedName);
        this.setRegistryName(new ResourceLocation(Reference.MODID, unlocalizedName));
        this.setResistance(5.0F);
        this.setHardness(2.0F);
        this.setSoundType(SoundType.WOOD);
        setTileEntityType(TileEntityButterChurn.class);

    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return BOUNDING_BOX;
    }

    @SuppressWarnings("deprecation")
    @Override
    public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable Entity entityIn, boolean p_185477_7_) {
        addCollisionBoxToList(pos, entityBox, collidingBoxes, BOUNDING_BOX);
    }

    private Block getPlungerBlock() {
        return GrowthcraftMilkBlocks.churnPlunger.getBlock();
    }

    @Override
    public void doRotateBlock(World world, BlockPos pos, IBlockState state, EnumFacing side) {
        IBlockState statePlunger = world.getBlockState(pos.up());

        EnumFacing churnFacing = state.getValue(TYPE_ROTATION);

        world.setBlockState(pos, state.withProperty(TYPE_ROTATION, churnFacing), BlockFlags.SYNC);
        world.setBlockState(pos.up(), statePlunger.withProperty(TYPE_ROTATION, churnFacing), BlockFlags.SYNC);
    }

    private void placePlungerFor(World world, BlockPos pos, EnumFacing facing) {
        IBlockState statePlunger = getPlungerBlock().getDefaultState()
                .withProperty(TYPE_ROTATION, facing);
        world.setBlockState(pos.up(), statePlunger, BlockFlags.SYNC);
    }


    @SuppressWarnings("deprecation")
    @Override
    public boolean isFullBlock(IBlockState state) {
        return false;
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @Override
    public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
        return BlockFaceShape.UNDEFINED;
    }

    public boolean tryChurning(World world, BlockPos pos, EntityPlayer player) {
        final TileEntityButterChurn butterChurn = getTileEntity(world, pos);
        if (butterChurn != null) {
            switch (butterChurn.doWork()) {
                case CHURN:
                case PRODUCE:
                    return true;
                case NONE:
                default:
                    break;
            }
        }
        return false;
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (super.onBlockActivated(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ))
            return true;
        if (!playerIn.isSneaking()) {
            if (tryChurning(worldIn, pos, playerIn))
                return true;
        }
        return false;
    }


    @Override
    public void onBlockAdded(World world, BlockPos pos, IBlockState state) {
        super.onBlockAdded(world, pos, state);
        EnumFacing facing = state.getValue(TYPE_ROTATION);
        placePlungerFor(world, pos, facing);
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
        EnumFacing facing = state.getValue(TYPE_ROTATION); // setOrientWhenPlacing(worldIn, pos, state, placer, false);
        placePlungerFor(worldIn, pos, facing);
    }

    @Override
    public void onBlockHarvested(World world, BlockPos pos, IBlockState state, EntityPlayer player) {
        if (player.capabilities.isCreativeMode && /*(m & 8) != 0 &&*/ plungerIsAbove(world, pos)) {
            // NOTE: A state like BlockSkull.NODROP is not existing, so ignored (m & 8) != 0 condition.

            world.destroyBlock(pos.up(), false);
            TileEntity te = world.getTileEntity(pos.up());
            if (te != null)
                te.invalidate();
        }

        super.onBlockHarvested(world, pos, state, player);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
        if (!this.canBlockStay(worldIn, pos)) {
            worldIn.destroyBlock(pos, true);
        }
    }

    /************
     * STUFF
     ************/

    /**
     * @param world - world block is in
     * @param x     - x coord
     * @param y     - y coord
     * @param z     - z coord
     * @return true if the BlockFruitPresser is above this block, false otherwise
     */
    public boolean plungerIsAbove(IBlockAccess world, BlockPos pos) {
        return getPlungerBlock() == world.getBlockState(pos.up()).getBlock();
    }

    public boolean canBlockStay(IBlockAccess world, BlockPos pos) {
        return plungerIsAbove(world, pos);
    }

    @Override
    public boolean canPlaceBlockAt(World world, BlockPos pos) {
        if (pos.getY() >= 255) return false;

        return world.getBlockState(pos.down()).isTopSolid() &&
                super.canPlaceBlockAt(world, pos) &&
                super.canPlaceBlockAt(world, pos.up());
    }

    @Override
    public int quantityDropped(Random random) {
        return 1;
    }

    @Override
    public int damageDropped(IBlockState state) {
        return 0;
    }


}
