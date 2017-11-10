package growthcraft.apples.blocks;

import growthcraft.apples.Reference;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.IGrowable;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

public class BlockApple extends BlockBush implements IGrowable {

    public static final PropertyInteger AGE = PropertyInteger.create("age", 0, 7);

    private static final AxisAlignedBB[] BOUNDING_BOXES = new AxisAlignedBB[]{
            new AxisAlignedBB(
                    0.0625 * 6, 0.0625 * 10, 0.0625 * 6,
                    0.0625 * 10, 0.0625 * 14, 0.0625 * 10 ),
            new AxisAlignedBB(
                    0.0625 * 5, 0.0625 * 9, 0.0625 * 5,
                    0.0625 * 11, 0.0625 * 14, 0.0625 * 11 ),
            new AxisAlignedBB(
                    0.0625 * 4, 0.0625 * 7, 0.0625 * 4,
                    0.0625 * 12, 0.0625 * 14, 0.0625 * 12 ),
    };

    public BlockApple(String unlocalizedName) {
        this.setUnlocalizedName(unlocalizedName);
        this.setRegistryName(new ResourceLocation(Reference.MODID, unlocalizedName));
        this.setTickRandomly(true);
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[] { AGE });
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(AGE);
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(AGE, meta);
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
        super.addInformation(stack, playerIn, tooltip, advanced);
        tooltip.add(TextFormatting.BLUE + I18n.format(this.getUnlocalizedName() + ".tooltip"));
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        int iAge = this.getAge(state);

        if ( iAge <= 3 ) {
            return BOUNDING_BOXES[0];
        } else if ( iAge > 3 && iAge <= 6 ) {
            return BOUNDING_BOXES[1];
        }

        return BOUNDING_BOXES[2];
    }

    @Override
    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
        super.updateTick(worldIn, pos, state, rand);
        if ( worldIn.getLightFromNeighbors(pos.up()) >= 9 && rand.nextInt(7) == 0) {
            this.grow(worldIn, rand, pos, state);
        }
    }

    @Override
    public boolean canGrow(World worldIn, BlockPos pos, IBlockState state, boolean isClient) {
        if (this.getAge(state) != 7) { return true; }
        return false;
    }

    @Override
    public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, IBlockState state) {
        return this.getAge(state) < 7 ? true : false;
    }

    @Override
    public void grow(World worldIn, Random rand, BlockPos pos, IBlockState state) {
        super.updateTick(worldIn, pos, state, rand);
        // If we have enough light there is a 25% chance of growth to the next stage
        if ( worldIn.getLightFromNeighbors(pos.up()) >= 9 && rand.nextInt(1) == 0) {
            // If the apple isn't full grown
            if ( this.getAge(state) != 7) {
                // Then increment the age.
                worldIn.setBlockState(pos, state.cycleProperty(AGE), 4);
                this.markBlockUpdate(worldIn, pos);
            }
        }
    }

    private void markBlockUpdate(World worldIn, BlockPos pos ) {
        worldIn.markBlockRangeForRenderUpdate(pos, pos);
        worldIn.notifyBlockUpdate(pos, worldIn.getBlockState(pos), worldIn.getBlockState(pos), 3);
        worldIn.scheduleBlockUpdate(pos, this, 0,0);
    }

    @Override
    public boolean canBlockStay(World worldIn, BlockPos pos, IBlockState state) {
        Block block = worldIn.getBlockState(pos.up()).getBlock();
        if ( block instanceof BlockAppleLeaves) {
            return true;
        }
        return false;
    }

    private int getAge(IBlockState state) {
        return ((Integer)state.getValue(AGE)).intValue();
    }

    @Override
    public void onBlockDestroyedByPlayer(World worldIn, BlockPos pos, IBlockState state) {
        super.onBlockDestroyedByPlayer(worldIn, pos, state);
        // TODO: Drop a sapling
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if ( this.getAge(state) == 7) {
            int qty = new Random().nextInt(1) + 1;
            if ( !worldIn.isRemote) {
                ItemStack appleStack = new ItemStack(Items.APPLE, qty);
                InventoryHelper.spawnItemStack(worldIn, pos.getX(), pos.getY(), pos.getZ(), appleStack);
                worldIn.setBlockToAir(pos);
            }
            return true;
        }
        return super.onBlockActivated(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ);
    }

}
