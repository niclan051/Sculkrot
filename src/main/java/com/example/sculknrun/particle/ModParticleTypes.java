package com.example.sculknrun.particle;

import com.example.sculknrun.Sculknrun;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import team.lodestar.lodestone.systems.particle.world.type.LodestoneWorldParticleType;

public final class ModParticleTypes {
    private static final DeferredRegister<ParticleType<?>> PARTICLES =
            DeferredRegister.create(BuiltInRegistries.PARTICLE_TYPE, Sculknrun.MODID);

    public static final DeferredHolder<ParticleType<?>, LodestoneWorldParticleType> QUASAR_BOLT =
            PARTICLES.register(
                    "quasar_bolt",
                    LodestoneWorldParticleType::new
            );

    public static void register(IEventBus bus) {
        PARTICLES.register(bus);
    }
}
