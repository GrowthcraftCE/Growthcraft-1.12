package growthcraft.cellar.common.tileentity.device;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import growthcraft.cellar.shared.CellarRegistry;
import growthcraft.cellar.shared.booze.BoozeTag;
import growthcraft.cellar.shared.processing.common.IProcessingRecipeBase;
import growthcraft.cellar.shared.processing.yeast.YeastRegistry;
import growthcraft.cellar.common.tileentity.TileEntityCellarDevice;
import growthcraft.core.shared.CoreRegistry;
import growthcraft.core.shared.item.WeightedItemStack;
import growthcraft.core.shared.tileentity.device.DeviceFluidSlot;
import growthcraft.core.shared.tileentity.device.DeviceInventorySlot;
import growthcraft.core.shared.tileentity.device.DeviceProgressive;
import growthcraft.core.shared.item.ItemUtils;
import net.minecraft.item.ItemStack;
import net.minecraft.util.WeightedRandom;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;

public class YeastGenerator extends DeviceProgressive {
    protected int consumption = 1200 / 16;
    protected DeviceFluidSlot fluidSlot;
    protected DeviceInventorySlot invSlot;
    protected List<WeightedItemStack> tempItemList = new ArrayList<WeightedItemStack>();

    /**
     * @param te             - parent tile entity
     * @param fluidSlotIndex - fluid slot id to use in parent
     *                       Fluid will be used from this slot
     * @param invSlotIndex   - inventory slot id to use in parent
     *                       Yeast will be generated into this slot
     */
    public YeastGenerator(TileEntityCellarDevice te, int fluidSlotIndex, int invSlotIndex) {
        super(te);
        this.fluidSlot = new DeviceFluidSlot(te, fluidSlotIndex);
        this.invSlot = new DeviceInventorySlot(te, invSlotIndex);
        setTimeMax(1200);
    }

    /**
     * How many fluid units are consumed per yeast gen?
     *
     * @param c - fluid consumption in milli-buckets
     */
    public YeastGenerator setConsumption(int c) {
        this.consumption = c;
        return this;
    }

    /**
     * Returns the current biome of the Yeast Generator's parent TileEntity.
     *
     * @return biome
     */
    public Biome getCurrentBiome() {
        return getWorld().getBiome(parent.getPos()); //.getBiomeGenForCoords(parent.xCoord, parent.zCoord);
    }

    /**
     * Determines if the given item stack can be replicated as a yeast item
     *
     * @param stack - item stack to test
     * @return true, it can be replicated, false otherwise
     */
    public boolean canReplicateYeast(ItemStack stack) {
        // prevent production if the stack size is currently maxed
        if (stack.getCount() >= stack.getMaxStackSize()) return false;
        // prevent item pointless ticking with invalid items
        if (!CellarRegistry.instance().yeast().isYeast(stack)) return false;
        return true;
    }

    /**
     * Determines if the jar can produce any yeast
     *
     * @return true, the generator can produce yeast, false otherwise
     */
    @Override
    public boolean canProcess() {
        if (fluidSlot.getAmount() < consumption) return false;
        final ItemStack yeastItem = invSlot.get();
        // we can ignore null items, this will fallback to the initProduceYeast
        if (!ItemUtils.isEmpty(yeastItem)) {
            if (!canReplicateYeast(yeastItem)) return false;
        }
        return CoreRegistry.instance().fluidDictionary().hasFluidTags(fluidSlot.getFluid(), BoozeTag.YOUNG);
    }

    public void consumeFluid() {
        fluidSlot.consume(consumption, true);
        markDirty();
    }

    /**
     * This is called to initialize the yeast slot, a random yeast type is
     * chosen from the various biome types and set in the slot,
     * any further yeast production will be of the same type.
     */
    protected void initProduceYeast() {
        Set<WeightedItemStack> tempItemSet = new HashSet<WeightedItemStack>();
        tempItemList.clear();
        final Biome biome = getCurrentBiome();
        if (biome != null) {
            final YeastRegistry reg = CellarRegistry.instance().yeast();

            {
                final Collection<WeightedItemStack> yl = reg.getYeastListForBiomeName(biome.biomeName);
                if (yl != null) {
                    tempItemSet.addAll(yl);
                }
            }

            for (Type t : BiomeDictionary.getTypes(biome)) {
                final Collection<WeightedItemStack> yeastList = reg.getYeastListForBiomeType(t);
                if (yeastList != null) {
                    tempItemSet.addAll(yeastList);
                }
            }

            if (tempItemSet.size() > 0) {
                tempItemList.addAll(tempItemSet);

                final WeightedItemStack weightedItemStack = (WeightedItemStack) WeightedRandom.getRandomItem(getWorld().rand, tempItemList);
                if (weightedItemStack != null && weightedItemStack.itemStack != null) {
                    final ItemStack result = weightedItemStack.itemStack.copy();
                    invSlot.set(result);
                    consumeFluid();
                }
            }
        }
    }

    @Override
    public void process(IProcessingRecipeBase recipe) {
        if (invSlot.isEmpty()) {
            initProduceYeast();
        } else {
            final ItemStack contents = invSlot.get();
            // ensure that the item is indeed some form of yeast, prevents item duplication
            // while canProduceYeast will prevent invalid items from popping up
            // produceYeast is public, and can be called outside the update
            // logic to force yeast generation, as such, this must ensure
            // item correctness
            if (canReplicateYeast(contents)) {
                invSlot.set(ItemUtils.increaseStack(contents));
                consumeFluid();
            }
        }
    }

    @Override
    public void update() {
        super.update();
    }
}
