package com.example.sculknrun;

import com.example.sculknrun.armor.ModArmorMaterials;
import com.example.sculknrun.block.SculkNodeBlock;
import com.example.sculknrun.block.blockentity.ModBlockEntityTypes;
import com.example.sculknrun.datagen.SculknrunDataGenerator;
import com.example.sculknrun.effect.ModMobEffects;
import com.example.sculknrun.item.QuasarItem;
import com.example.sculknrun.item.SculkHelmetItem;
import com.example.sculknrun.item.component.ModDataComponentTypes;
import com.example.sculknrun.particle.ModParticleTypes;
import com.mojang.logging.LogUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.slf4j.Logger;
import team.lodestar.lodestone.systems.particle.world.type.LodestoneWorldParticleType;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(Sculknrun.MODID)
public class Sculknrun {
    // Define mod id in a common place for everything to reference
    public static final String MODID = "sculknrun";
    // Create a Deferred Register to hold Blocks which will all be registered under the "examplemod" namespace
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(MODID);
    // Creates a new Block with the id "examplemod:example_block", combining the namespace and path
    public static final DeferredBlock<SculkNodeBlock> SCULK_NODE = BLOCKS.register(
            "sculk_node", () -> new SculkNodeBlock(
                    BlockBehaviour.Properties.of().mapColor(MapColor.STONE))
    );
    // Create a Deferred Register to hold Items which will all be registered under the "examplemod" namespace
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MODID);
    // Creates a new BlockItem with the id "examplemod:example_block", combining the namespace and path
    public static final DeferredItem<BlockItem> SCULK_NODE_ITEM = ITEMS.registerSimpleBlockItem(
            "sculk_node", SCULK_NODE);
    // Creates a new food item with the id "examplemod:example_id", nutrition 1 and saturation 2
    public static final DeferredItem<Item> SCULK_WINE = ITEMS.registerSimpleItem("sculk_wine", new Item.Properties().food(new FoodProperties.Builder()
            .alwaysEdible().nutrition(4).saturationModifier(2f).build()));

    public static final DeferredItem<Item> SUPERSONIC_BOLT = ITEMS.registerSimpleItem("supersonic_bolt");
    public static final DeferredItem<QuasarItem> QUASAR = ITEMS.register(
            "quasar",
            () -> new QuasarItem(new Item.Properties().stacksTo(1))
    );
    public static final DeferredItem<ArmorItem> SCULK_HELMET = ITEMS.register("sculk_helmet", () -> new SculkHelmetItem(
            ModArmorMaterials.SCULK,
            ArmorItem.Type.HELMET,
            new Item.Properties().durability(ArmorItem.Type.HELMET.getDurability(15))
    ));
    // Create a Deferred Register to hold CreativeModeTabs which will all be registered under the "examplemod" namespace
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(
            Registries.CREATIVE_MODE_TAB, MODID);
    // Creates a creative tab with the id "examplemod:example_tab" for the example item, that is placed after the
    // combat tab
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> EXAMPLE_TAB = CREATIVE_MODE_TABS.register(
            "sculknrun", () -> CreativeModeTab.builder()
                                              .title(Component.translatable(
                                                      "itemGroup.sculknrun")) //The language key for the title of
                                              // your CreativeModeTab
                                              .withTabsBefore(CreativeModeTabs.COMBAT)
                                              .icon(() -> SCULK_WINE.get().getDefaultInstance())
                                              .displayItems((parameters, output) -> {
                                                  output.accept(
                                                          SCULK_WINE.get()); // Add the example item to the tab. For
                                                  // your own tabs, this method is preferred over the event
                                              }).build()
    );
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();

    // The constructor for the mod class is the first code that is run when your mod is loaded.
    // FML will recognize some parameter types like IEventBus or ModContainer and pass them in automatically.
    public Sculknrun(IEventBus modEventBus) {
        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(SculknrunDataGenerator::gatherData);

        // Register the Deferred Register to the mod event bus so blocks get registered
        BLOCKS.register(modEventBus);
        ModDataComponentTypes.register(modEventBus);
        ModArmorMaterials.register(modEventBus);
        // Register the Deferred Register to the mod event bus so items get registered
        ITEMS.register(modEventBus);

        // Register the Deferred Register to the mod event bus so tabs get registered
        CREATIVE_MODE_TABS.register(modEventBus);

        ModMobEffects.register(modEventBus);
        ModBlockEntityTypes.register(modEventBus);
        ModParticleTypes.register(modEventBus);

        // Register ourselves for server and other game events we are interested in.
        // Note that this is necessary if and only if we want *this* class (ExampleMod) to respond directly to events.
        // Do not add this line if there are no @SubscribeEvent-annotated functions in this class, like
        // onServerStarting() below.
        NeoForge.EVENT_BUS.register(this);

        // Register the item to a creative tab
        modEventBus.addListener(this::addCreative);

        // Register our mod's ModConfigSpec so that FML can create and load the config file for us
        ModLoadingContext.get().getActiveContainer().registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        // Some common setup code
        LOGGER.info("HELLO FROM COMMON SETUP");

        if (Config.logDirtBlock)
            LOGGER.info("DIRT BLOCK >> {}", BuiltInRegistries.BLOCK.getKey(Blocks.DIRT));

        LOGGER.info(Config.magicNumberIntroduction + Config.magicNumber);

        Config.items.forEach((item) -> LOGGER.info("ITEM >> {}", item.toString()));
    }

    // Add the example block item to the building blocks tab
    private void addCreative(BuildCreativeModeTabContentsEvent event)
    {
        if (event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS)
            event.accept(SCULK_NODE_ITEM);
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        // Do something when the server starts
        LOGGER.info("HELLO from server starting");
    }
    public static int infectionLevel = 0;

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @EventBusSubscriber(modid = MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            // Some client setup code
            LOGGER.info("HELLO FROM CLIENT SETUP");
            LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());
            ItemProperties.register(
                    QUASAR.asItem(), QuasarItem.PULL,
                    (stack, level, livingEntity, seed) -> {
                        if (livingEntity == null || !livingEntity.isUsingItem()) {
                            return 0;
                        }
                        return (float) (stack.getUseDuration(livingEntity) - livingEntity.getUseItemRemainingTicks()) /
                               stack.getUseDuration(livingEntity);
                    }
            );
            ItemProperties.register(
                    QUASAR.asItem(), QuasarItem.CHARGED, (stack, level, livingEntity, seed) ->
                            QuasarItem.isCharged(stack) ? 1 : 0
            );
            ItemProperties.register(
                    QUASAR.asItem(), QuasarItem.PULLING, (stack, level, livingEntity, seed) ->
                            livingEntity != null && livingEntity.isUsingItem() && livingEntity.getUseItem() == stack ? 1
                                                                                                                     : 0
            );
        }

        @SubscribeEvent
        public static void onRegisterParticleProviders(RegisterParticleProvidersEvent event) {
            event.registerSpriteSet(ModParticleTypes.QUASAR_BOLT.get(), LodestoneWorldParticleType.Factory::new);
        }
    }
}
