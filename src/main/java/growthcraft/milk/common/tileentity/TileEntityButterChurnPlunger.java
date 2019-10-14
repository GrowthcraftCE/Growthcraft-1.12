package growthcraft.milk.common.tileentity;

import growthcraft.core.shared.tileentity.GrowthcraftTileBase;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileEntityButterChurnPlunger extends GrowthcraftTileBase implements ITickable {
    @SideOnly(Side.CLIENT)
    public float animProgress;
    @SideOnly(Side.CLIENT)
    public int animDir;

    @Override
    public void update() {
        if (world.isRemote) {
            TileEntity master = world.getTileEntity(getPos().down());
            if (master == null || !(master instanceof TileEntityButterChurn))
                return;
            TileEntityButterChurn bcMaster = (TileEntityButterChurn) master;

            final float step = 1.0f / 5.0f;
            if (bcMaster.getShaftState() == 0) {
                this.animDir = -1;
            } else {
                this.animDir = 1;
            }

            if (animDir > 0 && animProgress < 1.0f || animDir < 0 && animProgress > 0) {
                this.animProgress = MathHelper.clamp(this.animProgress + step * animDir, 0.0f, 1.0f);
            }
        }
    }
	
/*
	@TileEventHandler(event=TileEventHandler.EventType.NBT_READ)
	public void readFromNBT_FruitPresser(NBTTagCompound nbt)
	{
	}

	@TileEventHandler(event=TileEventHandler.EventType.NBT_WRITE)
	public void writeToNBT_FruitPresser(NBTTagCompound nbt)
	{
	}

	@TileEventHandler(event=TileEventHandler.EventType.NETWORK_READ)
	public boolean readFromStream_FruitPresser(ByteBuf stream) throws IOException
	{
		return true;
	}

	@TileEventHandler(event=TileEventHandler.EventType.NETWORK_WRITE)
	public boolean writeToStream_FruitPresser(ByteBuf stream) throws IOException
	{
		return true;
	}
	*/
}
