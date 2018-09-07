package growthcraft.rice.common.item;

import growthcraft.core.shared.item.GrowthcraftItemBase;
import growthcraft.rice.shared.Reference;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemRake extends GrowthcraftItemBase {

    public ItemRake(String unlocalizedName) {
        super();
        this.setUnlocalizedName(unlocalizedName);
        this.setRegistryName(Reference.MODID, unlocalizedName);
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        // TODO: ItemRake onItemUse on Blocks.DIRT make into Blocks.FARMLAND.
        // TODO: ItemRake onItemUse on Blocks.FARMLAND make into RicePaddy.

        return super.onItemUse(player, worldIn, pos, hand, facing, hitX, hitY, hitZ);
    }
}
