package growthcraft.cellar.shared.booze.modifier;

public class ModifierFunctionPotent extends AbstractModifierFunction
{
	// REVISE_ME 0
	
	@Override
	public int applyLevel(int lvl)
	{
		return lvl + 1;
	}

	@Override
	public int applyTime(int t)
	{
		return t / 2;
	}
}
