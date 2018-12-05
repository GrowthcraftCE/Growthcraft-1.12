package growthcraft.core.shared.definition;

import javax.annotation.Nonnull;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistry;

public class BlockTypeDefinition<T extends Block> extends ObjectDefinition<T> implements ISubItemStackFactory
{
	public BlockTypeDefinition(@Nonnull T block)
	{
		super(block);
	}

	@Nonnull
	public final T getBlock()
	{
		return getObject();
	}

	public final Item getItem()
	{
		return Item.getItemFromBlock(getBlock());
	}
	
	@Nonnull
	public final IBlockState getDefaultState() {
		return getBlock().getDefaultState();
	}

	@Nonnull
	@Override
	public ItemStack asStack(int size, int damage)
	{
		return new ItemStack(getBlock(), size, damage);
	}

	@Nonnull
	public ItemStack getItemAsStack(int size)
	{
		return new ItemStack(getItem(), size);
	}
	
	@Nonnull
	public ItemStack getItemAsStack(int size, int damage)
	{
		return new ItemStack(getItem(), size, damage);
	}

	/**
	 * Checks if the supplied block is equal to the given, this uses
	 * Block.isEqualTo to compare the blocks
	 *
	 * @param other - block to compare to
	 * @return true if blocks are equal, false otherwise
	 */
	public boolean equals(Block other)
	{
		return Block.isEqualTo(getBlock(), other);
	}

	/**
	 * Checks if the supplied item is equal to the given, this uses
	 * == to compare the items
	 *
	 * @param other - item to compare to
	 * @return true if items are equal, false otherwise
	 */
	public boolean equals(Item other)
	{
		return other == getItem();
	}

	/**
	 * Checks if the supplied block is the same as the given, this uses ==
	 * for comparison
	 *
	 * @param other - block to check
	 * @return true if block is the same, false otherwise
	 */
	public boolean isSameAs(Block other)
	{
		return getBlock() == other;
	}

	/**
	 * @param name - block name
	 * @param itemBlock - item class to register to
	 */
/*	public void register(ResourceLocation name, ItemBlock itemBlock)
	{
		getBlock().setUnlocalizedName(name.getResourcePath());
		getBlock().setRegistryName(name);

		if( itemBlock != null )
			register(itemBlock);
		else
			register(null);
	} */
	
	/**
	 * @param itemBlock - item class to register to
	 */
	public void registerBlock(IForgeRegistry<Block> registry)
	{
		registry.register(getBlock());
	}
	
	public void registerBlockItem(IForgeRegistry<Item> registry, ItemBlock itemBlock)
	{
       	registry.register(itemBlock.setRegistryName(getBlock().getRegistryName()));
	}
	
	public void registerBlockItem(IForgeRegistry<Item> registry)
	{
		registerBlockItem(registry, new ItemBlock(getBlock()));
	}
	
	/**
	 * @param name - block name
	 * @param itemBlock - item class to register to
	 */
	public void registerBlock(IForgeRegistry<Block> registry, ResourceLocation name)
	{
		getBlock().setUnlocalizedName(name.getResourcePath());
		getBlock().setRegistryName(name);
        
        registerBlock(registry);
	}

/*	public void register(boolean registerItemBlock)
	{
		if( registerItemBlock )
			register(new ItemBlock(getBlock()));
		else
			register(null);
	}*/

	
	/**
	 * @param name - block name
	 */
	@SideOnly(Side.CLIENT)
	public void registerItemRender()
	{
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(getBlock()), 0, new ModelResourceLocation(
                getBlock().getRegistryName(), "inventory"));
	}
	
	@SideOnly(Side.CLIENT)
    public void registerItemRender(int meta, String fileName){
    	String modID = getBlock().getRegistryName().getResourceDomain();
    	
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(getBlock()), meta,
                new ModelResourceLocation(new ResourceLocation(modID, fileName), "inventory"));
    }
}
