package growthcraft.cellar.client.gui;

import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

import growthcraft.cellar.GrowthcraftCellar;
import growthcraft.cellar.GrowthcraftCellarConfig;
import growthcraft.cellar.Reference;
import growthcraft.cellar.common.inventory.ContainerFruitPress;
import growthcraft.cellar.common.tileentity.TileEntityFruitPress;
import growthcraft.cellar.network.PacketClearTankButton;
import growthcraft.core.shared.client.gui.widget.GuiButtonDiscard;
import growthcraft.core.shared.client.gui.widget.WidgetDeviceProgressIcon;
import growthcraft.core.shared.client.gui.widget.WidgetFluidTank;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;

@SideOnly(Side.CLIENT)
public class GuiFruitPress extends GuiCellar<ContainerFruitPress, TileEntityFruitPress>
{
	// INITIALIZE
	
	public static final ResourceLocation FRUIT_PRESS_TEXTURE = new ResourceLocation(Reference.MODID, "textures/guis/fruitpress_gui.png");
	
	private GuiButtonDiscard discardButton;

	public GuiFruitPress(InventoryPlayer inv, TileEntityFruitPress fruitPress)
	{
		super(FRUIT_PRESS_TEXTURE, new ContainerFruitPress(inv, fruitPress), fruitPress);
	}

	@Override
	@SuppressWarnings({"rawtypes", "unchecked"})
	public void initGui()
	{
		super.initGui();
		widgets.add(new WidgetFluidTank(widgets, 0, 89, 17, 16, 52));
		widgets.add(new WidgetDeviceProgressIcon(widgets, 63, 34, 25, 16)
			.setProgressDirection(WidgetDeviceProgressIcon.ProgressDirection.LEFT_TO_RIGHT)
			.setTextureRect(176, 0, 25, 16));
		if (GrowthcraftCellarConfig.enableDiscardButton)
		{
			this.discardButton = new GuiButtonDiscard(guiResource, 1, guiLeft + 108, guiTop + 54);
			buttonList.add(discardButton);
			discardButton.enabled = false;
		}

		addTooltipIndex("fluidtank.primary", 89, 17, 16, 52);
		if (discardButton != null) addTooltipIndex("discard.fluidtank.primary", 108, 54, 16, 16);
	}

	@Override
	public void updateScreen()
	{
		super.updateScreen();
		if (discardButton != null) discardButton.enabled = tileEntity.isFluidTankFilled(0);
	}

	protected void actionPerformed(GuiButton butn)
	{
		GrowthcraftCellar.packetPipeline.sendToServer(new PacketClearTankButton(tileEntity.getPos()));
	}

	@Override
	public void addTooltips(String handle, List<String> tooltip)
	{
		switch (handle)
		{
			case "fluidtank.primary":
				addFluidTooltips(tileEntity.getFluidStack(0), tooltip);

				break;
			case "discard.fluidtank.primary":
				tooltip.add(I18n.format("gui.grc.discard"));

				break;
			default:
				break;
		}
	}
}