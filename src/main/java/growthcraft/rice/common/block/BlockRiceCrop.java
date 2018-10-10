package growthcraft.rice.common.block;

import growthcraft.core.shared.block.IPaddyCrop;
import growthcraft.rice.shared.Reference;
import growthcraft.rice.shared.init.GrowthcraftRiceItems;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.IGrowable;
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

    public BlockRiceCrop(String unlocalizedName) {
        this.setUnlocalizedName(unlocalizedName);
        this.setRegistryName(Reference.MODID, unlocalizedName);
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
    public boolean isFullCube(IBlockState state) {
        return false;
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
