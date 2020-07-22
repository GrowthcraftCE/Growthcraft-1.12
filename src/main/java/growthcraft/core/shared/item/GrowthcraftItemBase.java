package growthcraft.core.shared.item;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

public class GrowthcraftItemBase extends Item {
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
//	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        GrowthcraftItemBase.addDescription(this, stack, worldIn, tooltip, flagIn);
    }

    @SideOnly(Side.CLIENT)
    @SuppressWarnings({"unchecked", "rawtypes", "deprecation"})
    public static void addDescription(Item item, ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        final String src = item.getUnlocalizedNameInefficiently(stack) + ".desc";
        final String tr = ("" + I18n.translateToLocal(src)).trim();
        if (!src.equals(tr)) tooltip.add(tr);
    }
}
