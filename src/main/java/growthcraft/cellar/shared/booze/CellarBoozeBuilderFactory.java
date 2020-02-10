package growthcraft.cellar.shared.booze;

import growthcraft.cellar.shared.config.UserApis;
import net.minecraftforge.fluids.Fluid;

import javax.annotation.Nonnull;

public class CellarBoozeBuilderFactory {
    private UserApis userApis;

    public CellarBoozeBuilderFactory(@Nonnull UserApis apis) {
        this.userApis = apis;
    }

    public ICellarBoozeBuilder create(@Nonnull Fluid fluid) {
        return new UserApiCellarBoozeBuilder(userApis, fluid);
    }
}
