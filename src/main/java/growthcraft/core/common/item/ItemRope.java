package growthcraft.core.common.item;

import growthcraft.core.common.block.BlockRopeKnot;
import growthcraft.core.shared.GrowthcraftLogger;
import growthcraft.core.shared.Reference;
import growthcraft.core.shared.init.GrowthcraftCoreBlocks;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;

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

        GrowthcraftLogger.debugDumpLog(Reference.MODID, worldIn, pos);
        Block block = worldIn.getBlockState(pos).getBlock();

        // Iterate over each BlockRopeKnot in the ropeKnotFence dictionary to determine if we
        // can attach a rope to it.
        for (ItemStack stack : OreDictionary.getOres("ropeKnotFence")) {
            BlockRopeKnot ropeKnotFence = (BlockRopeKnot) Block.getBlockFromItem(stack.getItem());

            if (ropeKnotFence.getFenceBlock() == block) {
                // Replace the activated block with the given BlockRopeFence from the oreDictionary
                worldIn.setBlockState(pos, ropeKnotFence.getDefaultState());
                player.getHeldItem(hand).shrink(1);
                return EnumActionResult.SUCCESS;
            }
        }

        // Since we are not using this item on a BlockFence, check to see if we can place a
        // block on the side we are selecting.
        if (worldIn.getBlockState(pos.offset(facing)).getBlock().canPlaceBlockAt(worldIn, pos.offset(facing))) {
            worldIn.setBlockState(pos.offset(facing), GrowthcraftCoreBlocks.rope_fence.getDefaultState());
            player.getHeldItem(hand).shrink(1);
            return EnumActionResult.SUCCESS;
        }
        return EnumActionResult.PASS;
    }


}
