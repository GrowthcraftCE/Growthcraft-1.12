package growthcraft.rice.common.block;

import growthcraft.core.shared.block.BlockPaddyBase;
import growthcraft.rice.shared.Reference;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

import javax.annotation.Nonnull;
import java.util.Random;

public class BlockPaddy extends BlockPaddyBase {

    // TODO: Implement config for max field.

    public BlockPaddy(String unlocalizedName) {
        super(Material.GROUND);
        this.setUnlocalizedName(unlocalizedName);
        this.setRegistryName(Reference.MODID, unlocalizedName);
        this.setHardness(0.5F);
        this.setSoundType(SoundType.GROUND);
    }

    @Override
    public void fillWithRain(World worldIn, BlockPos pos) {
        super.fillWithRain(worldIn, pos);
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return Item.getItemFromBlock(Blocks.DIRT);
    }

    @Override
    public int quantityDropped(Random random) {
        return 1;
    }

    @Nonnull
    @Override
    public Fluid getFillingFluid() {
        return FluidRegistry.WATER;
    }

    @Nonnull
    @Override
    public Block getFluidBlock() {
        return Blocks.WATER;
    }
}
