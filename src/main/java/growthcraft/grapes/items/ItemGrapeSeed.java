package growthcraft.grapes.items;

import growthcraft.grapes.Reference;
import growthcraft.grapes.handlers.EnumHandler;
import growthcraft.grapes.init.GrowthcraftGrapesBlocks;
import net.minecraft.client.resources.I18n;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSeeds;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.common.IPlantable;

import java.util.List;

public class ItemGrapeSeed extends ItemSeeds implements IPlantable {

    public ItemGrapeSeed(String unlocalizedName) {
        super(GrowthcraftGrapesBlocks.grape_vine, Blocks.FARMLAND);
        this.setUnlocalizedName(unlocalizedName);
        this.setRegistryName(new ResourceLocation(Reference.MODID, unlocalizedName));
        this.setHasSubtypes(true);
    }

    @Override
    public void getSubItems(Item itemIn, CreativeTabs tab, NonNullList<ItemStack> subItems) {
        for ( int i = 0; i < EnumHandler.GrapeTypes.values().length; i++ ) {
            subItems.add(new ItemStack(itemIn, 1, i));
        }
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        for (int i = 0; i < EnumHandler.GrapeTypes.values().length; i++ ) {
            if ( stack.getItemDamage() == i ) {
                return  this.getUnlocalizedName() + "." + EnumHandler.GrapeTypes.values()[i].getName();
            } else {
                continue;
            }
        }
        return super.getUnlocalizedName() + "." + EnumHandler.GrapeTypes.PURPLE.getName();
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
        super.addInformation(stack, playerIn, tooltip, advanced);
        tooltip.add(TextFormatting.GRAY + I18n.format("item.grape_seed.tooltip"));
    }



}
