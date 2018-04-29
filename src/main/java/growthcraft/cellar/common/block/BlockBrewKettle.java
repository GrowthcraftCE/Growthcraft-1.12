package growthcraft.cellar.common.block;

import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import growthcraft.cellar.shared.config.GrowthcraftCellarConfig;
import growthcraft.cellar.shared.Reference;
import growthcraft.cellar.common.tileentity.TileEntityBrewKettle;
import growthcraft.core.shared.item.ItemUtils;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

public class BlockBrewKettle extends BlockCellarContainer {
	// INITIALIZE

	private static final AxisAlignedBB AABB_CONTENTS = new AxisAlignedBB(2/16.0, 4/16.0, 2/16.0, 12/16.0, 10/16.0, 12/16.0);
	
    private static final AxisAlignedBB AABB_FULL_BLOCK = new AxisAlignedBB(
            0.0625 * 0, 0.0625 * 0, 0.0625 * 0,
            0.0625 * 16, 0.0625 * 16, 0.0625 * 16);

    private static final AxisAlignedBB AABB_BASE = new AxisAlignedBB(
            0.0625 * 0, 0.0625 * 0, 0.0625 * 0,
            0.0625 * 16, 0.0625 * 4, 0.0625 * 16);
    private static final AxisAlignedBB AABB_WALL_NORTH = new AxisAlignedBB(
            0.0625 * 14, 0.0625 * 0, 0.0625 * 0,
            0.0625 * 16, 0.0625 * 16, 0.0625 * 16);
    private static final AxisAlignedBB AABB_WALL_EAST = new AxisAlignedBB(
            0.0625 * 14, 0.0625 * 0, 0.0625 * 0,
            0.0625 * 16, 0.0625 * 16, 0.0625 * 16);
    private static final AxisAlignedBB AABB_WALL_SOUTH = new AxisAlignedBB(
            0.0625 * 0, 0.0625 * 0, 0.0625 * 14,
            0.0625 * 2, 0.0625 * 16, 0.0625 * 16);
    private static final AxisAlignedBB AABB_WALL_WEST = new AxisAlignedBB(
            0.0625 * 0, 0.0625 * 0, 0.0625 * 0,
            0.0625 * 2, 0.0625 * 16, 0.0625 * 16);

    public BlockBrewKettle(String unlocalizedName) {
        super(Material.IRON);
        setSoundType(SoundType.METAL);
        setTileEntityType(TileEntityBrewKettle.class);
        this.setHardness(2.0F);
        this.setUnlocalizedName(unlocalizedName);
        this.setRegistryName(new ResourceLocation(Reference.MODID, unlocalizedName));
    }

	@Override
	public void fillWithRain(World world, BlockPos pos)
	{
		if (GrowthcraftCellarConfig.brewKettleFillsWithRain)
		{
			final TileEntityBrewKettle te = getTileEntity(world, pos);
			if (te != null)
			{
				te.fill(EnumFacing.UP, new FluidStack(FluidRegistry.WATER, GrowthcraftCellarConfig.brewKettleRainFillPerUnit), true);
			}
		}
		super.fillWithRain(world, pos);
	}
	
	@Override
	public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn)
	{
		if (!worldIn.isRemote)
		{
			final TileEntityBrewKettle te = getTileEntity(worldIn, pos);
			if (te != null)
			{
				if (GrowthcraftCellarConfig.dropItemsInBrewKettle)
				{
					if (entityIn instanceof EntityItem)
					{
						final EntityItem item = (EntityItem)entityIn;
						if (!ItemUtils.isEmpty(te.tryMergeItemIntoMainSlot(item.getItem())))
						{
							worldIn.playSound((double)pos.getX(), (double)pos.getY(), (double)pos.getZ(), SoundEvents.ENTITY_GENERIC_SPLASH, SoundCategory.BLOCKS, 0.3f, 0.5f, false);
						}
					}
				}
				if (GrowthcraftCellarConfig.setFireToFallenLivingEntities)
				{
					// TODO: If brew kettle is full and player is on fire, extinguish the player.0
					
					if (entityIn instanceof EntityLivingBase)
					{
						Vec3d epos = new Vec3d(entityIn.posX - pos.getX(), entityIn.posY - pos.getY(), entityIn.posZ - pos.getZ());
						if (AABB_CONTENTS.contains(epos))
						{
							boolean isFull = te.isFluidTankFull(0) || te.isFluidTankFull(1);
							boolean containsLava = te.getFluid(0) == FluidRegistry.LAVA || te.getFluid(1) == FluidRegistry.LAVA;
							
							if (containsLava || te.getHeatMultiplier() >= 0.5f)
							{
								entityIn.setFire(1);
							}
							else if( isFull && entityIn.isBurning() )
							{
								entityIn.playSound(SoundEvents.ENTITY_GENERIC_EXTINGUISH_FIRE, 0.7F, 1.6F + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.4F);
								entityIn.extinguish();
							}
						}
					}
				}
			}
		}
	}
	
//	@Override
//	protected boolean playerDrainTank(World world, BlockPos pos, ILegacyFluidHandler fh, ItemStack is, EntityPlayer player)
//	{
//		final FluidStack fs = GrowthcraftFluidUtils.playerDrainTank(world, pos, fh, is, player);
//		return fs != null && fs.amount > 0;
//	}

	@Override
	public int quantityDropped(Random random)
	{
		return 1;
	}
    
	/************
	 * RENDERS
	 ************/
    
    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isFullBlock(IBlockState state) {
        return false;
    }
    
	/************
	 * BOXES
	 ************/

    @Override
    public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable Entity entityIn, boolean p_185477_7_) {
        addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_BASE);
        addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_WALL_NORTH);
        addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_WALL_EAST);
        addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_WALL_SOUTH);
        addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_WALL_WEST);
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return AABB_FULL_BLOCK;
    }

	/************
	 * COMPARATOR
	 ************/
	@Override
	public boolean hasComparatorInputOverride(IBlockState state)
	{
		return true;
	}

	@Override
	public int getComparatorInputOverride(IBlockState state, World world, BlockPos pos)
	{
		final TileEntityBrewKettle te = getTileEntity(world, pos);
		if (te != null)
		{
			return te.getFluidAmountScaled(15, 1);
		}
		return 0;
	}
}
