package growthcraft.apples.handlers;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

import javax.annotation.Nullable;

public class GrowthcraftApplesColorHandler implements IBlockColor {

    public GrowthcraftApplesColorHandler( ) {

    }

    @Override
    public int colorMultiplier(IBlockState state, @Nullable IBlockAccess worldIn, @Nullable BlockPos pos, int tintIndex) {
        int color = 4764952;


        return color;
    }
}
