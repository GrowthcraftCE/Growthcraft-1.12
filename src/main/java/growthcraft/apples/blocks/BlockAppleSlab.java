package growthcraft.apples.blocks;

import growthcraft.apples.Reference;
import growthcraft.apples.init.GrowthcraftApplesBlocks;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import java.util.Random;

public abstract class BlockAppleSlab extends BlockSlab {

    public BlockAppleSlab(String unlocalizedName) {
        super(Material.WOOD, MapColor.WOOD);
        this.setUnlocalizedName(unlocalizedName);
        this.setRegistryName(new ResourceLocation(Reference.MODID, unlocalizedName));
        this.setSoundType(SoundType.WOOD);
        this.setResistance(5.0F);
        this.setHardness(2.0F);
        this.setHarvestLevel("axe", 1);
        this.useNeighborBrightness = true;
        Blocks.FIRE.setFireInfo(this, 5, 20);

        IBlockState state = this.blockState.getBaseState();
        if(!this.isDouble()) {
            state = state.withProperty(HALF, EnumBlockHalf.BOTTOM);
        }

        this.setDefaultState(state);
    }

    @Override
    public String getUnlocalizedName(int meta) {
        return this.getUnlocalizedName();
    }

    @Override
    public boolean isDouble() {
        return false;
    }

    @Override
    public IProperty<?> getVariantProperty() {
        return HALF;
    }

    @Override
    public Comparable<?> getTypeForItem(ItemStack stack) {
        return EnumBlockHalf.BOTTOM;
    }

    @Override
    public int damageDropped(IBlockState state) {
        return 0;
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        if(!this.isDouble()) {
            return this.getDefaultState().withProperty(HALF, EnumBlockHalf.values()[meta % EnumBlockHalf.values().length]);
        }
        return this.getDefaultState();
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        if(this.isDouble()) {
            return 0;
        }
        return ((EnumBlockHalf)state.getValue(HALF)).ordinal() + 1;

    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return Item.getItemFromBlock(GrowthcraftApplesBlocks.blockAppleSlabHalf);
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[]{HALF});
    }
}
