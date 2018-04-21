package growthcraft.cellar.common.potion;

import growthcraft.cellar.shared.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PotionCellar extends Potion {
	private static final ResourceLocation POTIONS_LOCATION = new ResourceLocation(Reference.MODID, "textures/potions/potion_tipsy.png");
	
	public PotionCellar(boolean isBadEffectIn, int liquidColorIn, int x, int y) {
		super(isBadEffectIn, liquidColorIn);
		this.setIconIndex(x, y);
	}
	
    @Override
    @SideOnly(Side.CLIENT)
    public boolean hasStatusIcon()
    {
        Minecraft.getMinecraft().getTextureManager().bindTexture(POTIONS_LOCATION);
        return super.hasStatusIcon();
    }
}
