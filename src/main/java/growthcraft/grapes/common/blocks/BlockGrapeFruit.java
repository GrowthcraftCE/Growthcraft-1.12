package growthcraft.grapes.common.blocks;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import growthcraft.cellar.handlers.EnumHandler.EnumYeast;
import growthcraft.core.common.block.GrowthcraftBlockBase;
import growthcraft.core.utils.ItemUtils;
import growthcraft.grapes.GrowthcraftGrapesConfig;
import growthcraft.grapes.Reference;
import growthcraft.grapes.api.definition.IGrapeType;
import growthcraft.grapes.init.GrowthcraftGrapesBlocks;
import growthcraft.grapes.init.GrowthcraftGrapesItems;
import growthcraft.grapes.utils.GrapeTypeUtils;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
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
	
	private final IGrapeType[] types;

    public BlockGrapeFruit(/*String unlocalizedName*/ IGrapeType[] types) {
    	super(Material.PLANTS);
//        this.setUnlocalizedName(unlocalizedName);
//        this.setRegistryName(new ResourceLocation(Reference.MODID, unlocalizedName));
		setHardness(0.0F);
		setSoundType(SoundType.PLANT);
		setDefaultState(this.getBlockState().getBaseState().withProperty(SUBTYPE, 0));
		
		this.types = types;
    }
    
    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return BOUNDING_BOX;
    }
    
    @Override
    public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable Entity entityIn, boolean p_185477_7_) {
//        addCollisionBoxToList(pos, entityBox, collidingBoxes, getBoundingBox(state, worldIn, pos));
    }
	
	@Override
	public boolean isOpaqueCube(IBlockState state)
	{
		return false;
	}

    @Override
    public boolean isFullBlock(IBlockState state) {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
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
		return GrowthcraftGrapesBlocks.grapeLeaves.getBlock() == world.getBlockState(pos.up()).getBlock();
	}
	
	/************
	 * STUFF
	 ************/
	@Override
	@SideOnly(Side.CLIENT)
	public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state)
	{
		return getFruitItemStackForBlock(state);
//		return GrowthcraftGrapesItems.grape.asStack();
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
		IGrapeType grapeType = GrapeTypeUtils.getTypeBySubID(types, typeID);
		if( grapeType != null )
			return grapeType.asStack();
		return ItemStack.EMPTY;
	}
	
	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune)
	{
		ItemStack stack = getFruitItemStackForBlock(state);
//		return GrowthcraftGrapesItems.grape.getItem();
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
