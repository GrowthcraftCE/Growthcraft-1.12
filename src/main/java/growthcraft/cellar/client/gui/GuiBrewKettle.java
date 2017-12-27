package growthcraft.cellar.client.gui;

import cjminecraft.bitofeverything.client.gui.ProgressBar;
import growthcraft.cellar.Reference;
import growthcraft.cellar.container.ContainerBrewKettle;
import growthcraft.cellar.tileentity.TileEntityBrewKettle;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;
import java.util.List;

public class GuiBrewKettle extends GuiContainer {

    public static final ResourceLocation BREW_KETTLE_TEXTURE = new ResourceLocation(Reference.MODID, "textures/guis/brew_kettle_gui.png");
    private ResourceLocation BREW_KETTLE_TANK_0_TEXTURE = new ResourceLocation("blocks/water_still");

    private TileEntityBrewKettle tileEntityBrewKettle;
    private IInventory inventory;

    private ProgressBar progressBarCooking;
    private ProgressBar progressBarHeatLevel;
    private ProgressBar progressBarTankInputLevel;

    private int heat;
    private int maxHeat;
    private int recipeProgress;
    private int fluidLevel;

    public GuiBrewKettle(IInventory inventory, TileEntityBrewKettle tileEntityBrewKettle) {
        super(new ContainerBrewKettle(inventory, tileEntityBrewKettle));

        this.xSize = 176;
        this.ySize = 166;

        this.heat = 0;
        this.maxHeat = 1;
        this.recipeProgress = 0;
        this.fluidLevel = 0;

        this.tileEntityBrewKettle = tileEntityBrewKettle;
        this.inventory = inventory;

        this.progressBarHeatLevel = new ProgressBar(BREW_KETTLE_TEXTURE, ProgressBar.ProgressBarDirection.DOWN_TO_UP, 14, 14, 67, 54, 176, 28);
        this.progressBarCooking = new ProgressBar(BREW_KETTLE_TEXTURE, ProgressBar.ProgressBarDirection.UP_TO_DOWN, 9, 28, 98, 30, 176, 0);
        this.progressBarTankInputLevel = new ProgressBar(BREW_KETTLE_TANK_0_TEXTURE, ProgressBar.ProgressBarDirection.DOWN_TO_UP, 16, 52, 46, 17, 0, 0);

    }

    public static boolean isInRect(int x, int y, int xSize, int ySize, int mouseX, int mouseY) {
        return ((mouseX >= x && mouseX <= x + xSize) && (mouseY >= y && mouseY <= y + ySize));
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
        List<String> hoveringText = new ArrayList<String>();

        String s = I18n.format("container.brew_kettle");

        FluidStack fluidStack = tileEntityBrewKettle.getTankFluidStack();

        this.mc.fontRenderer.drawString(s, this.xSize / 2 - this.mc.fontRenderer.getStringWidth(s) / 2, 6, 4210752);
        this.mc.fontRenderer.drawString(this.inventory.getDisplayName().getFormattedText(), 8, 73, 4210752);

        this.progressBarHeatLevel.setMin(tileEntityBrewKettle.isHeated() ? 1 : 0).setMax(maxHeat);
        this.progressBarHeatLevel.draw(this.mc);

        //this.progressBarCooking.setMin(recipeProgress).setMax(tileEntityBrewKettle.maxBrewTime);
        this.progressBarCooking.setMin(tileEntityBrewKettle.getBrewTime()).setMax(tileEntityBrewKettle.getMaxBrewTime());
        this.progressBarCooking.draw(this.mc);

        //fluidStack.getFluid().getBlock().getRegistryName()

        //Minecraft.getMinecraft().getBlockRendererDispatcher().getBlockModelShapes().getTexture()

        if (fluidStack != null) {
            this.progressBarTankInputLevel.setMin(fluidStack.amount).setMax(5000);
            this.progressBarTankInputLevel.draw(this.mc);
        }

        // If the mouse is over the progress bar ...
        if (isInRect(guiLeft + 98, guiTop + 30, 9, 28, mouseX, mouseY)) {
            hoveringText.add("Progress: ");
            if (tileEntityBrewKettle.canBrew()) {
                hoveringText.add(tileEntityBrewKettle.getBrewProgress() + "%");
            } else {
                hoveringText.add("Unable to brew");
            }
        }

        if (isInRect(guiLeft + 46, guiTop + 17, 16, 51, mouseX, mouseY)) {
            if (fluidStack != null) {
                hoveringText.add(fluidStack.getLocalizedName() + " (" + fluidStack.amount + "mb)");
            }
        }

        // TODO: MouseOver outputFluidSlot
        if (isInRect(guiLeft + 114, guiTop + 17, 16, 51, mouseX, mouseY)) {
            hoveringText.add("Need to implement output fluid.");
        }

        if (!hoveringText.isEmpty()) {
            drawHoveringText(hoveringText, mouseX - guiLeft, mouseY - guiTop, fontRenderer);
        }

    }

    // TODO: Fluid Gui
    // https://github.com/thebrightspark/S.T.E.M/blob/79b595cbc79be7e388d7bb6c23e8b4c91a7799e4/src/main/java/brightspark/stem/gui/GuiLiquidEnergiser.java#L30-L34
    // https://github.com/Ellpeck/ActuallyAdditions/blob/master/src/main/java/de/ellpeck/actuallyadditions/mod/inventory/gui/FluidDisplay.java


}
