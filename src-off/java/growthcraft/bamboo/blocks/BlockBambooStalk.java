package growthcraft.bamboo.blocks;

import growthcraft.bamboo.Reference;
import growthcraft.bamboo.init.GrowthcraftBambooBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockDirt;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class BlockBambooStalk extends Block {

    private static final AxisAlignedBB BOUNDING_BOX = new AxisAlignedBB(
            0.0625 * 4, 0.0625 * 0, 0.0625 * 4,
            0.0625 * 12, 0.0625 * 16, 0.0625 * 12);

    public BlockBambooStalk(String unlocalizedName) {
        super(Material.WOOD);
        this.setUnlocalizedName(unlocalizedName);
        this.setRegistryName(new ResourceLocation(Reference.MODID, unlocalizedName));
        this.setResistance(5.0F);
        this.setHardness(2.0F);
        this.setHarvestLevel("axe", 1);
        this.setSoundType(SoundType.WOOD);
        this.setTickRandomly(true);
        Blocks.FIRE.setFireInfo(this, 5, 20);

    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return BOUNDING_BOX;
    }

    @Override
    public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable Entity entityIn, boolean p_185477_7_) {
        addCollisionBoxToList(pos, entityBox, collidingBoxes, BOUNDING_BOX);
    }

    @Override
    public void randomTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
        super.randomTick(worldIn, pos, state, rand);

        Block blockDown = worldIn.getBlockState(pos.down()).getBlock();

        if ( !(blockDown instanceof BlockAir) && !(blockDown instanceof BlockBambooStalk) ) {
            // TODO: Add Bamboo Spread Chance to GrowthcraftBamboo GrowthcraftBambooConfig
            int j = rand.nextInt(10);
            if ( j == 1) {
                // Then spawn a BambooShoot if the random block is air and it is on top of a non-block air.
                BlockPos posSouthWest = new BlockPos(pos.south(2).west(2));

                // TODO: Add Bamboo Spread Max Area to GrowthcraftBamboo GrowthcraftBambooConfig
                int offsetNorth = rand.nextInt(2);
                int offsetEast = rand.nextInt(2);

                // If the random block is Air and the below block is Dirt
                if ( worldIn.getBlockState(posSouthWest.north(offsetNorth).east(offsetEast)).getBlock() instanceof BlockAir
                        && worldIn.getBlockState(posSouthWest.north(offsetNorth).east(offsetEast).down()).getBlock() instanceof BlockDirt) {
                    worldIn.setBlockState(posSouthWest.north(offsetNorth).east(offsetEast),
                            GrowthcraftBambooBlocks.bambooShoot.getDefaultState().withProperty(BlockBambooShoot.STAGE, 0));
                }
            }
        }
    }

}
