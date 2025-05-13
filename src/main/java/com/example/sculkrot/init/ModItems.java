package com.example.sculkrot.init;

import com.example.sculkrot.SculkrotMod;
import com.example.sculkrot.common.armor.SculkArmourItem;
import com.example.sculkrot.common.item.QuasarItem;
import com.example.sculkrot.common.item.SculkHelmetItem;
import com.example.sculkrot.common.item.tier.SculkTier;
import com.example.sculkrot.utils.RegistryUtils;
import net.minecraft.core.Holder;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.level.block.Block;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.Collection;
import java.util.function.Function;
import java.util.function.Supplier;

public final class ModItems {
    public static final DeferredRegister.Items ITEMS = RegistryUtils.createRegister(DeferredRegister::createItems);

    public static final DeferredItem<Item> RESIN_TEAR = registerSimple("resin_tear");
    public static final DeferredItem<Item> RESIN_INGOT = registerSimple("resin_ingot");
    public static final DeferredItem<Item> SCULK_WINE = register("sculk_wine", Item::new, new Item.Properties().food(new FoodProperties.Builder().alwaysEdible().nutrition(4).saturationModifier(2f).build()));

    public static final DeferredItem<Item> SUPERSONIC_BOLT = registerSimple("supersonic_bolt");
    public static final DeferredItem<QuasarItem> QUASAR = register(
            "quasar", QuasarItem::new, new Item.Properties().stacksTo(1)
    );
    public static final DeferredItem<ArmorItem> SCULK_HELMET = register(
            "sculk_helmet", properties -> new SculkArmourItem(ArmorItem.Type.HELMET, properties)
    );
    public static final DeferredItem<SwordItem> SCULKHANDER = register(
            "sculkhander",
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

    static DeferredItem<BlockItem> registerBlockItem(Holder<Block> blockHolder, Item.Properties properties) {
        return ITEMS.registerSimpleBlockItem(blockHolder, properties);
    }

    private static DeferredItem<BlockItem> registerSimpleBlockItem(Holder<Block> blockHolder) {
        return registerBlockItem(blockHolder, new Item.Properties());
    }

    public static void staticInit() {
    }

    public static Collection<DeferredHolder<Item, ? extends Item>> getItems() {
        return ITEMS.getEntries();
    }
}
