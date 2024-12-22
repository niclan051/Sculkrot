package com.example.sculknrun.network;

import com.example.sculknrun.particle.ModParticleTypes;
import com.example.sculknrun.utils.RayUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import team.lodestar.lodestone.systems.easing.Easing;
import team.lodestar.lodestone.systems.network.OneSidedPayloadData;
import team.lodestar.lodestone.systems.particle.builder.WorldParticleBuilder;
import team.lodestar.lodestone.systems.particle.data.GenericParticleData;
import team.lodestar.lodestone.systems.particle.world.behaviors.components.DirectionalBehaviorComponent;

public class QuasarRayPayload extends OneSidedPayloadData {
    private Vec3 start;
    private Vec3 end;

    public QuasarRayPayload(FriendlyByteBuf byteBuf) {
        this(byteBuf.readVec3(), byteBuf.readVec3());
    }

    public QuasarRayPayload(Vec3 start, Vec3 end) {
        this.start = start;
        this.end = end;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void handle(IPayloadContext context) {
        WorldParticleBuilder deathRayParticles =
                WorldParticleBuilder.create(
                                ModParticleTypes.QUASAR_BOLT,
                                new DirectionalBehaviorComponent(this.end.subtract(this.start))
                        )
                        .setScaleData(
                                GenericParticleData.create(3f, 0f)
                                        .setEasing(new Easing("fast") {
                                            @Override
                                            public float ease(float value, float min, float max, float time) {
                                                return (float) (
                                                        Math.pow(value / (time / 3), 2.5) *
                                                        max + min);
                                            }
                                        }).build())
                        .setTransparencyData(GenericParticleData.create(1f).build())
                        .setLifetime(40)
                        .enableNoClip();

        RayUtils.performOnRay(
                this.start, this.end, 0.25, pos -> {
                    deathRayParticles.spawn(Minecraft.getInstance().level, pos.x(), pos.y(), pos.z());
                }
        );

        WorldParticleBuilder shockwaveParticles =
                WorldParticleBuilder.create(
                                ModParticleTypes.QUASAR_BOLT,
                                new DirectionalBehaviorComponent(this.end.subtract(this.start))
                        )
                        .setTransparencyData(GenericParticleData.create(0.75f, 0f).build())
                        .setLifetime(100)
                        .enableNoClip();

        RayUtils.performOnRay(
                this.start, this.end, 2, pos -> {
                    Vec3 diff = pos.subtract(start);
                    shockwaveParticles.setScaleData(
                            GenericParticleData.create(2f, (float) (2.5f + diff.length() / 10)).build()
                    ).spawn(Minecraft.getInstance().level, pos.x(), pos.y(), pos.z());
                }
        );

    }

    @Override
    public void serialize(FriendlyByteBuf byteBuf) {
        byteBuf.writeVec3(this.start);
        byteBuf.writeVec3(this.end);
    }
}
