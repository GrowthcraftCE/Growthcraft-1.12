package growthcraft.milk.shared;

import growthcraft.milk.shared.cheese.CheeseRegistry;
import growthcraft.milk.shared.processing.cheesepress.CheesePressRegistry;
import growthcraft.milk.shared.processing.cheesevat.CheeseVatRegistry;
import growthcraft.milk.shared.processing.churn.ChurnRegistry;
import growthcraft.milk.shared.processing.pancheon.PancheonRegistry;

public class MilkRegistry
{
	private static final MilkRegistry INSTANCE = new MilkRegistry();

	private final CheesePressRegistry cheesePressRegistry = new CheesePressRegistry();
	private final CheeseVatRegistry cheeseVatRegistry = new CheeseVatRegistry();
	private final ChurnRegistry churnRegistry = new ChurnRegistry();
	private final PancheonRegistry pancheonRegistry = new PancheonRegistry();
	private final CheeseRegistry cheeseRegistry = new CheeseRegistry();

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
	
	/**
	 * @return instance of the CheeseRegistry
	 */
	public CheeseRegistry cheese() {
		return cheeseRegistry;
	}
}