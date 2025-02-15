package com.example.sculknrun.datagen;

import com.example.sculknrun.particle.ModParticleTypes;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.common.data.ParticleDescriptionProvider;

public class ModParticleDescriptionProvider extends ParticleDescriptionProvider {
    protected ModParticleDescriptionProvider(PackOutput output,
                                             ExistingFileHelper fileHelper) {
        super(output, fileHelper);
    }

    @Override
    protected void addDescriptions() {
        sprite(ModParticleTypes.QUASAR_BOLT.get(), ModParticleTypes.QUASAR_BOLT.getId());
        sprite(ModParticleTypes.RESIN_TEARS.get(), ModParticleTypes.RESIN_TEARS.getId());
    }
}
