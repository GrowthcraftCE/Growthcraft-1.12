package growthcraft.core.shared.io.nbt;

import java.util.List;

import growthcraft.core.shared.fluids.UnitFormatter;
import growthcraft.core.shared.io.ConstID;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.fluids.FluidStack;

public class TagFormatterFluidHandler {
    // REVISE_ME 0: move to nbt

    public static final TagFormatterFluidHandler INSTANCE = new TagFormatterFluidHandler();

    @SuppressWarnings("deprecation")
    public List<String> format(List<String> list, NBTTagCompound tag) {
        final int tankCount = tag.getInteger("tank_count");
        final NBTTagList tanks = tag.getTagList("tanks", NBTType.COMPOUND.id);
        final NBTTagList tankNames = tag.hasKey("tank_names") ? tag.getTagList("tank_names", NBTType.STRING.id) : null;
        for (int i = 0; i < tankCount; ++i) {
            final NBTTagCompound tankTag = tanks.getCompoundTagAt(i);
            final String name = tankNames != null ? tankNames.getStringTagAt(i) : null;
            String content = "";

            if (name != null && name.length() > 0) {
                content += I18n.translateToLocal(name) + " ";
            } else {
                if (tankCount > 1) {
                    // If the FluidHandler has multiple tanks, then prefix them as such,
                    // otherwise, display their content like normal
                    content += TextFormatting.GRAY + I18n.translateToLocalFormatted("format.tank_id", tankTag.getInteger("tank_id") + 1) + " ";
                }
            }

            final String fluidIDname = tankTag.getString("fluid_IDname");
            if (fluidIDname != null && !fluidIDname.equals(ConstID.NO_FLUID)) {
                final FluidStack fluidStack = FluidStack.loadFluidStackFromNBT(tankTag.getCompoundTag("fluid"));
                final String fluidName = UnitFormatter.fluidNameForContainer(fluidStack);
                content = content +
                        UnitFormatter.fractionNum(fluidStack.amount, tankTag.getInteger("capacity")) +
                        TextFormatting.GRAY + " " + I18n.translateToLocalFormatted("format.tank.content_suffix", fluidName);
            } else {
                content = content + UnitFormatter.noFluid();
            }
            list.add(content);
        }
        return list;
    }

}
