package growthcraft.apples.common.block;

import growthcraft.apples.shared.Reference;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.IGrowable;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
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
        return new BlockStateContainer(this, AGE);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(AGE);
    }

    @SuppressWarnings("deprecation")
    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(AGE, meta);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World player, List<String> tooltip, ITooltipFlag advanced) {
        super.addInformation(stack, player, tooltip, advanced);
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
        	if( ForgeHooks.onCropsGrowPre(worldIn, pos, state, rand.nextInt(7) == 0) ) {
				grow(worldIn, rand, pos, state);
				ForgeHooks.onCropsGrowPost(worldIn, pos, state, worldIn.getBlockState(pos));
			}
        }
    }

    @Override
    public boolean canGrow(World worldIn, BlockPos pos, IBlockState state, boolean isClient) {
        return this.getAge(state) != 7;
    }

    @Override
    public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, IBlockState state) {
        return this.getAge(state) < 7;
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
        return block instanceof BlockAppleLeaves;
    }

    private int getAge(IBlockState state) {
        return state.getValue(AGE).intValue();
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if ( this.getAge(state) == 7) {
            if ( !worldIn.isRemote) {
                this.dropBlockAsItem(worldIn, pos, state, 0);
                worldIn.setBlockToAir(pos);
            }
            return true;
        }
        return super.onBlockActivated(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ);
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        ItemStack appleStack = new ItemStack(Items.APPLE, 1);
        return appleStack.getItem();
    }
}
