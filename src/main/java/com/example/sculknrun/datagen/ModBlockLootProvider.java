package com.example.sculknrun.datagen;

import com.example.sculknrun.Sculknrun;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.Set;
import java.util.stream.Collectors;

public class ModBlockLootProvider extends BlockLootSubProvider {
    protected ModBlockLootProvider(HolderLookup.Provider registries) {
        super(Set.of(), FeatureFlags.DEFAULT_FLAGS, registries);
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return Sculknrun.BLOCKS.getEntries().stream().map(DeferredHolder::get).collect(Collectors.toUnmodifiableList());
    }

    @Override
    protected void generate() {
        add(Sculknrun.RESIN_TEARS.get(),
            block -> createSilkTouchDispatchTable(block,
                                                  LootItem.lootTableItem(Sculknrun.RESIN_TEAR)
                                                          .apply(
                                                                  SetItemCountFunction.setCount(UniformGenerator.between(2, 5))
                                                          )
            )
        );

        dropSelf(Sculknrun.SCULK_NODE.get());
        dropSelf(Sculknrun.SCULKSHROOM.get());
    }
}
