package growthcraft.core.common;

/**
 * This exception is thrown when something was registered more than once.
 */
public class DuplicateRegistrationError extends RuntimeException
{
	// REVISE_ME 0: Move to other package
	
	public static final long serialVersionUID = 1L;

	public DuplicateRegistrationError(String msg)
	{
		super(msg);
	}

	public DuplicateRegistrationError() {}

	/**
	 * Helper method for creating frozen errors for a specified object.
	 *
	 * @param obj - object to generate frozen error for
	 * @return new frozen error
	 */
	public static DuplicateRegistrationError newFor(Object obj)
	{
		return new DuplicateRegistrationError("Cannot re-register object! " + obj);
	}
}