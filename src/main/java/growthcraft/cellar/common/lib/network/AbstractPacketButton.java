package growthcraft.cellar.common.lib.network;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.util.math.BlockPos;

public abstract class AbstractPacketButton extends AbstractPacket
{
	BlockPos coord;
//	int xCoord;
//	int yCoord;
//	int zCoord;

	public AbstractPacketButton() {}

	public AbstractPacketButton(BlockPos pos)
	{
		coord = pos;
//		this.xCoord = pos.getX();
//		this.yCoord = pos.getY();
//		this.zCoord = pos.getZ();
	}

	@Override
	public void encodeInto(ChannelHandlerContext ctx, ByteBuf buffer)
	{
		buffer.writeInt(coord.getX());
		buffer.writeInt(coord.getY());
		buffer.writeInt(coord.getZ());
	}

	@Override
	public void decodeInto(ChannelHandlerContext ctx, ByteBuf buffer)
	{
		int xCoord = buffer.readInt();
		int yCoord = buffer.readInt();
		int zCoord = buffer.readInt();
		coord = new BlockPos( xCoord, yCoord, zCoord );
	}
}
