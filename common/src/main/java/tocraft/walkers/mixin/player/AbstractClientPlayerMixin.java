package tocraft.walkers.mixin.player;

import com.mojang.authlib.GameProfile;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import tocraft.walkers.api.PlayerAbilities;
import tocraft.walkers.api.PlayerShape;
import tocraft.walkers.api.WalkersTickHandler;
import tocraft.walkers.api.WalkersTickHandlers;
import tocraft.walkers.impl.PlayerDataProvider;
import tocraft.walkers.network.impl.VehiclePackets;

import java.util.Optional;
import java.util.UUID;

@Mixin(AbstractClientPlayer.class)
public abstract class AbstractClientPlayerMixin extends Player {

    public AbstractClientPlayerMixin(Level level, BlockPos pos, float yRot, GameProfile gameProfile) {
        super(level, pos, yRot, gameProfile);
    }

    @Inject(method = "tick", at = @At("HEAD"))
    private void clientTick(CallbackInfo info) {
        Optional<UUID> vehiclePlayerId = ((PlayerDataProvider) this).walkers$getVehiclePlayerUUID();
        if (vehiclePlayerId.isPresent() && Minecraft.getInstance().isLocalPlayer(vehiclePlayerId.get())) {
            Vec3 vehiclePos = Minecraft.getInstance().player.position();
            this.moveTo(vehiclePos.x, vehiclePos.y + 1, vehiclePos.z);
        }
    }
}
