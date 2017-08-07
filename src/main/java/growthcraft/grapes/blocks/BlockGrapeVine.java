package growthcraft.grapes.blocks;

import growthcraft.core.Reference;
import growthcraft.grapes.init.GrowthcraftGrapesItems;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.IGrowable;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.IPlantable;

public class BlockGrapeVine extends BlockCrops implements IGrowable, IPlantable {

    private int maxAge;

    public BlockGrapeVine(String unlocalizedName) {
        this.setUnlocalizedName(unlocalizedName);
        this.setRegistryName(new ResourceLocation(Reference.MODID, unlocalizedName));
        this.setMaxAge(3);
    }

    @Override
    protected Item getSeed() {
        return GrowthcraftGrapesItems.grape_seed;
    }

    @Override
    protected Item getCrop() {
        return GrowthcraftGrapesItems.grape_seed;
    }

    @Override
    public int getMaxAge() {
        return super.getMaxAge();
    }

    public void setMaxAge(int maxAge) {
        this.maxAge = maxAge;
    }
}
