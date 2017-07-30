package growthcraft.core.items;

import growthcraft.core.Reference;
import growthcraft.core.init.GrowthcraftCoreBlocks;
import growthcraft.core.tileentity.TileEntityRopeKnot;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFence;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemRope extends Item {

    public ItemRope(String unlocalizedName) {
        this.setUnlocalizedName(unlocalizedName);
        this.setRegistryName(new ResourceLocation(Reference.MODID, unlocalizedName));
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        Block block = worldIn.getBlockState(pos).getBlock();

        if(block instanceof BlockFence) {
            /**
             * TODO: Get the texture of the given fence and apply it the the fencePost layer in our RopeKnot block model.
             * IBlockState state = worldIn.getBlockState(pos);
             * int meta = block.getMetaFromState(state);
             * String model = Minecraft.getMinecraft().getBlockRendererDispatcher().getBlockModelShapes().getTexture(state).getIconName();
            */
            worldIn.setBlockState(pos, GrowthcraftCoreBlocks.rope_knot.getDefaultState());
            TileEntityRopeKnot tileEntity = (TileEntityRopeKnot)worldIn.getTileEntity(pos);
            tileEntity.addStackToInventory(new ItemStack(block));

            return EnumActionResult.SUCCESS;
        }
        return EnumActionResult.PASS;
    }


}
