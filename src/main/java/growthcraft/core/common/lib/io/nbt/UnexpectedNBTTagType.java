package growthcraft.core.common.lib.io.nbt;

/**
 * Exception thrown when a specific tag type is expected and another is encountered instead.
 */
public class UnexpectedNBTTagType extends RuntimeException
{
	public static final long serialVersionUID = 1L;

	public UnexpectedNBTTagType(String msg)
	{
		super(msg);
	}

	public UnexpectedNBTTagType() {}

	public static UnexpectedNBTTagType createFor(Object expected, Object actual)
	{
		return new UnexpectedNBTTagType("Wrong NBT Tag type `" + actual + "` (expected `" + expected + "`)");
	}
}
