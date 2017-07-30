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
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
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

    public BlockRopeKnot(String unlocalizedName) {
        super(Material.WOOD);
        this.setUnlocalizedName(unlocalizedName);
        this.setRegistryName(new ResourceLocation(Reference.MODID, unlocalizedName));
        this.setHardness(3);
        this.setResistance(20);
        this.setSoundType(SoundType.CLOTH);
        this.setHarvestLevel("axe", 0);
        this.useNeighborBrightness = true;
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
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
        ItemStack rope = new ItemStack(GrowthcraftCoreItems.rope, 1);
        InventoryHelper.spawnItemStack(worldIn, pos.getX(), pos.getY(), pos.getZ(), rope);

        TileEntityRopeKnot te = (TileEntityRopeKnot) worldIn.getTileEntity(pos);
        IItemHandler handler = te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
        for(int slot = 0; slot < handler.getSlots(); slot++) {
            ItemStack stack = handler.getStackInSlot(slot);
            InventoryHelper.spawnItemStack(worldIn, pos.getX(), pos.getY(), pos.getZ(), stack);
        }
        super.breakBlock(worldIn, pos, state);
    }

    @Override
    public int quantityDropped(Random random) {
        return 0;
    }


    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityRopeKnot();
    }

}
