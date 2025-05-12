package com.example.sculkrot.init;

import com.example.sculkrot.SculkrotMod;
import com.example.sculkrot.common.block.ResinTearsBlock;
import com.example.sculkrot.common.block.SculkNodeBlock;
import com.example.sculkrot.common.block.SculkshroomBlock;
import com.example.sculkrot.utils.RegistryUtils;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.VineBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.Collection;
import java.util.function.Function;
import java.util.function.Supplier;

public final class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS = RegistryUtils.createRegister(DeferredRegister::createBlocks);

    public static final DeferredBlock<SculkNodeBlock> SCULK_NODE = register("sculk_node", () -> new SculkNodeBlock(BlockBehaviour.Properties.of().mapColor(MapColor.STONE)), new Item.Properties());
    public static final DeferredBlock<VineBlock> RESIN_TEARS = register(
            "resin_tears", () -> new ResinTearsBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.VINE).emissiveRendering((state, blockGetter, pos) -> true).lightLevel(state -> 7))
            , new Item.Properties());
    public static final DeferredBlock<SculkshroomBlock> SCULKSHROOM = register(
            "sculkshroom",
            () -> new SculkshroomBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.RED_MUSHROOM))
            , new Item.Properties());

    private static <T extends Block> DeferredBlock<T> register(String id, Supplier<T> block, Item.Properties pIProp) {
        DeferredBlock<T> toReturn = BLOCKS.register(id.toLowerCase(), block);
        makeBlockItem(toReturn, pIProp);
        return toReturn;
    }
    private static <T extends Block> DeferredBlock<T> registerNoItem(String id, Supplier<T> block) {
        return BLOCKS.register(id.toLowerCase(), block);
    }
    private static <T extends Block> void makeBlockItem(DeferredBlock<T> block, Item.Properties pIProp) {
        ModItems.registerBlockItem(block, pIProp);
    }

    public static void staticInit() {
    }

    public static Collection<DeferredHolder<Block, ? extends Block>> getBlocks() {
        return BLOCKS.getEntries();
    }
}
