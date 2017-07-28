package growthcraft.core.items;

import growthcraft.core.Reference;
import growthcraft.core.utils.GrowthcraftLogger;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFence;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemRope extends Item {

    public ItemRope(String unlocalizedName) {
        this.setUnlocalizedName(unlocalizedName);
        this.setRegistryName(new ResourceLocation(Reference.MODID, unlocalizedName));
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        Block block = worldIn.getBlockState(pos).getBlock();

        if(block instanceof BlockFence) {
            IBlockState state = worldIn.getBlockState(pos);
            int meta = block.getMetaFromState(state);

            String model = Minecraft.getMinecraft().getBlockRendererDispatcher().getBlockModelShapes().getTexture(state).getIconName();
            if(worldIn.isRemote) {
                GrowthcraftLogger.getLogger().info("We have a fence! " + model);
                // TODO: Create a new BlockRopeKnot

                // TODO: Added the selected FENCE to the inventory of the BlockRopeKnot

                // TODO: Switch block with out new BlockRopeKnot
            }
        }
        return super.onItemUse(player, worldIn, pos, hand, facing, hitX, hitY, hitZ);
    }
}
