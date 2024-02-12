package growthcraft.bamboo.common.block;

import growthcraft.bamboo.shared.Reference;
import growthcraft.bamboo.shared.init.GrowthcraftBambooBlocks;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.SoundType;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.IShearable;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.List;

public class BlockBambooLeaves extends BlockLeaves implements IShearable {

    public BlockBambooLeaves(String unlocalizedName) {
        this.setTranslationKey(unlocalizedName);
        this.setRegistryName(new ResourceLocation(Reference.MODID, unlocalizedName));
        this.setLightOpacity(1);
        this.setSoundType(SoundType.PLANT);
        this.setTickRandomly(true);
        this.setDefaultState(this.blockState.getBaseState().withProperty(CHECK_DECAY, Boolean.valueOf(true)).withProperty(DECAYABLE, Boolean.valueOf(true)));
        Blocks.FIRE.setFireInfo(this, 5, 20);
    }

    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[]{CHECK_DECAY, DECAYABLE});
    }

    /**
     * Convert the BlockState into the correct metadata value
     */
    public int getMetaFromState(IBlockState state) {
        int i = 0;
        if (!state.getValue(DECAYABLE)) {
            i |= 4;
        }
        if (state.getValue(CHECK_DECAY)) {
            i |= 8;
        }
        return i;
    }

    /**
     * Convert the given metadata into a BlockState for this Block
     */
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(DECAYABLE, Boolean.valueOf((meta & 4) == 0)).withProperty(CHECK_DECAY, Boolean.valueOf((meta & 8) > 0));
    }

    @Override
    public boolean isShearable(@Nonnull ItemStack item, IBlockAccess world, BlockPos pos) {
        return true;
    }

    @Override
    public List<ItemStack> onSheared(@Nonnull ItemStack item, IBlockAccess world, BlockPos pos, int fortune) {
        return Arrays.asList(GrowthcraftBambooBlocks.bambooLeaves.getItemAsStack(1));
    }

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

    @Override
    public BlockRenderLayer getRenderLayer() {
        return Blocks.LEAVES.getRenderLayer();
    }

    @Override
    public BlockPlanks.EnumType getWoodType(int i) {
        return null;
    }

    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
        // drop nothing unless sheared.
    }

    @Override
    public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
        // Drop nothing, bamboo leaves can only be sheared.
    }
}
