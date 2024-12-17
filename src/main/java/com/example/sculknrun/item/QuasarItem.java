package com.example.sculknrun.item;

import com.example.sculknrun.Sculknrun;
import com.example.sculknrun.item.component.ModDataComponentTypes;
import com.example.sculknrun.particle.ModParticleTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import team.lodestar.lodestone.systems.easing.Easing;
import team.lodestar.lodestone.systems.particle.builder.WorldParticleBuilder;
import team.lodestar.lodestone.systems.particle.data.GenericParticleData;
import team.lodestar.lodestone.systems.particle.data.GenericParticleDataBuilder;
import team.lodestar.lodestone.systems.particle.world.behaviors.components.DirectionalBehaviorComponent;

import java.util.function.Consumer;
import java.util.function.Function;

@SuppressWarnings("NullableProblems") // SHUT THE FUCK UPPP
public class QuasarItem extends Item {
    public static final ResourceLocation MODEL_STANDBY = ResourceLocation.fromNamespaceAndPath(
            Sculknrun.MODID, "quasar");
    public static final ResourceLocation MODEL_CHARGE_1 = ResourceLocation.fromNamespaceAndPath(
            Sculknrun.MODID, "quasar_charge_1");
    public static final ResourceLocation MODEL_CHARGE_2 = ResourceLocation.fromNamespaceAndPath(
            Sculknrun.MODID, "quasar_charge_2");
    public static final ResourceLocation MODEL_CHARGE_3 = ResourceLocation.fromNamespaceAndPath(
            Sculknrun.MODID, "quasar_charge_3");
    public static final ResourceLocation MODEL_CHARGED = ResourceLocation.fromNamespaceAndPath(
            Sculknrun.MODID, "quasar_charged");
    public static final ResourceLocation PULLING = ResourceLocation.fromNamespaceAndPath(Sculknrun.MODID, "pulling");
    public static final ResourceLocation PULL = ResourceLocation.fromNamespaceAndPath(Sculknrun.MODID, "pull");
    public static final ResourceLocation CHARGED = ResourceLocation.fromNamespaceAndPath(Sculknrun.MODID, "charged");


    public QuasarItem(Properties pProperties) {
        super(pProperties);
    }

    public static boolean isCharged(ItemStack stack) {
        return stack.getOrDefault(ModDataComponentTypes.QUASAR_CHARGED, false);
    }

    public static void setCharged(ItemStack stack, boolean charged) {
        stack.set(ModDataComponentTypes.QUASAR_CHARGED, charged);
    }

    public static void shoot(Level level, LivingEntity user, ItemStack stack) {
        Vec3 eyePos = user.getEyePosition();

        var hitResult = level.clip(new ClipContext(
                eyePos, eyePos.add(Vec3.directionFromRotation(user.getRotationVector()).multiply(100, 100, 100)),
                ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, user
        ));
        Vec3 endPos = hitResult.getLocation();

        WorldParticleBuilder deathRayParticles = WorldParticleBuilder.create(ModParticleTypes.QUASAR_BOLT)
                .setScaleData(GenericParticleData.create(3f, 0f)
                .setEasing(new Easing("fast") {
                    @Override
                    public float ease(float value, float min, float max, float time) {
                        return (float) (Math.pow(value / (time / 3), 2.5) * max + min);
                    }
                }).build())
                .setTransparencyData(GenericParticleData.create(1f).build())
                .setLifetime(40)
                .enableNoClip();

        spawnRay(level, eyePos, endPos, 0.05, deathRayParticles,currentPos -> {
            AABB damageBox = AABB.encapsulatingFullBlocks(
                    BlockPos.containing(currentPos),
                    BlockPos.containing(currentPos)
            ).inflate(2);
            level.getEntities(user, damageBox, entity -> entity.position().distanceTo(currentPos) <= 2)
                 .forEach(entity -> entity.hurt(entity.damageSources().sonicBoom(user), 50));
        }, false);

        WorldParticleBuilder shockwaveParticles = WorldParticleBuilder.create(ModParticleTypes.QUASAR_BOLT)
                .setScaleData(GenericParticleData.create(3f, 10f).build())
                .setTransparencyData(GenericParticleData.create(0.75f, 0f).build())
                .setLifetime(100)
                .enableNoClip();
        spawnRay(level, eyePos, endPos, 2, shockwaveParticles, currentPos -> {
            AABB damageBox = AABB.encapsulatingFullBlocks(
                    BlockPos.containing(currentPos),
                    BlockPos.containing(currentPos)
            ).inflate(5);
            level.getEntities(user, damageBox, entity -> entity.position().distanceTo(currentPos) <= 5)
                 .forEach(entity -> entity.hurt(
                            entity.damageSources().sonicBoom(user),
                            (float) (20 / (entity.position().distanceTo(currentPos) * 4))
                        )
                 );
        }, true);

        Vec3 soundPos = user.getEyePosition().add(Vec3.directionFromRotation(user.getRotationVector()));
        level.playSound(
                null, soundPos.x(), soundPos.y(), soundPos.z(),
                SoundEvents.WARDEN_SONIC_BOOM, SoundSource.PLAYERS, 1, 1
        );
    }

    private static void spawnRay(Level level, Vec3 origin, Vec3 target, double step,
                                 WorldParticleBuilder particleBuilder,
                                 Consumer<Vec3> onIteration, boolean isShockwave) {
        Vec3 diff = target.subtract(origin);
        Vec3 diffNormalized = diff.normalize();
        for (double i = 1; i < diff.length(); i += step) {
            Vec3 currentPos = origin.add(diffNormalized.multiply(i, i, i));

            onIteration.accept(currentPos);

            particleBuilder.setBehavior(new DirectionalBehaviorComponent(diffNormalized));
            if (isShockwave) {
                particleBuilder.setScaleData(GenericParticleData.create(2f, (float) (2.5f + 10f * (i / diff.length()))).build());
            }
            particleBuilder.spawn(level, currentPos.x, currentPos.y, currentPos.z);
        }
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return isCharged(stack) ? super.getUseDuration(stack, entity) : 32;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        ItemStack stack = player.getItemInHand(usedHand);
        if (isCharged(stack)) {
            setCharged(stack, false);
            shoot(level, player, stack);
            return InteractionResultHolder.consume(stack);
        }
        else {
            player.startUsingItem(usedHand);
            return InteractionResultHolder.consume(stack);
        }
        // return InteractionResultHolder.fail(stack);
    }

    @Override
    public void releaseUsing(ItemStack stack, Level level, LivingEntity livingEntity, int timeLeft) {
        if (timeLeft <= 0 && !isCharged(stack)) {
            setCharged(stack, true);
        }
    }

    @Override
    public boolean useOnRelease(ItemStack stack) {
        return stack.is(this);
    }
}
