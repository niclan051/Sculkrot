package com.example.sculkrot.block.blockentity;

import com.example.sculkrot.SculkrotMod;
import com.example.sculkrot.block.ModBlocks;
import net.minecraft.Util;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.util.datafix.fixes.References;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public final class ModBlockEntityTypes {
    private static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(
            BuiltInRegistries.BLOCK_ENTITY_TYPE, SculkrotMod.MODID);

    private static <B extends BlockEntity> DeferredHolder<BlockEntityType<?>,
            BlockEntityType<?>> register(String id, Supplier<BlockEntityType.Builder<B>> builder) {
        //noinspection DataFlowIssue
        return BLOCK_ENTITY_TYPES.register(
                id, () -> builder.get().build(Util.fetchChoiceType(
                        References.BLOCK_ENTITY,
                        id
                ))
        );
    }

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITY_TYPES.register(eventBus);
    }

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<?>> SCULK_NODE = register(
            "sculk_node",
            () -> BlockEntityType.Builder.of(SculkNodeBlockEntity::new, ModBlocks.SCULK_NODE.get())
    );


    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<?>> SCULKSHROOM = register(
            "sculkshroom",
            () -> BlockEntityType.Builder.of(SculkshroomBlockEntity::new, ModBlocks.SCULKSHROOM.get())
    );


}
