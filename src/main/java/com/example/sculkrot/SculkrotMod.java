package com.example.sculkrot;

import com.example.sculkrot.armor.ModArmorMaterials;
import com.example.sculkrot.block.ModBlocks;
import com.example.sculkrot.block.blockentity.ModBlockEntityTypes;
import com.example.sculkrot.datagen.SculkrotDataGenerator;
import com.example.sculkrot.effect.ModMobEffects;
import com.example.sculkrot.item.ModItems;
import com.example.sculkrot.item.component.ModDataComponentTypes;
import com.example.sculkrot.particle.ModParticleTypes;
import com.example.sculkrot.potion.ModPotions;
import com.mojang.logging.LogUtils;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(SculkrotMod.MODID)
public class SculkrotMod {
    // Define mod id in a common place for everything to reference
    public static final String MODID = "sculkrot";
    // Create a Deferred Register to hold CreativeModeTabs which will all be registered under the "examplemod" namespace
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(
            Registries.CREATIVE_MODE_TAB, MODID);
    // Create a Deferred Register to hold Items which will all be registered under the "examplemod" namespace
    // Creates a creative tab with the id "examplemod:example_tab" for the example item, that is placed after the
    // combat tab
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> EXAMPLE_TAB = CREATIVE_MODE_TABS.register(
            MODID, () -> CreativeModeTab.builder()
                                        .title(Component.translatable(
                                                "itemGroup.sculkrot")) //The language key for the title of
                                        // your CreativeModeTab
                                        .withTabsBefore(CreativeModeTabs.COMBAT)
                                        .icon(() -> ModItems.SCULK_WINE.get().getDefaultInstance())
                                        .displayItems((parameters, output) -> {
                                            output.accept(
                                                    ModItems.SCULK_WINE.get()); // Add the example item to the
                                            // tab. For
                                            // your own tabs, this method is preferred over the event
                                        }).build()
    );
    private static final Logger LOGGER = LogUtils.getLogger();
    // Directly reference a slf4j logger
    public static int infectionLevel = 0;

    // The constructor for the mod class is the first code that is run when your mod is loaded.
    // FML will recognize some parameter types like IEventBus or ModContainer and pass them in automatically.
    public SculkrotMod(IEventBus modEventBus) {
        modEventBus.addListener(SculkrotDataGenerator::gatherData);

        ModBlocks.register(modEventBus);
        ModDataComponentTypes.register(modEventBus);
        ModArmorMaterials.register(modEventBus);
        ModItems.register(modEventBus);

        CREATIVE_MODE_TABS.register(modEventBus);

        ModMobEffects.register(modEventBus);
        ModPotions.register(modEventBus);
        ModBlockEntityTypes.register(modEventBus);
        ModParticleTypes.register(modEventBus);

        // Register our mod's ModConfigSpec so that FML can create and load the config file for us
        ModLoadingContext.get().getActiveContainer().registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }
}
