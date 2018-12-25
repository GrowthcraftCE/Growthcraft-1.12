package growthcraft.grapes.common.blocks;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import growthcraft.cellar.shared.init.GrowthcraftCellarItems.EnumYeast;
import growthcraft.core.shared.block.GrowthcraftBlockBase;
import growthcraft.core.shared.item.ItemUtils;
import growthcraft.grapes.common.utils.GrapeTypeUtils;
import growthcraft.grapes.shared.config.GrowthcraftGrapesConfig;
import growthcraft.grapes.shared.definition.IGrapeType;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockGrapeFruit extends GrowthcraftBlockBase {
	private static final AxisAlignedBB BOUNDING_BOX = new AxisAlignedBB(0.1875F, 0.5F, 0.1875F, 0.8125F, 1.0F, 0.8125F); //(0.0625 * 0, 0.0625 * 0, 0.0625 * 0, 0.0625 * 16, 0.0625 * 16, 0.0625 * 16);

	private static final PropertyInteger SUBTYPE = BlockGrapeVineBase.SUBTYPE;
	
	private Random rand = new Random();
	
	protected int bayanusDropRarity = GrowthcraftGrapesConfig.bayanusDropRarity;
	protected int grapesDropMin = GrowthcraftGrapesConfig.grapesDropMin;
	protected int grapesDropMax = GrowthcraftGrapesConfig.grapesDropMax;
	
	private final IGrapeType[] grapeTypes;

    public BlockGrapeFruit(IGrapeType[] grapeTypes) {
    	super(Material.PLANTS);
		setHardness(0.0F);
		setSoundType(SoundType.PLANT);
		setDefaultState(this.getBlockState().getBaseState().withProperty(SUBTYPE, 0));
		
		this.grapeTypes = grapeTypes;
    }

	@SuppressWarnings("deprecation")
    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return BOUNDING_BOX;
    }

	@SuppressWarnings("deprecation")
    @Override
    public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable Entity entityIn, boolean p_185477_7_) {
    }

	@SuppressWarnings("deprecation")
	@Override
	public boolean isOpaqueCube(IBlockState state)
	{
		return false;
	}

	@SuppressWarnings("deprecation")
    @Override
    public boolean isFullBlock(IBlockState state) {
        return false;
    }

	@SuppressWarnings("deprecation")
    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }
	
	@SuppressWarnings("deprecation")
	@Override
	public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
		return BlockFaceShape.UNDEFINED;
	}
    
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer()
    {
        return BlockRenderLayer.CUTOUT;
    }
    
	/************
	 * TICK
	 ************/
	@Override
	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand)
	{
		if (!this.canBlockStay(worldIn, pos))
		{
			fellBlockAsItem(worldIn, pos);
		}
	}
	
	/************
	 * TRIGGERS
	 ************/
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		if (!worldIn.isRemote)
		{
			fellBlockAsItem(worldIn, pos);
		}
		return true;
	}

	@SuppressWarnings("deprecation")
	@Override
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos)
	{
		if (!this.canBlockStay(worldIn, pos))
		{
			fellBlockAsItem(worldIn, pos);
		}
	}
	
	/************
	 * CONDITIONS
	 ************/
	public boolean canBlockStay(World world, BlockPos pos)
	{
		return world.getBlockState(pos.up()).getBlock() instanceof BlockGrapeLeaves;
	}
	
	/************
	 * STUFF
	 ************/
	@Override
	@SideOnly(Side.CLIENT)
	@SuppressWarnings("deprecation")
	public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state)
	{
		return getFruitItemStackForBlock(state);
	}

	@Override
	public boolean canSilkHarvest(World world, BlockPos pos, IBlockState state, EntityPlayer player)
	{
		return false;
	}
	
	/************
	 * DROPS
	 ************/
	
	public ItemStack getFruitItemStackForBlock(IBlockState state) {
		int typeID = state.getValue(SUBTYPE);
		IGrapeType type = GrapeTypeUtils.getTypeBySubID(grapeTypes, typeID);
		if( type != null )
			return type.asStack();
		return ItemStack.EMPTY;
	}
	
	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune)
	{
		ItemStack stack = getFruitItemStackForBlock(state);
		return !ItemUtils.isEmpty(stack)? stack.getItem() : null;
	}

	@Override
	public int quantityDropped(Random random)
	{
		return grapesDropMin + random.nextInt(grapesDropMax - grapesDropMin);
	}
	
	@Override
	public int damageDropped(IBlockState state) {
		ItemStack stack = getFruitItemStackForBlock(state);
		return !ItemUtils.isEmpty(stack)? stack.getMetadata() : 0;
	}
	
	@Override
	public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune)
	{
		final ArrayList<ItemStack> ret = new ArrayList<ItemStack>();
		final int count = quantityDropped(rand);
		for(int i = 0; i < count; ++i)
		{
			final Item item = getItemDropped(state, rand, fortune);
			if (item != null)
			{
				ret.add(new ItemStack(item, 1, damageDropped(state)));
			}
			if (rand.nextInt(bayanusDropRarity) == 0)
			{
				ret.add(EnumYeast.BAYANUS.asStack(1));
			}
		}
		return ret;
	}
	
	/************
	 * STATES
	 ************/
	
	@Nonnull
	@Override
	protected BlockStateContainer createBlockState() {
	    return new BlockStateContainer(this, SUBTYPE);
	}

	@Nonnull
	@Override
	@SuppressWarnings("deprecation")
	public IBlockState getStateFromMeta(int meta) {
	    return this.getDefaultState().withProperty(SUBTYPE, meta & 0x7);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		int meta = 0;
		meta |= state.getValue(SUBTYPE) & 0x7;
	    return meta;
	}
}
