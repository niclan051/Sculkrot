package com.example.sculkrot.block;

import com.example.sculkrot.SculkrotMod;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.VineBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Function;

public final class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(SculkrotMod.MODID);
    public static final DeferredBlock<SculkNodeBlock> SCULK_NODE = register(
            "sculk_node", SculkNodeBlock::new, BlockBehaviour.Properties.of().mapColor(MapColor.STONE)
    );
    public static final DeferredBlock<VineBlock> RESIN_TEARS = register(
            "resin_tears",
            ResinTearsBlock::new,
            BlockBehaviour.Properties.ofFullCopy(Blocks.VINE)
                                     .emissiveRendering((state, blockGetter, pos) -> true)
                                     .lightLevel(state -> 7)
    );
    public static final DeferredBlock<SculkshroomBlock> SCULKSHROOM = register(
            "sculkshroom",
            SculkshroomBlock::new,
            BlockBehaviour.Properties.ofFullCopy(Blocks.RED_MUSHROOM)
    );

    private static <T extends Block> DeferredBlock<T> register(String id,
                                                               Function<BlockBehaviour.Properties, T> factory,
                                                               BlockBehaviour.Properties properties) {
        return BLOCKS.registerBlock(id, factory, properties);
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
