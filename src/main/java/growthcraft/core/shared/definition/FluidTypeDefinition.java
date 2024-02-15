package growthcraft.core.shared.definition;

import growthcraft.core.shared.legacy.FluidContainerRegistry;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;

public class FluidTypeDefinition<T extends Fluid> implements IFluidStackFactory {
    protected T fluid;

    public FluidTypeDefinition(@Nonnull T obj) {
        this.fluid = obj;
    }

    @Nonnull
    public T getFluid() {
        return fluid;
    }

    @Nonnull
    public FluidStack asFluidStack(int size) {
        return new FluidStack(getFluid(), size);
    }

    @Nonnull
    public FluidStack asFluidStack() {
        return asFluidStack(1);
    }

    public boolean equals(Fluid other) {
        if (other == null) return false;
        return getFluid() == other;
    }

    public void register() {
        FluidRegistry.registerFluid(fluid);
    }

    public void registerBucketItem() {
        FluidContainerRegistry.addBucketForFluid(fluid);
    }

    @SideOnly(Side.CLIENT)
    public void registerRenderer() {
        Item item = Item.getItemFromBlock(fluid.getBlock());

        // subString 6 because "fluid." is 6.
        String modID = fluid.getBlock().getRegistryName().getNamespace();
        ModelResourceLocation modelResourceLocation = new ModelResourceLocation(modID + ":" + fluid.getUnlocalizedName().substring(6), fluid.getName());

        // Set the custom mesh definition for the item using a lambda
        ModelLoader.setCustomMeshDefinition(item, stack -> modelResourceLocation);

        // Set the custom state mapper for the fluid material
        ModelLoader.setCustomStateMapper(fluid.getBlock(), new StateMapperBase() {
            @Override
            protected ModelResourceLocation getModelResourceLocation(IBlockState state) {
                return modelResourceLocation;
            }
        });
    }
}
