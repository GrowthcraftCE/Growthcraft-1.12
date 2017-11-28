package growthcraft.cellar.client.gui;

import growthcraft.cellar.Reference;
import growthcraft.cellar.container.ContainerBrewKettle;
import growthcraft.cellar.tileentity.TileEntityBrewKettle;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;

public class GuiBrewKettle extends GuiContainer {

    public static final ResourceLocation BREW_KETTLE_TEXTURE = new ResourceLocation(Reference.MODID, "textures/guis/brew_kettle_gui.png");
    private TileEntityBrewKettle tileEntityBrewKettle;
    private IInventory inventory;

    public GuiBrewKettle(IInventory inventory, TileEntityBrewKettle tileEntityBrewKettle) {
        super(new ContainerBrewKettle(inventory, tileEntityBrewKettle));

        this.xSize = 176;
        this.ySize = 166;

        this.tileEntityBrewKettle = tileEntityBrewKettle;
        this.inventory = inventory;
    }

    @Override
    public void initGui() {
        super.initGui();
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GlStateManager.color(1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(BREW_KETTLE_TEXTURE);
        this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        String s = I18n.format("container.brew_kettle");

        this.mc.fontRenderer.drawString(s, this.xSize / 2 - this.mc.fontRenderer.getStringWidth(s) / 2, 6, 4210752);
        this.mc.fontRenderer.drawString(this.inventory.getDisplayName().getFormattedText(), 8, 73, 4210752);
    }
}
