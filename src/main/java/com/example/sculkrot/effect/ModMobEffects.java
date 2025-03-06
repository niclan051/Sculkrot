package com.example.sculkrot.effect;

import com.example.sculkrot.SculkrotMod;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public final class ModMobEffects {
    public static final DeferredRegister<MobEffect> EFFECTS = DeferredRegister.create(
            BuiltInRegistries.MOB_EFFECT,
            SculkrotMod.MODID
    );

    public static final DeferredHolder<MobEffect, SculkedMobEffect> SCULKED = register(
            "sculked",
            () -> new SculkedMobEffect(MobEffectCategory.HARMFUL, 0x0A5060)
    );

    private static <T extends MobEffect> DeferredHolder<MobEffect, T> register(String id, Supplier<T> effect) {
        return EFFECTS.register(id, effect);
    }

    public static void register(IEventBus bus) {
        EFFECTS.register(bus);
    }
}
