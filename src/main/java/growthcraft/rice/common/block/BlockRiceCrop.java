package growthcraft.rice.common.block;

import growthcraft.core.shared.block.GrowthcraftBlockBase;
import growthcraft.core.shared.block.ICropDataProvider;
import growthcraft.core.shared.block.IPaddyCrop;
import growthcraft.rice.shared.Reference;
import net.minecraft.block.IGrowable;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.Random;

public class BlockRiceCrop extends GrowthcraftBlockBase implements IPaddyCrop, ICropDataProvider, IGrowable {

    public BlockRiceCrop(String unlocalizedName) {
        super(Material.PLANTS);
        this.setUnlocalizedName(unlocalizedName);
        this.setRegistryName(Reference.MODID, unlocalizedName);
    }

    @Override
    public float getGrowthProgress(IBlockAccess world, BlockPos pos, IBlockState state) {
        return 0;
    }

    @Override
    public boolean canGrow(World world, BlockPos blockPos, IBlockState iBlockState, boolean b) {
        return false;
    }

    @Override
    public boolean canUseBonemeal(World world, Random random, BlockPos blockPos, IBlockState iBlockState) {
        return false;
    }

    @Override
    public void grow(World world, Random random, BlockPos blockPos, IBlockState iBlockState) {

    }
}
