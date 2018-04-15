package growthcraft.cellar.client.gui;

import java.util.List;

import growthcraft.cellar.common.inventory.CellarContainer;
import growthcraft.cellar.common.lib.CellarRegistry;
import growthcraft.cellar.common.tileentity.TileEntityCellarDevice;
import growthcraft.core.shared.client.gui.GrowthcraftGuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.fml.relauncher.Side;

@SideOnly(Side.CLIENT)
public class GuiCellar<C extends CellarContainer, T extends TileEntityCellarDevice> extends GrowthcraftGuiContainer<C, T>
{
	public GuiCellar(ResourceLocation res, C container, T cd)
	{
		super(res, container, cd);
	}

	protected void addFermentTooltips(FluidStack fluid, List<String> tooltip)
	{
		if (fluid == null) return;
		if (fluid.amount <= 0) return;

		addFluidTooltips(fluid, tooltip);
		if (!CellarRegistry.instance().fermenting().canFerment(fluid))
		{
			tooltip.add("");
			tooltip.add(TextFormatting.RED + I18n.format("gui.grc.cantferment"));
		}
	}
}
