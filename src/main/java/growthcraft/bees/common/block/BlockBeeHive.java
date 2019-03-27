package growthcraft.bees.common.block;

import growthcraft.bees.shared.Reference;
import growthcraft.bees.shared.init.GrowthcraftBeesItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class BlockBeeHive extends Block {
    public BlockBeeHive(String unlocalizedName) {
        super(Material.WOOD);
        this.setUnlocalizedName(unlocalizedName);
        this.setRegistryName(new ResourceLocation(Reference.MODID, unlocalizedName));
        this.setHarvestLevel("axe", 1);
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return GrowthcraftBeesItems.bee.getItem();
    }

    @Override
    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
        super.updateTick(worldIn, pos, state, rand);
        IBlockState upState = worldIn.getBlockState(pos.up());
        if (upState.getBlock() instanceof BlockAir) {
            this.dropBlockAsItem(worldIn, pos, state, 0);
        }
    }

}
