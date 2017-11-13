package growthcraft.milk.init;

import growthcraft.milk.Reference;
import growthcraft.milk.blocks.fluids.FluidMilk;
import growthcraft.milk.blocks.fluids.FluidRennet;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

public class GrowthcraftMilkFluids {

    public static FluidMilk fluidMilk;
    public static FluidRennet fluidRennet;

    public static void init() {
        fluidMilk = new FluidMilk("fluid_milk");
        fluidRennet = new FluidRennet("fluid_rennet");
    }

    public static void register() {

        FluidRegistry.registerFluid(fluidMilk);
        FluidRegistry.addBucketForFluid(fluidMilk);

        FluidRegistry.registerFluid(fluidRennet);
        FluidRegistry.addBucketForFluid(fluidRennet);

    }

    public static void  registerRenders() {
        registerRender(fluidMilk);
        registerRender(fluidRennet);
    }

    public static void  registerRender(Fluid fluid) {

        Item item = Item.getItemFromBlock(fluid.getBlock());

        // subString 6 because "fluid." is 6.
        ModelResourceLocation modelResourceLocation = new ModelResourceLocation(Reference.MODID + ":" + fluid.getUnlocalizedName().substring(6), fluid.getName());

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
