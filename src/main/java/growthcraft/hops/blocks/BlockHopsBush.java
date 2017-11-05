package growthcraft.hops.blocks;

import growthcraft.core.blocks.BlockRopeFence;
import growthcraft.hops.Reference;
import growthcraft.hops.init.GrowthcraftHopsBlocks;
import growthcraft.hops.tileentity.TileEntityHopsBush;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
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
import java.util.Random;

public class BlockHopsBush extends BlockBush implements IGrowable, ITileEntityProvider {

    private static final AxisAlignedBB BOUNDING_BOX = new AxisAlignedBB(
            0.0625 * 0, 0.0625 * 0, 0.0625 * 0,
            0.0625 * 16, 0.0625 * 16, 0.0625 * 16);

    public static final PropertyBool NORTH = PropertyBool.create("north");
    public static final PropertyBool EAST = PropertyBool.create("east");
    public static final PropertyBool SOUTH = PropertyBool.create("south");
    public static final PropertyBool WEST = PropertyBool.create("west");

    public BlockHopsBush() {
        super(Material.WOOD);
        this.setUnlocalizedName("hops_bush");
        this.setRegistryName(new ResourceLocation(Reference.MODID, "hops_bush"));

        this.setHarvestLevel("axe", 0);
        this.setDefaultState(this.blockState.getBaseState()
                .withProperty(NORTH, Boolean.valueOf(false))
                .withProperty(EAST, Boolean.valueOf(false))
                .withProperty(SOUTH, Boolean.valueOf(false))
                .withProperty(WEST, Boolean.valueOf(false))
        );
        this.setTickRandomly(true);
    }

    public boolean hasHops(IBlockAccess world, BlockPos pos, EnumFacing facing) {
        TileEntityHopsBush te = (TileEntityHopsBush) world.getTileEntity(pos);
        IItemHandler handler = te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);

        if (facing == EnumFacing.NORTH && !handler.getStackInSlot(0).isEmpty())
            return true;
        if (facing == EnumFacing.EAST && !handler.getStackInSlot(1).isEmpty())
            return true;
        if (facing == EnumFacing.SOUTH && !handler.getStackInSlot(2).isEmpty())
            return true;
        if (facing == EnumFacing.WEST && !handler.getStackInSlot(3).isEmpty())
            return true;

        return false;
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return BOUNDING_BOX;
    }

    @Nullable
    @Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
        return BOUNDING_BOX;
    }

    @Override
    public boolean canBlockStay(World worldIn, BlockPos pos, IBlockState state) {
        return true;
    }

    @Override
    public boolean canGrow(World worldIn, BlockPos pos, IBlockState state, boolean isClient) {
        BlockPos[] blockPositions = new BlockPos[] { pos.north(), pos.east(), pos.south(), pos.west(), pos.up() };

        for ( BlockPos blockPosition : blockPositions ) {
            Block block = worldIn.getBlockState(blockPosition).getBlock();
            if ( block instanceof BlockRopeFence || block instanceof BlockAir) {
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, IBlockState state) {
        return true;
    }

    

    @Override
    public void grow(World worldIn, Random rand, BlockPos pos, IBlockState state) {
        BlockPos[] blockPositions = new BlockPos[] { pos.north(), pos.east(), pos.south(), pos.west(), pos.up(), pos.down() };

        for ( BlockPos blockPosition : blockPositions ) {
            Block block = worldIn.getBlockState(blockPosition).getBlock();
            if ( block instanceof BlockRopeFence ) {
                worldIn.setBlockState(blockPosition, GrowthcraftHopsBlocks.hops_bush.getDefaultState());
            }
        }

    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        // Dump the entity inventory.
        if(!worldIn.isRemote) {
            TileEntityHopsBush te = (TileEntityHopsBush) worldIn.getTileEntity(pos);
            IItemHandler handler = te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);

            // Iterate over the handler and add one hop if the slot is empty.
            for (int i = 0; i < handler.getSlots(); i++) {
                if (!handler.getStackInSlot(i).isEmpty()) {
                    ItemStack stack = handler.extractItem(i, handler.getStackInSlot(i).getCount(), false);
                    InventoryHelper.spawnItemStack(worldIn, pos.getX(), pos.getY(), pos.getZ(), stack);
                }
            }

            te.markBlockUpdate(worldIn, pos);
        }
        return true;
    }

    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
        return state.withProperty(NORTH, hasHops(worldIn, pos, EnumFacing.NORTH))
                .withProperty(EAST, hasHops(worldIn, pos, EnumFacing.EAST))
                .withProperty(SOUTH, hasHops(worldIn, pos, EnumFacing.SOUTH))
                .withProperty(WEST, hasHops(worldIn, pos, EnumFacing.WEST));
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[] { NORTH, EAST, SOUTH, WEST } );
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState();
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return 0;
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityHopsBush();
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new TileEntityHopsBush();
    }

}
