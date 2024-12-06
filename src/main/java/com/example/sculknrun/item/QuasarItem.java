package com.example.sculknrun.item;

import com.example.sculknrun.Sculknrun;
import com.example.sculknrun.item.component.ModDataComponentTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
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
import team.lodestar.lodestone.registry.common.particle.LodestoneParticleTypes;
import team.lodestar.lodestone.systems.particle.builder.WorldParticleBuilder;
import team.lodestar.lodestone.systems.particle.data.GenericParticleData;

import java.awt.*;

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
        user.sendSystemMessage(Component.literal("thy end is now"));
        Color startingColor = new Color(100, 0, 100);
        Color endingColor = new Color(0, 100, 200);
        Vec3 eyePos = user.getEyePosition();

        var hitResult = level.clip(new ClipContext(
                eyePos, eyePos.add(Vec3.directionFromRotation(user.getRotationVector()).multiply(100, 100, 100)),
                ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, user
        ));
        Vec3 endPos = hitResult.getLocation();

        spawnRay(
                level, eyePos, eyePos.add(Vec3.directionFromRotation(user.getRotationVector()).multiply(10, 10, 10)),
                0.25, true
        );
    }

    private static void spawnRay(Level level, Vec3 origin, Vec3 target, double step, boolean main) {
        Vec3 diff = target.subtract(origin);
        Vec3 diffNormalized = diff.normalize();
        for (double i = 1; i < diff.length(); i += step) {
            Vec3 currentPos = origin.add(diffNormalized.multiply(i, i, i));
            WorldParticleBuilder.create(LodestoneParticleTypes.WISP_PARTICLE)
                                .setScaleData(GenericParticleData.create(0.5f, 0).build())
                                .setTransparencyData(GenericParticleData.create(0.75f, 0.25f).build())
                                .setLifetime(40)
                                .addMotion(0, 0.01f, 0)
                                .enableNoClip()
                                .spawn(level, currentPos.x, currentPos.y, currentPos.z);
            if (main) {
                double depth = 5;
                for (double angle = 0; angle < 360; angle += 45) {
                    double rad = Math.toRadians(angle);
                    double sin = Math.sin(rad);
                    double cos = Math.cos(rad);
                    Vec3 subTarget = currentPos.add(depth * cos, depth * sin, 0);
                    spawnRay(level, currentPos, subTarget, step, false);
                }
            }
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
