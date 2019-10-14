package growthcraft.core.shared.utils;

import java.util.Random;

public class SpatialRandom {
    private Random random;

    public SpatialRandom(Random rand) {
        this.random = rand;
    }

    public SpatialRandom() {
        this(new Random());
    }

    public Pair<Double, Double> nextD2() {
        return new Pair<Double, Double>(random.nextDouble(), random.nextDouble());
    }

    public Pair<Double, Double> nextCenteredD2() {
        return new Pair<Double, Double>(random.nextDouble() - 0.5, random.nextDouble() - 0.5);
    }

    public Triplet<Double, Double, Double> nextD3() {
        return new Triplet<Double, Double, Double>(random.nextDouble(), random.nextDouble(), random.nextDouble());
    }

    public Triplet<Double, Double, Double> nextCenteredD3() {
        return new Triplet<Double, Double, Double>(random.nextDouble() - 0.5, random.nextDouble() - 0.5, random.nextDouble() - 0.5);
    }
}
