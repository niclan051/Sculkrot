package com.example.sculknrun.particle.provider;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.SuspendedParticle;
import net.minecraft.core.particles.SimpleParticleType;
import org.jetbrains.annotations.Nullable;

public class ResinTearsParticleProvider implements ParticleProvider<SimpleParticleType> {
    private final SpriteSet sprite;

    public ResinTearsParticleProvider(SpriteSet sprites) {
        this.sprite = sprites;
    }

    @Override
    public @Nullable Particle createParticle(SimpleParticleType type, ClientLevel level, double x, double y, double z,
                                             double xSpeed, double ySpeed, double zSpeed) {
        SuspendedParticle particle = new SuspendedParticle(level, this.sprite, x, y, z, 0.0, -0.8, 0.0);
        particle.scale(0.75f);
        particle.setLifetime(10);
        return particle;
    }
}
