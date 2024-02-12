package growthcraft.cellar.common.block;

import growthcraft.cellar.common.block.BlockFruitPresser.Orient;
import growthcraft.cellar.common.tileentity.TileEntityFruitPress;
import growthcraft.cellar.shared.Reference;
import growthcraft.cellar.shared.init.GrowthcraftCellarBlocks;
import growthcraft.core.shared.block.BlockFlags;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.Random;

public class BlockFruitPress extends BlockRotatableCellarContainer {
    // INITIALIZE

    public BlockFruitPress(String unlocalizedName) {
        super(Material.WOOD);
        setTileEntityType(TileEntityFruitPress.class);
        setHardness(2.0F);
        this.setResistance(5.0F);
        setSoundType(SoundType.WOOD);
        this.setTranslationKey(unlocalizedName);
        this.setRegistryName(new ResourceLocation(Reference.MODID, unlocalizedName));
    }

    private Block getPresserBlock() {
        return GrowthcraftCellarBlocks.fruitPresser.getBlock();
    }

    @Override
    public void doRotateBlock(World world, BlockPos pos, IBlockState state, EnumFacing side) {
        IBlockState statePresser = world.getBlockState(pos.up());

        EnumFacing pressFacing = state.getValue(TYPE_ROTATION);
        Orient presserOrient = statePresser.getValue(BlockFruitPresser.TYPE_ORIENT);
        if (pressFacing == EnumFacing.NORTH || pressFacing == EnumFacing.SOUTH)
            pressFacing = EnumFacing.EAST;
        else
            pressFacing = EnumFacing.SOUTH;
        presserOrient = Orient.fromFacing(pressFacing);

        world.setBlockState(pos, state.withProperty(TYPE_ROTATION, pressFacing), BlockFlags.SYNC);
        world.setBlockState(pos.up(), statePresser.withProperty(BlockFruitPresser.TYPE_ORIENT, presserOrient), BlockFlags.SYNC);
    }

    private void placePresserFor(World world, BlockPos pos, EnumFacing facing) {
        IBlockState statePresser = getPresserBlock().getDefaultState()
                .withProperty(BlockFruitPresser.TYPE_ORIENT, Orient.fromFacing(facing));
        world.setBlockState(pos.up(), statePresser, BlockFlags.SYNC);
    }

    @Override
    public void onBlockAdded(World world, BlockPos pos, IBlockState state) {
        super.onBlockAdded(world, pos, state);
        EnumFacing facing = state.getValue(TYPE_ROTATION);
        placePresserFor(world, pos, facing);
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
        EnumFacing facing = setOrientWhenPlacing(worldIn, pos, state, placer, false);
        placePresserFor(worldIn, pos, facing);
    }

    @Override
    public void onBlockHarvested(World world, BlockPos pos, IBlockState state, EntityPlayer player) {
        if (player.capabilities.isCreativeMode && /*(m & 8) != 0 &&*/ presserIsAbove(world, pos)) {
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
     * CONDITIONS
     ************/
    @SuppressWarnings("deprecation")
    @Override
    public boolean isSideSolid(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing side) {
        EnumFacing facing = state.getValue(TYPE_ROTATION);
        Orient orient = Orient.fromFacing(facing);

        if (orient == Orient.TOEAST) {
            return side == EnumFacing.EAST || side == EnumFacing.WEST;
        } else if (orient == Orient.TOSOUTH) {
            return side == EnumFacing.NORTH || side == EnumFacing.SOUTH;
        }

        return isNormalCube(state, world, pos);
    }

    @Override
    public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
        return BlockFaceShape.UNDEFINED;
    }


    /************
     * STUFF
     ************/

    //Fixme: More broken java docs.

    /**
     * @param world - world block is in
     * @param x     - x coord
     * @param y     - y coord
     * @param z     - z coord
     * @return true if the BlockFruitPresser is above this block, false otherwise
     */
    public boolean presserIsAbove(IBlockAccess world, BlockPos pos) {
        return getPresserBlock() == world.getBlockState(pos.up()).getBlock();
    }

    public boolean canBlockStay(IBlockAccess world, BlockPos pos) {
        return presserIsAbove(world, pos);
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


    /************
     * RENDERS
     ************/

    @SuppressWarnings("deprecation")
    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    /************
     * COMPARATOR
     ************/
    @SuppressWarnings("deprecation")
    @Override
    public boolean hasComparatorInputOverride(IBlockState state) {
        return true;
    }

    @SuppressWarnings("deprecation")
    @Override
    public int getComparatorInputOverride(IBlockState state, World world, BlockPos pos) {
        final TileEntityFruitPress te = getTileEntity(world, pos);
        if (te != null) {
            return te.getFluidAmountScaled(15, 0);
        }
        return 0;
    }
}
