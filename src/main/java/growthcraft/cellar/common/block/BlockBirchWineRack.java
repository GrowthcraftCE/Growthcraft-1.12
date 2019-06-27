package growthcraft.cellar.common.block;

import growthcraft.cellar.shared.Reference;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.util.ResourceLocation;

public class BlockBirchWineRack extends BlockWineRack {
	
	public BlockBirchWineRack(String unlocalizedName) {
		super(Material.WOOD);
		this.setUnlocalizedName(unlocalizedName);
		this.setRegistryName(new ResourceLocation(Reference.MODID, unlocalizedName));
	}
	
}
