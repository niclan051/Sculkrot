package com.example.sculkrot.init;

import com.example.sculkrot.SculkrotMod;
import com.example.sculkrot.utils.RegistryUtils;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import team.lodestar.lodestone.systems.particle.world.type.LodestoneWorldParticleType;

public final class ModParticleTypes {
    private static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES = RegistryUtils.createRegister(Registries.PARTICLE_TYPE);
    public static final DeferredHolder<ParticleType<?>, LodestoneWorldParticleType> QUASAR_BOLT =
            PARTICLE_TYPES.register(
                    "quasar_bolt",
                    LodestoneWorldParticleType::new
            );
    public static final DeferredHolder<ParticleType<?>, LodestoneWorldParticleType> QUASAR_BEAM =
            PARTICLE_TYPES.register(
                    "quasar_beam",
                    LodestoneWorldParticleType::new
            );

    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> RESIN_TEARS =
            PARTICLE_TYPES.register(
                    "resin_tears",
                    () -> new SimpleParticleType(false)
            );

    public static void staticInit() {
    }
}
