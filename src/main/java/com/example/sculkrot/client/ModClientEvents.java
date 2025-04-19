package com.example.sculkrot.client;

import com.example.sculkrot.SculkrotMod;
import com.example.sculkrot.item.ModItems;
import com.example.sculkrot.item.QuasarItem;
import com.example.sculkrot.particle.ModParticleTypes;
import com.example.sculkrot.particle.provider.ResinTearsParticleProvider;
import com.mojang.logging.LogUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.item.ItemProperties;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;
import org.slf4j.Logger;
import team.lodestar.lodestone.systems.particle.world.type.LodestoneWorldParticleType;

@EventBusSubscriber(modid = SculkrotMod.MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public final class ModClientEvents {
    private static final Logger LOGGER = LogUtils.getLogger();

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        // Some client setup code
        LOGGER.info("HELLO FROM CLIENT SETUP");
        LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());
        ItemProperties.register(
                ModItems.QUASAR.asItem(), QuasarItem.PULL,
                (stack, level, livingEntity, seed) -> {
                    if (livingEntity == null || !livingEntity.isUsingItem()) {
                        return 0;
                    }
                    return (float) (stack.getUseDuration(livingEntity) - livingEntity.getUseItemRemainingTicks()) /
                           stack.getUseDuration(livingEntity);
                }
        );
        ItemProperties.register(
                ModItems.QUASAR.asItem(), QuasarItem.CHARGED, (stack, level, livingEntity, seed) ->
                        QuasarItem.isCharged(stack) ? 1 : 0
        );
        ItemProperties.register(
                ModItems.QUASAR.asItem(), QuasarItem.PULLING, (stack, level, livingEntity, seed) ->
                        livingEntity != null && livingEntity.isUsingItem() && livingEntity.getUseItem() == stack ? 1
                                                                                                                 : 0
        );
    }

    @SubscribeEvent
    public static void onRegisterParticleProviders(RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(ModParticleTypes.QUASAR_BOLT.get(), LodestoneWorldParticleType.Factory::new);
        event.registerSpriteSet(ModParticleTypes.QUASAR_BEAM.get(), LodestoneWorldParticleType.Factory::new);
        event.registerSpriteSet(ModParticleTypes.RESIN_TEARS.get(), ResinTearsParticleProvider::new);
    }
}
