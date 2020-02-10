package growthcraft.core.shared.item;

import com.google.common.base.CaseFormat;
import growthcraft.core.shared.definition.IItemStackFactory;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import java.util.Locale;

/**
 * Sometimes you honestly can't remember them all, but hey, enum!
 */
public enum EnumDye implements IItemStackFactory {
    BLACK,
    RED,
    GREEN,
    BROWN,
    BLUE,
    PURPLE,
    CYAN,
    LIGHT_GRAY,
    GRAY,
    PINK,
    LIME,
    YELLOW,
    LIGHT_BLUE,
    MAGENTA,
    ORANGE,
    WHITE;

    // Aliases
    public static final EnumDye INK_SAC = BLACK;
    public static final EnumDye CACTUS_GREEN = GREEN;
    public static final EnumDye COCOA_BEANS = BROWN;
    public static final EnumDye LAPIS_LAZULI = BLUE;
    public static final EnumDye BONE_MEAL = WHITE;
    public static final EnumDye[] VALUES = values();

    public final int meta;
    public final String name;

    private EnumDye() {
        this.name = name().toLowerCase(Locale.ENGLISH);
        this.meta = ordinal();
    }

    public ItemStack asStack(int size) {
        return new ItemStack(Items.DYE, size, meta);
    }

    public ItemStack asStack() {
        return asStack(1);
    }

    public String getOreName() {
        return String.format("dye%s", CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, name()));
    }

    public static EnumDye getByMeta(int meta) {
        if (meta < 0 || meta >= VALUES.length) {
            return BLACK;
        }
        return VALUES[meta];
    }
}