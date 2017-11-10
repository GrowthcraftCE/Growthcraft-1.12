package growthcraft.bamboo.handler;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

import javax.annotation.Nullable;

public class ColorHandlerBlockBambooLeaves implements IBlockColor {

    public ColorHandlerBlockBambooLeaves() {

    }

    @Override
    public int colorMultiplier(IBlockState state, @Nullable IBlockAccess worldIn, @Nullable BlockPos pos, int tintIndex) {
        int color = 0;
        switch ( tintIndex ) {
            case 0:
                color = 6192150;
                break;
            case 1:
                color = 0xbbd284;
                break;
        }
        return color;
    }
}