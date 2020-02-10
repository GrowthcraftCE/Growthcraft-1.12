package growthcraft.cellar.client.render;

import growthcraft.cellar.common.tileentity.TileEntityBrewKettle;
import growthcraft.core.shared.client.render.TileFluidTanksSpecialRenderer;
import growthcraft.core.shared.utils.BBox;

import javax.annotation.Nonnull;

public class RenderBrewKettle extends TileFluidTanksSpecialRenderer<TileEntityBrewKettle> {
    public static final BBox BBOX_FLUID = BBox.newCube(1, 4, 1, 14, 10, 14).scale(1.0 / 16.0);

    public RenderBrewKettle() {
        super(BBOX_FLUID);
    }

    @Override
    public void render(@Nonnull TileEntityBrewKettle te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        renderMaxFluid(te, x, y, z);
    }

}
