package growthcraft.cellar.common.lib.booze.modifier;

import growthcraft.cellar.common.lib.booze.IModifierFunction;

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
