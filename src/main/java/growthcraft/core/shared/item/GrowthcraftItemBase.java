package growthcraft.core.shared.item;

import java.util.List;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class GrowthcraftItemBase extends Item
{
	public GrowthcraftItemBase() {
		super();
	}
	
    public GrowthcraftItemBase(String modId, String unlocalizedName) {
        this.setUnlocalizedName(unlocalizedName);
        this.setRegistryName(modId, unlocalizedName);
    }
	
	@Override
	@SideOnly(Side.CLIENT)
	@SuppressWarnings({"unchecked", "rawtypes"})
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool)
	{
		super.addInformation(stack, player, list, bool);
		GrowthcraftItemBase.addDescription(this, stack, player, list, bool);
	}

	@SideOnly(Side.CLIENT)
	@SuppressWarnings({"unchecked", "rawtypes"})
	public static void addDescription(Item item, ItemStack stack, EntityPlayer player, List list, boolean bool)
	{
		final String src = item.getUnlocalizedNameInefficiently(stack) + ".desc";
		final String tr = ("" + I18n.format(src)).trim();
		if (!src.equals(tr)) list.add(tr);
	}
}
