package com.example.sculkrot.datagen;

import com.example.sculkrot.SculkrotMod;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.List;
import java.util.Set;

public class SculkrotDataGenerator {
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();

        generator.addProvider(
                event.includeClient(), new ModBlockStateProvider(output, SculkrotMod.MODID, existingFileHelper));
        generator.addProvider(event.includeServer(), new LootTableProvider(
                                      output, Set.of(),
                                      List.of(new LootTableProvider.SubProviderEntry(ModBlockLootProvider::new,
                                                                                     LootContextParamSets.BLOCK)),
                                      event.getLookupProvider()
                              )
        );
        generator.addProvider(event.includeServer(), new ModRecipeProvider(output, event.getLookupProvider()));
        generator.addProvider(
                event.includeClient(), new ModItemModelProvider(output, SculkrotMod.MODID, existingFileHelper));
        generator.addProvider(event.includeClient(), new ModLanguageProvider(output, SculkrotMod.MODID));
        generator.addProvider(event.includeClient(), new ModParticleDescriptionProvider(output, existingFileHelper));
    }
}