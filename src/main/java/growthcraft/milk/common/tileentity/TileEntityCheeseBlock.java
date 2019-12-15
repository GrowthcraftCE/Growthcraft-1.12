package growthcraft.milk.common.tileentity;

import java.io.IOException;
import java.util.List;

import growthcraft.core.shared.io.nbt.INBTItemSerializable;
import growthcraft.core.shared.tileentity.GrowthcraftTileBase;
import growthcraft.core.shared.tileentity.event.TileEventHandler;
import growthcraft.core.shared.tileentity.feature.IItemOperable;
import growthcraft.core.shared.item.ItemTest;
import growthcraft.core.shared.item.ItemUtils;
import growthcraft.milk.GrowthcraftMilk;
import growthcraft.milk.common.item.ItemBlockCheeseBlock;
import growthcraft.milk.common.tileentity.struct.Cheese;
import growthcraft.milk.shared.definition.EnumCheeseStage;
import growthcraft.milk.shared.definition.ICheeseBlockStackFactory;
import growthcraft.milk.shared.init.GrowthcraftMilkItems;
import io.netty.buffer.ByteBuf;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ITickable;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;

public class TileEntityCheeseBlock extends GrowthcraftTileBase implements ITickable, IItemOperable, INBTItemSerializable {
    private Cheese cheese = new Cheese();

    public List<ItemStack> populateDrops(List<ItemStack> list) {
        // Populate drop with top cheese wheel
        {
            ItemStack stack = null;

            boolean bHasAnySlices = cheese.getTopSlices() > 0;
            if (bHasAnySlices) {
                boolean bHasFullSlices = cheese.getTopSlices() == cheese.getTopSlicesMax();
                EnumCheeseStage stage = cheese.getStage();
                if (bHasFullSlices) {
                    if (stage == EnumCheeseStage.CUT) {
                        GrowthcraftMilk.logger.warn("A cut cheese wheel with full amount of slices found.");
                        stage = EnumCheeseStage.AGED;    // Is a full wheel
                    }
                }

                stack = cheese.getType().getCheeseBlocks().asStackForStage(cheese.getTopSlices(), stage);
            }

            if (stack != null)
                list.add(stack);
        }

        // Populate drop with bottom cheese wheel
        {
            ItemStack stack = null;
            if (cheese.isDoubleStacked()) {
                EnumCheeseStage stage = cheese.getStage();
                if (stage == EnumCheeseStage.CUT)
                    stage = EnumCheeseStage.AGED;    // Is a full wheel
                stack = cheese.getType().getCheeseBlocks().asStackForStage(cheese.getTopSlicesMax(), stage);
            }

            if (stack != null)
                list.add(stack);
        }

        return list;
    }

    public Cheese getCheese() {
        return cheese;
    }

    public int getCheeseId() {
        return getCheese().getId();
    }

    public int getCheeseStageId() {
        return getCheese().getStageId();
    }

    protected void readCheeseFromNBT(NBTTagCompound nbt) {
        cheese.readFromNBT(nbt);
    }

    /**
     * When the tileentity is reloaded from an ItemStack
     *
     * @param nbt tag compound to read
     */
    @Override
    public void readFromNBTForItem(NBTTagCompound nbt) {
        super.readFromNBTForItem(nbt);
        readCheeseFromNBT(nbt);
    }

    @TileEventHandler(event = TileEventHandler.EventType.NBT_READ)
    public void readFromNBT_CheeseBlock(NBTTagCompound nbt) {
        readCheeseFromNBT(nbt);
    }

    protected void writeCheeseToNBT(NBTTagCompound nbt) {
        cheese.writeToNBT(nbt);
    }

    @Override
    public void writeToNBTForItem(NBTTagCompound nbt) {
        super.writeToNBTForItem(nbt);
        writeCheeseToNBT(nbt);
    }

    @TileEventHandler(event = TileEventHandler.EventType.NBT_WRITE)
    public void writeToNBT_CheeseBlock(NBTTagCompound nbt) {
        writeCheeseToNBT(nbt);
    }

