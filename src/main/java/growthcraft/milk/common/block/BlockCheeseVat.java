package growthcraft.milk.common.block;

import java.util.List;
import java.util.Random;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import growthcraft.core.shared.block.GrowthcraftBlockContainer;
import growthcraft.milk.common.tileentity.TileEntityCheeseVat;
import growthcraft.milk.shared.Reference;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Growthcraft Milk: Cheese Vat
 * <p>
 * The cheese vat is used to make cheese. To get started making cheese, you will need a cheese vat, culture jar, and a
 * sword. The cheese vat will take five buckets (LEVEL) of milk.
 */
public class BlockCheeseVat extends GrowthcraftBlockContainer {
    // The amount of milk in the Cheese Vat

//    public static final PropertyInteger LEVEL = PropertyInteger.create("level", 0, 5);
	public static final PropertyBool ATTACH_NORTH = PropertyBool.create("attachnorth");
	public static final PropertyBool ATTACH_SOUTH = PropertyBool.create("attachsouth");
	public static final PropertyBool ATTACH_EAST = PropertyBool.create("attacheast");
	public static final PropertyBool ATTACH_WEST = PropertyBool.create("attachwest");
	
    private static final AxisAlignedBB BOUNDING_BOX = new AxisAlignedBB(
            0.0625 * 1, 0.0625 * 0, 0.0625 * 1,
            0.0625 * 15, 0.0625 * 14, 0.0625 * 15);

    public BlockCheeseVat(String unlocalizedName) {
        super(Material.IRON, MapColor.STONE);
        this.setUnlocalizedName(unlocalizedName);
        this.setRegistryName(new ResourceLocation(Reference.MODID, unlocalizedName));
        this.setResistance(5.0F);
        this.setHardness(2.0F);
        this.setSoundType(SoundType.METAL);
        this.useNeighborBrightness = true;
//        this.setDefaultState(this.blockState.getBaseState().withProperty(LEVEL, Integer.valueOf(0)));
        this.setDefaultState(this.blockState.getBaseState());
        setTileEntityType(TileEntityCheeseVat.class);
    }

	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(IBlockState state, World world, BlockPos pos, Random random)
	{
		if (random.nextInt(12) == 0)
		{
			final TileEntityCheeseVat te = getTileEntity(world, pos);
			if (te != null)
			{
				if (te.isWorking())
				{
					for (int i = 0; i < 3; ++i)
					{
						final double px = (double)pos.getX() + 0.5d + (random.nextFloat() - 0.5d);
						final double py = (double)pos.getY() + (1d / 16d);
						final double pz = (double)pos.getZ() + 0.5d + (random.nextFloat() - 0.5d);
						world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, px, py, pz, 0.0D, 1d / 32d, 0.0D);
						// world.playSoundEffect((double)x, (double)y, (double)z, "liquid.lavapop", 0.3f, 0.5f);
						world.playSound((double)pos.getX(), (double)pos.getY(), (double)pos.getZ(), SoundEvents.BLOCK_LAVA_POP, SoundCategory.BLOCKS, 0.3f, 0.5f, false);
					}
				}
			}
		}
	}

	@SuppressWarnings("deprecation")
    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return BOUNDING_BOX;
    }

	@SuppressWarnings("deprecation")
    @Override
    public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable Entity entityIn, boolean p_185477_7_) {
        addCollisionBoxToList(pos, entityBox, collidingBoxes, BOUNDING_BOX);
    }

	@SuppressWarnings("deprecation")
    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

	@SuppressWarnings("deprecation")
    @Override
    public boolean isFullBlock(IBlockState state) {
        return false;
    }
	
	@Override
    public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face)
    {
        return (face == EnumFacing.UP) ? BlockFaceShape.BOWL : BlockFaceShape.UNDEFINED;
    }
	
	@Nonnull
	@Override
	protected BlockStateContainer createBlockState() {
	    return new BlockStateContainer(this, ATTACH_NORTH, ATTACH_SOUTH, ATTACH_EAST, ATTACH_WEST );
	}
	
    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState();
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return 0;
    }
	
	@Override
    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos)
    {
		BlockPos down = pos.down();
		IBlockState stateDown = worldIn.getBlockState(down);
		boolean isOpaqueDown = stateDown.isOpaqueCube();

		return state.withProperty(ATTACH_NORTH, !isOpaqueDown && worldIn.getBlockState(down.north()).isOpaqueCube())
		            .withProperty(ATTACH_SOUTH, !isOpaqueDown && worldIn.getBlockState(down.south()).isOpaqueCube())
		            .withProperty(ATTACH_EAST, !isOpaqueDown && worldIn.getBlockState(down.east()).isOpaqueCube())
		            .withProperty(ATTACH_WEST, !isOpaqueDown && worldIn.getBlockState(down.west()).isOpaqueCube());
    }
	
	@SuppressWarnings("deprecation")
	@Override
	public boolean hasComparatorInputOverride(IBlockState state)
	{
		return true;
	}

	@SuppressWarnings("deprecation")
	@Override
	public int getComparatorInputOverride(IBlockState state, World world, BlockPos pos)
	{
		final TileEntityCheeseVat te = getTileEntity(world, pos);
		if (te != null)
		{
			return te.calcRedstone();
		}
		return 0;
	}
}
