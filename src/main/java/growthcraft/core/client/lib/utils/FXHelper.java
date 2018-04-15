package growthcraft.core.client.lib.utils;

import growthcraft.core.client.lib.particle.ParticleDrop;
import net.minecraft.client.particle.Particle;
import net.minecraft.world.World;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class FXHelper
{
	private FXHelper() {}

	@SideOnly(Side.CLIENT)
	public static void dropParticle(World world, double px, double py, double pz, int color)
	{
		final float particleRed = ((color >> 16) & 0xFF) / 255.0f;
		final float particleGreen = ((color >> 8) & 0xFF) / 255.0f;
		final float particleBlue = (color & 0xFF) / 255.0f;
		final Particle fx = new ParticleDrop(world, px, py, pz, particleRed, particleGreen, particleBlue);
		FMLClientHandler.instance().getClient().effectRenderer.addEffect(fx);
	}
}
