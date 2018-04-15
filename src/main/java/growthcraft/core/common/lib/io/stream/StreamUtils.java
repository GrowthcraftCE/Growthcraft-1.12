package growthcraft.core.common.lib.io.stream;

import java.io.UnsupportedEncodingException;

import growthcraft.core.common.lib.utils.ConstID;
import io.netty.buffer.ByteBuf;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;

/**
 * Utility class for handling data streams
 */
public class StreamUtils
{
	private StreamUtils() {}

	/**
	 * Reads an ASCII string from the stream, the first int should be the length
	 * of the string.
	 *
	 * @param stream - stream to read from
	 * @return string
	 */
	public static String readStringASCII(ByteBuf stream)
	{
		final int len = stream.readInt();
		final byte[] bytes = stream.readBytes(len).array();
		try {
			return new String(bytes, "US-ASCII");
		} catch (UnsupportedEncodingException e) {
			// Should never happen!
			return new String(bytes);
		}
	}

	/**
	 * Writes an ASCII string to the stream, the first value will be an integer for the length of the
	 * string, followed by bytes
	 *
	 * @param stream - stream to write to
	 * @param str - string to write
	 */
	public static void writeStringASCII(ByteBuf stream, String str)
	{
		byte[] bytes;
		try {
			bytes = str.getBytes("US-ASCII");
		} catch (UnsupportedEncodingException e) {
			// Should never happen!
			bytes = str.getBytes();
		}
		stream.writeInt(str.length());
		stream.writeBytes(bytes);
	}

	public static void readFluidTank(ByteBuf stream, FluidTank tank)
	{
		final int capacity = stream.readInt();
		final String fluidIdName = readStringASCII(stream); // = stream.readInt();
		final int fluidAmount = stream.readInt();

		final Fluid fluid = !fluidIdName.equals(ConstID.NO_FLUID) ? FluidRegistry.getFluid(fluidIdName) : null;
		final FluidStack fluidStack = fluid != null ? new FluidStack(fluid, fluidAmount) : null;

		tank.setCapacity(capacity);
		tank.setFluid(fluidStack);
	}

	public static void writeFluidTank(ByteBuf stream, FluidTank tank)
	{
		String fluidIdName = ConstID.NO_FLUID;
		int fluidAmount = 0;
		final int capacity = tank.getCapacity();
		final FluidStack fs = tank.getFluid();

		if (fs != null)
		{
			fluidIdName = fs.getFluid().getName();
			fluidAmount = fs.amount;
		}

		stream.writeInt(capacity);
		writeStringASCII(stream, fluidIdName);
		stream.writeInt(fluidAmount);
	}
}
