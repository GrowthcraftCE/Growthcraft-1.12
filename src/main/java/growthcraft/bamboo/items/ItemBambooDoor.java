package growthcraft.bamboo.items;

import growthcraft.bamboo.Reference;
import net.minecraft.block.Block;
import net.minecraft.item.ItemDoor;
import net.minecraft.util.ResourceLocation;

public class ItemBambooDoor extends ItemDoor {
    public ItemBambooDoor(String unlocalizedName, Block block) {
        super(block);
        this.setUnlocalizedName(unlocalizedName);
        this.setRegistryName(new ResourceLocation(Reference.MODID, unlocalizedName));
    }
}
