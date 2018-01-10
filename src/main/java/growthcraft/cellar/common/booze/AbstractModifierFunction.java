package growthcraft.cellar.common.booze;

import growthcraft.cellar.api.booze.IModifierFunction;

public class AbstractModifierFunction implements IModifierFunction
{
	// REVISE_ME 0
	
	@Override
	public int applyLevel(int l)
	{
		return l;
	}

	@Override
	public int applyTime(int t)
	{
		return t;
	}
}
