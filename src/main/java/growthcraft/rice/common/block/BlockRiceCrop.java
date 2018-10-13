package growthcraft.rice.common.block;

import growthcraft.core.shared.block.BlockPaddyBase;
import growthcraft.core.shared.block.IPaddyCrop;
import growthcraft.rice.shared.Reference;
import growthcraft.rice.shared.init.GrowthcraftRiceItems;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.IGrowable;
import net.minecraft.block.SoundType;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class BlockRiceCrop extends BlockCrops implements IGrowable, IPlantable, IPaddyCrop {

    public static class RiceStage
    {
        public static final int SPROUTING = 0;
        public static final int SPROUT = 1;
        public static final int SAPLING = 2;
        public static final int FLOWERING = 3;
        public static final int MATURE = 4;

        private RiceStage() {}
    }

    public static final PropertyInteger AGE = PropertyInteger.create("age", RiceStage.SPROUTING, RiceStage.MATURE);

    private static Random rand = new Random();

    public BlockRiceCrop(String unlocalizedName) {
        this.setUnlocalizedName(unlocalizedName);
        this.setRegistryName(Reference.MODID, unlocalizedName);
        setHardness(0.0F);
        setSoundType(SoundType.PLANT);
        setDefaultState(this.getBlockState().getBaseState()
                .withProperty(AGE, 0)
        );
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World player, List<String> tooltip, ITooltipFlag advanced) {
        super.addInformation(stack, player, tooltip, advanced);
    }

    @Override
    protected Item getSeed() {
        return GrowthcraftRiceItems.rice.getItem();
    }

    @Override
    protected Item getCrop() {
        return GrowthcraftRiceItems.rice.getItem();
    }

    @Override
    public boolean isFlammable(IBlockAccess world, BlockPos pos, EnumFacing face) {
        return false;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }


    @Override
    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
        if( worldIn.isRemote )
            return;

        if (!this.canGrow(worldIn, pos, state, true))
        {
            List<ItemStack> drops = super.getDrops(worldIn, pos, state, 0);
            for( ItemStack drop : drops ) {
                spawnAsEntity(worldIn, pos, drop);
            }
        } else {
            grow(worldIn, rand, pos, state);
        }
    }

    public int getAge(IBlockState state) {
        return state.getValue(AGE);
    }

    @Override
    public boolean canGrow(World world, BlockPos blockPos, IBlockState iBlockState, boolean b) {
        return  (canBlockStay(world, blockPos, iBlockState) && getAge(iBlockState) < RiceStage.MATURE );
    }

    @Override
    public boolean canUseBonemeal(World world, Random random, BlockPos blockPos, IBlockState iBlockState) {
        return (canBlockStay(world, blockPos, iBlockState) && getAge(iBlockState) < RiceStage.MATURE );
    }

    @Override
    public void grow(World world, Random random, BlockPos blockPos, IBlockState iBlockState) {

    }

    /**
     * BlockRiceCrops can only be in the world if the block below them are an instance of BlockPaddyBase.
     */
    @Override
    public boolean canBlockStay(World world, BlockPos pos, IBlockState state) {
        BlockPos dwn = pos.down();
        IBlockState dwnState = world.getBlockState(dwn);
       return dwnState.getBlock() instanceof BlockPaddyBase;
    }


}
