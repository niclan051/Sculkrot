package com.example.sculknrun.datagen;

import com.example.sculknrun.Sculknrun;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.List;
import java.util.Set;

public class SculknrunDataGenerator {
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();

        generator.addProvider(
                event.includeClient(), new ModBlockStateProvider(output, Sculknrun.MODID, existingFileHelper));
        generator.addProvider(event.includeServer(), new LootTableProvider(
                                      output, Set.of(),
                                      List.of(new LootTableProvider.SubProviderEntry(ModBlockLootProvider::new,
                                                                                     LootContextParamSets.BLOCK)),
                                      event.getLookupProvider()
                              )
        );
        generator.addProvider(event.includeServer(), new ModRecipeProvider(output, event.getLookupProvider()));
        generator.addProvider(
                event.includeClient(), new ModItemModelProvider(output, Sculknrun.MODID, existingFileHelper));
        generator.addProvider(event.includeClient(), new ModLanguageProvider(output, Sculknrun.MODID));
        generator.addProvider(event.includeClient(), new ModParticleDescriptionProvider(output, existingFileHelper));
    }
}