package com.example.sculkrot.client.model.layered;

import com.example.sculkrot.SculkrotMod;
import com.google.common.collect.ImmutableList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import team.lodestar.lodestone.systems.model.LodestoneArmorModel;

public class SculkArmourModel extends LodestoneArmorModel {
    public static ModelLayerLocation LAYER = new ModelLayerLocation(SculkrotMod.asResource("sculk_armour"), "main");

    public SculkArmourModel(ModelPart root) {
        super(root);
    }

    @Override
    protected Iterable<ModelPart> bodyParts() {
        if (slot == EquipmentSlot.CHEST) {
            return ImmutableList.of(body, leftArm, rightArm);
        } else if (slot == EquipmentSlot.LEGS) {
            return ImmutableList.of(leftLegging, rightLegging, leggings);
        } else if (slot == EquipmentSlot.FEET) {
            return ImmutableList.of(leftFoot, rightFoot);
        } else return ImmutableList.of();
    }

    @Override
    public void copyFromDefault(HumanoidModel model) {
        super.copyFromDefault(model);
        //cape.copyFrom(model.body);
    }

    @Override
    public void setupAnim(LivingEntity pEntity, float pLimbSwing, float pLimbSwingAmount, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {
        float pPartialTicks = Minecraft.getInstance().getFrameTimeNs();
        var gay = true;
        super.setupAnim(pEntity, pLimbSwing, pLimbSwingAmount, pAgeInTicks, pNetHeadYaw, pHeadPitch);
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = HumanoidModel.createMesh(new CubeDeformation(0), 0);
        PartDefinition root = createHumanoidAlias(mesh);

        PartDefinition body = root.getChild("body");

        PartDefinition leggings = root.getChild("leggings");

        PartDefinition right_legging = root.getChild("right_legging");

        PartDefinition right_foot = root.getChild("right_foot");

        PartDefinition right_arm = root.getChild("right_arm");

        PartDefinition left_legging = root.getChild("left_legging");

        PartDefinition left_foot = root.getChild("left_foot");

        PartDefinition left_arm = root.getChild("left_arm");

        PartDefinition head = root.getChild("head");
        PartDefinition mask = head.addOrReplaceChild("mask", CubeListBuilder.create().texOffs(0, 0).addBox(-4.5F, -8.5F, -4.5F, 9.0F, 9.0F, 9.0F, new CubeDeformation(0.0F))
                .texOffs(36, 14).addBox(-2.0F, -2.5F, 4.5F, 4.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(36, 4).addBox(-5.5F, -1.5F, 4.5F, 11.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 36).addBox(-5.0F, -0.5F, -5.5F, 10.0F, 1.0F, 10.0F, new CubeDeformation(0.0F))
                .texOffs(0, 18).addBox(-4.5F, -8.5F, -4.5F, 9.0F, 9.0F, 9.0F, new CubeDeformation(0.25F)), PartPose.offset(0.0F, 0.0F, 0.5F));

        PartDefinition mask_filter = head.addOrReplaceChild("mask_filter", CubeListBuilder.create().texOffs(36, 0).addBox(-12.0F, -2.0F, -0.5F, 11.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(48, 11).addBox(-8.5F, -4.0F, -1.5F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(6.5F, 0.6F, -5.0F, 0.3927F, 0.0F, 0.0F));

        return LayerDefinition.create(mesh, 128, 128);
    }
}
