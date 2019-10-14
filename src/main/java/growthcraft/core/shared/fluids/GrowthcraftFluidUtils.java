package growthcraft.core.shared.fluids;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import growthcraft.core.shared.item.ItemUtils;
import growthcraft.core.shared.legacy.FluidContainerRegistry;
import growthcraft.core.shared.legacy.FluidContainerRegistry.FluidContainerData;
import growthcraft.core.shared.utils.ExperienceUtils;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidActionResult;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;

public class GrowthcraftFluidUtils {
    private static Map<Fluid, List<FluidContainerData>> fluidData;

    private GrowthcraftFluidUtils() {
    }

    /////////
    // Fluid container registry helpers
    /////////

    public static Map<Fluid, List<FluidContainerData>> getFluidData() {
        if (fluidData == null || fluidData.size() == 0) {
            fluidData = new HashMap<Fluid, List<FluidContainerData>>();
            for (FluidContainerData data : Arrays.asList(FluidContainerRegistry.getRegisteredFluidContainerData())) {
                if (!fluidData.containsKey(data.fluid.getFluid())) {
                    fluidData.put(data.fluid.getFluid(), new ArrayList<FluidContainerData>());
                }
                fluidData.get(data.fluid.getFluid()).add(data);
            }
        }

        return fluidData;
    }

    public static List<ItemStack> getFluidContainers(FluidStack... fluids) {
        if (fluids.length == 1) {
            final ArrayList<ItemStack> fluidContainers = new ArrayList<ItemStack>();
            final FluidStack fluidStack = fluids[0];

            for (FluidContainerData data : getFluidData().get(fluidStack.getFluid())) {
                if (data.fluid.amount >= fluidStack.amount) {
                    fluidContainers.add(data.filledContainer);
                }
            }

            return fluidContainers;
        } else {
            return getFluidContainers(Arrays.asList(fluids));
        }
    }

    public static List<ItemStack> getFluidContainers(Collection<FluidStack> fluids) {
        final ArrayList<ItemStack> fluidContainers = new ArrayList<ItemStack>();

        for (FluidStack fluidStack : fluids) {
            fluidContainers.addAll(getFluidContainers(fluidStack));
        }

        return fluidContainers;
    }

    /////////
    // Fluid registry helpers
    /////////

    public static List<Fluid> getFluidsByNames(List<String> names) {
        final List<Fluid> fluids = new ArrayList<Fluid>();
        for (String name : names) {
            fluids.add(FluidRegistry.getFluid(name));
        }
        return fluids;
    }

    public static boolean doesFluidExist(String name) {
        return FluidRegistry.getFluid(name) != null && FluidRegistry.isFluidRegistered(name);
    }

    public static boolean doesFluidExist(Fluid fluid) {
        return fluid != null && FluidRegistry.isFluidRegistered(fluid);
    }

    public static boolean doesFluidsExist(Fluid[] fluid) {
        for (int i = 0; i < fluid.length; ++i) {
            if (!doesFluidExist(fluid[i])) {
                return false;
            }
        }
        return true;
    }

    /////////
    // Fluid interactions
    /////////

    public static boolean playerFillTank(World world, BlockPos pos, IFluidHandler tank, ItemStack held, EntityPlayer player) {
        if (ItemUtils.isEmpty(held)) return false;

        FluidStack heldContents = FluidUtil.getFluidContained(held);
        FluidActionResult fac = null;

        if (heldContents == null) {
            heldContents = FluidContainerRegistry.getFluidForFilledItem(held);
            if (heldContents == null)
                return false;
            if (tank.fill(heldContents, false) <= 0)
                return false;
        } else {
            fac = FluidUtil.tryEmptyContainer(held, tank, Integer.MAX_VALUE, player, false);
            if (FluidActionResult.FAILURE.equals(fac))
                return false;
        }

        if (!world.isRemote) {
            ItemStack containerItem = null;
            if (fac != null) {
                fac = FluidUtil.tryEmptyContainer(held, tank, Integer.MAX_VALUE, player, true);
                if (FluidActionResult.FAILURE.equals(fac))
                    return false;
                containerItem = fac.getResult();
            } else {
                tank.fill(heldContents, true);
                containerItem = FluidContainerRegistry.drainFluidContainer(held);
            }

            if (!player.inventory.addItemStackToInventory(containerItem)) {
                if (containerItem == null) {
                    // WARN about invalid container item
                } else {
                    world.spawnEntity(new EntityItem(world, (double) pos.getX() + 0.5D, (double) pos.getY() + 1.5D, (double) pos.getZ() + 0.5D, containerItem));
                }
            } else if (player instanceof EntityPlayerMP) {
                ((EntityPlayerMP) player).sendContainerToPlayer(player.inventoryContainer);
            }

            if (!player.capabilities.isCreativeMode) {
                held.shrink(1);
                if (held.isEmpty()) {
                    player.inventory.setInventorySlotContents(player.inventory.currentItem, ItemStack.EMPTY);
                }
            }
        }
        return true;
    }

