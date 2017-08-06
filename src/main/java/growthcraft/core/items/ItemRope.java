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

            worldIn.setBlockState(pos, GrowthcraftCoreBlocks.rope_knot.getDefaultState());
            TileEntityRopeKnot tileEntity = (TileEntityRopeKnot)worldIn.getTileEntity(pos);
            tileEntity.addStackToInventory(new ItemStack(block));

            return EnumActionResult.SUCCESS;
        } else {
            // Place a BlockRopeFence into the world.

        }
        return EnumActionResult.PASS;
    }


}
