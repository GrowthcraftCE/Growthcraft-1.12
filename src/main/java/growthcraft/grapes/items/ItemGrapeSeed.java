package growthcraft.grapes.items;

import growthcraft.grapes.Reference;
import growthcraft.grapes.init.GrowthcraftGrapesBlocks;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemSeeds;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.common.IPlantable;

import java.util.List;

public class ItemGrapeSeed extends ItemSeeds implements IPlantable {

    public ItemGrapeSeed(String unlocalizedName) {
        super(GrowthcraftGrapesBlocks.grape_vine, Blocks.FARMLAND);
        this.setUnlocalizedName(unlocalizedName);
        this.setRegistryName(new ResourceLocation(Reference.MODID, unlocalizedName));
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
        super.addInformation(stack, playerIn, tooltip, advanced);
        tooltip.add(TextFormatting.GRAY + I18n.format("item.grape_seed.tooltip"));
    }



}
