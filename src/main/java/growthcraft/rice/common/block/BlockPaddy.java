package growthcraft.rice.common.block;

import growthcraft.core.shared.block.BlockPaddyBase;
import growthcraft.rice.shared.Reference;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidRegistry;

import java.util.Random;

public class BlockPaddy extends BlockPaddyBase {

    // TODO: Implement config for max field.

    public BlockPaddy(String unlocalizedName) {
        super(Material.GROUND);
        this.setUnlocalizedName(unlocalizedName);
        this.setRegistryName(Reference.MODID, unlocalizedName);
        this.setHardness(0.5F);
        this.setSoundType(SoundType.GROUND);
    }

    @Override
    public void fillWithRain(World worldIn, BlockPos pos) {
        super.fillWithRain(worldIn, pos);
    }

    @Override
    public int quantityDropped(Random random) {
        return 1;
    }

    /**
     * Override the default onBlockActivation.
     *
     * @param worldIn
     * @param pos
     * @param state
     * @param playerIn
     * @param hand
     * @param facing
     * @param hitX
     * @param hitY
     * @param hitZ
     * @return
     */
    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if( !worldIn.isRemote ) {
            ItemStack itemStack = playerIn.getHeldItem(hand);
            Item item = itemStack.getItem();
            if(item == Items.WATER_BUCKET) {
                if(this.fillFluidTank(FluidRegistry.getFluidStack("water", 1000))) {
                    playerIn.getHeldItem(hand).shrink(1);
                    playerIn.addItemStackToInventory(new ItemStack(Items.BUCKET, 1));
                    // TODO: make a splashing sound.
                    return true;
                }
            }
        }
        return super.onBlockActivated(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ);
    }
}
