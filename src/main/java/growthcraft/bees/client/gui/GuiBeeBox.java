package growthcraft.bees.client.gui;

import net.minecraftforge.fml.relauncher.SideOnly;
import growthcraft.bees.common.inventory.ContainerBeeBox;
import growthcraft.bees.common.tileentity.TileEntityBeeBox;
import growthcraft.bees.shared.Reference;
import growthcraft.core.shared.client.gui.GrowthcraftGuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;

@SideOnly(Side.CLIENT)
public class GuiBeeBox extends GrowthcraftGuiContainer<ContainerBeeBox, TileEntityBeeBox>
{
	private static final ResourceLocation res = new ResourceLocation(Reference.MODID, "textures/guis/beebox_gui.png");
	private TileEntityBeeBox te;

	public GuiBeeBox(InventoryPlayer inv, TileEntityBeeBox beeBox)
	{
		super(res, new ContainerBeeBox(inv, beeBox), beeBox);
		this.te = beeBox;
		this.xSize = 176;
		this.ySize = 200;
	}
}
