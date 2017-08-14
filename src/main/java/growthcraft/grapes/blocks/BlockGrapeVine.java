package growthcraft.grapes.blocks;

import growthcraft.core.utils.GrowthcraftLogger;
import growthcraft.grapes.Reference;
import growthcraft.grapes.init.GrowthcraftGrapesItems;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.IGrowable;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

public class BlockGrapeVine extends BlockCrops implements IGrowable {

    public static final PropertyInteger AGE = PropertyInteger.create("age", 0, 7);

    private static final AxisAlignedBB[] BOUNDING_BOXES = new AxisAlignedBB[]{
            new AxisAlignedBB(0.0625 * 6, 0.0625 * 0, 0.0625 * 6, 0.0625 * 10, 0.0625 * 4, 0.0625 * 10),
            new AxisAlignedBB(0.0625 * 6, 0.0625 * 0, 0.0625 * 6, 0.0625 * 10, 0.0625 * 14, 0.0625 * 10),
            new AxisAlignedBB(0.0625 * 6, 0.0625 * 0, 0.0625 * 6, 0.0625 * 10, 0.0625 * 16, 0.0625 * 10)
    };

    private int maxAge;

    public BlockGrapeVine(String unlocalizedName) {
        this.setUnlocalizedName(unlocalizedName);
        this.setRegistryName(new ResourceLocation(Reference.MODID, unlocalizedName));
        this.setMaxAge(7);
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
        super.addInformation(stack, playerIn, tooltip, advanced);
        tooltip.add(TextFormatting.BLUE + I18n.format(this.getUnlocalizedName() + ".tooltip"));
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        if ( this.getAge(state) <= 3 ) {
            return BOUNDING_BOXES[0];
        } else if ( this.getAge(state) <= 6) {
            return BOUNDING_BOXES[1];
        }
        return BOUNDING_BOXES[2];
    }

    @Override
    protected Item getSeed() {
        return new ItemStack(GrowthcraftGrapesItems.grape_seed, 1, 0).getItem();
    }

    @Override
    protected Item getCrop() {
        return new ItemStack(GrowthcraftGrapesItems.grape_seed, 1, 0).getItem();
    }

    @Override
    public int getMaxAge() {
        return this.maxAge;
    }

    public void setMaxAge(int maxAge) {
        this.maxAge = maxAge;
    }

    @Override
    public boolean canGrow(World worldIn, BlockPos pos, IBlockState state, boolean isClient) {
        // The closest that grape vine can be planted together would be pos + 3.
        return super.canGrow(worldIn, pos, state, isClient);
    }

    @Override
    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
        super.updateTick(worldIn, pos, state, rand);
        if (this.getAge(state) == 7) {
            GrowthcraftLogger.getLogger().info("updateTick = Age = 7");
            this.generateGrapeVine(worldIn, pos, state);
        }
    }

    @Override
    public void grow(World worldIn, BlockPos pos, IBlockState state) {
        super.grow(worldIn, pos, state);
        if (this.getAge(state) == 7) {
            this.generateGrapeVine(worldIn, pos, state);
        }
    }

    /**
     * Generate the vine head up until you find a rope.
     * @param worldIn
     * @param pos
     * @param state
     */
    public void generateGrapeVine(World worldIn, BlockPos pos, IBlockState state) {
        // Locate the rope block to bind to.

        GrowthcraftLogger.getLogger().info("generateGrapeVine HAS BEEN CALLED!!!!");
    }


}
