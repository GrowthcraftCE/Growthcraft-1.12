package growthcraft.fishtrap.common.utils;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

public class GrowthcraftPlaySound {

    private GrowthcraftPlaySound() {
        // Do nothing at this time.
    }

    private static final Random rand = new Random();

    /**
     * Only play this sound if a player is within a specific range.
     *
     * @param world
     * @param pos
     * @param soundEvent
     * @param soundCategory
     * @param range
     */
    public static void onlyNearByPlayers(World world, BlockPos pos, SoundEvent soundEvent, SoundCategory soundCategory, int range) {

        List<EntityPlayer> listEntities = world.getEntitiesWithinAABB(EntityPlayer.class, new AxisAlignedBB(
                pos.getX() - (double) range,
                pos.getY() - (double) range,
                pos.getZ() - (double) range,
                pos.getX() + (double) range,
                pos.getY() + (double) range,
                pos.getZ() + (double) range));

        for (EntityPlayer player : listEntities) {
            BlockPos playerPos = player.getPosition();
            world.playSound((EntityPlayer) null, playerPos.getY(), playerPos.getY(), playerPos.getZ(), soundEvent, soundCategory, 10000.0F, 0.8F + rand.nextFloat() * 0.2F);
        }

    }


}
