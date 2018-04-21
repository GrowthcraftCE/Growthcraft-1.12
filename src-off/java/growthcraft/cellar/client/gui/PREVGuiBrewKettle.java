package growthcraft.cellar.client.gui;

import java.util.List;

import growthcraft.cellar.GrowthcraftCellar;
import growthcraft.cellar.GrowthcraftCellarConfig;
import growthcraft.cellar.Reference;
import growthcraft.cellar.common.inventory.ContainerBrewKettle;
import growthcraft.cellar.common.lib.network.PacketClearTankButtonWByte;
import growthcraft.cellar.common.lib.network.PacketSwitchTankButton;
import growthcraft.cellar.common.tileentity.TileEntityBrewKettle;
import growthcraft.core.shared.client.gui.widget.GuiButtonDiscard;
import growthcraft.core.shared.client.gui.widget.GuiButtonSwitch;
import growthcraft.core.shared.client.gui.widget.WidgetDeviceProgressIcon;
import growthcraft.core.shared.client.gui.widget.WidgetFluidTank;
import growthcraft.core.shared.client.gui.widget.WidgetHeatIcon;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class PREVGuiBrewKettle extends GuiCellar<ContainerBrewKettle, TileEntityBrewKettle>
{
	public static final ResourceLocation BREW_KETTLE_TEXTURE = new ResourceLocation(Reference.MODID, "textures/guis/brew_kettle_gui.png");
	
	private GuiButtonDiscard button0;
	private GuiButtonDiscard button1;
	private GuiButtonSwitch button2;

	public PREVGuiBrewKettle(InventoryPlayer inv, TileEntityBrewKettle brewKettle)
	{
		super(BREW_KETTLE_TEXTURE, new ContainerBrewKettle(inv, brewKettle), brewKettle);
	}

	@Override
	@SuppressWarnings({"rawtypes", "unchecked"})
	public void initGui()
	{
		super.initGui();
		widgets.add(new WidgetHeatIcon(widgets, 67, 53, 14, 14).setTextureRect(176, 28, 14, 14));
		widgets.add(new WidgetFluidTank(widgets, 0, 46, 17, 16, 52));
		widgets.add(new WidgetFluidTank(widgets, 1, 114, 17, 16, 52));
		widgets.add(new WidgetDeviceProgressIcon(widgets, 98, 30, 9, 28)
			.setProgressDirection(WidgetDeviceProgressIcon.ProgressDirection.TOP_TO_BOTTOM)
			.setTextureRect(176, 0, 9, 28));

		if (GrowthcraftCellarConfig.enableDiscardButton)
		{
			this.button0 = new GuiButtonDiscard(guiResource, 1, guiLeft + 27, guiTop + 54);
			button0.enabled = false;

			this.button1 = new GuiButtonDiscard(guiResource, 1, guiLeft + 133, guiTop + 54);
			button1.enabled = false;
		}

		this.button2 = new GuiButtonSwitch(guiResource, 1, this.guiLeft + 133, this.guiTop + 37);
		this.button2.enabled = false;

		if (button0 != null) this.buttonList.add(this.button0);
		if (button1 != null) this.buttonList.add(this.button1);
		buttonList.add(this.button2);

		addTooltipIndex("fluidtank.input", 46, 17, 16, 52);
		addTooltipIndex("fluidtank.output", 114, 17, 16, 52);
		if (button0 != null) addTooltipIndex("discard.fluidtank.input", 27, 54, 16, 16);
		if (button1 != null) addTooltipIndex("discard.fluidtank.output", 133, 54, 16, 16);
		addTooltipIndex("switch.fluidtanks", 133, 37, 16, 16);
	}

	@Override
	public void updateScreen()
	{
		super.updateScreen();
		if (button0 != null)
		{
			button0.enabled = tileEntity.isFluidTankFilled(0);
		}

		if (button1 != null)
		{
			button1.enabled = tileEntity.isFluidTankFilled(1);
		}

		//button2.enabled = tileEntity.isFluidTankFilled(1) && tileEntity.isFluidTankEmpty(0);
		button2.enabled = tileEntity.isFluidTankFilled(0) || tileEntity.isFluidTankFilled(1);
	}

	protected void actionPerformed(GuiButton button)
	{
		if (button0 != null && button == button0)
		{
			GrowthcraftCellar.packetPipeline.sendToServer(new PacketClearTankButtonWByte(tileEntity.getPos(), (byte)0));
		}
		else if (button1 != null && button == button1)
		{
			GrowthcraftCellar.packetPipeline.sendToServer(new PacketClearTankButtonWByte(tileEntity.getPos(), (byte)1));
		}
		else if (button == button2)
		{
			GrowthcraftCellar.packetPipeline.sendToServer(new PacketSwitchTankButton(tileEntity.getPos()));
		}
	}

	@Override
	public void addTooltips(String handle, List<String> tooltip)
	{
		switch (handle)
		{
			case "fluidtank.input":
				addFluidTooltips(tileEntity.getFluidStack(0), tooltip);
				break;
			case "fluidtank.output":
				addFluidTooltips(tileEntity.getFluidStack(1), tooltip);
				break;
			case "discard.fluidtank.input":
				tooltip.add(I18n.format("gui.grc.discard"));
				break;
			case "discard.fluidtank.output":
				tooltip.add(I18n.format("gui.grc.discard"));
				break;
			case "switch.fluidtanks":
				tooltip.add(I18n.format("gui.grc.switch"));
				break;
			default:
				break;
		}
	}
}
