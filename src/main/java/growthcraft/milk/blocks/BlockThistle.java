package growthcraft.milk.blocks;

import growthcraft.milk.Reference;
import growthcraft.milk.init.GrowthcraftMilkItems;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.IGrowable;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.IPlantable;

public class BlockThistle extends BlockCrops implements IGrowable, IPlantable {

    public BlockThistle(String unlocalizedName) {
        this.setUnlocalizedName(unlocalizedName);
        this.setRegistryName(new ResourceLocation(Reference.MODID, unlocalizedName));
    }

    protected Item getSeed() {
        return GrowthcraftMilkItems.thistle;
    }

    protected Item getCrop() {
        return GrowthcraftMilkItems.thistle;
    }
}
