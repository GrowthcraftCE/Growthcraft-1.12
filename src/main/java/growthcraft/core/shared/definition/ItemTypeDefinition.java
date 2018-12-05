package growthcraft.core.shared.definition;

import javax.annotation.Nonnull;

import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistry;

public class ItemTypeDefinition<T extends Item> extends ObjectDefinition<T> implements ISubItemStackFactory
{
	public ItemTypeDefinition(@Nonnull T item)
	{
		super(item);
	}

	@Nonnull
	public T getItem()
	{
		return getObject();
	}

	@Nonnull
	@Override
	public ItemStack asStack(int size, int damage)
	{
		return new ItemStack(getItem(), size, damage);
	}

	public boolean equals(Item other)
	{
		if (other == null) return false;
		return getItem() == other;
	}

	/**
	 * @param name - item name
	 */
	public void registerItem(IForgeRegistry<Item> registry, ResourceLocation name)
	{
		getItem().setUnlocalizedName(name.getResourcePath());
		getItem().setRegistryName(name);
		registerItem(registry);
	}
	
	public void registerItem(IForgeRegistry<Item> registry)
	{
		registry.register(getItem());
	}
	
	@SideOnly(Side.CLIENT)
    public void registerRender() {
        ModelLoader.setCustomModelResourceLocation(getItem(), 0,
                new ModelResourceLocation(getItem().getRegistryName(), "inventory"));
    }

	@SideOnly(Side.CLIENT)
    public void registerRender(int meta, String fileName) {
    	String modID = getItem().getRegistryName().getResourceDomain();
    	
        ModelLoader.setCustomModelResourceLocation(getItem(), meta,
                new ModelResourceLocation(new ResourceLocation(modID, fileName), "inventory"));
    }
    
	@SideOnly(Side.CLIENT)
    public <ET extends Enum<?> & IObjectVariant & IStringSerializable> void registerRenders(Class<ET> clazz) {
    	ET[] values = clazz.getEnumConstants();
    	registerRenders(values);
    }
    
	@SideOnly(Side.CLIENT)
    @SuppressWarnings("unchecked")
	public <ET extends IObjectVariant & IStringSerializable> void registerRenders(ET ... variants) {
    	ResourceLocation itemResloc = getItem().getRegistryName();
    	
    	for( ET type : variants ) {
    		registerRender(type.getVariantID(), itemResloc.getResourcePath() + "_" + type.getName() );
    	}
    }
    
	@SideOnly(Side.CLIENT)
    public <ET extends Enum<?> & IStringSerializable> void registerModelBakeryVariants(Class<ET> clazz) {
    	ET[] values = clazz.getEnumConstants();
    	registerModelBakeryVariants(values);
    }
    
	@SideOnly(Side.CLIENT)
    @SuppressWarnings("unchecked")
	public <ET extends IStringSerializable> void registerModelBakeryVariants(ET ... variants) {
    	ResourceLocation itemResloc = getItem().getRegistryName();
    	ResourceLocation reslocs[] = new ResourceLocation[variants.length];
    	
    	for( int i = 0; i < reslocs.length; i ++ ) {
    		ET type = variants[i]; 
    		reslocs[i] = new ResourceLocation(itemResloc.getResourceDomain(), itemResloc.getResourcePath() + "_" + type.getName() );	
    	}
    	
    	ModelBakery.registerItemVariants(getItem(), reslocs);
    }
}
