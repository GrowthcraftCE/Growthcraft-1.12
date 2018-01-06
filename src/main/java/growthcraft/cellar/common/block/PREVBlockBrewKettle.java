package growthcraft.cellar.common.block;

import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import growthcraft.cellar.GrowthcraftCellarConfig;
import growthcraft.cellar.Reference;
import growthcraft.cellar.common.tileentity.PREVTileEntityBrewKettle;
import growthcraft.core.Utils;
import growthcraft.core.lib.legacy.ILegacyFluidHandler;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

public class PREVBlockBrewKettle extends BlockCellarContainer {
	// OPEN
	// INITIALIZE

	// TODO: Represent coordinates of AABB_CONTENTS as 0 to 1 numbers
	private static final AxisAlignedBB AABB_CONTENTS = new AxisAlignedBB(2, 4, 2, 12, 10, 12); // .scale(1f / 16f);
	
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

    public PREVBlockBrewKettle(String unlocalizedName) {
        super(Material.IRON);
//TODO        this.setStepSound(soundTypeMetal);
        setTileEntityType(PREVTileEntityBrewKettle.class);
        this.setHardness(2.0F);
        this.setUnlocalizedName(unlocalizedName);
        this.setRegistryName(new ResourceLocation(Reference.MODID, unlocalizedName));
    }

	@Override
	public void fillWithRain(World world, BlockPos pos)
	{
		if (GrowthcraftCellarConfig.brewKettleFillsWithRain)
		{
			final PREVTileEntityBrewKettle te = getTileEntity(world, pos);
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
			final PREVTileEntityBrewKettle te = getTileEntity(worldIn, pos);
			if (te != null)
			{
				if (GrowthcraftCellarConfig.dropItemsInBrewKettle)
				{
					if (entityIn instanceof EntityItem)
					{
						final EntityItem item = (EntityItem)entityIn;
						if (te.tryMergeItemIntoMainSlot(item.getItem()) != null)
						{
							worldIn.playSoundEffect((double)x, (double)y, (double)z, "liquid.splash", 0.3f, 0.5f);
						}
					}
				}
				if (GrowthcraftCellarConfig.setFireToFallenLivingEntities)
				{
					if (entityIn instanceof EntityLivingBase)
					{
						if (te.getHeatMultiplier() >= 0.5f)
						{
							Vec3d epos = new Vec3d(entityIn.posX - pos.getX(), entityIn.posY - pos.getY(), entityIn.posZ - pos.getZ());
							epos.scale(16);
							if (AABB_CONTENTS.contains(epos))
							{
								entityIn.setFire(1);
							}
						}
					}
				}
			}
		}
	}
	
	@Override
	protected boolean playerDrainTank(World world, BlockPos pos, ILegacyFluidHandler fh, ItemStack is, EntityPlayer player)
	{
		final FluidStack fs = Utils.playerDrainTank(world, pos, fh, is, player);
		return fs != null && fs.amount > 0;
	}

	@Override
	public int quantityDropped(Random random)
	{
		return 1;
	}
    
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

    // ...
}
