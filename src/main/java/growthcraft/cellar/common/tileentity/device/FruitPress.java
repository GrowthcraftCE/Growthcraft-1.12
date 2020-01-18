package growthcraft.cellar.common.tileentity.device;

import growthcraft.cellar.common.block.BlockFruitPresser;
import growthcraft.cellar.common.block.BlockFruitPresser.PressState;
import growthcraft.cellar.shared.CellarRegistry;
import growthcraft.cellar.shared.processing.common.Residue;
import growthcraft.cellar.shared.processing.fermenting.IFermentationRecipe;
import growthcraft.cellar.shared.processing.pressing.IPressingRecipe;
import growthcraft.cellar.common.tileentity.TileEntityCellarDevice;
import growthcraft.core.shared.fluids.GrowthcraftFluidUtils;
import growthcraft.core.shared.tileentity.device.DeviceFluidSlot;
import growthcraft.core.shared.tileentity.device.DeviceInventorySlot;
import growthcraft.core.shared.tileentity.device.DeviceProgressive;
import growthcraft.core.shared.item.ItemUtils;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidStack;

public class FruitPress extends DeviceProgressive<IPressingRecipe> {
    private float pomace;
    private DeviceFluidSlot fluidSlot;
    private DeviceInventorySlot inputSlot;
    private DeviceInventorySlot residueSlot;


    /**
     * @param te - parent tile
     * @param fs - fluid tank id
     * @param is - input inventory slot id
     * @param rs - residue inventory slot id
     */
    public FruitPress(TileEntityCellarDevice te, int fs, int is, int rs) {
        super(te);
        this.fluidSlot = new DeviceFluidSlot(te, fs);
        this.inputSlot = new DeviceInventorySlot(te, is);
        this.residueSlot = new DeviceInventorySlot(te, rs);
    }

    /**
     * @return meta - the metadata for the FruitPresser usually above the fruit press
     */
    public boolean isPressed() {
        return getWorld().getBlockState(parent.getPos().up()).getValue(BlockFruitPresser.TYPE_PRESSED) == PressState.PRESSED;
    }

    @Override
    protected IPressingRecipe loadRecipe() {
        return CellarRegistry.instance().pressing().getPressingRecipe(inputSlot.get());
    }

    @Override
    protected float getSpeedMultiplier(){
        return super.getSpeedMultiplier()*(isPressed()? 1:0);
    }
    @Override
    protected boolean canProcess() {
        IPressingRecipe recipe = getWorkingRecipe();
        if (inputSlot.get() == null) return false;
        if (fluidSlot.isFull()) return false;

        if (recipe == null) return false;
        if (!inputSlot.hasEnough((recipe.getInput()))) return false;

        if (fluidSlot.isEmpty()) return true;

        final FluidStack stack = recipe.getFluidStack();
        return stack.isFluidEqual(fluidSlot.get());
    }

    public void producePomace() {
        IPressingRecipe recipe = getWorkingRecipe();
        if (recipe == null) return;
        final Residue residue = recipe.getResidue();
        if (residue != null) {
            this.pomace = this.pomace + residue.pomaceRate;
            if (this.pomace >= 1.0F) {
                this.pomace = this.pomace - 1.0F;
                final ItemStack residueResult = ItemUtils.mergeStacks(residueSlot.get(), residue.residueItem);
                if (!ItemUtils.isEmpty(residueResult)) residueSlot.set(residueResult);
            }
        }
    }

    @Override
    public void process(IPressingRecipe recipe) {
        final ItemStack pressingItem = inputSlot.get();
        producePomace();
        final FluidStack fluidstack = recipe.getFluidStack();
        fluidSlot.fill(fluidstack, true);
        inputSlot.consume(recipe.getInput());
    }

    @Override
    public void update() {
        super.update();
    }

    @Override
    public void readFromNBT(NBTTagCompound data) {
        super.readFromNBT(data);
        this.pomace = data.getFloat("pomace");
    }

    @Override
    public void writeToNBT(NBTTagCompound data) {
        super.writeToNBT(data);
        data.setFloat("pomace", pomace);
    }
}
