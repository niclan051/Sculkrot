package com.example.sculkrot.init;

import com.example.sculkrot.utils.RegistryUtils;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Consumer;

public class ModCreativeTabs {
    private static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = RegistryUtils.createRegister(Registries.CREATIVE_MODE_TAB);
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> SCULKROT_TAB;

    static {
        SCULKROT_TAB = registerTabSearchBar("calamos", ModItems.RESIN_INGOT, output -> {

            for (DeferredHolder<Item, ? extends Item> registry : ModItems.getItems()) {
                if (registry.get() instanceof BlockItem) continue;
                output.accept(registry.get());
            }
            for (DeferredHolder<Block, ? extends Block> registry : ModBlocks.getBlocks()) {
                output.accept(registry.get());
            }

        }, builder -> builder.withTabsBefore(CreativeModeTabs.COMBAT));
    }

    private static DeferredHolder<CreativeModeTab, CreativeModeTab> registerTabSearchBar(String name, Holder<Item> icon, Consumer<CreativeModeTab.Output> displayItems, Consumer<CreativeModeTab.Builder> additionalProperties) {
        return CREATIVE_MODE_TABS.register(name, id -> {
            final CreativeModeTab.Builder builder = CreativeModeTab.builder();
            builder.title(Component.translatable(id.toLanguageKey("itemGroup")))
                    .icon(() -> new ItemStack(icon))
                    .withSearchBar()
                    .displayItems((pParameters, pOutput) -> displayItems.accept(pOutput));
            additionalProperties.accept(builder);
            return builder.build();
        });
    }

    public static void staticInit() {

    }
}
