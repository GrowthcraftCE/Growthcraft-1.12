package growthcraft.milk.common.tileentity.struct;

import growthcraft.core.shared.io.nbt.INBTSerializableContext;
import growthcraft.core.shared.io.stream.IStreamable;
import growthcraft.milk.shared.MilkRegistry;
import growthcraft.milk.shared.cheese.CheeseIO;
import growthcraft.milk.shared.definition.ICheeseType;
import growthcraft.milk.shared.init.GrowthcraftMilkItems.WaxedCheeseTypes;
import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;

public class CheeseCurd implements INBTSerializableContext, IStreamable {
    public boolean needClientUpdate;
    private ICheeseType cheese = WaxedCheeseTypes.CHEDDAR;
    private int age;
    private int ageMax = 1200;
    private boolean dried;

    public ICheeseType getType() {
        return cheese;
    }

    public int getId() {
//		return cheese.getMetaForInitialStage();
        return MilkRegistry.instance().cheese().getCheeseId(cheese);
    }

    public float getAgeProgress() {
        return (float) age / ageMax;
    }

    public int getRenderColor() {
        return cheese.getColor();
    }

    public boolean isDried() {
        return dried;
    }

    public void writeToNBT(NBTTagCompound nbt) {
        CheeseIO.writeToNBT(nbt, cheese);
        nbt.setBoolean("dried", dried);
        nbt.setInteger("age", age);
        nbt.setInteger("age_max", ageMax);
    }

    public void writeToNBT(NBTTagCompound nbt, String name) {
        final NBTTagCompound target = new NBTTagCompound();
        writeToNBT(target);
        nbt.setTag(name, target);
    }

    public void readFromNBT(NBTTagCompound nbt) {
        this.cheese = CheeseIO.loadFromNBT(nbt);
        this.dried = nbt.getBoolean("dried");
        this.age = nbt.getInteger("age");
    }

    public void readFromNBT(NBTTagCompound nbt, String name) {
        if (nbt.hasKey(name)) {
            readFromNBT(nbt.getCompoundTag(name));
        } else {
            // WARN
        }
    }

    @Override
    public boolean readFromStream(ByteBuf stream) {
        this.cheese = CheeseIO.loadFromStream(stream);
        this.dried = stream.readBoolean();
        this.age = stream.readInt();
        return false;
    }

    @Override
    public boolean writeToStream(ByteBuf stream) {
        CheeseIO.writeToStream(stream, cheese);
        stream.writeBoolean(dried);
        stream.writeInt(age);
        return false;
    }

    public void update() {
        if (dried) {
            if (age != ageMax) {
                this.age = ageMax;
                this.needClientUpdate = true;
            }
        } else {
            if (this.age < ageMax) {
                this.age += 1;
            } else {
                this.dried = true;
                this.needClientUpdate = true;
            }
        }
    }
}
