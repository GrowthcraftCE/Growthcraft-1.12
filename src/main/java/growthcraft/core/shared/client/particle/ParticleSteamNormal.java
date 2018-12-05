package growthcraft.core.shared.client.particle;

import net.minecraft.client.particle.ParticleSmokeNormal;
import net.minecraft.world.World;

public class ParticleSteamNormal extends ParticleSmokeNormal {
	
	public ParticleSteamNormal(World worldIn,
			double xCoordIn, double yCoordIn, double zCoordIn,
			double xSpeedIn, double ySpeedIn, double zSpeedIn) {
		super(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn, 1.0f);
		
        float f = getSteamColor();
        this.particleRed = f;
        this.particleGreen = f;
        this.particleBlue = f;
	}
	
	static float getSteamColor() {
		return 0.6f + (float)(Math.random() * 0.10000001192092896D - 0.05);
	}
}
