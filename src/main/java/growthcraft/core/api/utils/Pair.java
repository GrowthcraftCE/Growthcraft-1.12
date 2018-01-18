package growthcraft.core.api.utils;

public class Pair<TLeft, TRight>
{
	public TLeft left;
	public TRight right;

	public Pair(TLeft l, TRight r)
	{
		this.left = l;
		this.right = r;
	}
}
