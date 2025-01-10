package com.example.sculknrun.datagen;

import com.example.sculknrun.Sculknrun;
import com.example.sculknrun.effect.ModMobEffects;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;

public class ModLanguageProvider extends LanguageProvider {
    public ModLanguageProvider(PackOutput output, String modid) {
        super(output, modid, "en_us");
    }

    @Override
    protected void addTranslations() {
        this.addEffect(ModMobEffects.SCULKED, "Sculked");
        this.addItem(Sculknrun.QUASAR, "Quasar");
        this.addBlock(Sculknrun.SCULK_NODE, "Sculk Node");

        this.add("sculknrun.patchouli.sculkinomicon.name", "Sculkinomicon");
        this.add("sculknrun.patchouli.sculkinomicon.landing", "Welcome to the Sculkinomicon");

        //dont write translations manually
        //use this.add("test.sculknrun.some_translation_key", "translation")
    }
}
