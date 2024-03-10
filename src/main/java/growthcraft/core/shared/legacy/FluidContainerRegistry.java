package growthcraft.core.shared.legacy;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import growthcraft.core.shared.item.ItemUtils;
import net.minecraft.init.Items;
import net.minecraft.init.PotionTypes;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionUtils;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.ForgeModContainer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.UniversalBucket;
import net.minecraftforge.fml.common.FMLLog;
import net.minecraftforge.fml.common.eventhandler.Event;

import java.util.Map;
import java.util.Set;

/**
 * Register simple items that contain fluids here. Useful for buckets, bottles, and things that have
 * ID/metadata mappings.
 * <p>
 * For more complex items, use {@link IFluidContainerItem} instead.
 *
 * @author King Lemming
 */
public abstract class FluidContainerRegistry {
    // Holder object that implements HashCode for an ItemStack,
    // the local maps are not guaranteed to have the same internal generic structure,
    // but the external interface for checking ItemStacks will still exist.
    private static class ContainerKey {
        ItemStack container;
        FluidStack stack;

        private ContainerKey(ItemStack container) {
            this.container = container;
        }

        private ContainerKey(ItemStack container, FluidStack stack) {
            this(container);
            this.stack = stack;
        }

        @Override
        public int hashCode() {
            int code = 1;
            code = 31 * code + container.getItem().hashCode();
            code = 31 * code + container.getItemDamage();
            if (container.getTagCompound() != null)
                code = 31 * code + container.getTagCompound().hashCode();
            if (stack != null)
                code = 31 * code + stack.getFluid().hashCode();
            return code;
        }

        @Override
        public boolean equals(Object o) {
            if (!(o instanceof ContainerKey)) return false;
            ContainerKey ck = (ContainerKey) o;
            if (!ItemStack.areItemsEqual(container, ck.container)) return false;
            if (!ItemStack.areItemStackTagsEqual(container, ck.container)) return false;
            if (stack == null && ck.stack != null) return false;
            if (stack != null && ck.stack == null) return false;
            if (stack == null && ck.stack == null) return true;
            if (stack.getFluid() != ck.stack.getFluid()) return false;
            if (!FluidStack.areFluidStackTagsEqual(stack, stack)) return false;
            return true;
        }
    }

    private static Map<ContainerKey, FluidContainerData> containerFluidMap = Maps.newHashMap();
    private static Map<ContainerKey, FluidContainerData> filledContainerMap = Maps.newHashMap();
    private static Set<ContainerKey> emptyContainers = Sets.newHashSet();

    public static final int BUCKET_VOLUME = Fluid.BUCKET_VOLUME;
    public static final int BOTTLE_VOLUME = BUCKET_VOLUME / 4;
    public static final ItemStack EMPTY_BUCKET = new ItemStack(Items.BUCKET);
    public static final ItemStack EMPTY_BOTTLE = new ItemStack(Items.GLASS_BOTTLE);
    private static final ItemStack NULL_EMPTYCONTAINER = new ItemStack(Items.BUCKET);

    static {
        registerFluidContainer(FluidRegistry.WATER, new ItemStack(Items.WATER_BUCKET), EMPTY_BUCKET);
        registerFluidContainer(FluidRegistry.LAVA, new ItemStack(Items.LAVA_BUCKET), EMPTY_BUCKET);
        registerFluidContainer(new FluidStack(FluidRegistry.WATER, BOTTLE_VOLUME),
                PotionUtils.addPotionToItemStack(new ItemStack(Items.POTIONITEM), PotionTypes.WATER),
                EMPTY_BOTTLE);
    }

    private FluidContainerRegistry() {
    }

    public static boolean addBucketForFluid(Fluid fluid) {
        if (!FluidRegistry.addBucketForFluid(fluid))
            return false;
        final UniversalBucket universalBucket = ForgeModContainer.getInstance().universalBucket;
        final ItemStack emptyContainer = new ItemStack(Items.BUCKET, 1);
        final ItemStack filledContainer = UniversalBucket.getFilledBucket(universalBucket, fluid);
        return registerFluidContainer(new FluidStack(fluid, BUCKET_VOLUME), filledContainer, emptyContainer);
    }

    /**
     * Register a new fluid containing item.
     *
     * @param stack           FluidStack containing the type and amount of the fluid stored in the item.
     * @param filledContainer ItemStack representing the container when it is full.
     * @param emptyContainer  ItemStack representing the container when it is empty.
     * @return True if container was successfully registered; false if it already is.
     */
    public static boolean registerFluidContainer(FluidStack stack, ItemStack filledContainer, ItemStack emptyContainer) {
        return registerFluidContainer(new FluidContainerData(stack, filledContainer, emptyContainer));
    }

