package growthcraft.core.shared.config.description;

import java.util.List;

/**
 * Interface for objects that can provide a Description
 */
public interface IDescribable
{
	// REVISE_ME 0: Move to utils
	
	/**
	 * This method is normally called by items when they want a description
	 * of their objects, feel free to push as many lines as you need into
	 * the list to describe the object
	 *
	 * @param list - list to add description lines to
	 */
	void getDescription(List<String> list);
}
