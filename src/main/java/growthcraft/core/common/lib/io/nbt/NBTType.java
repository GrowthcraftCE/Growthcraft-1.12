package growthcraft.core.common.lib.io.nbt;

import java.util.HashMap;
import java.util.Map;

public enum NBTType
{
	// REVISE_ME 0: Move to utils
	
	END(0),
	BYTE(1),
	SHORT(2),
	INT(3),
	LONG(4),
	FLOAT(5),
	DOUBLE(6),
	BYTE_ARRAY(7),
	STRING(8),
	LIST(9),
	COMPOUND(10),
	INT_ARRAY(11);

	public static final Map<Integer, NBTType> MAPPING = new HashMap<Integer, NBTType>();
	static
	{
		for (NBTType type : values())
		{
			type.register();
		}
	}

	public final int id;

	private NBTType(int i)
	{
		this.id = i;
	}

	private void register()
	{
		MAPPING.put(this.id, this);
	}

	public static NBTType byId(int id)
	{
		return MAPPING.get(id);
	}
}