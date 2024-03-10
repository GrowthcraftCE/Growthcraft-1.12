package growthcraft.milk.common.item;

import growthcraft.core.shared.definition.IObjectVariant;
import growthcraft.core.shared.io.nbt.NBTHelper;
import growthcraft.core.shared.item.IItemTileBlock;
import growthcraft.milk.shared.cheese.CheeseIO;
import growthcraft.milk.shared.cheese.CheeseUtils;
import growthcraft.milk.shared.definition.ICheeseCurdStackFactory;
import growthcraft.milk.shared.definition.ICheeseType;
import net.minecraft.block.Block;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

public class ItemBlockHangingCurds<T extends ICheeseType & IObjectVariant> extends ItemBlockCheeseBase<T> implements IItemTileBlock {
    public ItemBlockHangingCurds(Block block, T[] typeLookup) {
        super(block, typeLookup);
    }

    private NBTTagCompound getTileTagCompoundABS(ItemStack stack) {
        final NBTTagCompound tag = NBTHelper.openItemStackTag(stack);
        if (!tag.hasKey("te_curd_block")) {
            final NBTTagCompound curdTag = new NBTTagCompound();
            final ICheeseType cheeseType = getTypeForVariantID(CheeseUtils.getVariantIDFromMeta(stack.getItemDamage())); // EnumCheeseType.getSafeById(EnumCheeseType.getTypeFromMeta(stack.getItemDamage()));
            CheeseIO.writeToNBT(curdTag, cheeseType);
            tag.setTag("te_curd_block", curdTag);
        }
        return tag.getCompoundTag("te_curd_block");
    }

    public ICheeseType getCheeseType(ItemStack stack) {
        final NBTTagCompound tag = getTileTagCompoundABS(stack);
        return CheeseIO.loadFromNBT(tag);
    }

    @Override
    public void setTileTagCompound(ItemStack stack, NBTTagCompound tileTag) {
        final NBTTagCompound tag = NBTHelper.openItemStackTag(stack);
        tag.setTag("te_curd_block", tileTag);
    }

    @Override
    public NBTTagCompound getTileTagCompound(ItemStack stack) {
        final NBTTagCompound tag = getTileTagCompoundABS(stack);
/*		final ICheeseType type = getCheeseType(stack);
		if (stack.getItemDamage() != type.getMetaForInitialStage())
		{
			stack.setItemDamage(type.getMetaForInitialStage());
		} */
        return tag;
    }

    public boolean isDried(ItemStack stack) {
        final NBTTagCompound nbt = getTileTagCompound(stack);
        if (nbt.hasKey("dried")) {
            return nbt.getBoolean("dried");
        }
        return false;
    }

    @Override
    public String getTranslationKey(ItemStack stack) {
        String str = super.getTranslationKey(stack);
        str += "." + getCheeseType(stack).getRegistryName().getPath();
        if (isDried(stack)) str += ".dried";
        return str;
    }

    @Override
    @SideOnly(Side.CLIENT)
    @SuppressWarnings({"unchecked", "rawtypes"})
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        final NBTTagCompound nbt = getTileTagCompound(stack);
        if (nbt.hasKey("dried") && nbt.getBoolean("dried")) {
            tooltip.add(I18n.format("grcmilk.hanging_curds.dried"));
        } else {
            final int age = nbt.getInteger("age");
            if (age > 0) {
                final int ageMax = nbt.getInteger("age_max");
                final int t = age * 100 / (ageMax > 0 ? ageMax : 1200);
                tooltip.add(I18n.format("grcmilk.hanging_curds.drying.prefix") +
                        I18n.format("grcmilk.hanging_curds.drying.progress.format", t));
            }
        }
        super.addInformation(stack, worldIn, tooltip, flagIn);
    }

    public static NBTTagCompound openNBT(ItemStack stack) {
        final Item item = stack.getItem();
        if (item instanceof ItemBlockHangingCurds) {
            return ((ItemBlockHangingCurds) item).getTileTagCompound(stack);
        } else {
            // throw error
        }
        return null;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> subItems) {
        if (!this.isInCreativeTab(tab))
            return;

        for (T type : getAllVariants()) {
            ICheeseCurdStackFactory curdStackFactory = type.getCurdBlocks();
            ItemStack stack = curdStackFactory.asStack();
            subItems.add(stack);
        }
    }
}