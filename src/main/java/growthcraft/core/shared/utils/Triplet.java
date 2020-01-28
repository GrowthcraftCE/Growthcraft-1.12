package growthcraft.core.shared.utils;

public class Triplet<TLeft, TCenter, TRight> {
    public TLeft left;
    public TCenter center;
    public TRight right;

    public Triplet(TLeft l, TCenter c, TRight r) {
        this.left = l;
        this.center = c;
        this.right = r;
    }
}
