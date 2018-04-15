package growthcraft.core.common.lib.io.stream;

import io.netty.buffer.ByteBuf;

public interface IStreamable
{
	boolean readFromStream(ByteBuf stream);
	boolean writeToStream(ByteBuf stream);
}
