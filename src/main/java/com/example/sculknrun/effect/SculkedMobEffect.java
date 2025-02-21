package com.example.sculknrun.effect;

import com.example.sculknrun.particle.ModParticleTypes;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;

public class SculkedMobEffect extends MobEffect {
    protected SculkedMobEffect(MobEffectCategory pCategory, int pColor) {
        super(pCategory, pColor);
    }

    @Override
    public ParticleOptions createParticleOptions(MobEffectInstance effect) {
        return ModParticleTypes.RESIN_TEARS.get();
    }

    @Override
    public boolean applyEffectTick(LivingEntity livingEntity, int amplifier) {
        livingEntity.hurt(livingEntity.damageSources().wither(), 1);

        return true;
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return amplifier > 0;
    }
}
