package com.example.sculkrot.init.data;

import com.example.sculkrot.SculkrotMod;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.*;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;

public class ModDamageTypes {

    public static final ResourceKey<DamageType> SCULK = create("sculk");

    private static ResourceKey<DamageType> create(String name) {
        return ResourceKey.create(Registries.DAMAGE_TYPE, SculkrotMod.asResource(name));
    }

    private static void registerDamageTypes(BootstrapContext<DamageType> context) {
        register(context, SCULK, toMsgId(SCULK), DamageScaling.NEVER, 0.0f, DamageEffects.HURT, DeathMessageType.DEFAULT);
    }

    private static void register(BootstrapContext<DamageType> context, ResourceKey<DamageType> key, String msgId, DamageScaling scaling, float exhaustion, DamageEffects effects, DeathMessageType deathMessageType) {
        context.register(key, new DamageType(msgId, scaling, exhaustion, effects, deathMessageType));
    }

    private static String toMsgId(ResourceKey<DamageType> key) {
        return key.location().toString().replace('/', '.').replace(':', '.');
    }

    public static void bootstrap(BootstrapContext<DamageType> context) {
        registerDamageTypes(context);
    }

    public static DamageSource asSource(Level level, ResourceKey<DamageType> key) {
        return new DamageSource(level.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(key));
    }

    public static Holder<DamageType> getHolder(Level level, ResourceKey<DamageType> key) {
        return level.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(key);
    }
}
