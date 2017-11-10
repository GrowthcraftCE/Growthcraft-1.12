package growthcraft.apples.items;

import growthcraft.apples.Reference;
import net.minecraft.block.Block;
import net.minecraft.item.ItemDoor;
import net.minecraft.util.ResourceLocation;

public class ItemAppleDoor extends ItemDoor {
    public ItemAppleDoor(String unlocalizedName, Block block) {
        super(block);
        this.setUnlocalizedName(unlocalizedName);
        this.setRegistryName(new ResourceLocation(Reference.MODID, unlocalizedName));
    }
}
