package com.example.sculkrot;

import com.example.sculkrot.init.*;
import com.example.sculkrot.datagen.SculkrotDataGenerator;
import com.mojang.logging.LogUtils;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
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
    private static SculkrotMod INSTANCE;
    public static final String MODID = "sculkrot";
    private static final Logger LOGGER = LogUtils.getLogger();
    public static int infectionLevel = 0;
    private final IEventBus modEventBus;

    public SculkrotMod(IEventBus modEventBus) {
        this.modEventBus = modEventBus;
        INSTANCE = this;

        ModBlocks.staticInit();
        ModDataComponentTypes.staticInit();
        ModArmorMaterials.staticInit();
        ModItems.staticInit();
        ModCreativeTabs.staticInit();

        ModMobEffects.staticInit();
        ModPotions.staticInit();
        ModBlockEntityTypes.staticInit();
        ModParticleTypes.staticInit();

        // Register our mod's ModConfigSpec so that FML can create and load the config file for us
        ModLoadingContext.get().getActiveContainer().registerConfig(ModConfig.Type.COMMON, Config.SPEC);
        this.modEventBus.addListener(SculkrotDataGenerator::gatherData);
        //this.modEventBus.register(this);
    }

    public static IEventBus getEventBus() {
        return INSTANCE.modEventBus;
    }

    public static ResourceLocation asResource(String path) {
        return ResourceLocation.fromNamespaceAndPath(MODID, path);
    }
}
