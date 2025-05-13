package com.example.sculkrot.init.client;

import com.example.sculkrot.SculkrotMod;
import com.example.sculkrot.client.model.layered.SculkArmourModel;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

@EventBusSubscriber(modid = SculkrotMod.MODID, value = Dist.CLIENT, bus = EventBusSubscriber.Bus.MOD)
public class ModelRegistry {

    public static SculkArmourModel SCULK_ARMOUR;

    @SubscribeEvent
    public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(SculkArmourModel.LAYER, SculkArmourModel::createBodyLayer);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @SubscribeEvent
    public static void registerLayers(EntityRenderersEvent.AddLayers event) {
        SCULK_ARMOUR = new SculkArmourModel(event.getEntityModels().bakeLayer(SculkArmourModel.LAYER));
    }
}
