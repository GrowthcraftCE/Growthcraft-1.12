package growthcraft.cellar.items;

import java.util.List;

import growthcraft.cellar.Reference;
import growthcraft.cellar.handlers.EnumHandler.EnumYeast;
import growthcraft.core.common.item.GrowthcraftItemBase;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemYeast extends GrowthcraftItemBase {
	public ItemYeast(String unlocalizedName) {
		super();
		setHasSubtypes(true);
		setMaxDamage(0);
        this.setUnlocalizedName(unlocalizedName);
        this.setRegistryName(new ResourceLocation(Reference.MODID, unlocalizedName));
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack)
	{
		return super.getUnlocalizedName(stack) + "." + EnumYeast.getSafeByMeta(stack.getItemDamage()).toString().toLowerCase();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item itemIn, CreativeTabs tab, NonNullList<ItemStack> subItems)
	{
		for (EnumYeast ytype : EnumYeast.values())
		{
			subItems.add(ytype.asStack());
		}
	}
}
