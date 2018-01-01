package growthcraft.cellar.blocks;

import growthcraft.cellar.Reference;
import growthcraft.cellar.client.gui.GuiHandler;
import growthcraft.cellar.tileentity.TileEntityBrewKettle;
import growthcraft.core.handlers.FluidHandler;
import growthcraft.core.utils.GrowthcraftLogger;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;

import javax.annotation.Nullable;
import java.util.List;

public class BlockBrewKettle extends BlockCellarContainer implements ITileEntityProvider {

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
        this.setUnlocalizedName(unlocalizedName);
        this.setRegistryName(new ResourceLocation(Reference.MODID, unlocalizedName));
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (!worldIn.isRemote) {

            FluidStack fluidStack = FluidUtil.getFluidContained(playerIn.getHeldItem(hand));
            TileEntityBrewKettle tileEntity = (TileEntityBrewKettle) worldIn.getTileEntity(pos);

            if (fluidStack != null) {
                GrowthcraftLogger.getLogger().info("Holding " + fluidStack.getUnlocalizedName() + " which is a " + fluidStack.getFluid().getClass().toString());
            }

            //if (fluidStack != null && fluidStack.getFluid() instanceof FluidRennet) {
            if (fluidStack != null) {
                FluidHandler fluidHandler = (FluidHandler) tileEntity.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, null);

                if (fluidHandler.getFluidTank().getFluidAmount() + fluidStack.amount <= fluidHandler.getFluidTank().getCapacity()) {
                    fluidHandler.fill(fluidStack, true);

                    if (!playerIn.capabilities.isCreativeMode) {
                        playerIn.setHeldItem(hand, new ItemStack(Items.BUCKET));
                    }
                    return true;
                }
            }

            playerIn.openGui(Reference.MODID, GuiHandler.BREW_KETTLE, worldIn, pos.getX(), pos.getY(), pos.getZ());
        }
        return true;
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

    @Override
    public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {
        super.onEntityCollidedWithBlock(worldIn, pos, state, entityIn);
        // TODO: If brew kettle is full and player is on fire, extinguish the player.0
    }

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

    /* -- ITileEntityProvider -- */

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityBrewKettle();
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new TileEntityBrewKettle();
    }
}
