package growthcraft.core.blocks;

import growthcraft.core.Reference;
import growthcraft.core.init.GrowthcraftCoreItems;
import growthcraft.core.tileentity.TileEntityRopeKnot;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
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

    private static final AxisAlignedBB BOUNDING_BOX = new AxisAlignedBB(0.0625 * 5, 0.0625 * 6, 0.0625 * 5, 0.0625 * 11, 0.0625 * 14, 0.0625 * 11);
    private static final AxisAlignedBB COLLISION_BOX = new AxisAlignedBB(0.0625 * 6, 0.0625 * 6, 0.0625 * 6, 0.0625 * 10, 0.0625 * 13, 0.0625 * 10);

    private boolean wasActivated;

    public BlockRopeKnot(String unlocalizedName) {
        super(Material.WOOD);
        this.setUnlocalizedName(unlocalizedName);
        this.setRegistryName(new ResourceLocation(Reference.MODID, unlocalizedName));
        this.setHardness(3);
        this.setResistance(20);
        this.setSoundType(SoundType.CLOTH);
        this.setHarvestLevel("axe", 0);
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
        return BOUNDING_BOX;
    }

    @Override
    public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable Entity entityIn, boolean p_185477_7_) {
        super.addCollisionBoxToList(pos, entityBox, collidingBoxes, COLLISION_BOX);
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
            InventoryHelper.spawnItemStack(worldIn, pos.getX(), pos.getY(), pos.getZ(), stack);
        }
        // Always set it to false upon block break.
        this.wasActivated = false;
        //super.breakBlock(worldIn, pos, state);
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


    /**
     *
     * @param worldIn
     * @param pos
     * @return
     */
    private IItemHandler getInventoryHandler(World worldIn, BlockPos pos) {
        TileEntityRopeKnot te = (TileEntityRopeKnot) worldIn.getTileEntity(pos);
        IItemHandler handler = te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
        return handler;
    }
}
