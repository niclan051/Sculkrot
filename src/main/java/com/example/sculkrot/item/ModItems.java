package com.example.sculkrot.item;

import com.example.sculkrot.SculkrotMod;
import com.example.sculkrot.armor.ModArmorMaterials;
import com.example.sculkrot.block.ModBlocks;
import com.example.sculkrot.item.tier.SculkTier;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.level.block.Block;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Function;
import java.util.function.Supplier;

public final class ModItems {
    private static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(SculkrotMod.MODID);

    public static final DeferredItem<BlockItem> SCULK_NODE = registerSimpleBlockItem(
            "sculk_node", ModBlocks.SCULK_NODE::get);
    public static final DeferredItem<BlockItem> RESIN_TEARS = registerSimpleBlockItem(
            "resin_tears", ModBlocks.RESIN_TEARS::get);
    public static final DeferredItem<BlockItem> SCULKSHROOM = registerSimpleBlockItem(
            "sculkshroom", ModBlocks.SCULKSHROOM::get);

    public static final DeferredItem<Item> RESIN_TEAR = registerSimple("resin_tear");
    public static final DeferredItem<Item> RESIN_INGOT = registerSimple("resin_ingot");
    // Creates a new food item with the id "examplemod:example_id", nutrition 1 and saturation 2
    public static final DeferredItem<Item> SCULK_WINE = register(
            "sculk_wine", Item::new, new Item.Properties().food(new FoodProperties.Builder()
                                                                        .alwaysEdible().nutrition(4)
                                                                        .saturationModifier(2f)
                                                                        .build())
    );

    public static final DeferredItem<Item> SUPERSONIC_BOLT = registerSimple("supersonic_bolt");
    public static final DeferredItem<QuasarItem> QUASAR = register(
            "quasar", QuasarItem::new, new Item.Properties().stacksTo(1)
    );
    public static final DeferredItem<ArmorItem> SCULK_HELMET = register(
            "sculk_helmet", properties -> new SculkHelmetItem(
                    ModArmorMaterials.SCULK,
                    ArmorItem.Type.HELMET,
                    properties
            )
    );
    public static final DeferredItem<SwordItem> SCULKHANDER1 = register(
            "sculkhander1",
            properties -> new SwordItem(new SculkTier(), properties),
            new Item.Properties()
                    .stacksTo(1)
                    .attributes(SwordItem.createAttributes(new SculkTier(), 3, -2.4F))
    );
    public static final DeferredItem<SwordItem> SCULKHANDER2 = register(
            "sculkhander2",
            properties -> new SwordItem(new SculkTier(), properties),
            new Item.Properties()
                    .stacksTo(1)
                    .attributes(SwordItem.createAttributes(new SculkTier(), 3, -2.4F))
    );

    private static <T extends Item> DeferredItem<T> register(String id,
                                                             Function<Item.Properties, T> factory,
                                                             Item.Properties properties) {
        return ITEMS.registerItem(id, factory, properties);
    }

    private static <T extends Item> DeferredItem<T> register(String id,
                                                             Function<Item.Properties, T> factory) {
        return register(id, factory, new Item.Properties());
    }

    private static DeferredItem<Item> registerSimple(String id) {
        return register(id, Item::new);
    }

    private static DeferredItem<BlockItem> registerBlockItem(String id, Supplier<Block> block,
                                                             Item.Properties properties) {
        return ITEMS.registerSimpleBlockItem(id, block, properties);
    }

    private static DeferredItem<BlockItem> registerSimpleBlockItem(String id, Supplier<Block> block) {
        return registerBlockItem(id, block, new Item.Properties());
    }

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
