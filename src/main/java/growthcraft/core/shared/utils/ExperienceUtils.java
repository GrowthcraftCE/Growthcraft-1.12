package growthcraft.core.shared.utils;

import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.MathHelper;

import java.util.Random;

public class ExperienceUtils {
    private static Random random = new Random();

    private ExperienceUtils() {
    }

    public static void spawnExp(int amount, float exp, EntityPlayer player) {
        int j;

        if (exp == 0.0F) {
            amount = 0;
        } else if (exp < 1.0F) {
            j = MathHelper.floor((float) amount * exp);

            if (j < MathHelper.ceil((float) amount * exp) && (float) random.nextDouble() < (float) amount * exp - (float) j) {
                ++j;
            }

            amount = j;
        }

        while (amount > 0) {
            j = EntityXPOrb.getXPSplit(amount);
            amount -= j;
            player.world.spawnEntity(new EntityXPOrb(player.world, player.posX, player.posY + 0.5D, player.posZ + 0.5D, j));
        }
    }
}
