package com.example.sculkrot.common.armor;

import com.example.sculkrot.SculkrotMod;
import com.example.sculkrot.init.ModArmorMaterials;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import team.lodestar.lodestone.systems.item.LodestoneArmorItem;

import java.util.List;

public class SculkArmourItem extends LodestoneArmorItem {


    public SculkArmourItem(Type pType, Properties pProperties) {
        super(ModArmorMaterials.SCULK, pType, pProperties);
    }

    @Override
    public ResourceLocation getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, ArmorMaterial.Layer layer, boolean innerModel) {
        return SculkrotMod.asResource("textures/armor/sculk.png");
    }

    @Override
    public List<ItemAttributeModifiers.Entry> createExtraAttributes() {
        return List.of();
    }
}
