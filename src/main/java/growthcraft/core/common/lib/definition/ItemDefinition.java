package growthcraft.core.common.lib.definition;

import javax.annotation.Nonnull;

import net.minecraft.item.Item;

public class ItemDefinition extends ItemTypeDefinition<Item>
{
	public ItemDefinition(@Nonnull Item item)
	{
		super(item);
	}
}
