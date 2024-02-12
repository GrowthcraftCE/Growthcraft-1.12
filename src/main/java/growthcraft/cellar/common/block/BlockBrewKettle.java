package growthcraft.cellar.common.block;

import growthcraft.cellar.common.tileentity.TileEntityBrewKettle;
import growthcraft.cellar.shared.Reference;
import growthcraft.cellar.shared.config.GrowthcraftCellarConfig;
import growthcraft.cellar.shared.init.GrowthcraftCellarItems;
import growthcraft.core.shared.item.ItemUtils;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
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
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class BlockBrewKettle extends BlockCellarContainer {
    private static final AxisAlignedBB AABB_CONTENTS = new AxisAlignedBB(2 / 16.0, 4 / 16.0, 2 / 16.0, 12 / 16.0, 10 / 16.0, 12 / 16.0);

    private static final PropertyBool TYPE_LID = PropertyBool.create("haslid");

    private static final AxisAlignedBB AABB_FULL_BLOCK = new AxisAlignedBB(
            0.0625 * 0, 0.0625 * 0, 0.0625 * 0,
            0.0625 * 16, 0.0625 * 16, 0.0625 * 16);

    private static final AxisAlignedBB AABB_BASE = new AxisAlignedBB(
            0.0625 * 0, 0.0625 * 0, 0.0625 * 0,
            0.0625 * 16, 0.0625 * 4, 0.0625 * 16);
    private static final AxisAlignedBB AABB_WALL_NORTH = new AxisAlignedBB(
            0.0625 * 0, 0.0625 * 0, 0.0625 * 0,
            0.0625 * 16, 0.0625 * 16, 0.0625 * 2);
    private static final AxisAlignedBB AABB_WALL_EAST = new AxisAlignedBB(
            0.0625 * 14, 0.0625 * 0, 0.0625 * 0,
            0.0625 * 16, 0.0625 * 16, 0.0625 * 16);
    private static final AxisAlignedBB AABB_WALL_SOUTH = new AxisAlignedBB(
            0.0625 * 0, 0.0625 * 0, 0.0625 * 14,
            0.0625 * 16, 0.0625 * 16, 0.0625 * 16);
    private static final AxisAlignedBB AABB_WALL_WEST = new AxisAlignedBB(
            0.0625 * 0, 0.0625 * 0, 0.0625 * 0,
            0.0625 * 2, 0.0625 * 16, 0.0625 * 16);
    private static final AxisAlignedBB AABB_LID = new AxisAlignedBB(
            0.0625 * 2, 0.0625 * 14, 0.0625 * 2,
            0.0625 * 14, 0.0625 * 16, 0.0625 * 14);

    public BlockBrewKettle(String unlocalizedName) {
        super(Material.IRON);
        setSoundType(SoundType.METAL);
        setTileEntityType(TileEntityBrewKettle.class);
        this.setHardness(2.0F);
        this.setTranslationKey(unlocalizedName);
        this.setDefaultState(this.getBlockState().getBaseState().withProperty(TYPE_LID, false));
        this.setRegistryName(new ResourceLocation(Reference.MODID, unlocalizedName));
    }

    @Override
    public void fillWithRain(World world, BlockPos pos) {
        if (GrowthcraftCellarConfig.brewKettleFillsWithRain) {
            final TileEntityBrewKettle te = getTileEntity(world, pos);
            if (te != null && !te.hasLid()) {
                te.fill(EnumFacing.UP, new FluidStack(FluidRegistry.WATER, GrowthcraftCellarConfig.brewKettleRainFillPerUnit), true);
            }
        }
        super.fillWithRain(world, pos);
    }

    @Override
    public void onEntityCollision(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {
        if (!worldIn.isRemote) {
            final TileEntityBrewKettle te = getTileEntity(worldIn, pos);
            if (te != null && !te.hasLid()) {
                if (GrowthcraftCellarConfig.dropItemsInBrewKettle) {
                    if (entityIn instanceof EntityItem) {
                        final EntityItem item = (EntityItem) entityIn;
                        if (!GrowthcraftCellarItems.brewKettleLid.equals(item.getItem().getItem()) &&
                                !ItemUtils.isEmpty(te.tryMergeItemIntoMainSlot(item.getItem()))) {
                            worldIn.playSound((double) pos.getX(), (double) pos.getY(), (double) pos.getZ(), SoundEvents.ENTITY_GENERIC_SPLASH, SoundCategory.BLOCKS, 0.3f, 0.5f, false);
                        }
                    }
                }
                if (GrowthcraftCellarConfig.setFireToFallenLivingEntities) {
                    // TODO: If brew kettle is full and player is on fire, extinguish the player.0

                    if (entityIn instanceof EntityLivingBase) {
                        Vec3d epos = new Vec3d(entityIn.posX - pos.getX(), entityIn.posY - pos.getY(), entityIn.posZ - pos.getZ());
                        if (AABB_CONTENTS.contains(epos)) {
                            boolean isFull = te.isFluidTankFull(0) || te.isFluidTankFull(1);
                            boolean containsLava = te.getFluid(0) == FluidRegistry.LAVA || te.getFluid(1) == FluidRegistry.LAVA;

                            if (containsLava || te.getHeatMultiplier() >= 0.5f) {
                                entityIn.setFire(1);
                            } else if (isFull && entityIn.isBurning()) {
                                float randA = this.rand.nextFloat();
                                float randB = this.rand.nextFloat();
                                entityIn.playSound(SoundEvents.ENTITY_GENERIC_EXTINGUISH_FIRE, 0.7F, 1.6F + (randA - randB) * 0.4F);
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
    public int quantityDropped(Random random) {
        return 1;
    }

    @Override
    public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
        if (face == EnumFacing.UP) {
            final TileEntityBrewKettle te = getTileEntity(worldIn, pos);
            if (te != null && te.hasLid()) {
                return BlockFaceShape.SOLID;
            }
            return BlockFaceShape.BOWL;
        }

        return face == EnumFacing.DOWN ? BlockFaceShape.UNDEFINED : BlockFaceShape.SOLID;
    }

    /************
     * RENDERS
     ************/

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

    @SuppressWarnings("deprecation")
    @Override
    public boolean isFullBlock(IBlockState state) {
        return false;
    }

    /************
     * BOXES
     ************/

    @SuppressWarnings("deprecation")
    @Override
    public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable Entity entityIn, boolean isActualState) {
        if (!isActualState)
            state = state.getActualState(worldIn, pos);

        addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_BASE);
        addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_WALL_NORTH);
        addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_WALL_EAST);
        addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_WALL_SOUTH);
        addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_WALL_WEST);

        if (state.getValue(TYPE_LID).booleanValue())
            addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_LID);
    }

    @SuppressWarnings("deprecation")
    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return AABB_FULL_BLOCK;
    }

    /************
     * STATES
     ************/

    @Nonnull
    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, TYPE_LID);
    }

    @SuppressWarnings("deprecation")
    @Nonnull
    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState();
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return 0;
    }

    @SuppressWarnings("deprecation")
    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
        final TileEntityBrewKettle te = getTileEntity(worldIn, pos);
        if (te != null && te.hasLid()) {
            return state.withProperty(TYPE_LID, true);
        }

        return state;
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
        final TileEntityBrewKettle te = getTileEntity(world, pos);
        if (te != null) {
            return te.getFluidAmountScaled(15, 1);
        }
        return 0;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack stack, @Nullable World player, List<String> tooltip, ITooltipFlag advanced) {
        super.addInformation(stack, player, tooltip, advanced);
    }
}
