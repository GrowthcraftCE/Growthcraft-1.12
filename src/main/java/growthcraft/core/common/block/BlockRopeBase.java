package growthcraft.core.common.block;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import growthcraft.core.shared.block.IBlockRope;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public abstract class BlockRopeBase extends Block implements IBlockRope {

	public BlockRopeBase(Material materialIn) {
		super(materialIn);
	}

    @SuppressWarnings("deprecation")
    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
    	ArrayList<AxisAlignedBB> collidingBoxes = new ArrayList<>(5);
    	
    	state = state.getActualState(source, pos);
    	populateCollisionBoxes(state, pos, FULL_BLOCK_AABB.offset(pos), collidingBoxes);
    	if( collidingBoxes.isEmpty() )
    		return FULL_BLOCK_AABB;
    	
    	AxisAlignedBB box = collidingBoxes.get(0);	// NOTE: Assuming that collidingBoxes is non empty!
    	for( int i = 1; i < collidingBoxes.size(); i ++ )
    		box = box.union(collidingBoxes.get(i));
    	box = box.offset(new BlockPos(-pos.getX(), -pos.getY(), -pos.getZ()));
    	
    	return box;
    }

    @SuppressWarnings("deprecation")
    @Override
    public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable Entity entityIn, boolean isActualState) {
        if( !isActualState )
        	state = state.getActualState(worldIn, pos);
        
        populateCollisionBoxes(state, pos, entityBox, collidingBoxes);
    }
	
	protected abstract void populateCollisionBoxes(IBlockState actualState, BlockPos pos, AxisAlignedBB entityBox,
			List<AxisAlignedBB> collidingBoxes);

}