    public static FluidStack playerDrainTank(World world, BlockPos pos, IFluidHandler tank, ItemStack held, EntityPlayer player, boolean expbool, int amount, float exp) {
        if (ItemUtils.isEmpty(held)) return null;

        final FluidStack available = tank.drain(Integer.MAX_VALUE, false);
        if (available == null)
            return null;

        FluidActionResult fac = FluidUtil.tryFillContainer(held, tank, Integer.MAX_VALUE, player, false);
        if (FluidActionResult.FAILURE.equals(fac)) {
            // Happens with bottles without fluid handlers. Using FluidContainerRegistry in this case.
            if (FluidContainerRegistry.fillFluidContainer(available, held) == null)
                return null;
        }

        if (!world.isRemote) {
            ItemStack filled;
            FluidStack heldContents;
            fac = FluidUtil.tryFillContainer(held, tank, Integer.MAX_VALUE, player, true);
            if (FluidActionResult.FAILURE.equals(fac)) {
                filled = FluidContainerRegistry.fillFluidContainer(available, held);
                heldContents = FluidContainerRegistry.getFluidForFilledItem(filled);
                if (heldContents == null)
                    return null;
                tank.drain(heldContents.amount, true);
            } else {
                filled = fac.getResult();
                heldContents = FluidUtil.getFluidContained(filled);
            }

            if (!player.inventory.addItemStackToInventory(filled)) {
                world.spawnEntity(new EntityItem(world, (double) pos.getX() + 0.5D, (double) pos.getY() + 1.5D, (double) pos.getZ() + 0.5D, filled));
            } else if (player instanceof EntityPlayerMP) {
                ((EntityPlayerMP) player).sendContainerToPlayer(player.inventoryContainer);
            }

            if (!player.capabilities.isCreativeMode) {
                held.shrink(1);
                if (held.isEmpty()) {
                    player.inventory.setInventorySlotContents(player.inventory.currentItem, ItemStack.EMPTY);
                }
            }

            if (expbool) {
                // NOTE: Assuming first tank is drained.
                ExperienceUtils.spawnExp(amount * heldContents.amount / tank.getTankProperties()[0].getCapacity(), exp, player);
            }
        }

        return available;
    }

    public static FluidStack playerDrainTank(World world, BlockPos pos, IFluidHandler fh, ItemStack held, EntityPlayer player) {
        return playerDrainTank(world, pos, fh, held, player, false, 0, 0);
    }

    /////////
    // Fluidstack utils
    /////////

    public static FluidStack removeStackTags(FluidStack stack) {
        if (stack == null)
            return null;
        if (stack.tag == null)
            return stack;
        return new FluidStack(stack.getFluid(), stack.amount);
    }

    public static FluidStack exchangeFluid(FluidStack stack, Fluid newFluid) {
        return new FluidStack(newFluid, stack.amount);
    }

    public static FluidStack replaceFluidStack(String fluidId, FluidStack srcStack) {
        final Fluid fluid = FluidRegistry.getFluid(fluidId);
        if (fluid == null) {
            // An invalid fluid
            return null;
        }
        return replaceFluidStack(fluid, srcStack);
    }

    public static FluidStack replaceFluidStack(Fluid fluid, FluidStack srcStack) {
        if (fluid == null || srcStack == null) {
            return new FluidStack(FluidRegistry.WATER, 0);
        }
        return new FluidStack(fluid, srcStack.amount);
    }


    public static FluidStack updateFluidStackAmount(FluidStack srcStack, int amount) {
        if (srcStack == null) {
            return new FluidStack(FluidRegistry.WATER, amount);
        }
        srcStack.amount = amount;
        return srcStack;
    }

    /////////
    // MC1.7 to MC1.11.2 compatibility layer
    /////////

    public static FluidTankInfo[] convertTankPropsToInfo(IFluidTankProperties[] tankProperties) {
        FluidTankInfo[] infos = new FluidTankInfo[tankProperties.length];
        for (int i = 0; i < tankProperties.length; i++) {
            IFluidTankProperties prop = tankProperties[i];
            infos[i] = new FluidTankInfo(prop.getContents(), prop.getCapacity());
        }
        return infos;
    }

    public static FluidStack replaceFluidStackTags(FluidStack from, FluidStack tagsFrom) {
        if (from == null)
            return null;
        if (tagsFrom == null)
            return from;

        if (tagsFrom.tag == null) {
            if (from.tag != null)
                return new FluidStack(from.getFluid(), from.amount);
            else
                return from;
        }

        return new FluidStack(from.getFluid(), from.amount, tagsFrom.tag);
    }
}
