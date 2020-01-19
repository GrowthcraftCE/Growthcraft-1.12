package growthcraft.cellar.common.tileentity.device;

import growthcraft.cellar.common.block.BlockFruitPresser;
import growthcraft.cellar.common.block.BlockFruitPresser.PressState;
import growthcraft.cellar.common.tileentity.TileEntityFruitPress;
import growthcraft.cellar.shared.CellarRegistry;
import growthcraft.cellar.shared.processing.common.Residue;
import growthcraft.cellar.shared.processing.pressing.IPressingRecipe;
import growthcraft.cellar.common.tileentity.TileEntityCellarDevice;
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


    public boolean isPressed() {
       if(parent instanceof TileEntityFruitPress){
           return ((TileEntityFruitPress)parent).isPressed();
       }
        return false;
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
        if(recipe == null) return false;
        //Checks for input items
        if(!recipe.getInput().containsItemStack(inputSlot.get())) return false;
        //Checks for output fluids
        if(!fluidSlot.hasCapacityFor(recipe.getFluidStack())) return false;
        //Checks for output items
        if(!residueSlot.hasCapacityFor(recipe.getResidue().residueItem)) return false;

        return true;
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
