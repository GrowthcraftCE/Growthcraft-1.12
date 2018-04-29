package growthcraft.fishtrap.client.gui;

import growthcraft.fishtrap.common.container.ContainerFishtrap;
import growthcraft.fishtrap.common.tileentity.TileEntityFishtrap;
import growthcraft.fishtrap.shared.Reference;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;

public class GuiFishtrap extends GuiContainer {

    private static final ResourceLocation FISHTRAP_TEXTURE = new ResourceLocation(Reference.MODID, "textures/guis/container/fishtrap_gui.png");
    private TileEntityFishtrap tileEntityFishtrap;
    private IInventory inventory;

    public GuiFishtrap(IInventory inventory, TileEntityFishtrap tileEntityFishtrap) {
        super(new ContainerFishtrap(inventory, tileEntityFishtrap));

        this.xSize = 175;
        this.ySize = 132;

        this.tileEntityFishtrap = tileEntityFishtrap;
        this.inventory = inventory;
    }

    @Override
    public void initGui() {
        super.initGui();

    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GlStateManager.color(1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(FISHTRAP_TEXTURE);
        this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        String s = I18n.format("container.fishtrap");

        this.mc.fontRenderer.drawString(s, this.xSize / 2 - this.mc.fontRenderer.getStringWidth(s) / 2, 6, 4210752);
        this.mc.fontRenderer.drawString(this.inventory.getDisplayName().getFormattedText(), 8, 40, 4210752);
    }
}
