package growthcraft.core.common.lib.tileentity.event;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Nonnull;

import net.minecraft.tileentity.TileEntity;

public class TileEventHandlerMap<T extends TileEntity> extends HashMap<Class<? extends T>, TileEventFunctionMap>
{
	public static final long serialVersionUID = 1L;

	protected void addHandlerEventFunction(@Nonnull TileEventFunctionMap handlerMap, @Nonnull TileEventHandler.EventType type, @Nonnull Method method)
	{
		if (!handlerMap.containsKey(type))
		{
			handlerMap.put(type, new ArrayList<TileEventFunction>());
		}
		handlerMap.get(type).add(new TileEventFunction(method));
	}

	public TileEventFunctionMap getEventFunctionMap(Class<? extends T> klass)
	{
		TileEventFunctionMap cached = get(klass);
		if (cached == null)
		{
			cached = new TileEventFunctionMap();
			put(klass, cached);
			for (Method method : klass.getMethods())
			{
				final TileEventHandler anno = method.getAnnotation(TileEventHandler.class);
				if (anno != null) addHandlerEventFunction(cached, anno.event(), method);
			}
		}
		return cached;
	}

	public List<TileEventFunction> getEventFunctionsForClass(Class<? extends T> klass, TileEventHandler.EventType type)
	{
		return getEventFunctionMap(klass).get(type);
	}
}
