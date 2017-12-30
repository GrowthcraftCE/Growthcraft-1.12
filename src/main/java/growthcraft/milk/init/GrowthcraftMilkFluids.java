package growthcraft.milk.init;

import growthcraft.milk.Reference;
import growthcraft.milk.blocks.fluids.*;
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
    public static FluidButterMilk fluidButterMilk;
    public static FluidCream fluidCream;
    public static FluidMilkCurds fluidMilkCurds;
    public static FluidSkimMilk fluidSkimMilk;
    public static FluidWhey fluidWhey;
    public static FluidPasteurizedMilk fluidPasteurizedMilk;

    public static void init() {
        fluidMilk = new FluidMilk("fluid_milk");
        fluidRennet = new FluidRennet("fluid_rennet");
        fluidButterMilk = new FluidButterMilk("fluid_butter_milk");
        fluidCream = new FluidCream("fluid_cream");
        fluidMilkCurds = new FluidMilkCurds("fluid_milk_curds");
        fluidSkimMilk = new FluidSkimMilk("fluid_skim_milk");
        fluidWhey = new FluidWhey("fluid_whey");
        fluidPasteurizedMilk = new FluidPasteurizedMilk("fluid_pasteurized_milk");
    }

    public static void register() {

        FluidRegistry.registerFluid(fluidMilk);
        FluidRegistry.addBucketForFluid(fluidMilk);

        FluidRegistry.registerFluid(fluidRennet);
        FluidRegistry.addBucketForFluid(fluidRennet);

        FluidRegistry.registerFluid(fluidButterMilk);
        FluidRegistry.addBucketForFluid(fluidButterMilk);

        FluidRegistry.registerFluid(fluidCream);
        FluidRegistry.addBucketForFluid(fluidCream);

        FluidRegistry.registerFluid(fluidMilkCurds);
        FluidRegistry.addBucketForFluid(fluidMilkCurds);

        FluidRegistry.registerFluid(fluidSkimMilk);
        FluidRegistry.addBucketForFluid(fluidSkimMilk);

        FluidRegistry.registerFluid(fluidWhey);
        FluidRegistry.addBucketForFluid(fluidWhey);

        FluidRegistry.registerFluid(fluidPasteurizedMilk);
        FluidRegistry.addBucketForFluid(fluidPasteurizedMilk);

    }

    public static void  registerRenders() {
        registerRender(fluidMilk);
        registerRender(fluidRennet);
        registerRender(fluidButterMilk);
        registerRender(fluidCream);
        registerRender(fluidMilkCurds);
        registerRender(fluidSkimMilk);
        registerRender(fluidWhey);
        registerRender(fluidPasteurizedMilk);
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
