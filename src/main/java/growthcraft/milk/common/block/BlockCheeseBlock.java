package growthcraft.milk.common.block;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import growthcraft.core.common.block.GrowthcraftBlockContainer;
import growthcraft.core.utils.ItemUtils;
import growthcraft.milk.Reference;
import growthcraft.milk.common.item.ItemBlockCheeseBlock;
import growthcraft.milk.common.tileentity.TileEntityCheeseBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockCheeseBlock extends GrowthcraftBlockContainer {
    private static final AxisAlignedBB BOUNDING_BOX = new AxisAlignedBB(
            0.0625 * 0, 0.0625 * 0, 0.0625 * 0,
            0.0625 * 16, 0.0625 * 8, 0.0625 * 16);
	
	public BlockCheeseBlock(String unlocalizedName) {
		super(Material.CAKE);
        this.setUnlocalizedName(unlocalizedName);
        this.setRegistryName(new ResourceLocation(Reference.MODID, unlocalizedName));
		this.setHardness(0.5F);
//		setStepSound(soundTypeCloth);
		setTileEntityType(TileEntityCheeseBlock.class);
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
    public boolean isFullBlock(IBlockState state) {
        return false;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

	@Override
	protected boolean shouldRestoreBlockState(World world, BlockPos pos, ItemStack stack)
	{
		return true;
	}

	@Override
	protected boolean shouldDropTileStack(World world, BlockPos pos, IBlockState state, int fortune)
	{
		return false;
	}

	@Override
	protected ItemStack createHarvestedBlockItemStack(World world, EntityPlayer player, BlockPos pos, int meta)
	{
		final TileEntityCheeseBlock te = getTileEntity(world, pos);
		if (te != null)
		{
			return te.asItemStack();
		}
		return new ItemStack(this, 1, meta);
	}

	@Override
	protected void getTileItemStackDrops(List<ItemStack> ret, World world, BlockPos pos, IBlockState state, int fortune)
	{
		final TileEntityCheeseBlock te = getTileEntity(world, pos);
		if (te != null)
		{
			ret.add(te.asItemStack());
		}
		else
		{
			super.getTileItemStackDrops(ret, world, pos, state, fortune);
		}
	}
	
	@Override
	protected boolean shouldScatterInventoryOnBreak(World world, BlockPos pos)
	{
		return true;
	}
	
	@Override
	protected void scatterInventory(World world, BlockPos pos, Block block)
	{
		final TileEntityCheeseBlock te = getTileEntity(world, pos);
		if (te != null)
		{
			final List<ItemStack> drops = new ArrayList<ItemStack>();
			te.populateDrops(drops);
			for (ItemStack stack : drops)
			{
				ItemUtils.spawnItemStack(world, pos.getX(), pos.getY(), pos.getZ(), stack, rand);
			}
		}
	}
	
	@Override
	public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player)
	{
		final TileEntityCheeseBlock teCheeseBlock = getTileEntity(world, pos);
		if (teCheeseBlock != null)
		{
			return teCheeseBlock.asItemStack();
		}
		return super.getPickBlock(state, target, world, pos, player);
	}

	@Override
	public ArrayList<ItemStack> getDrops(World world, BlockPos pos, IBlockState state, int fortune)
	{
		final ArrayList<ItemStack> ret = new ArrayList<ItemStack>();
		final TileEntityCheeseBlock te = getTileEntity(world, pos);
		if (te != null)
		{
			te.populateDrops(ret);
		}
		return ret;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	@SuppressWarnings({"rawtypes", "unchecked"})
	public void getSubBlocks(Item itemIn, CreativeTabs tab, NonNullList<ItemStack> list)
	{
		if (itemIn instanceof ItemBlockCheeseBlock)
		{
			final ItemBlockCheeseBlock ib = (ItemBlockCheeseBlock)itemIn;
			for (EnumCheeseType cheese : EnumCheeseType.VALUES)
			{
				if (cheese.hasBlock())
				{
					final ItemStack stack = new ItemStack(itemIn, 1, cheese.getMetaForInitialStage());
					// This causes the NBT data to refresh
					ib.getTileTagCompound(stack);
					list.add(stack);
				}
			}
		}
	}
	
	@Override
	public int damageDropped(IBlockState state)
	{
		return state.getBlock().getMetaFromState(state);
	}
}
