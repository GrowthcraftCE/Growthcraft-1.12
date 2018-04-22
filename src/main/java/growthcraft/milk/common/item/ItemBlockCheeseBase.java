package growthcraft.milk.common.item;

import growthcraft.core.shared.definition.IObjectVariant;
import growthcraft.milk.shared.definition.ICheeseType;
import growthcraft.milk.shared.init.GrowthcraftMilkItems.WaxedCheeseTypes;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;

public class ItemBlockCheeseBase<T extends ICheeseType & IObjectVariant> extends ItemBlock {
	private final T[] typeLookup;
	
	public ItemBlockCheeseBase(Block block, T[] typeLookup) {
		super(block);
		this.typeLookup = typeLookup;
		setHasSubtypes(true);
	}

	@SuppressWarnings("unchecked")
	public T getTypeForVariantID(int variantID) {
		// Maybe the types are ordered in table by ID s beginning with 0 and incrementing by 1
		if( variantID >= 0 && variantID < typeLookup.length && typeLookup[variantID].getVariantID() ==  variantID )
			return typeLookup[variantID];
		
		// Need to search ...
		for( T type : typeLookup ) {
			if( type.getVariantID() == variantID )
				return type;
		}
		
		// Otherwise return a fallback case cheese
		return (T)WaxedCheeseTypes.CHEDDAR;
	}
	
	public T[] getAllVariants() {
		return typeLookup;
	}
}
