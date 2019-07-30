package growthcraft.bees.common.crafting.recipe;

import com.google.gson.JsonObject;
import growthcraft.core.shared.GrowthcraftLogger;
import growthcraft.core.shared.Reference;
import growthcraft.core.shared.crafting.recipe.UtilRecipeParser;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.crafting.IRecipeFactory;
import net.minecraftforge.common.crafting.JsonContext;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import javax.annotation.Nullable;

public class ShapelessRecipeWithBucket extends ShapelessOreRecipe {

    public ShapelessRecipeWithBucket(@Nullable final ResourceLocation group, final NonNullList<Ingredient> input, final ItemStack result) {
        super(group, input, result);
    }

    @Override
    public String getGroup() {
        return group == null ? "" : group.toString();
    }

    public static class Factory implements IRecipeFactory {
        @Override
        public IRecipe parse(final JsonContext context, final JsonObject json) {
            final String group = JsonUtils.getString(json, "group", "");
            final NonNullList<Ingredient> ingredients = UtilRecipeParser.parseShapeless(context, json);
            final ItemStack result = CraftingHelper.getItemStack(JsonUtils.getJsonObject(json, "result"), context);
            GrowthcraftLogger.getLogger(Reference.MODID).debug("Loading recipe for " + result.getItem().getRegistryName());
            return new ShapelessRecipeWithBucket(group.isEmpty() ? null : new ResourceLocation(group), ingredients, result);
        }
    }

}
