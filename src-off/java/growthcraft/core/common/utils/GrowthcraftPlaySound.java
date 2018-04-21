package growthcraft.core.common.utils;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

public class GrowthcraftPlaySound {

    /**
     * Only play this sound if a player is within a specific range.
     * @param world
     * @param pos
     * @param soundEvent
     * @param soundCategory
     * @param range
     */
    public static void onlyNearByPlayers(World world, BlockPos pos, SoundEvent soundEvent, SoundCategory soundCategory, int range) {

        Random rand = new Random();

        List<EntityPlayer> listEnitities = world.getEntitiesWithinAABB(EntityPlayer.class, new AxisAlignedBB(pos.getX() - range, pos.getY() - range, pos.getZ() - range, pos.getX() + range, pos.getY() + range, pos.getZ() + range));

        for (EntityPlayer player : listEnitities) {
            BlockPos playerPos = player.getPosition();
            world.playSound((EntityPlayer)null, playerPos.getY(), playerPos.getY(), playerPos.getZ(), soundEvent, soundCategory, 10000.0F, 0.8F + rand.nextFloat() * 0.2F);
        }

    }




}
