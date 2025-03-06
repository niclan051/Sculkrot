package com.example.sculkrot.particle;

import com.example.sculkrot.SculkrotMod;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import team.lodestar.lodestone.systems.particle.world.type.LodestoneWorldParticleType;

public final class ModParticleTypes {
    private static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES =
            DeferredRegister.create(BuiltInRegistries.PARTICLE_TYPE, SculkrotMod.MODID);

    public static final DeferredHolder<ParticleType<?>, LodestoneWorldParticleType> QUASAR_BOLT =
            PARTICLE_TYPES.register(
                    "quasar_bolt",
                    LodestoneWorldParticleType::new
            );

    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> RESIN_TEARS =
            PARTICLE_TYPES.register(
                    "resin_tears",
                    () -> new SimpleParticleType(false)
            );

    public static void register(IEventBus bus) {
        PARTICLE_TYPES.register(bus);
    }
}
