package growthcraft.core.shared.crafting.recipe;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.NonNullList;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.crafting.JsonContext;

public class UtilRecipeParser {

    public static NonNullList<Ingredient> parseShapeless(final JsonContext context, final JsonObject json) {
        final NonNullList<Ingredient> ingredients = NonNullList.create();
        for (final JsonElement element : JsonUtils.getJsonArray(json, "ingredients"))
            ingredients.add(CraftingHelper.getIngredient(element, context));

        if (ingredients.isEmpty())
            throw new JsonParseException("No ingredients for shapeless recipe");

        return ingredients;
    }

}
