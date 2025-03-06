package com.example.sculkrot.gameevent;

import com.example.sculkrot.SculkrotMod;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.gameevent.GameEvent;

public final class ModGameEvents {
    public static final Holder.Reference<GameEvent> INFECTION_GROW = register(
            "infection_grow",  128
    );

    private static Holder.Reference<GameEvent> register(String name, int notificationRadius) {
        return Registry.registerForHolder(
                BuiltInRegistries.GAME_EVENT, ResourceLocation.fromNamespaceAndPath(
                        SculkrotMod.MODID, name),
                new GameEvent(notificationRadius)
        );
    }

    public static void register() {
        // static initialization strikes again
    }
}
