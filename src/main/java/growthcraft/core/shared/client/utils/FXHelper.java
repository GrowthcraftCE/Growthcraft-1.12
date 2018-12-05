package growthcraft.core.shared.client.utils;

import growthcraft.core.shared.client.particle.ParticleDrop;
import growthcraft.core.shared.client.particle.ParticleSteamLarge;
import growthcraft.core.shared.client.particle.ParticleSteamNormal;
import net.minecraft.client.particle.Particle;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class FXHelper
{
	private FXHelper() {}

	public static void dropParticle(World world, double px, double py, double pz, int color)
	{
		final float particleRed = ((color >> 16) & 0xFF) / 255.0f;
		final float particleGreen = ((color >> 8) & 0xFF) / 255.0f;
		final float particleBlue = (color & 0xFF) / 255.0f;
		final Particle fx = new ParticleDrop(world, px, py, pz, particleRed, particleGreen, particleBlue);
		FMLClientHandler.instance().getClient().effectRenderer.addEffect(fx);
	}
	
	public static void emitSmoke(World world, double px, double py, double pz, double speedX, double speedY, double speedZ) {
		world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, px, py, pz, speedX, speedY, speedZ);
	}

	public static void emitSmokeBig(World world, double px, double py, double pz, double speedX, double speedY, double speedZ) {
		world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, px, py, pz, speedX, speedY, speedZ);
	}

	public static void emitSteam(World world, double px, double py, double pz, double speedX, double speedY, double speedZ) {
		final Particle fx = new ParticleSteamNormal(world, px, py, pz, speedX, speedY, speedZ);
		FMLClientHandler.instance().getClient().effectRenderer.addEffect(fx);
	}
	
	public static void emitSteamBig(World world, double px, double py, double pz, double speedX, double speedY, double speedZ) {
		final Particle fx = new ParticleSteamLarge(world, px, py, pz, speedX, speedY, speedZ);
		FMLClientHandler.instance().getClient().effectRenderer.addEffect(fx);
	}
}