    public ItemStack asItemStack() {
        final ICheeseBlockStackFactory blockStackFactory = cheese.getType().getCheeseBlocks();
        final int numSlices = MathHelper.clamp(cheese.getSlices(), 0, cheese.getTopSlicesMax());    // NOTE: Not a full representation! Is clamped by maximal slices of a single wheel.
        if (numSlices <= 0)
            return ItemStack.EMPTY;
        final ItemStack stack = blockStackFactory.asStackForStage(numSlices, blockStackFactory.getInitialStage());  // GrowthcraftMilkBlocks.cheeseBlock.asStack();
        final NBTTagCompound tag = ItemBlockCheeseBlock.openNBT(stack);
        writeToNBTForItem(tag);
        return stack;
    }

    @TileEventHandler(event = TileEventHandler.EventType.NETWORK_READ)
    public boolean readFromStream_CheeseBlock(ByteBuf stream) throws IOException {
        cheese.readFromStream(stream);
        return true;
    }

    @TileEventHandler(event = TileEventHandler.EventType.NETWORK_WRITE)
    public boolean writeToStream_CheeseBlock(ByteBuf stream) throws IOException {
        cheese.writeToStream(stream);
        return true;
    }

    @Override
    public void update() {
        if (!world.isRemote) {
            cheese.update();
            if (cheese.needClientUpdate) {
                cheese.needClientUpdate = false;
                if (cheese.hasSlices()) {
                    markForUpdate(true);
                } else {
                    world.setBlockToAir(getPos());
                }
            }
        }
    }
	
/*
	@Override
	public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newSate) {
		// return super.shouldRefresh(world, pos, oldState, newSate);
		return false;
	}
*/

    @Override
    public boolean tryPlaceItem(IItemOperable.Action action, EntityPlayer player, ItemStack onHand) {
        if (IItemOperable.Action.RIGHT != action) return false;

        int consumeAmount = cheese.canWaxing(onHand);
        if (consumeAmount > 0) {
            // Waxing
            cheese.setStage(EnumCheeseStage.UNAGED);
            updateOnTryPlaceItem(consumeAmount, player, onHand);
            return true;
        }

        consumeAmount = cheese.canStack(onHand);
        if (consumeAmount > 0) {
            // Stacking
            cheese.stackWheel(onHand, true);

            // placement sound
            {
                World world = player.getEntityWorld();
                IBlockState state = world.getBlockState(pos);
                SoundType soundtype = getBlockType().getSoundType(state, world, pos, player);
                world.playSound(null, pos, soundtype.getPlaceSound(), SoundCategory.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F);
            }

            updateOnTryPlaceItem(consumeAmount, player, onHand);
            return true;
        }

        return false;
    }

    private void updateOnTryPlaceItem(int consumeAmount, EntityPlayer player, ItemStack onHand) {
        if (!player.capabilities.isCreativeMode) {
            if (onHand.getCount() > consumeAmount) {
                onHand.shrink(consumeAmount);
                player.inventory.setInventorySlotContents(player.inventory.currentItem, onHand);
            } else
                player.inventory.setInventorySlotContents(player.inventory.currentItem, ItemStack.EMPTY);
        }
        markDirtyAndUpdate(true); // Test, if correct
    }

    @Override
    public boolean tryTakeItem(IItemOperable.Action action, EntityPlayer player, ItemStack onHand) {
        if (IItemOperable.Action.RIGHT != action) return false;
        if (cheese.isAged()) {
            if (!(onHand.getItem() instanceof ItemSword) &&
                    !ItemTest.itemMatchesOre(onHand, "toolKnife"))
                return false;

            final ItemStack stack = cheese.yankSlices(1, true);
            if (!ItemUtils.isEmpty(stack)) {
                ItemUtils.addStackToPlayer(stack, player, false);
            }
            markDirtyAndUpdate(true);
            cheese.needClientUpdate |= true;
            return true;
        }
        return false;
    }
}
