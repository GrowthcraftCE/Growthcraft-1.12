package growthcraft.core.shared.client.particle;

import net.minecraft.client.particle.ParticleSmokeLarge;
import net.minecraft.world.World;

public class ParticleSteamLarge extends ParticleSmokeLarge {
	public ParticleSteamLarge(World worldIn,
			double xCoordIn, double yCoordIn, double zCoordIn,
			double xSpeedIn, double ySpeedIn, double zSpeedIn) {
		super(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn);
		
        float f = ParticleSteamNormal.getSteamColor();
        this.particleRed = f;
        this.particleGreen = f;
        this.particleBlue = f;
	}
}
