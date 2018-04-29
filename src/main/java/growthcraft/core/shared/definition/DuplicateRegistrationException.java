package growthcraft.core.shared.definition;

/**
 * This exception is thrown when something was registered more than once.
 */
public class DuplicateRegistrationException extends RuntimeException
{
	// REVISE_ME 0: Move to other package
	
	public static final long serialVersionUID = 1L;

	public DuplicateRegistrationException(String msg)
	{
		super(msg);
	}

	public DuplicateRegistrationException() {}

	/**
	 * Helper method for creating frozen errors for a specified object.
	 *
	 * @param obj - object to generate frozen error for
	 * @return new frozen error
	 */
	public static DuplicateRegistrationException newFor(Object obj)
	{
		return new DuplicateRegistrationException("Cannot re-register object! " + obj);
	}
}