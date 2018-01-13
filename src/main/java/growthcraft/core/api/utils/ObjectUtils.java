package growthcraft.core.api.utils;

public class ObjectUtils
{
	private ObjectUtils() {}

	@SuppressWarnings({"unchecked"})
	public static <T> T maybe(T ...objects)
	{
		for (T object : objects)
		{
			if (object != null) return object;
		}
		return null;
	}
}
