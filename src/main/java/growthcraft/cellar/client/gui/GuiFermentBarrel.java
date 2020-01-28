package growthcraft.cellar.client.gui;

import growthcraft.cellar.GrowthcraftCellar;
import growthcraft.cellar.common.inventory.ContainerFermentBarrel;
import growthcraft.cellar.common.lib.network.PacketClearTankButton;
import growthcraft.cellar.common.tileentity.TileEntityFermentBarrel;
import growthcraft.cellar.shared.Reference;
import growthcraft.cellar.shared.booze.BoozeTag;
import growthcraft.cellar.shared.config.GrowthcraftCellarConfig;
import growthcraft.core.shared.CoreRegistry;
import growthcraft.core.shared.client.gui.widget.GuiButtonDiscard;
import growthcraft.core.shared.fluids.FluidTag;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

import java.util.Collection;
import java.util.List;

@SideOnly(Side.CLIENT)
public class GuiFermentBarrel extends GuiCellar<ContainerFermentBarrel, TileEntityFermentBarrel> {
    // INITIALIZE

    public static final ResourceLocation FERMENT_BARREL_TEXTURE = new ResourceLocation(Reference.MODID, "textures/guis/fermentbarrel_gui.png");

    private GuiButtonDiscard button;

    public GuiFermentBarrel(InventoryPlayer inv, TileEntityFermentBarrel fermentBarrel) {
        super(FERMENT_BARREL_TEXTURE, new ContainerFermentBarrel(inv, fermentBarrel), fermentBarrel);
    }

    @Override
    @SuppressWarnings({"rawtypes", "unchecked"})
    public void initGui() {
        super.initGui();
        if (GrowthcraftCellarConfig.enableDiscardButton) {
            this.button = new GuiButtonDiscard(guiResource, 1, this.guiLeft + 116, this.guiTop + 54);
            this.buttonList.add(this.button);
            this.button.enabled = false;
        }

        addTooltipIndex("fluidtank.primary", 63, 17, 50, 52);
        addTooltipIndex("progress_indicator", 42, 22, 3, 26);
        if (button != null) addTooltipIndex("discard.fluidtank.primary", 116, 54, 16, 16);
    }

    @Override
    public void updateScreen() {
        super.updateScreen();
        if (button != null) {
            this.button.enabled = tileEntity.isFluidTankFilled(0);
        }
    }

    @Override
    protected void actionPerformed(GuiButton butn) {
        GrowthcraftCellar.packetPipeline.sendToServer(new PacketClearTankButton(tileEntity.getPos()));
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
        super.drawGuiContainerBackgroundLayer(par1, par2, par3);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        bindGuiTexture();
        final int x = getGuiX();
        final int y = getGuiY();

        if (tileEntity.getTime() > 0) {
            int i = tileEntity.getDeviceProgressScaled(29);
            if (i > 0) {
                drawTexturedModalRect(x + 39, y + 21 + 29 - i, 188, 29 - i, 9, i);
            }

            final int k1 = (tileEntity.getTime() / 2) % 60;
            i = k1 * 29 / 60;
            if (i > 0) {
                drawTexturedModalRect(x + 49, y + 20 + 29 - i, 176, 29 - i, 12, i);
            }
        }

        final int i = tileEntity.getFluidAmountScaled(52, 0);
        if (i > 0) {
            final FluidStack fluid = tileEntity.getFluidStack(0);
            drawTank(63, 17, x, y, 50, 52, i, tileEntity.getFluidTank(0));
            bindGuiTexture();

            itemRender.zLevel = 100.0F;

            // render active modifiers
            final Collection<FluidTag> tags = CoreRegistry.instance().fluidDictionary().getFluidTags(fluid);
            if (tags != null) {
                if (tags.contains(BoozeTag.FERMENTED)) {
                    itemRender.renderItemAndEffectIntoGUI(new ItemStack(Items.NETHER_WART), x + 114, y + 16);
                }
                if (tags.contains(BoozeTag.EXTENDED)) {
                    itemRender.renderItemAndEffectIntoGUI(new ItemStack(Items.REDSTONE), x + 114, y + 32);
                }
                if (tags.contains(BoozeTag.HYPER_EXTENDED)) {
                    itemRender.renderItemAndEffectIntoGUI(new ItemStack(Items.REDSTONE), x + 114, y + 32);
                }
                if (tags.contains(BoozeTag.POTENT)) {
                    itemRender.renderItemAndEffectIntoGUI(new ItemStack(Items.GLOWSTONE_DUST), x + 130, y + 32);
                }
            }
            itemRender.zLevel = 0.0F;
        }
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int par1, int par2) {
        super.drawGuiContainerForegroundLayer(par1, par2);

        if (!tileEntity.isFluidTankEmpty(0)) {
            final String s = String.valueOf(tileEntity.getFluidAmount(0));
            this.fontRenderer.drawStringWithShadow(s, this.xSize - 62 - this.fontRenderer.getStringWidth(s), this.ySize - 104, 0xFFFFFF);
        }
    }

    @Override
    public void addTooltips(String handle, List<String> tooltip) {
        switch (handle) {
            case "progress_indicator":
                tooltip.add(
                        I18n.format(
                                I18n.format(TextFormatting.WHITE + String.valueOf(tileEntity.getDeviceProgressScaled(100)))
                        ) + "%"
                );
                break;
            case "fluidtank.primary":
                if (tileEntity.isFluidTankFilled(0)) {
                    addFermentTooltips(tileEntity.getFluidStack(0), tooltip);
                }
                break;
            case "discard.fluidtank.primary":
                tooltip.add(I18n.format("gui.grc.discard"));
                break;
            default:
                break;
        }
    }
}
