package growthcraft.core.shared.handlers;

import growthcraft.core.shared.utils.ColorUtils;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.world.ColorizerFoliage;
import net.minecraft.world.biome.BiomeColorHelper;

public class BlockColorHandler {

    private BlockColorHandler() {
        // Nothing to do at this time.
    }

    public static void registerBlockColorHandler(Block block, int hexColor) {

        BlockColors blockColors = Minecraft.getMinecraft().getBlockColors();
        blockColors.registerBlockColorHandler(
                (state, worldIn, pos, tintIndex) -> {
                    float[] color = ColorUtils.getFloat4ARGB(hexColor);
                    if (worldIn != null && pos != null) {
                        float[] baseColor = ColorUtils.getFloat4ARGB(ColorizerFoliage.getFoliageColorBasic());
                        float[] curColor = ColorUtils.getFloat4ARGB(BiomeColorHelper.getFoliageColorAtPos(worldIn, pos));

                        float[] colorRatio = new float[3];
                        for (int i = 1; i < 3; i++)
                            colorRatio[i - 1] = curColor[i] / baseColor[i];

                        for (int i = 1; i < 3; i++)
                            color[i] *= colorRatio[i - 1];

                    }

                    return ColorUtils.getIntARGB(color);
                },
                block);
    }
}