    /**
     * Register a new fluid containing item. The item is assumed to hold 1000 mB of fluid. Also
     * registers the Fluid if possible.
     *
     * @param fluid           Fluid type that is stored in the item.
     * @param filledContainer ItemStack representing the container when it is full.
     * @param emptyContainer  ItemStack representing the container when it is empty.
     * @return True if container was successfully registered; false if it already is, or an invalid parameter was passed.
     */
    public static boolean registerFluidContainer(Fluid fluid, ItemStack filledContainer, ItemStack emptyContainer) {
        if (!FluidRegistry.isFluidRegistered(fluid)) {
            FluidRegistry.registerFluid(fluid);
        }
        return registerFluidContainer(new FluidStack(fluid, BUCKET_VOLUME), filledContainer, emptyContainer);
    }

    /**
     * Register a new fluid containing item that does not have an empty container.
     *
     * @param stack           FluidStack containing the type and amount of the fluid stored in the item.
     * @param filledContainer ItemStack representing the container when it is full.
     * @return True if container was successfully registered; false if it already is, or an invalid parameter was passed.
     */
    public static boolean registerFluidContainer(FluidStack stack, ItemStack filledContainer) {
        return registerFluidContainer(new FluidContainerData(stack, filledContainer, null, true));
    }

    /**
     * Register a new fluid containing item that does not have an empty container. The item is
     * assumed to hold 1000 mB of fluid. Also registers the Fluid if possible.
     *
     * @param fluid           Fluid type that is stored in the item.
     * @param filledContainer ItemStack representing the container when it is full.
     * @return True if container was successfully registered; false if it already is, or an invalid parameter was passed.
     */
    public static boolean registerFluidContainer(Fluid fluid, ItemStack filledContainer) {
        if (!FluidRegistry.isFluidRegistered(fluid)) {
            FluidRegistry.registerFluid(fluid);
        }
        return registerFluidContainer(new FluidStack(fluid, BUCKET_VOLUME), filledContainer);
    }

    /**
     * Register a new fluid containing item.
     *
     * @param data See {@link FluidContainerData}.
     * @return True if container was successfully registered; false if it already is, or an invalid parameter was passed.
     */
    public static boolean registerFluidContainer(FluidContainerData data) {
        if (isFilledContainer(data.filledContainer) || data.filledContainer == null) {
            return false;
        }
        if (data.fluid == null || data.fluid.getFluid() == null) {
            FMLLog.bigWarning("Invalid registration attempt for a fluid container item %s has occurred. The registration has been denied to prevent crashes. The mod responsible for the registration needs to correct this.", data.filledContainer.getItem().getTranslationKey(data.filledContainer));
            return false;
        }
        containerFluidMap.put(new ContainerKey(data.filledContainer), data);

        if (data.emptyContainer != null && data.emptyContainer != NULL_EMPTYCONTAINER) {
            filledContainerMap.put(new ContainerKey(data.emptyContainer, data.fluid), data);
            emptyContainers.add(new ContainerKey(data.emptyContainer));
        }

        MinecraftForge.EVENT_BUS.post(new FluidContainerRegisterEvent(data));
        return true;
    }

    /**
     * Like {@link ForgeHooks#getContainerItem(ItemStack), but returns container items
     * registered within the FluidContainerRegistry in case of fallback.
     *
     * @param stack the item stack containing the fluid
     * @return the empty container of stack
     */
    public static ItemStack getContainerItemWithFallback(ItemStack stack) {
        ItemStack result = ForgeHooks.getContainerItem(stack);
        if (!ItemUtils.isEmpty(result))
            return result;

        result = drainFluidContainer(stack);
        if (!ItemUtils.isEmpty(result))
            return result;

        return ItemStack.EMPTY;
    }

    /**
     * Determines the fluid type and amount inside a container.
     *
     * @param container The fluid container.
     * @return FluidStack representing stored fluid.
     */
    public static FluidStack getFluidForFilledItem(ItemStack container) {
        if (container == null) {
            return null;
        }

        FluidContainerData data = containerFluidMap.get(new ContainerKey(container));
        return data == null ? null : data.fluid.copy();
    }

