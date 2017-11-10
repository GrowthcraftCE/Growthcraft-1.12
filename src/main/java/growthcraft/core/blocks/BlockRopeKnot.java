package growthcraft.core.blocks;

import growthcraft.core.Reference;
import growthcraft.core.init.GrowthcraftCoreItems;
import growthcraft.core.tileentity.TileEntityRopeKnot;
import growthcraft.grapes.blocks.BlockGrapeVineBush;
import growthcraft.hops.blocks.BlockHopsBush;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class BlockRopeKnot extends Block implements ITileEntityProvider {

    private static final AxisAlignedBB KNOT_BOUNDING_BOX = new AxisAlignedBB(0.0625 * 5, 0.0625 * 6, 0.0625 * 5, 0.0625 * 11, 0.0625 * 14, 0.0625 * 11);
    private static final AxisAlignedBB NORTH_BOUNDING_BOX = new AxisAlignedBB(0.0625 * 5, 0.0625 * 6, 0.0625 * 5, 0.0625 * 11, 0.0625 * 14, 0.0625 * 11);
    private static final AxisAlignedBB EAST_BOUNDING_BOX = new AxisAlignedBB(0.0625 * 5, 0.0625 * 6, 0.0625 * 5, 0.0625 * 11, 0.0625 * 14, 0.0625 * 11);
    private static final AxisAlignedBB SOUTH_BOUNDING_BOX = new AxisAlignedBB(0.0625 * 5, 0.0625 * 6, 0.0625 * 5, 0.0625 * 11, 0.0625 * 14, 0.0625 * 11);
    private static final AxisAlignedBB WEST_BOUNDING_BOX = new AxisAlignedBB(0.0625 * 5, 0.0625 * 6, 0.0625 * 5, 0.0625 * 11, 0.0625 * 14, 0.0625 * 11);

    private static final AxisAlignedBB COLLISION_BOX = new AxisAlignedBB(0.0625 * 6, 0.0625 * 6, 0.0625 * 6, 0.0625 * 10, 0.0625 * 13, 0.0625 * 10);

    public static final PropertyBool NORTH = PropertyBool.create("north");
    public static final PropertyBool EAST = PropertyBool.create("east");
    public static final PropertyBool SOUTH = PropertyBool.create("south");
    public static final PropertyBool WEST = PropertyBool.create("west");
    public static final PropertyBool UP = PropertyBool.create("up");
    public static final PropertyBool DOWN = PropertyBool.create("down");

    private boolean wasActivated;

    public BlockRopeKnot(String unlocalizedName) {
        super(Material.WOOD);
        this.setUnlocalizedName(unlocalizedName);
        this.setRegistryName(new ResourceLocation(Reference.MODID, unlocalizedName));

        this.setHardness(3);
        this.setResistance(20);
        this.setSoundType(SoundType.CLOTH);
        this.setHarvestLevel("axe", 0);

        this.setDefaultState(this.blockState.getBaseState()
                .withProperty(NORTH, Boolean.valueOf(false))
                .withProperty(EAST, Boolean.valueOf(false))
                .withProperty(SOUTH, Boolean.valueOf(false))
                .withProperty(WEST, Boolean.valueOf(false))
                .withProperty(UP, Boolean.valueOf(false))
                .withProperty(DOWN, Boolean.valueOf(false)));

        this.useNeighborBrightness = true;
        this.wasActivated = false;
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return KNOT_BOUNDING_BOX;
    }

    @Override
    public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable Entity entityIn, boolean p_185477_7_) {
        addCollisionBoxToList(pos, entityBox, collidingBoxes, COLLISION_BOX);
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if(playerIn.isSneaking()) {
            this.wasActivated = true;
            // Put the Fence back.
            IItemHandler inventoryHandler = this.getInventoryHandler(worldIn, pos);
            ItemStack stack = inventoryHandler.getStackInSlot(0);
            IBlockState fenceBlockState = Block.getBlockFromItem(stack.getItem()).getDefaultState();
            worldIn.setBlockState(pos, fenceBlockState);
        } else {
            this.wasActivated = false;
        }
        return false;
    }

    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
        // Always return a rope when broken
        ItemStack rope = new ItemStack(GrowthcraftCoreItems.rope, 1);
        InventoryHelper.spawnItemStack(worldIn, pos.getX(), pos.getY(), pos.getZ(), rope);

        if(!this.wasActivated) {
            // Returned the stored ItemFence
            IItemHandler inventoryHandler = this.getInventoryHandler(worldIn, pos);
            ItemStack stack = inventoryHandler.getStackInSlot(0);
            InventoryHelper.spawnItemStack(worldIn, pos.getX() + 0.5F, pos.getY() + 1, pos.getZ() + 0.5F, stack);
        }

        this.wasActivated = false;
    }

    @Override
    public int quantityDropped(Random random) {
        // Always return 0 as this is not a normal block.
        return 0;
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityRopeKnot();
    }

    private IItemHandler getInventoryHandler(World worldIn, BlockPos pos) {
        TileEntityRopeKnot te = (TileEntityRopeKnot) worldIn.getTileEntity(pos);
        IItemHandler handler = te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
        return handler;
    }

    /**
     * Determines if this RopeKnot block can connect to a neighbor block on the given face.
     * @param world world
     * @param pos RopeKnot Position
     * @param facing Requesting Side
     * @return
     */
    private boolean canConnectRopeTo(IBlockAccess world, BlockPos pos, EnumFacing facing) {
        Block block = world.getBlockState(pos.offset(facing)).getBlock();
        if ( block instanceof BlockRopeFence || block instanceof BlockRopeKnot || block instanceof BlockGrapeVineBush || block instanceof BlockHopsBush) {
            return true;
        }
        return false ;
    }

    @Override
    public boolean canBeConnectedTo(IBlockAccess world, BlockPos pos, EnumFacing facing) {
        Block block = world.getBlockState(pos.offset(facing)).getBlock();
        if ( block instanceof BlockRopeFence || block instanceof BlockRopeKnot) {
            return true;
        }
        return false ;
    }

    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
        return state.withProperty(NORTH, canConnectRopeTo(worldIn, pos, EnumFacing.NORTH))
                .withProperty(EAST, canConnectRopeTo(worldIn, pos, EnumFacing.EAST))
                .withProperty(SOUTH, canConnectRopeTo(worldIn, pos, EnumFacing.SOUTH))
                .withProperty(WEST, canConnectRopeTo(worldIn, pos, EnumFacing.WEST))
                .withProperty(UP, canConnectRopeTo(worldIn, pos, EnumFacing.UP))
                .withProperty(DOWN, canConnectRopeTo(worldIn, pos, EnumFacing.DOWN));
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[] { NORTH, EAST, SOUTH, WEST, UP, DOWN } );
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState();
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return 0;
    }

}
