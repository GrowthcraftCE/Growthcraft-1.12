package growthcraft.core.shared.client.particle;

import net.minecraft.client.particle.ParticleSmokeNormal;
import net.minecraft.world.World;

import java.util.Random;


public class ParticleSteamNormal extends ParticleSmokeNormal {
    private static Random random = new Random();

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
        return 0.6f + (float) (random.nextDouble() * 0.10000001192092896D - 0.05);
    }
}
