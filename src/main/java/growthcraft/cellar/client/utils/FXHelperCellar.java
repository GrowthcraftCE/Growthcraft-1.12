package growthcraft.cellar.client.utils;

import growthcraft.cellar.client.particle.ParticleKettleBubble;
import growthcraft.cellar.client.particle.params.FluidTanksParams;
import net.minecraft.client.particle.Particle;
import net.minecraft.world.World;
import net.minecraftforge.fml.client.FMLClientHandler;

public class FXHelperCellar {
	private FXHelperCellar() {}
	
	public static void emitKettleBubbles(World world, double px, double py, double pz, double speedX, double speedY, double speedZ, FluidTanksParams params) {
		final Particle fx = new ParticleKettleBubble(world, px, py, pz, speedX, speedY, speedZ, params);
		FMLClientHandler.instance().getClient().effectRenderer.addEffect(fx);
	}
}
