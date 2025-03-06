package com.example.sculkrot.datagen;

import com.example.sculkrot.block.ModBlocks;
import com.example.sculkrot.effect.ModMobEffects;
import com.example.sculkrot.item.ModItems;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;

public class ModLanguageProvider extends LanguageProvider {
    public ModLanguageProvider(PackOutput output, String modid) {
        super(output, modid, "en_us");
    }

    @Override
    protected void addTranslations() {
        this.addEffect(ModMobEffects.SCULKED, "Sculked");
        this.addItem(ModItems.QUASAR, "Quasar");
        this.addBlock(ModBlocks.SCULK_NODE, "Sculk Node");

        this.add("sculkrot.patchouli.sculkinomicon.name", "Sculkinomicon");
        this.add("sculkrot.patchouli.sculkinomicon.landing", "Welcome to the Sculkinomicon");
    }
}
