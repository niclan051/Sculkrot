package com.example.sculkrot.datagen;

import com.example.sculkrot.particle.ModParticleTypes;
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
        spriteSet(
                ModParticleTypes.RESIN_TEARS.get(),
                ModParticleTypes.RESIN_TEARS.getId()
                                            .withPrefix(
                                                    ModParticleTypes.RESIN_TEARS.getId().getPath() + "/"
                                            ),
                5, false
        );
        spriteSet(
                ModParticleTypes.QUASAR_BEAM.get(),
                ModParticleTypes.QUASAR_BEAM.getId()
                                            .withPrefix(
                                                    ModParticleTypes.QUASAR_BEAM.getId().getPath() + "/"
                                            ),
                15, false
        );
    }
}
