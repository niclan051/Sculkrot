package com.example.sculkrot.common.effect;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

public class MycotoxinMobEffect extends MobEffect {
    public MycotoxinMobEffect(MobEffectCategory category, int color) {
        super(category, color, ParticleTypes.SCULK_SOUL);
    }
}
