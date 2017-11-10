package growthcraft.grapes.blocks;

import growthcraft.core.blocks.BlockRopeFence;
import growthcraft.grapes.Reference;
import growthcraft.grapes.init.GrowthcraftGrapesBlocks;
import net.minecraft.block.*;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nullable;
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
        this.setTickRandomly(true);
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

    @Nullable
    @Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
        if ( this.getAge(state) <= 3 ) {
            return BOUNDING_BOXES[0];
        } else if ( this.getAge(state) <= 6) {
            return BOUNDING_BOXES[1];
        }
        return BOUNDING_BOXES[2];
    }

    @Override
    protected Item getSeed() {
        // Only the BlockGrapeVineBush should return a seed.
        return null;
    }

    @Override
    protected Item getCrop() {
        // Only the BlockGrapeVineBush should return a seed.
        return null;
    }

    @Override
    public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
        return super.getDrops(world, pos, state, fortune);
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

    @Override
    public boolean canBlockStay(World worldIn, BlockPos pos, IBlockState state) {
        return true;
    }

    /**
     * Generate the vine head up until you find a rope.
     * @param worldIn
     * @param pos
     * @param state
     */
    public void generateGrapeVine(World worldIn, BlockPos pos, IBlockState state) {
        // Locate the rope block to bind to.
        Block blockUpOne = worldIn.getBlockState(pos.up(1)).getBlock();

        if ( blockUpOne instanceof BlockRopeFence ) {
            // Then we have a short vine.
            // Spawn in a BlockGrapeVineBush
            worldIn.setBlockState(pos.up(), GrowthcraftGrapesBlocks.grape_vine_bush.getDefaultState());
        } else if ( blockUpOne instanceof BlockAir) {
            // Then we have a normal vine.
            // Spawn in a GrapeVine at pos.up()
            worldIn.setBlockState(pos.up(), state);
        } else if ( blockUpOne instanceof BlockGrapeVineBush || blockUpOne instanceof BlockGrapeVine ) {
            // Then don't do anything.
        }

        // Fixes the Villager harvest issue. Grape veins are mature plants and once planted
        // do not need tilled land to continue to grow.
        Block blockDownOne = worldIn.getBlockState(pos.down()).getBlock();
        if ( blockDownOne instanceof BlockFarmland) {
            worldIn.setBlockState(pos.down(), Blocks.DIRT.getDefaultState());
        }

    }

}
