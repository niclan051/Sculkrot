package com.example.sculkrot.init;

import com.example.sculkrot.SculkrotMod;
import com.example.sculkrot.utils.RegistryUtils;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.alchemy.Potion;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public final class ModPotions {
    private static final DeferredRegister<Potion> POTIONS = RegistryUtils.createRegister(Registries.POTION);

    public static final Holder<Potion> STRONG_SCULKED = register(
            "strong_sculked",
            () -> new Potion("sculked", new MobEffectInstance(ModMobEffects.SCULKED, 200, 1))
    );

    private static DeferredHolder<Potion, Potion> register(String name, Supplier<Potion> potion) {
        return POTIONS.register(name, potion);
    }

    public static void staticInit() {
    }
}
