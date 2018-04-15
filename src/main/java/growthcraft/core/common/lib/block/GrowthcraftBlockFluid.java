package growthcraft.core.common.lib.block;

import javax.annotation.Nonnull;

import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.common.ForgeModContainer;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.UniversalBucket;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class GrowthcraftBlockFluid extends BlockFluidClassic {
	// OPEN

	private int color = 0xFFFFFF;

	public GrowthcraftBlockFluid(Fluid fluid, Material material) {
		super(fluid, material);
		this.setUnlocalizedName(fluid.getUnlocalizedName());
	}

	@Nonnull
	@Override
	public String getUnlocalizedName() {
		Fluid fluid = FluidRegistry.getFluid(fluidName);
		if (fluid != null) {
			return fluid.getUnlocalizedName();
		}
		return super.getUnlocalizedName();
	}
	
	public GrowthcraftBlockFluid refreshColor()
	{
		setColor(getFluid().getColor());
		refreshLight();
		
		return this;
	}

	public GrowthcraftBlockFluid refreshSettings() {
		setDensity(definedFluid.getDensity());
		return this;
	}

	public GrowthcraftBlockFluid refreshLight() {
		// http://stackoverflow.com/questions/596216/formula-to-determine-brightness-of-rgb-color
		final float lum = 0.2126f * (((color >> 16) & 0xFF) / 255.0f) + 0.7152f * (((color >> 8) & 0xFF) / 255.0f)
				+ 0.0722f * ((color & 0xFF) / 255.0f);
		setLightOpacity((int) ((1f - lum) * 15));
		return this;
	}

	public GrowthcraftBlockFluid setColor(int kolor) {
		this.color = kolor;
		return this;
	}

	public int getColor() {
		return this.color;
	}

/*	@SideOnly(Side.CLIENT)
	@Override
	public void getSubBlocks(Item itemIn, CreativeTabs tab, NonNullList<ItemStack> list) {
		final ItemStack bucket = UniversalBucket.getFilledBucket(ForgeModContainer.getInstance().universalBucket, getFluid());
		list.add(bucket);
	}*/
}
