package growthcraft.grapes.common.blocks;

import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import growthcraft.core.shared.block.BlockFlags;
import growthcraft.grapes.common.utils.GrapeTypeUtils;
import growthcraft.grapes.shared.config.GrowthcraftGrapesConfig;
import growthcraft.grapes.shared.definition.IGrapeType;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockGrapeVine0 extends BlockGrapeVineBase {

    private static final AxisAlignedBB[] BOUNDING_BOXES = new AxisAlignedBB[]{
            new AxisAlignedBB(0.0625 * 6, 0.0625 * 0, 0.0625 * 6, 0.0625 * 10, 0.0625 * 4, 0.0625 * 10),
            new AxisAlignedBB(0.0625 * 6, 0.0625 * 0, 0.0625 * 6, 0.0625 * 10, 0.0625 * 14, 0.0625 * 10)
    };
    
    private final BlockGrapeVine1 blockVine1;
    private final IGrapeType[] grapeTypes;
	
	public BlockGrapeVine0(IGrapeType[] grapeTypes, BlockGrapeVine1 blockVine1) {
		super();
		setGrowthRateMultiplier(GrowthcraftGrapesConfig.grapeVineSeedlingGrowthRate);
		setTickRandomly(true);
		setHardness(0.0F);
		setSoundType(SoundType.PLANT);
		
		this.blockVine1 = blockVine1;
		this.grapeTypes = grapeTypes;
	}
	
    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        if ( this.getAge(state) == 0 ) {
            return BOUNDING_BOXES[0];
        }
        else {
            return BOUNDING_BOXES[1];
        }
    }
    
    @Override
    public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable Entity entityIn, boolean p_185477_7_) {
    }
    
	@Override
	public int getMaxAge() {
		return 1;
	}
	
	/************
	 * DROPS
	 ************/
	
	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune)
	{
    	int typeID = state.getValue(SUBTYPE);
    	IGrapeType type = GrapeTypeUtils.getTypeBySubID(grapeTypes, typeID);
		return type.asSeedsStack().getItem();
	}

	@Override
	public int quantityDropped(Random random)
	{
		return 1;
	}
	
    @Override
    public int damageDropped(IBlockState state) {
    	int typeID = state.getValue(SUBTYPE);
    	IGrapeType type = GrapeTypeUtils.getTypeBySubID(grapeTypes, typeID);
    	return type.asSeedsStack().getItemDamage();    	
    }
	
	/************
	 * TICK
	 ************/
	@Override
	protected boolean canUpdateGrowth(World world, BlockPos pos)
	{
		// TODO Check it!
		return world.getLight(pos.up()) >= 9;
	}

	@Override
	protected void doGrowth(World world, BlockPos pos, IBlockState state)
	{
		int age = getAge(state);
		if (age <= 0)
		{
			incrementGrowth(world, pos, state);
		}
		else
		{
			int type = state.getValue(SUBTYPE);
			world.setBlockState(pos, blockVine1.getDefaultState().withProperty(SUBTYPE, type), BlockFlags.UPDATE_AND_SYNC);
		}
	}

}
