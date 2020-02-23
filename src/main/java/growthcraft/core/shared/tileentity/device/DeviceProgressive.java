package growthcraft.core.shared.tileentity.device;

import growthcraft.cellar.shared.processing.common.IProcessingRecipeBase;
import growthcraft.core.shared.tileentity.GrowthcraftTileDeviceBase;
import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;

public class DeviceProgressive<T extends IProcessingRecipeBase> extends DeviceBase {
    protected double time;
    protected int timeMax;
    protected boolean recheckRecipe = true;
    private T activeRecipe;

    public DeviceProgressive(GrowthcraftTileDeviceBase te) {
        super(te);
    }

    //TODO:Move to DeviceBase, or make a DeviceRecipe class?
    //Buffered recipe logic

    protected T loadRecipe() {
        return null;
    }

    public void markForRecipeRecheck() {
        this.recheckRecipe = true;
    }

    public T refreshRecipe() {
        final T recipe = loadRecipe();
        if (recipe != null && recipe != activeRecipe) {
            if (activeRecipe != null) {
                resetTime();
            }
            this.activeRecipe = recipe;
            markDirty();
        } else {
            if (recipe == null && activeRecipe != null) {
                activeRecipe = null;
                resetTime();
                markDirty();
            }
        }
        this.recheckRecipe = false;
        return activeRecipe;
    }

    protected boolean canProcess(){return activeRecipe != null;}

    protected void process(T recipe){if(!canProcess()) return;}

    public T getWorkingRecipe() {
        if (recheckRecipe) {refreshRecipe();}
        return activeRecipe;
    }

    //Progress and time related stuff

    protected float getSpeedMultiplier(){
        return 1f;
    }

    public float getProgress() {
        final int tmx = getTimeMax();
        if (tmx > 0) {
            return (float) time / (float) tmx;
        }
        return 0.0f;
    }

    public int getProgressScaled(int scale) {
        final int tmx = getTimeMax();
        if (tmx > 0) {
            return (int) this.time * scale / tmx;
        }
        return 0;
    }

    public double getTime() {
        return time;
    }

    public int getTimeMax() {
        return timeMax;
    }

    public void setTime(int t) {
        this.time = t;
    }

    public void setTimeMax(int t) {
        this.timeMax = t;
    }

    /**
     * @return time was reset, false otherwise
     */
    public boolean resetTime() {
        if (this.time != 0) {
            setTime(0);
            return true;
        }
        return false;
    }


    public void increaseTime() {
        time+=getSpeedMultiplier();
    }

    public void update() {
        try {
            final T recipe = getWorkingRecipe();
            if (canProcess()) {
                setTimeMax(recipe.getTime());
                if (time >= timeMax) {
                    process(recipe);
                    resetTime();
                } else {
                    increaseTime();
                }
            } else {
                if (resetTime()) markForUpdate(true);
            }
        } catch (NullPointerException e) {
            /* Do nothing as there is an issue with the recipe. */
        }
    }

    //Data stuff

    /**
     * @param data - nbt data to read from
     */
    @Override
    public void readFromNBT(NBTTagCompound data) {
        super.readFromNBT(data);
        this.time = data.getDouble("time");
    }

    /**
     * @param data - nbt to write to
     */
    @Override
    public void writeToNBT(NBTTagCompound data) {
        super.writeToNBT(data);
        data.setDouble("time", time);
    }

    /**
     * @param buf - buffer to read from
     */
    @Override
    public boolean readFromStream(ByteBuf buf) {
        super.readFromStream(buf);
        this.time = buf.readDouble();
        return false;
    }

    /**
     * @param buf - buffer to write to
     */
    @Override
    public boolean writeToStream(ByteBuf buf) {
        super.writeToStream(buf);
        buf.writeDouble(time);
        return false;
    }
}
