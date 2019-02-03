package growthcraft.cellar.client.particle;

import growthcraft.cellar.client.particle.params.FluidTanksParams;
import growthcraft.cellar.client.utils.FluidRenderUtils;
import growthcraft.core.shared.utils.BBox;
import growthcraft.core.shared.utils.ColorUtils;
import net.minecraft.client.particle.Particle;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
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
        
        // TODO: Retrieve color from texture as well
        this.particleRed = 1.0F;
        this.particleGreen = 1.0F;
        this.particleBlue = 1.0F;

        FluidStack stack = FluidRenderUtils.getActualFluidBBoxForMax(params.getFluidBBox(), params.getTanks()).getFluidStack();
        if( stack != null ) {
        	Fluid fluid = stack.getFluid();
        	if( fluid != null ) {
        		float argb[] = ColorUtils.getFloat4ARGB(fluid.getColor());
                this.particleRed = argb[1];
                this.particleGreen = argb[2];
                this.particleBlue = argb[3];
        	}
        }        
        
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

        BBox localFluidBBox = FluidRenderUtils.getActualFluidBBoxForMax(params.getFluidBBox(), params.getTanks()).getFluidBBox();
        Double correctPosX = null;
        Double correctPosY = null;
        Double correctPosZ = null;
        if( this.posX < originPos.getX() + localFluidBBox.x0() )
        	correctPosX = originPos.getX() + localFluidBBox.x0();
        if( this.posX > originPos.getX() + localFluidBBox.x1() )
        	correctPosX = originPos.getX() + localFluidBBox.x1();
        if( this.posY < originPos.getY() + localFluidBBox.y0() )
        	correctPosY = originPos.getY() + localFluidBBox.y0();
        if( this.posZ < originPos.getZ() + localFluidBBox.z0() )
        	correctPosZ = originPos.getZ() + localFluidBBox.z0();
        if( this.posZ > originPos.getZ() + localFluidBBox.z1() )
        	correctPosZ = originPos.getZ() + localFluidBBox.z1();
        
        if( correctPosX != null || correctPosY != null || correctPosZ != null ) {
        	this.setPosition(correctPosX != null?correctPosX:posX,
        					 correctPosY != null?correctPosY:posY,
        					 correctPosZ != null?correctPosZ:posZ);
        }
       
        if( this.posY > originPos.getY() + localFluidBBox.y1() )
        {
            this.setExpired();
        }

        if (this.particleMaxAge-- <= 0)
        {
            this.setExpired();
        }
    }

}
