package growthcraft.apples.common.block;

import growthcraft.apples.shared.Reference;
import growthcraft.apples.shared.init.GrowthcraftApplesItems;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class BlockAppleDoor extends BlockDoor {

    public BlockAppleDoor(String unlocalizedName) {
        super(Material.WOOD);
        this.setTranslationKey(unlocalizedName);
        this.setRegistryName(new ResourceLocation(Reference.MODID, unlocalizedName));
        this.setSoundType(SoundType.WOOD);
        this.disableStats();
        this.setHardness(3F);
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return GrowthcraftApplesItems.itemAppleDoor.getItem();
    }

    /**
     *      
     *
     * @deprecated (when, why, refactoring advice...)      
     */
    @Override
    @Deprecated
    public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state) {
        return GrowthcraftApplesItems.itemAppleDoor.asStack(1);
    }
}
