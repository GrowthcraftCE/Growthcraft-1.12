package growthcraft.core.shared.config.schema;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Nonnull;

import growthcraft.core.shared.CoreRegistry;
import growthcraft.core.shared.definition.IMultiFluidStacks;
import growthcraft.core.shared.fluids.FluidTag;
import growthcraft.core.shared.fluids.FluidTest;
import growthcraft.core.shared.fluids.GrowthcraftFluidUtils;
import growthcraft.core.shared.fluids.MultiFluidStacks;
import growthcraft.core.shared.fluids.TaggedFluidStacks;
import growthcraft.core.shared.utils.StringUtils;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

public class MultiFluidStackSchema implements ICommentable, IValidatable, IMultiFluidStacks {
    public String name;
    public List<String> names = new ArrayList<String>();
    public List<String> inclusion_tags = new ArrayList<String>();
    public List<String> exclusion_tags = new ArrayList<String>();
    public String comment = "";
    public int amount;

    public MultiFluidStackSchema(@Nonnull IMultiFluidStacks fluidStacks) {
        if (fluidStacks instanceof TaggedFluidStacks) {
            final TaggedFluidStacks taggedStack = (TaggedFluidStacks) fluidStacks;
            inclusion_tags.addAll(taggedStack.getTags());
            exclusion_tags.addAll(taggedStack.getExclusionTags());
        } else if (fluidStacks instanceof MultiFluidStacks) {
            names.addAll(((MultiFluidStacks) fluidStacks).getNames());
        } else {
            throw new IllegalArgumentException("Expected a TaggedFluidStacks or a MultiFluidStacks");
        }
        this.amount = fluidStacks.getAmount();
    }

    public MultiFluidStackSchema(@Nonnull FluidStack fluidStack) {
        this.name = fluidStack.getFluid().getName();
        this.amount = fluidStack.amount;
    }

    public MultiFluidStackSchema() {
    }

    @Override
    public void setComment(String comm) {
        this.comment = comm;
    }

    @Override
    public String getComment() {
        return comment;
    }

    private List<FluidTag> expandTagNames(@Nonnull List<String> tagNames) {
        return CoreRegistry.instance().fluidTags().expandTagNames(tagNames);
    }

    public List<FluidTag> expandInclusionTags() {
        return expandTagNames(inclusion_tags);
    }

    public List<FluidTag> expandExclusionTags() {
        return expandTagNames(exclusion_tags);
    }

    public Collection<Fluid> getFluidsByTags() {
        final Set<Fluid> result = new HashSet<Fluid>();
        final Collection<Fluid> fluids = CoreRegistry.instance().fluidDictionary().getFluidsByTags(expandInclusionTags());
        final Collection<Fluid> exfluids = CoreRegistry.instance().fluidDictionary().getFluidsByTags(expandExclusionTags());
        result.addAll(fluids);
        result.removeAll(exfluids);
        return result;
    }

    public Collection<Fluid> getFluidsByNames() {
        final Set<Fluid> result = new HashSet<Fluid>();
        if (name != null) {
            final Fluid fluid = FluidRegistry.getFluid(name);
            if (fluid != null) {
                result.add(fluid);
            }
        }
        for (String fluidName : names) {
            final Fluid fluid = FluidRegistry.getFluid(fluidName);
            if (fluid != null) {
                result.add(fluid);
            }
        }
        return result;
    }

    public Collection<Fluid> getFluids() {
        final Set<Fluid> result = new HashSet<Fluid>();
        result.addAll(getFluidsByTags());
        result.addAll(getFluidsByNames());
        return result;
    }

    @Override
    public int getAmount() {
        return amount;
    }

    @Override
    public List<FluidStack> getFluidStacks() {
        final List<FluidStack> stacks = new ArrayList<FluidStack>();
        for (Fluid fluid : getFluids()) {
            stacks.add(new FluidStack(fluid, amount));
        }
        return stacks;
    }

    public List<IMultiFluidStacks> getMultiFluidStacks() {
        final List<IMultiFluidStacks> result = new ArrayList<IMultiFluidStacks>();
        result.add(new TaggedFluidStacks(amount, inclusion_tags, exclusion_tags));
        final List<FluidStack> fluidStacks = new ArrayList<FluidStack>();
        for (Fluid fluid : getFluidsByNames()) {
            fluidStacks.add(new FluidStack(fluid, amount));
        }
        result.add(new MultiFluidStacks(fluidStacks));
        return result;
    }

    @Override
    public boolean containsFluid(Fluid expectedFluid) {
        if (FluidTest.isValid(expectedFluid)) {
            for (Fluid fluid : getFluids()) {
                if (fluid == expectedFluid) return true;
            }
        }
        return false;
    }

    @Override
    public boolean containsFluidStack(FluidStack stack) {
        if (FluidTest.isValid(stack)) {
            final Fluid expected = stack.getFluid();
            return containsFluid(expected);
        }
        return false;
    }

    @Override
    public boolean isValid() {
        return !getFluids().isEmpty();
    }

    @Override
    public boolean isInvalid() {
        return !isValid();
    }

    @Override
    public String toString() {
        return String.format("Schema<MultiFluidStack>(comment: '%s', name: '%s', names: %s, inclusion_tags: %s, exclusion_tags: %s, amount: %d)",
                StringUtils.inspect(comment),
                StringUtils.inspect(name),
                names, inclusion_tags, exclusion_tags, amount);
    }

    public static MultiFluidStackSchema newWithTags(int amount, String... tags) {
        final MultiFluidStackSchema schema = new MultiFluidStackSchema();
        for (String tag : tags) {
            schema.inclusion_tags.add(tag);
        }
        schema.amount = amount;
        return schema;
    }

    @Override
    public List<ItemStack> getItemStacks() {
        return GrowthcraftFluidUtils.getFluidContainers(getFluidStacks());
    }
}
