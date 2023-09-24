package tocraft.walkers.mixin;

import tocraft.walkers.Walkers;
import tocraft.walkers.api.FlightHelper;
import tocraft.walkers.api.platform.WalkersConfig;
import net.minecraft.advancements.Advancement;
import net.minecraft.server.PlayerAdvancements;
import net.minecraft.server.level.ServerPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerAdvancements.class)
public class PlayerAdvancementTrackerMixin {

    @Shadow private ServerPlayer player;

    @Inject(
            method = "award",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/advancements/AdvancementRewards;grant(Lnet/minecraft/server/level/ServerPlayer;)V")
    )
    private void refreshFlight(Advancement advancement, String criterionName, CallbackInfoReturnable<Boolean> cir) {
        if(Walkers.hasFlyingPermissions(player)) {
            FlightHelper.grantFlightTo(player);
            player.getAbilities().setFlyingSpeed(WalkersConfig.getInstance().flySpeed());
            player.onUpdateAbilities();
        }
    }
}
