package com.example.sculkrot.init;

import com.example.sculkrot.SculkrotMod;
import com.example.sculkrot.common.effect.SculkedMobEffect;
import com.example.sculkrot.utils.RegistryUtils;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public final class ModMobEffects {
    public static final DeferredRegister<MobEffect> EFFECTS = RegistryUtils.createRegister(Registries.MOB_EFFECT);

    public static final DeferredHolder<MobEffect, SculkedMobEffect> SCULKED = register(
            "sculked",
            () -> new SculkedMobEffect(MobEffectCategory.HARMFUL, 0x0A5060)
    );

    private static <T extends MobEffect> DeferredHolder<MobEffect, T> register(String id, Supplier<T> effect) {
        return EFFECTS.register(id, effect);
    }

    public static void staticInit() {
    }


}
