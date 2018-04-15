package growthcraft.core.shared.client.gui;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nonnull;

import growthcraft.core.GrowthcraftCore;
import growthcraft.core.shared.tileentity.feature.IInteractionObject;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

/**
 * Cross Ported from YATM
 */
public class GrowthcraftGuiProvider implements IGuiHandler {
	public static class InvalidGuiElement extends IllegalStateException
	{
		public static final long serialVersionUID = 1L;

		public InvalidGuiElement(String str)
		{
			super(str);
		}
	}

	// Leave this Map empty on server, only the client should fill it
	@SuppressWarnings({"rawtypes"})
	public final Map<String, Class> guiMap = new HashMap<String, Class>();

	public GrowthcraftGuiProvider()
	{
	}

	/**
	 * Register your GUI class mappings here and have your tile entities import
	 * @param name name to register the gui as, prefix your mod domain, just because
	 * @param guiClass - the Class<Gui> to use, ensure it has a constructor for (InventoryPlayer, TileEntity), (IInventory, TileEntity) will work as well
	 */
	@SuppressWarnings({"rawtypes"})
	public void register(String name, Class guiClass)
	{
		if (guiMap.containsKey(name))
		{
			GrowthcraftCore.logger.warn("Overwriting Existing Gui mapping: %s with `%s`", name, guiClass);
		}
		guiMap.put(name, guiClass);
	}

	private String typeName(Object inventory)
	{
		if (inventory == null)
		{
			return "NULL";
		}
		return inventory.getClass().getName();
	}

	@SuppressWarnings({"rawtypes", "unchecked"})
	private Constructor findConstructor(Constructor[] c, InventoryPlayer inventory, Object te)
	{
		final Class teClass = te.getClass();
		final Class invClass = inventory.getClass();
		for (Constructor con : c)
		{
			final Class[] types = con.getParameterTypes();
			if (types.length == 2)
			{
				if (types[0].isAssignableFrom(invClass) && types[1].isAssignableFrom(teClass))
				{
					return con;
				}
			}
		}
		return null;
	}

	@SuppressWarnings({"rawtypes", "unchecked"})
	private Object createContainerInstance(@Nonnull Class containerClass, @Nonnull InventoryPlayer inventory, Object te)
	{
		try
		{
			final Constructor[] c = containerClass.getConstructors();
			if (c.length == 0)
			{
				throw new InvalidGuiElement("Invalid Gui Element Class " + containerClass.getName());
			}

			final Constructor target = findConstructor(c, inventory, te);

			if (target == null)
			{
				throw new IllegalStateException("Cannot find " + containerClass.getName() + "( " + this.typeName(inventory) + ", " + this.typeName(te) + " )");
			}

			return target.newInstance(inventory, te);
		}
		catch (Throwable t)
		{
			throw new IllegalStateException(t);
		}
	}

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		final TileEntity te = world.getTileEntity(new BlockPos(x, y, z));
		if (te instanceof IInteractionObject)
		{
			final IInteractionObject iobj = (IInteractionObject)te;
			return iobj.createContainer(player.inventory, player);
		}
		else
		{
			GrowthcraftCore.logger.error("Container requested for TE but TE was not a IInteractionObject tile_entity=%s id=%d", te, ID);
		}
		return null;
	}

	@Override
	@SuppressWarnings({"rawtypes"})
	public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z)
	{
		final TileEntity te = world.getTileEntity(new BlockPos(x, y, z));
		if (te instanceof IInteractionObject)
		{
			final IInteractionObject iobj = (IInteractionObject)te;
			final String guiId = iobj.getGuiID();
			final Class klass = guiMap.get(guiId);
			if (klass != null)
			{
				return createContainerInstance(klass, player.inventory, te);
			}
			else
			{
				GrowthcraftCore.logger.error("Missing GUI Class for %s", guiId);
			}
		}
		return null;
	}

}
