package com.example.sculkrot.client.extension;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import team.lodestar.lodestone.systems.model.LodestoneArmorModel;

import java.util.function.Supplier;

public class ArmorClientItemExtensions implements IClientItemExtensions {
    private final Supplier<LodestoneArmorModel> model;

    public ArmorClientItemExtensions(Supplier<LodestoneArmorModel> model) {
        this.model = model;
    }

    @Override
    public LodestoneArmorModel getHumanoidArmorModel(LivingEntity entity, ItemStack itemStack, EquipmentSlot armorSlot, HumanoidModel _default) {
        float pticks = (float) (Minecraft.getInstance().getFrameTimeNs() / 20000000000L);
        float f = Mth.rotLerp(pticks, entity.yBodyRotO, entity.yBodyRot);
        float f1 = Mth.rotLerp(pticks, entity.yHeadRotO, entity.yHeadRot);
        float netHeadYaw = f1 - f;
        float netHeadPitch = Mth.lerp(pticks, entity.xRotO, entity.getXRot());
        LodestoneArmorModel model = this.model.get();
        model.slot = armorSlot;
        model.copyFromDefault(_default);
        model.setupAnim(entity, entity.walkAnimation.position(), entity.walkAnimation.speed(), entity.tickCount + pticks, netHeadYaw, netHeadPitch);
        return model;
    }
}
