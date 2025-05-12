package com.example.sculkrot.network;

import com.example.sculkrot.SculkrotMod;
import com.example.sculkrot.common.infection.InfectionSavedData;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import team.lodestar.lodestone.registry.common.LodestoneNetworkPayloads;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD, modid = SculkrotMod.MODID)
public final class ModPacketPayloads {
    public static final LodestoneNetworkPayloads.PayloadRegistryHelper CHANNEL =
            new LodestoneNetworkPayloads.PayloadRegistryHelper(SculkrotMod.MODID);

    @SubscribeEvent
    public static void register(final RegisterPayloadHandlersEvent event) {
        final PayloadRegistrar registrar = event.registrar("1");
        registrar.playToClient(
                InfectionSavedData.UpdatePayload.TYPE,
                InfectionSavedData.UpdatePayload.STREAM_CODEC,
                (payload, context) -> SculkrotMod.infectionLevel = payload.infectionLevel()
        );

        CHANNEL.playToClient(registrar, "quasar_ray", QuasarRayPayload.class, QuasarRayPayload::new);
    }
}
