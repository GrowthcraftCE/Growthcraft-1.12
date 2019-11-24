package growthcraft.core.shared.client.particle;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.particle.Particle;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fluids.IFluidBlock;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

@SideOnly(Side.CLIENT)
public class ParticleDrop extends Particle {
    private static Random random = new Random();
    /**
     * The height of the current bob
     */
    private int bobTimer;

    public ParticleDrop(World world, double x, double y, double z, float particleRed, float particleGreen, float particleBlue) {
        super(world, x, y, z, 0.0D, 0.0D, 0.0D);

        this.particleRed = particleRed;
        this.particleGreen = particleGreen;
        this.particleBlue = particleBlue;

        setParticleTextureIndex(113);
        setSize(0.01F, 0.01F);
        this.particleGravity = 0.06F;
        this.bobTimer = 40;
        this.particleMaxAge = (int) (64.0D / (random.nextDouble() * 0.8D + 0.2D));
        this.motionX = 0.0D;
        this.motionY = 0.0D;
        this.motionZ = 0.0D;
    }

    /**
     * Called to update the entity's position/logic.
     */
    @Override
    public void onUpdate() {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;

        this.motionY -= this.particleGravity;

        if (this.bobTimer-- > 0) {
            this.motionX *= 0.02D;
            this.motionY *= 0.02D;
            this.motionZ *= 0.02D;
            setParticleTextureIndex(113);
        } else {
            setParticleTextureIndex(112);
        }

        move(this.motionX, this.motionY, this.motionZ);
        this.motionX *= 0.9800000190734863D;
        this.motionY *= 0.9800000190734863D;
        this.motionZ *= 0.9800000190734863D;

        if (this.particleMaxAge-- <= 0) {
            setExpired();
        }

        if (this.onGround) {
            setParticleTextureIndex(114);

            this.motionX *= 0.699999988079071D;
            this.motionZ *= 0.699999988079071D;
        }

        BlockPos pos = new BlockPos(MathHelper.floor(this.posX), MathHelper.floor(this.posY), MathHelper.floor(this.posZ));
        final IBlockState blockState = world.getBlockState(pos);
        Block block = blockState.getBlock();

        final Material material = blockState.getMaterial();

        if ((material.isLiquid() || material.isSolid()) && block instanceof IFluidBlock) {
            final double d0 = MathHelper.floor(this.posY) + 1 -
                    ((IFluidBlock) block).getFilledPercentage(world, pos);

            if (this.posY < d0) {
                setExpired();
            }
        }
    }
}
