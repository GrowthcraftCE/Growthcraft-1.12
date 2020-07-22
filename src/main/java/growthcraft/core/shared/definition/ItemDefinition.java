package growthcraft.core.shared.definition;

import net.minecraft.item.Item;

import javax.annotation.Nonnull;

public class ItemDefinition extends ItemTypeDefinition<Item> {
    public ItemDefinition(@Nonnull Item item) {
        super(item);
    }
}
