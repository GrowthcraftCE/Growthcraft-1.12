package growthcraft.milk.api;

import growthcraft.milk.api.processing.cheesepress.CheesePressRegistry;
import growthcraft.milk.api.processing.cheesevat.CheeseVatRegistry;
import growthcraft.milk.api.processing.churn.ChurnRegistry;
import growthcraft.milk.api.processing.pancheon.PancheonRegistry;

public class MilkRegistry
{
	private static final MilkRegistry INSTANCE = new MilkRegistry();

	private final CheesePressRegistry cheesePressRegistry = new CheesePressRegistry();
	private final CheeseVatRegistry cheeseVatRegistry = new CheeseVatRegistry();
	private final ChurnRegistry churnRegistry = new ChurnRegistry();
	private final PancheonRegistry pancheonRegistry = new PancheonRegistry();

	/**
	 * @return current instrance of the MilkRegistry
	 */
	public static final MilkRegistry instance()
	{
		return INSTANCE;
	}

	/**
	 * @return instance of the CheesePressRegistry
	 */
	public CheesePressRegistry cheesePress()
	{
		return cheesePressRegistry;
	}

	/**
	 * @return instance of the CheeseVatRegistry
	 */
	public CheeseVatRegistry cheeseVat()
	{
		return cheeseVatRegistry;
	}

	/**
	 * @return instance of the ChurnRegistry
	 */
	public ChurnRegistry churn()
	{
		return churnRegistry;
	}

	/**
	 * @return instance of the PancheonRegistry
	 */
	public PancheonRegistry pancheon()
	{
		return pancheonRegistry;
	}
}