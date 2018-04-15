package growthcraft.cellar.client.gui;

import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

import org.lwjgl.opengl.GL11;

import growthcraft.cellar.GrowthcraftCellar;
import growthcraft.cellar.GrowthcraftCellarConfig;
import growthcraft.cellar.Reference;
import growthcraft.cellar.common.inventory.ContainerCultureJar;
import growthcraft.cellar.common.lib.network.PacketClearTankButton;
import growthcraft.cellar.common.tileentity.TileEntityCultureJar;
import growthcraft.core.shared.client.gui.widget.GuiButtonDiscard;
import growthcraft.core.shared.client.gui.widget.WidgetDeviceProgressIcon;
import growthcraft.core.shared.client.gui.widget.WidgetFluidTank;
import growthcraft.core.shared.client.gui.widget.WidgetHeatIcon;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;

@SideOnly(Side.CLIENT)
public class GuiCultureJar extends GuiCellar<ContainerCultureJar, TileEntityCultureJar>
{
	// INITIALIZE
	
	public static final ResourceLocation CULTURE_JAR_TEXTURE = new ResourceLocation(Reference.MODID, "textures/guis/gui_ferment_jar.png");
	
	private GuiButtonDiscard discardButton;

	public GuiCultureJar(InventoryPlayer inv, TileEntityCultureJar fermentJar)
	{
		super(CULTURE_JAR_TEXTURE, new ContainerCultureJar(inv, fermentJar), fermentJar);
	}

	@Override
	@SuppressWarnings({"rawtypes", "unchecked"})
	public void initGui()
	{
		super.initGui();
		widgets.add(new WidgetFluidTank(widgets, 0, 36, 14, 16, 58).setRuleOverlay(176, 53, 16, 58));
		widgets.add(new WidgetHeatIcon(widgets, 82, 56, 14, 14).setTextureRect(176, 17, 14, 14));
		widgets.add(new WidgetDeviceProgressIcon(widgets, 55, 35, 22, 17)
			.setProgressDirection(WidgetDeviceProgressIcon.ProgressDirection.LEFT_TO_RIGHT)
			.setTextureRect(176, 0, 22, 17));

		if (GrowthcraftCellarConfig.enableDiscardButton)
		{
			this.discardButton = new GuiButtonDiscard(guiResource, 1, guiLeft + 116, guiTop + 54);
			discardButton.enabled = false;
			buttonList.add(discardButton);
		}

		addTooltipIndex("fluid_tank.primary", 36, 17, 16, 58);
		if (discardButton != null) addTooltipIndex("discard.fluid_tank.primary", 16, 52, 16, 16);
	}

	@Override
	protected void actionPerformed(GuiButton butn)
	{
		GrowthcraftCellar.packetPipeline.sendToServer(new PacketClearTankButton(tileEntity.getPos()));
	}

	@Override
	public void updateScreen()
	{
		super.updateScreen();
		if (discardButton != null) discardButton.enabled = tileEntity.isFluidTankFilled(0);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float par1, int mx, int my)
	{
		super.drawGuiContainerBackgroundLayer(par1, mx, my);

		if (!tileEntity.isCulturing())
		{
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			bindGuiTexture();
			// Red Ring around Yeast Slot
			drawTexturedModalRect(getGuiX() + 77, getGuiY() + 32, 176, 31, 22, 22);
		}
	}

	@Override
	public void addTooltips(String handle, List<String> tooltip)
	{
		switch (handle)
		{
			case "fluid_tank.primary":
				addFluidTooltips(tileEntity.getFluidStack(0), tooltip);
				break;
			case "discard.fluid_tank.primary":
				tooltip.add(I18n.format("gui.grc.discard"));
				break;
			default:
				break;
		}
	}
}
