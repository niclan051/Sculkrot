package com.example.sculknrun.datagen;

import com.example.sculknrun.Sculknrun;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

public class SculknrunDataGenerator {
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();

        generator.addProvider(
                event.includeClient(), new ModBlockStateProvider(output, Sculknrun.MODID, existingFileHelper));
        generator.addProvider(event.includeClient(), new ModLanguageProvider(output, Sculknrun.MODID));
    }
}