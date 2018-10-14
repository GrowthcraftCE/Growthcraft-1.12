package growthcraft.rice.common.block;

import growthcraft.core.shared.GrowthcraftLogger;
import growthcraft.core.shared.block.BlockFlags;
import growthcraft.core.shared.block.BlockPaddyBase;
import growthcraft.core.shared.block.IPaddyCrop;
import growthcraft.rice.shared.Reference;
import growthcraft.rice.shared.init.GrowthcraftRiceItems;
import net.minecraft.block.BlockBush;
import net.minecraft.block.IGrowable;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

import static growthcraft.core.shared.block.BlockPaddyBase.IS_RADIOACTIVE;

public class BlockRiceCrop extends BlockBush implements IGrowable, IPlantable, IPaddyCrop {

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

    protected Item getSeed() {
        return GrowthcraftRiceItems.rice.getItem();
    }

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

    public boolean isMature(IBlockAccess worldIn, BlockPos pos, IBlockState state) {
        return getAge(state) >= RiceStage.MATURE;
    }

    public float getGrowthProgress(IBlockAccess worldIn, BlockPos pos, IBlockState state) {
        return (float)getAge(state) / (float)RiceStage.MATURE;
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
    public void grow(World world, Random random, BlockPos pos, IBlockState state) {

        BlockPos posDown = pos.down();
        IBlockState downBlockState = world.getBlockState(posDown);

        if (downBlockState.getValue(IS_RADIOACTIVE)) {
            GrowthcraftLogger.getLogger(Reference.MODID).info("Block is radioactive.");
            growWithBoneMeal(world, random, pos, state);
        } else {
            int nextAge = getAge(state) + 1;
            if (nextAge <= RiceStage.MATURE && world.getLightFromNeighbors(pos.up()) >= 9 && rand.nextInt(7) == 0) {
                world.setBlockState(pos, state.withProperty(AGE, nextAge), BlockFlags.SYNC);
            }
        }
    }

    public void growWithBoneMeal(World world, Random random, BlockPos pos, IBlockState state) {
        int nextAge = getAge(state) + 1;

        if( nextAge <= RiceStage.MATURE && world.getLightFromNeighbors(pos.up()) >= 9 && rand.nextInt(7) == 0 ) {
            world.setBlockState(pos, state.withProperty(AGE, nextAge), BlockFlags.SYNC);
            spawnBoneMealParticles(world, pos, 15);
        }
    }

    @SideOnly(Side.CLIENT)
    public static void spawnBoneMealParticles(World worldIn, BlockPos pos, int amount) {
        if (amount == 0) {
            amount = 15;
        }

        IBlockState iblockstate = worldIn.getBlockState(pos);
        int i;
        double d0;
        double d1;
        double d2;
        if (iblockstate.getMaterial() != Material.AIR) {
            for(i = 0; i < amount; ++i) {
                d0 = rand.nextGaussian() * 0.02D;
                d1 = rand.nextGaussian() * 0.02D;
                d2 = rand.nextGaussian() * 0.02D;
                worldIn.spawnParticle(EnumParticleTypes.VILLAGER_HAPPY, (double)((float)pos.getX() + rand.nextFloat()), (double)pos.getY() + (double)rand.nextFloat() * iblockstate.getBoundingBox(worldIn, pos).maxY, (double)((float)pos.getZ() + rand.nextFloat()), d0, d1, d2, new int[0]);
                GrowthcraftLogger.getLogger(Reference.MODID).info("Spawn particles ...");

            }
        } else {
            for(i = 0; i < amount; ++i) {
                d0 = rand.nextGaussian() * 0.02D;
                d1 = rand.nextGaussian() * 0.02D;
                d2 = rand.nextGaussian() * 0.02D;
                worldIn.spawnParticle(EnumParticleTypes.VILLAGER_HAPPY, (double)((float)pos.getX() + rand.nextFloat()), (double)pos.getY() + (double)rand.nextFloat() * 1.0D, (double)((float)pos.getZ() + rand.nextFloat()), d0, d1, d2, new int[0]);
            }
        }

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

    /************
     * IPLANTABLE
     ************/
    @Override
    public EnumPlantType getPlantType(IBlockAccess world, BlockPos pos)
    {
        return EnumPlantType.Crop;
    }

    @Override
    public IBlockState getPlant(IBlockAccess world, BlockPos pos)
    {
        return getDefaultState();
    }

    /************
     * STATES
     ************/

    @Nonnull
    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, AGE );
    }

    @Nonnull
    @Override
    @SuppressWarnings("deprecation")
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(AGE, meta & 0x3);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        int meta = 0;
        meta |= state.getValue(AGE) & 0x3;
        return meta;
    }

}
