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
        this.addItem(Sculknrun.SCULK_WINE, "Sculk Wine");
        this.addBlock(Sculknrun.SCULK_NODE, "Sculk Node");

        //dont write translations manually
        //use this.add("test.sculknrun.some_translation_key", "translation")
    }
}
