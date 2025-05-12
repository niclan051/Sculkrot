package com.example.sculkrot.init;

import com.example.sculkrot.SculkrotMod;
import com.example.sculkrot.utils.RegistryUtils;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;
import java.util.function.UnaryOperator;

public final class ModDataComponentTypes {
    private static final DeferredRegister<DataComponentType<?>> DATA_COMPONENT_TYPES = RegistryUtils.createRegister(Registries.DATA_COMPONENT_TYPE);

    public static final Supplier<DataComponentType<Boolean>> QUASAR_CHARGED =
            register("charged", () -> DataComponentType.<Boolean>builder().persistent(Codec.BOOL).networkSynchronized(ByteBufCodecs.BOOL).build());

    private static <T> DeferredHolder<DataComponentType<?>, DataComponentType<T>> register(String name, Supplier<DataComponentType<T>> builder) {
        return DATA_COMPONENT_TYPES.register(name, builder);
    }

    public static void staticInit() {
    }

}
