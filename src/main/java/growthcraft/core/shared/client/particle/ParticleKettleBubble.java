package growthcraft.core.shared.client.particle;

import growthcraft.cellar.client.utils.FluidRenderUtils;
import growthcraft.core.shared.client.particle.params.FluidTanksParams;
import growthcraft.core.shared.utils.BBox;
import net.minecraft.client.particle.Particle;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ParticleKettleBubble extends Particle {
	private final BlockPos originPos;
	private final FluidTanksParams params;
	
    public ParticleKettleBubble(World worldIn,
    							double xCoordIn, double yCoordIn, double zCoordIn,
    							double xSpeedIn, double ySpeedIn, double zSpeedIn,
    							FluidTanksParams params)
    {
        super(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn);
        this.particleRed = 1.0F;
        this.particleGreen = 1.0F;
        this.particleBlue = 1.0F;
        this.setParticleTextureIndex(32);
        this.setSize(0.02F, 0.02F);
        this.particleScale *= this.rand.nextFloat() * 0.3F + 0.2F;
        this.motionX = xSpeedIn * 0.20000000298023224D + (Math.random() * 2.0D - 1.0D) * 0.019999999552965164D;
        this.motionY = ySpeedIn * 0.20000000298023224D + (Math.random() * 2.0D - 1.0D) * 0.019999999552965164D;
        this.motionZ = zSpeedIn * 0.20000000298023224D + (Math.random() * 2.0D - 1.0D) * 0.019999999552965164D;
        this.particleMaxAge = (int)(8.0D / (Math.random() * 0.8D + 0.2D));
        
        this.originPos = new BlockPos(this.posX, this.posY, this.posZ);
        this.params = params;
    }

    public void onUpdate()
    {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        this.motionY += 0.002D;
        this.move(this.motionX, this.motionY, this.motionZ);
        this.motionX *= 0.8500000238418579D;
        this.motionY *= 0.8500000238418579D;
        this.motionZ *= 0.8500000238418579D;

        // if (this.world.getBlockState(new BlockPos(this.posX, this.posY, this.posZ)).getMaterial() != Material.WATER)
        //if( !new BlockPos(this.posX, this.posY, this.posZ).equals(originPos) )
        
//      if( this.posX < originPos.getX()+2*0.0625 || this.posX > originPos.getX()+1-3*0.0625 ||
//    	this.posY < originPos.getY()+4*0.0625 || this.posY > originPos.getY()+1-3*0.0625 ||
//    	this.posZ < originPos.getZ()+2*0.0625 || this.posZ > originPos.getZ()+1-3*0.0625 )
        BBox localFluidBBox = FluidRenderUtils.getActualFluidBBoxForMax(params.getFluidBBox(), params.getTanks());
        if( this.posX < originPos.getX() + localFluidBBox.x0() || this.posX > originPos.getX() + localFluidBBox.x1() ||
        	this.posY < originPos.getY() + localFluidBBox.y0() || this.posY > originPos.getY() + localFluidBBox.y1() ||
        	this.posZ < originPos.getZ() + localFluidBBox.z0() || this.posZ > originPos.getZ() + localFluidBBox.z1() )
        {
            this.setExpired();
        }

        if (this.particleMaxAge-- <= 0)
        {
            this.setExpired();
        }
    }

}
