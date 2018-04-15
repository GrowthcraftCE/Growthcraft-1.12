package growthcraft.cellar.common.lib.processing.heatsource.user;

import java.util.HashMap;
import java.util.Map;

import growthcraft.core.shared.item.ItemKey;
import growthcraft.core.shared.config.schema.ICommentable;

public class UserHeatSourceEntry implements ICommentable
{
	public String comment = "";
	public String mod_id;
	public String block_name;
	public Map<Integer, Float> states;

	public UserHeatSourceEntry() {}

	public UserHeatSourceEntry(String m, String b, Map<Integer, Float> s)
	{
		this.mod_id = m;
		this.block_name = b;
		this.states = s;
	}

	@Override
	public String toString()
	{
		String result = String.format("UserHeatSourceEntry(mod_id: '%s', block_name: '%s')", mod_id, block_name);
		if (states != null)
		{
			result += "~(";
			for (Map.Entry<Integer, Float> pair : states.entrySet())
			{
				result += pair.getKey() + "=>" + pair.getValue() + ", ";
			}
			result += ")";
		}
		return result;
	}

	@Override
	public void setComment(String comm)
	{
		this.comment = comm;
	}

	@Override
	public String getComment()
	{
		return comment;
	}

	public static Map<Integer, Float> newHeatPair(int i, float h)
	{
		final Map<Integer, Float> map = new HashMap<Integer, Float>();
		map.put(i, h);
		return map;
	}

	public static Map<Integer, Float> newWildcardHeat(float h)
	{
		return newHeatPair(ItemKey.WILDCARD_VALUE, h);
	}
}
