package com.example.sculkrot.client.model.layered;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

public class SculkArmourModel extends HumanoidModel {
    public SculkArmourModel(ModelPart root) {
        super(root);
    }

    public static LayerDefinition createArmorLayer(CubeDeformation deformation) {
        MeshDefinition meshdefinition = HumanoidModel.createMesh(deformation, 0.0F);
        PartDefinition partdefinition = meshdefinition.getRoot();
        PartDefinition head = partdefinition.getChild("head");
        PartDefinition body = partdefinition.getChild("body");
        PartDefinition leftLeg = partdefinition.getChild("left_leg");
        PartDefinition rightLeg = partdefinition.getChild("right_leg");
        PartDefinition leftArm = partdefinition.getChild("left_arm");
        PartDefinition rightArm = partdefinition.getChild("right_arm");

        return LayerDefinition.create(meshdefinition, 128, 128);
    }
}
