package growthcraft.milk.common.item;

import growthcraft.core.shared.definition.IObjectVariant;
import growthcraft.core.shared.io.nbt.NBTHelper;
import growthcraft.core.shared.item.IItemTileBlock;
import growthcraft.core.shared.tileentity.feature.IItemOperable;
import growthcraft.milk.GrowthcraftMilk;
import growthcraft.milk.common.block.BlockCheeseBlock;
import growthcraft.milk.common.tileentity.TileEntityCheeseBlock;
import growthcraft.milk.shared.cheese.CheeseIO;
import growthcraft.milk.shared.cheese.CheeseUtils;
import growthcraft.milk.shared.definition.EnumCheeseStage;
import growthcraft.milk.shared.definition.ICheeseBlockStackFactory;
import growthcraft.milk.shared.definition.ICheeseType;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemBlockCheeseBlock<T extends ICheeseType & IObjectVariant> extends ItemBlockCheeseBase<T> implements IItemTileBlock {
    public ItemBlockCheeseBlock(Block block, T[] typeLookup) {
        super(block, typeLookup);
        this.maxStackSize = 1;
    }

    private NBTTagCompound getTileTagCompoundABS(ItemStack stack) {
        final NBTTagCompound tag = NBTHelper.openItemStackTag(stack);
        if (!tag.hasKey("te_cheese_block")) {
            final NBTTagCompound cheeseTag = new NBTTagCompound();
            final ICheeseType cheese = getTypeForVariantID(CheeseUtils.getVariantIDFromMeta(stack.getItemDamage())); // EnumCheeseType.getSafeById(EnumCheeseType.getTypeFromMeta(stack.getItemDamage()));
            final EnumCheeseStage stage = CheeseUtils.getStageFromMeta(stack.getItemDamage());
            final int slices = CheeseUtils.getTopSlicesFromMeta(stack.getItemDamage());
            CheeseIO.writeToNBT(cheeseTag, cheese);
            stage.writeToNBT(cheeseTag);
            cheeseTag.setInteger("slices", slices);
            tag.setTag("te_cheese_block", cheeseTag);
        }
        return tag.getCompoundTag("te_cheese_block");
    }

    @Override
    public void setTileTagCompound(ItemStack stack, NBTTagCompound tileTag) {
        final NBTTagCompound tag = NBTHelper.openItemStackTag(stack);
        tag.setTag("te_cheese_block", tileTag);
    }

    @Override
    public NBTTagCompound getTileTagCompound(ItemStack stack) {
        final NBTTagCompound tag = getTileTagCompoundABS(stack);
        return tag;
    }

    public ICheeseType getCheeseType(ItemStack stack) {
        final NBTTagCompound tag = getTileTagCompoundABS(stack);
        return CheeseIO.loadFromNBT(tag);
    }

    public EnumCheeseStage getCheeseStage(ItemStack stack) {
        final NBTTagCompound tag = getTileTagCompoundABS(stack);
        return EnumCheeseStage.loadFromNBT(tag);
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        // Handle shift click behavior
        IBlockState iblockstate = worldIn.getBlockState(pos);
        Block block = iblockstate.getBlock();
        if (block instanceof BlockCheeseBlock) {
            // FIXME: Copied logic from GrowthcraftBlockContainer.handleOnUseItem() . Avoid code duplication!
            BlockCheeseBlock cheeseBlock = (BlockCheeseBlock) block;
            TileEntityCheeseBlock te = cheeseBlock.getTileEntity(worldIn, pos);
            if (te != null) {
                ItemStack onHand = player.getHeldItem(hand);
                if (te.tryPlaceItem(IItemOperable.Action.RIGHT, player, onHand)) {
                    cheeseBlock.markBlockForUpdate(worldIn, pos);
                    return EnumActionResult.SUCCESS;
                }
            }
        }

        return super.onItemUse(player, worldIn, pos, hand, facing, hitX, hitY, hitZ);
    }


    @Override
    public String getUnlocalizedName(ItemStack stack) {
        return super.getUnlocalizedName(stack) +
                "." + getCheeseType(stack).getRegistryName().getResourcePath() +
                "." + getCheeseStage(stack).getName();
    }

    public int getTopSlices(ItemStack stack) {
        return getTileTagCompound(stack).getInteger("slices");
    }

    public int getTopSlicesMax(ItemStack stack) {
        return getTileTagCompound(stack).getInteger("slices_max");
    }

    @Override
    public int getMetadata(int damage) {
        int numSlices = CheeseUtils.getTopSlicesFromMeta(damage);
        if (numSlices <= 0) {
            GrowthcraftMilk.logger.warn("Cheese item meta returned slicescount=0.");
            numSlices = 1;
        }
        return (numSlices - 1) << 2;
    }

    public static NBTTagCompound openNBT(ItemStack stack) {
        final Item item = stack.getItem();
        if (item instanceof ItemBlockCheeseBlock) {
            return ((ItemBlockCheeseBlock<?>) item).getTileTagCompound(stack);
        } else {
            // throw error
        }
        return null;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> subItems) {
        if (!this.isInCreativeTab(tab))
            return;

        for (T type : getAllVariants()) {
            ICheeseBlockStackFactory blockStackFactory = type.getCheeseBlocks();
            ItemStack stack = blockStackFactory.asStackForStage(4, blockStackFactory.getInitialStage());
            subItems.add(stack);
        }
    }
}