    /**
     * Attempts to fill an empty container with a fluid.
     * <p>
     * NOTE: Returns null on fail, NOT the empty container.
     *
     * @param fluid     FluidStack containing the type and amount of fluid to fill.
     * @param container ItemStack representing the empty container.
     * @return Filled container if successful, otherwise null.
     */
    public static ItemStack fillFluidContainer(FluidStack fluid, ItemStack container) {
        // FIXME: Use MC 1.11.2 convention and return ItemStack.EMPTY instead of null . Adapt any code using this method.

        if (ItemUtils.isEmpty(container) || fluid == null) {
            return null;
        }

        FluidContainerData data = filledContainerMap.get(new ContainerKey(container, fluid));
        if (data != null && fluid.amount >= data.fluid.amount) {
            return data.filledContainer.copy();
        }
        return null;
    }

    /**
     * Attempts to empty a full container.
     *
     * @param container ItemStack representing the full container.
     * @return Empty container if successful, otherwise null.
     */
    public static ItemStack drainFluidContainer(ItemStack container) {
        // FIXME: Use MC 1.11.2 convention and return ItemStack.EMPTY instead of null . Adapt any code using this method.

        if (ItemUtils.isEmpty(container)) {
            return null;
        }

        FluidContainerData data = containerFluidMap.get(new ContainerKey(container));
        if (data != null) {
            return data.emptyContainer.copy();
        }

        return null;
    }

    /**
     * Determines the capacity of a full container.
     *
     * @param container The full container.
     * @return The containers capacity, or 0 if the ItemStack does not represent
     * a registered container.
     */
    public static int getContainerCapacity(ItemStack container) {
        return getContainerCapacity(null, container);
    }

    /**
     * Determines the capacity of a container.
     *
     * @param fluid     FluidStack containing the type of fluid the capacity should be
     *                  determined for (ignored for full containers).
     * @param container The container (full or empty).
     * @return The containers capacity, or 0 if the ItemStack does not represent
     * a registered container or the FluidStack is not registered with
     * the empty container.
     */
    public static int getContainerCapacity(FluidStack fluid, ItemStack container) {
        if (container == null) {
            return 0;
        }

        FluidContainerData data = containerFluidMap.get(new ContainerKey(container));

        if (data != null) {
            return data.fluid.amount;
        }

        if (fluid != null) {
            data = filledContainerMap.get(new ContainerKey(container, fluid));

            if (data != null) {
                return data.fluid.amount;
            }
        }

        return 0;
    }

    /**
     * Determines if a container holds a specific fluid.
     */
    public static boolean containsFluid(ItemStack container, FluidStack fluid) {
        if (container == null || fluid == null) {
            return false;
        }

        FluidContainerData data = containerFluidMap.get(new ContainerKey(container));
        return data == null ? false : data.fluid.containsFluid(fluid);
    }

    public static boolean isBucket(ItemStack container) {
        if (container == null) {
            return false;
        }

        if (container.isItemEqual(EMPTY_BUCKET)) {
            return true;
        }

        FluidContainerData data = containerFluidMap.get(new ContainerKey(container));
        return data != null && data.emptyContainer.isItemEqual(EMPTY_BUCKET);
    }

    public static boolean isContainer(ItemStack container) {
        return isEmptyContainer(container) || isFilledContainer(container);
    }

    public static boolean isEmptyContainer(ItemStack container) {
        return container != null && emptyContainers.contains(new ContainerKey(container));
    }

    public static boolean isFilledContainer(ItemStack container) {
        return container != null && getFluidForFilledItem(container) != null;
    }

    public static FluidContainerData[] getRegisteredFluidContainerData() {
        return containerFluidMap.values().toArray(new FluidContainerData[containerFluidMap.size()]);
    }

    /**
     * Wrapper class for the registry entries. Ensures that none of the attempted registrations
     * contain null references unless permitted.
     */
    public static class FluidContainerData {
        public final FluidStack fluid;
        public final ItemStack filledContainer;
        public final ItemStack emptyContainer;

        public FluidContainerData(FluidStack stack, ItemStack filledContainer, ItemStack emptyContainer) {
            this(stack, filledContainer, emptyContainer, false);
        }

        public FluidContainerData(FluidStack stack, ItemStack filledContainer, ItemStack emptyContainer, boolean nullEmpty) {
            this.fluid = stack;
            this.filledContainer = filledContainer;
            this.emptyContainer = emptyContainer == null ? NULL_EMPTYCONTAINER : emptyContainer;

            if (stack == null || filledContainer == null || emptyContainer == null && !nullEmpty) {
                throw new RuntimeException("Invalid FluidContainerData - a parameter was null.");
            }
        }

        public FluidContainerData copy() {
            return new FluidContainerData(fluid, filledContainer, emptyContainer, true);
        }
    }

    public static class FluidContainerRegisterEvent extends Event {
        public final FluidContainerData data;

        public FluidContainerRegisterEvent(FluidContainerData data) {
            this.data = data.copy();
        }
    }
}