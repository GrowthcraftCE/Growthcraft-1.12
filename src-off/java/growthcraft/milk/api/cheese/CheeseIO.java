package growthcraft.milk.api.cheese;

import growthcraft.milk.api.MilkRegistry;
import growthcraft.milk.api.definition.ICheeseType;
import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;

public class CheeseIO {
	private CheeseIO() { }
	
	public static ICheeseType loadFromStream(ByteBuf stream)
	{
		final int id = stream.readInt();
		return MilkRegistry.instance().cheese().getSafeCheeseById(id);
	}
	
	public static boolean writeToStream(ByteBuf stream, ICheeseType type)
	{
		stream.writeInt(MilkRegistry.instance().cheese().getCheeseId(type));
		return false;
	}

	public static ICheeseType loadFromNBT(NBTTagCompound nbt)
	{
		final String modId = nbt.getString("cheese_modid");
		final String name = nbt.getString("cheese_name");
		return MilkRegistry.instance().cheese().getSafeCheeseByName(new ResourceLocation(modId, name));
	}
	
	public static NBTTagCompound writeToNBT(NBTTagCompound tag, ICheeseType type)
	{
		ResourceLocation name = type.getRegistryName();
		tag.setString("cheese_modid", name.getResourceDomain());
		tag.setString("cheese_name", name.getResourcePath());
		return tag;
	}
}
