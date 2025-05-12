package com.example.sculkrot.common.particle.provider;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import org.jetbrains.annotations.Nullable;
import team.lodestar.lodestone.systems.particle.SimpleParticleOptions;
import team.lodestar.lodestone.systems.particle.builder.WorldParticleBuilder;
import team.lodestar.lodestone.systems.particle.data.GenericParticleData;
import team.lodestar.lodestone.systems.particle.render_types.LodestoneWorldParticleRenderType;
import team.lodestar.lodestone.systems.particle.world.LodestoneWorldParticle;
import team.lodestar.lodestone.systems.particle.world.options.WorldParticleOptions;

public class ResinTearsParticleProvider implements ParticleProvider<SimpleParticleType> {
    private final SpriteSet sprite;

    public ResinTearsParticleProvider(SpriteSet sprites) {
        this.sprite = sprites;
    }

    @Override
    public @Nullable Particle createParticle(SimpleParticleType type, ClientLevel level, double x, double y, double z,
                                             double xSpeed, double ySpeed, double zSpeed) {
        if (this.sprite instanceof ParticleEngine.MutableSpriteSet mutableSpriteSet) {
            WorldParticleBuilder particleBuilder = WorldParticleBuilder.create(new WorldParticleOptions(type))
                    .setLifetime(10)
                    .setSpritePicker(SimpleParticleOptions.ParticleSpritePicker.WITH_AGE)
                    .setScaleData(GenericParticleData.create(0.15f).build())
                    .setTransparencyData(GenericParticleData.create(1, 0).build())
                    .setRenderType(LodestoneWorldParticleRenderType.TRANSPARENT);

            return new LodestoneWorldParticle(
                    level, particleBuilder.getParticleOptions(), mutableSpriteSet, x, y, z,
                    level.random.nextDouble() / 100, level.random.nextDouble() / 100, level.random.nextDouble() / 100
            );
        }
        return null;
    }
}
