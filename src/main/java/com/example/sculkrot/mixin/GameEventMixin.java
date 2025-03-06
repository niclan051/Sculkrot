package com.example.sculkrot.mixin;

import com.example.sculkrot.gameevent.ModGameEvents;
import net.minecraft.world.level.gameevent.GameEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameEvent.class)
public abstract class GameEventMixin {
    @Inject(method = "<clinit>", at = @At("TAIL"))
    private static void registerGameEvents(CallbackInfo ci) {
        ModGameEvents.register();
    }
}
