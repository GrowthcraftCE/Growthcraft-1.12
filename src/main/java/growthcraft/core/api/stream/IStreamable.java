package growthcraft.core.api.stream;

import io.netty.buffer.ByteBuf;

public interface IStreamable
{
	boolean readFromStream(ByteBuf stream);
	boolean writeToStream(ByteBuf stream);
}
