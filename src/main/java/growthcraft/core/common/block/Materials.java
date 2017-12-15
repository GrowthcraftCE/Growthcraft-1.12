package growthcraft.core.common.block;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialLiquid;

public class Materials {
	// REVISE_ME remove cellar dependency
	
	static class MaterialDevice extends Material
	{
		public MaterialDevice(MapColor color)
		{
			super(color);
			setNoPushMobility();
		}
	}

	static class MaterialFireproofLeaves extends Material
	{
		public MaterialFireproofLeaves(MapColor color)
		{
			super(color);
			setNoPushMobility();
		}

		@Override
		public boolean isOpaque()
		{
			return false;
		}
	}

	static class MaterialBooze extends MaterialLiquid
	{
		public MaterialBooze(MapColor color)
		{
			super(color);
			setNoPushMobility();
		}

		public boolean isLiquid()
		{
			return true;
		}
	}
	
	public static final Material fireproofWood = new Material(Material.WOOD.getMaterialMapColor());
	public static final Material fireproofLeaves = new MaterialFireproofLeaves(Material.LEAVES.getMaterialMapColor());
	public static final Material booze = new MaterialBooze(Material.WATER.getMaterialMapColor());

	private Materials() {}
}

