package com.example.sculknrun.armor;

import com.example.sculknrun.Sculknrun;
import net.minecraft.Util;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.EnumMap;
import java.util.List;
import java.util.function.Supplier;

public final class ModArmorMaterials {
    private static final DeferredRegister<ArmorMaterial> ARMOR_MATERIALS =
            DeferredRegister.create(BuiltInRegistries.ARMOR_MATERIAL, Sculknrun.MODID);

    public static final DeferredHolder<ArmorMaterial, ArmorMaterial> SCULK = register(
            "silly", () -> new ArmorMaterial(
                    Util.make(
                            new EnumMap<>(ArmorItem.Type.class), map -> {
                                map.put(ArmorItem.Type.HELMET, 2);
                            }
                    ),
                    0,
                    SoundEvents.ARMOR_EQUIP_GENERIC,
                    () -> Ingredient.EMPTY,
                    List.of(new ArmorMaterial.Layer(ResourceLocation.fromNamespaceAndPath(Sculknrun.MODID, "sculk"))),
                    0,
                    0
            )
    );

    private static DeferredHolder<ArmorMaterial, ArmorMaterial> register(String id,
                                                                         Supplier<ArmorMaterial> material) {
        return ARMOR_MATERIALS.register(id, material);
    }

    public static void register(IEventBus eventBus) {
        ARMOR_MATERIALS.register(eventBus);
    }
}
