package growthcraft.core.shared.definition;

import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

public abstract class ObjectDefinition<T> {
    protected T obj;

    public ObjectDefinition(@Nonnull T o) {
        this.obj = o;
    }

    @Nonnull
    public T getObject() {
        return obj;
    }

    @Nonnull
    public abstract ItemStack asStack(int size, int damage);

    @Nonnull
    public ItemStack asStack(int size) {
        return asStack(size, 0);
    }

    @Nonnull
    public ItemStack asStack() {
        return asStack(1);
    }
}
