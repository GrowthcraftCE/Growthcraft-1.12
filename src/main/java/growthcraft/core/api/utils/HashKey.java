package growthcraft.core.api.utils;

public abstract class HashKey
{
	protected int hash;

	@Override
	public int hashCode()
	{
		return hash;
	}

	@Override
	public boolean equals(Object other)
	{
		if (!(other instanceof HashKey)) return false;
		return hashCode() == other.hashCode();
	}
}
