package growthcraft.core.shared.block;

import java.util.Random;

import growthcraft.core.GrowthcraftCore;
import growthcraft.core.common.init.GrowthcraftCoreBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;

public class BlockCheck
{
	/* An extension of EnumFacing, supports 26 directions */
	public static enum BlockDirection
	{
		DOWN(0, -1, 0),
		UP(0, 1, 0),
		NORTH(0, 0, -1),
		SOUTH(0, 0, 1),
		WEST(-1, 0, 0),
		EAST(1, 0, 0),
		UNKNOWN(0, 0, 0),

		NORTH_WEST(-1, 0, -1),
		NORTH_EAST(1, 0, -1),
		SOUTH_WEST(-1, 0, 1),
		SOUTH_EAST(1, 0, 1),

		DOWN_NORTH(0, -1, -1),
		DOWN_SOUTH(0, -1, 1),
		DOWN_WEST(-1, -1, 0),
		DOWN_EAST(1, -1, 0),
		DOWN_NORTH_WEST(-1, -1, -1),
		DOWN_NORTH_EAST(1, -1, -1),
		DOWN_SOUTH_WEST(-1, -1, 1),
		DOWN_SOUTH_EAST(1, -1, 1),

		UP_NORTH(0, 1, -1),
		UP_SOUTH(0, 1, 1),
		UP_WEST(-1, 1, 0),
		UP_EAST(1, 1, 0),
		UP_NORTH_WEST(-1, 1, -1),
		UP_NORTH_EAST(1, 1, -1),
		UP_SOUTH_WEST(-1, 1, 1),
		UP_SOUTH_EAST(1, 1, 1);


		public final int offsetX;
		public final int offsetY;
		public final int offsetZ;
		public final int flag;

		private BlockDirection(int x, int y, int z)
		{
			offsetX = x;
			offsetY = y;
			offsetZ = z;
			flag = 1 << ordinal();
		}
	}

	/**
	 * 2D directions
	 */
	public static final EnumFacing[] DIR4 = new EnumFacing[] {
		EnumFacing.NORTH,
		EnumFacing.SOUTH,
		EnumFacing.WEST,
		EnumFacing.EAST
	};
	public static final BlockDirection[] DIR8 = new BlockDirection[] {
		BlockDirection.NORTH,
		BlockDirection.SOUTH,
		BlockDirection.WEST,
		BlockDirection.EAST,
		BlockDirection.NORTH_WEST,
		BlockDirection.NORTH_EAST,
		BlockDirection.SOUTH_WEST,
		BlockDirection.SOUTH_EAST
	};

	private BlockCheck() {}

	/**
	 * Randomly selects a direction from the DIR4 array and returns it
	 *
	 * @param random - random number generator
	 * @return a random direction
	 */
	public static EnumFacing randomDirection4(Random random)
	{
		return DIR4[random.nextInt(DIR4.length)];
	}

	/**
	 * Randomly selects a direction from the DIR8 array and returns it
	 *
	 * @param random - random number generator
	 * @return a random direction
	 */
	public static BlockDirection randomDirection8(Random random)
	{
		return DIR8[random.nextInt(DIR8.length)];
	}

	/**
	 * Determines if block is a water block
	 *
	 * @param block - the block to check
	 * @return true if the block is a water, false otherwise
	 */
	public static boolean isWater(IBlockState blockState)
	{
		if (blockState == null) return false;
		return blockState.getMaterial() == Material.WATER;
	}

	/**
	 * Determines if block is a rope block
	 *
	 * @param block - the block to check
	 * @return true if the block is a rope block, false otherwise
	 */
	public static boolean isRopeBlock(Block block)
	{
		return block instanceof IBlockRope;
	}

	/**
	 * Determines if block is a "rope"
	 *
	 * @param block - the block to check
	 * @return true if the block is a Rope, false otherwise
	 */
	public static boolean isRope(Block block)
	{
		// REVISE_ME Remove core block dependency
		
		return GrowthcraftCoreBlocks.rope_fence.equals(block);
	}

	/**
	 * Determines if block at the specified location is a valid rope block
	 *
	 * @param world - World, duh.
	 * @param x  - x coord
	 * @param y  - y coord
	 * @param z  - z coord
	 * @return true if the block is a Rope, false otherwise
	 */
	public static boolean isRope(IBlockAccess world, BlockPos pos)
	{
		final Block block = world.getBlockState(pos).getBlock();
		// TODO: IBlockRope is used for any block which can grow on Ropes,
		// as well as Ropes themselves, we need someway to seperate them,
		// either, IBlockRope.isRope(world, x, y, z) OR an additional interface
		// IBlockRopeCrop, IRope
		return isRope(block);
	}

	/**
	 * Determines if the block at the specified location can sustain an
	 * IPlantable plant.
	 *
	 * @param soil - The soil block
	 * @param world - World
	 * @param x  - x coord
	 * @param y  - y coord
	 * @param z  - z coord
	 * @param dir  - direction in which the plant will grow
	 * @param plant  - the plant in question
	 * @return true if the block can be planted, false otherwise
	 */
	public static boolean canSustainPlantOn(IBlockState soilState, IBlockAccess world, BlockPos pos, EnumFacing dir, IPlantable plant)
	{
		return soilState != null && soilState.getBlock().canSustainPlant(soilState, world, pos, dir, plant);
	}

	/**
	 * Determines if the block at the specified location can sustain an
	 * IPlantable plant.
	 *
	 * @param world - World, duh.
	 * @param x  - x coord
	 * @param y  - y coord
	 * @param z  - z coord
	 * @param dir  - direction in which the plant will grow
	 * @param plant  - the plant in question
	 * @return true if the block can be planted, false otherwise
	 */
	public static boolean canSustainPlant(IBlockAccess world, BlockPos pos, EnumFacing dir, IPlantable plant)
	{
		final IBlockState soilState = world.getBlockState(pos);
		return canSustainPlantOn(soilState, world, pos, dir, plant);
	}

	/**
	 * Determines if the block at the specified location can sustain an IPlantable plant, returns the block if so, else returns null;
	 *
	 * @param world - World
	 * @param x  - x coord
	 * @param y  - y coord
	 * @param z  - z coord
	 * @param dir  - direction in which the plant will grow
	 * @param plant  - the plant in question
	 * @return block if it can be planted upon, else null
	 */
	public static Block getFarmableBlock(IBlockAccess world, BlockPos pos, EnumFacing dir, IPlantable plant)
	{
		final IBlockState soilState = world.getBlockState(pos);
		if (canSustainPlantOn(soilState, world, pos, dir, plant))
			return soilState.getBlock();
		return null;
	}

	/**
	 * Determines if a block can be placed on the given side of the coords
	 *
	 * @param world - World
	 * @param x  - x coord
	 * @param y  - y coord
	 * @param z  - z coord
	 * @param dir  - direction the block will be placed against
	 */
	public static boolean isBlockPlacableOnSide(World world, BlockPos pos, EnumFacing dir)
	{
		if (world.isAirBlock(pos)) return false;
		final IBlockState state = world.getBlockState(pos);
		if (state != null)
		{
			return state.getBlock().isSideSolid(state, world, pos, dir);
		}
		return false;
	}
}