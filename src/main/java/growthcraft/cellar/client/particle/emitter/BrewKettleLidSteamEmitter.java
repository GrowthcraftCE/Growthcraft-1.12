package growthcraft.cellar.client.particle.emitter;

import growthcraft.core.shared.client.utils.FXHelper;
import growthcraft.core.shared.utils.Pair;
import growthcraft.core.shared.utils.PulseStepper;
import growthcraft.core.shared.utils.SpatialRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;


public class BrewKettleLidSteamEmitter {
    private PulseStepper animPulsar = new PulseStepper(5, 8);
    private SpatialRandom sprand = new SpatialRandom();
    private Pair<Double, Double> steamEmitPos = null;

    public void update(World world, BlockPos pos) {
        PulseStepper.State pulseState = animPulsar.update();
        if (pulseState == PulseStepper.State.NONE || steamEmitPos == null) {
            // NOTE: steamEmitPos == null means initial state.
            if (world.rand.nextInt(20) >= 19) {
                steamEmitPos = sprand.nextCenteredD2();
                animPulsar.reset();
            }
        } else if (pulseState == PulseStepper.State.PULSE) {
            if (world.rand.nextInt(10) >= 3) {
                // Spread steamEmitPos a little
                Pair<Double, Double> p = sprand.nextCenteredD2();
                p = new Pair<Double, Double>(steamEmitPos.left + p.left * 0.5, steamEmitPos.right + p.right * 0.5);

                // Normalize by 2-Norm (used for velocity)
                double horDist = Math.sqrt(p.left * p.left + p.right * p.right);
                if (Math.abs(horDist) >= 0.00001) {
                    p = new Pair<Double, Double>(p.left / horDist, p.right / horDist);
                } else {
                    p = new Pair<Double, Double>(1.0, 0.0);
                }

                // Normalize by max-Norm (used for position)
                double maxHorDist = Math.max(Math.abs(p.left), Math.abs(p.right));
                Pair<Double, Double> pMax = new Pair<Double, Double>(p.left / maxHorDist, p.right / maxHorDist);

                double vel = (0.125 + 0.1 * world.rand.nextDouble()) * 0.0625;
                double px = (double) pos.getX() + 0.5 + pMax.left * 0.5;
                double py = (double) pos.getY() + 1.0;
                double pz = (double) pos.getZ() + 0.5 + pMax.right * 0.5;
                double vx = p.left * vel;
                double vy = vel;
                double vz = p.right * vel;
                FXHelper.emitSteam(world, px, py, pz, vx, vy, vz);
            }
        }
    }
}
