package com.example.sculkrot.datagen;

import com.example.sculkrot.block.ModBlocks;
import com.example.sculkrot.item.ModItems;
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
        return ModBlocks.BLOCKS.getEntries().stream().map(DeferredHolder::get).collect(Collectors.toUnmodifiableList());
    }

    @Override
    protected void generate() {
        add(
                ModBlocks.RESIN_TEARS.get(),
                block -> createSilkTouchDispatchTable(
                        block,
                        LootItem.lootTableItem(ModItems.RESIN_TEAR)
                                .apply(
                                        SetItemCountFunction.setCount(UniformGenerator.between(2, 5))
                                )
                )
        );

        dropSelf(ModBlocks.SCULK_NODE.get());
        dropSelf(ModBlocks.SCULKSHROOM.get());
    }
}
