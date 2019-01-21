package growthcraft.apples.common.block;

import growthcraft.apples.shared.Reference;
import growthcraft.apples.shared.init.GrowthcraftApplesBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockPlanks.EnumType;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.IGrowable;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;

import javax.annotation.Nullable;

import java.util.List;
import java.util.Random;

public class BlockAppleLeaves extends BlockLeaves implements IGrowable {
    private static final AxisAlignedBB BOUNDING_BOX =
            new AxisAlignedBB(0.0625 * 0, 0.0625 * 0, 0.0625 * 0, 0.0625 * 16, 0.0625 * 16, 0.0625 * 16);

    public BlockAppleLeaves(String unlocalizedName) {
        this.setUnlocalizedName(unlocalizedName);
        this.setRegistryName(new ResourceLocation(Reference.MODID, unlocalizedName));
        this.setCreativeTab(null);	// Will be initialized in Init class
        this.setDefaultState(this.blockState.getBaseState().withProperty(CHECK_DECAY, Boolean.valueOf(true)).withProperty(DECAYABLE, Boolean.valueOf(true)));
        Blocks.FIRE.setFireInfo(this, 5, 20);
    }

/*    @SuppressWarnings("deprecation")
    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    } */

/*    @SuppressWarnings("deprecation")
    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    } */

/*    @Override
    public BlockRenderLayer getBlockLayer() {
        return BlockRenderLayer.CUTOUT_MIPPED;
    } */

/*    @Override
    public boolean recolorBlock(World world, BlockPos pos, EnumFacing side, EnumDyeColor color) {
        return super.recolorBlock(world, pos, side, EnumDyeColor.GREEN);
    } */

/*    @SuppressWarnings("deprecation")
    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return BOUNDING_BOX;
    } */

/*    @SuppressWarnings("deprecation")
    @Nullable
    @Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
        return BOUNDING_BOX;
    } */

/*    @Override
    public boolean isPassable(IBlockAccess worldIn, BlockPos pos) {
        return false;
    } */
    
/*
    @Override
    public boolean canBlockStay(World worldIn, BlockPos pos, IBlockState state) {
        return true;
    }
*/
    
    ///////
    // States
    ///////
    
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[] {CHECK_DECAY, DECAYABLE});
    }
    
    /**
     * Convert the BlockState into the correct metadata value
     */
    public int getMetaFromState(IBlockState state)
    {
        int i = 0;

        if (!((Boolean)state.getValue(DECAYABLE)).booleanValue())
        {
            i |= 4;
        }

        if (((Boolean)state.getValue(CHECK_DECAY)).booleanValue())
        {
            i |= 8;
        }

        return i;
    }
    
    /**
     * Convert the given metadata into a BlockState for this Block
     */
    public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState().withProperty(DECAYABLE, Boolean.valueOf((meta & 4) == 0)).withProperty(CHECK_DECAY, Boolean.valueOf((meta & 8) > 0));
    }
    
    ///////
    // Growing apples
    ///////
    
    @Override
    public boolean canGrow(World worldIn, BlockPos pos, IBlockState state, boolean isClient) {
        Block block = worldIn.getBlockState(pos.down()).getBlock();
        return block instanceof BlockAir;
    }

    @Override
    public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, IBlockState state) {
        return false;
    }

    @Override
    public void grow(World worldIn, Random rand, BlockPos pos, IBlockState state) {
        if ( worldIn.getBlockState(pos.down()).getBlock() instanceof BlockAir ){
            worldIn.setBlockState(pos.down(), GrowthcraftApplesBlocks.blockApple.getDefaultState());
        }
    }

    @Override
    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
        super.updateTick(worldIn, pos, state, rand);

    	if ( !worldIn.isRemote ) {
            // check the light level and pick a randomness for growth.
            if ( worldIn.getLightFromNeighbors(pos.up()) >= 9 ) {
            	if( ForgeHooks.onCropsGrowPre(worldIn, pos, state, rand.nextInt(7) == 0) ) {
    				grow(worldIn, rand, pos, state);
    				ForgeHooks.onCropsGrowPost(worldIn, pos, state, worldIn.getBlockState(pos));
    			}
            }
        }
    }

    @Override
    public void onBlockDestroyedByPlayer(World worldIn, BlockPos pos, IBlockState state) {
        super.onBlockDestroyedByPlayer(worldIn, pos, state);
        Block blockDown = worldIn.getBlockState(pos.down()).getBlock();
        if ( blockDown instanceof BlockApple) {
            worldIn.destroyBlock(pos.down(), false);
        }
    }

	@Override
	public EnumType getWoodType(int meta) {
		// NOTE: Is only used by ItemLeaves. 
		return null;
	}
    
    ////
    // DROPS
    ////
    
	@Override
	protected int getSaplingDropChance(IBlockState state) {
		return 20;
	}
    
	@Override
	protected void dropApple(World worldIn, BlockPos pos, IBlockState state, int chance) {
		if (worldIn.rand.nextInt(chance) == 0 ) {
			spawnAsEntity(worldIn, pos, new ItemStack(Items.APPLE));
		}
	}
    
    @Override
    public int quantityDroppedWithBonus(int fortune, Random random) {
        return MathHelper.clamp(this.quantityDropped(random) + random.nextInt(fortune + 1), 0, 2);
    }

    @Override
    public int quantityDropped(Random random) {
        return random.nextInt(5) == 0 ? 1 : 0;
    }
    
    @Override
    public int damageDropped(IBlockState state) {
    	return 0;
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return GrowthcraftApplesBlocks.blockAppleSapling.getItem();
    }
    
	@Override
	public List<ItemStack> onSheared(ItemStack item, IBlockAccess world, BlockPos pos, int fortune) {
		return NonNullList.withSize(1, new ItemStack(this, 1, 0));
	}
	
	@Override
	protected ItemStack getSilkTouchDrop(IBlockState state) {
		return new ItemStack(Item.getItemFromBlock(this), 1, 0);
	}
	

	@Override
    public void harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state, @Nullable TileEntity te, ItemStack stack)
    {
		// SYNC: Copied from net.minecraft.block.BlockOldLeaf.harvestBlock() . Keep in sync with it on changes.
		
        if (!worldIn.isRemote && stack.getItem() == Items.SHEARS)
        {
            player.addStat(StatList.getBlockStats(this));
        }
        else
        {
            super.harvestBlock(worldIn, player, pos, state, te, stack);
        }
    }
}
