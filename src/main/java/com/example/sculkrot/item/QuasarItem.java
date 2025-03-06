package com.example.sculkrot.item;

import com.example.sculkrot.SculkrotMod;
import com.example.sculkrot.infection.InfectionSavedData;
import com.example.sculkrot.item.component.ModDataComponentTypes;
import com.example.sculkrot.network.QuasarRayPayload;
import com.example.sculkrot.utils.RayUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
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
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("NullableProblems") // SHUT THE FUCK UPPP
public class QuasarItem extends Item {
    public static final ResourceLocation MODEL_STANDBY = ResourceLocation.fromNamespaceAndPath(
            SculkrotMod.MODID, "quasar");
    public static final ResourceLocation MODEL_CHARGE_1 = ResourceLocation.fromNamespaceAndPath(
            SculkrotMod.MODID, "quasar_charge_1");
    public static final ResourceLocation MODEL_CHARGE_2 = ResourceLocation.fromNamespaceAndPath(
            SculkrotMod.MODID, "quasar_charge_2");
    public static final ResourceLocation MODEL_CHARGE_3 = ResourceLocation.fromNamespaceAndPath(
            SculkrotMod.MODID, "quasar_charge_3");
    public static final ResourceLocation MODEL_CHARGED = ResourceLocation.fromNamespaceAndPath(
            SculkrotMod.MODID, "quasar_charged");
    public static final ResourceLocation PULLING = ResourceLocation.fromNamespaceAndPath(SculkrotMod.MODID, "pulling");
    public static final ResourceLocation PULL = ResourceLocation.fromNamespaceAndPath(SculkrotMod.MODID, "pull");
    public static final ResourceLocation CHARGED = ResourceLocation.fromNamespaceAndPath(SculkrotMod.MODID, "charged");


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

        if (!level.isClientSide()) {
            PacketDistributor.sendToAllPlayers(new QuasarRayPayload(eyePos, endPos));
        }

        Map<LivingEntity, Double> entityToDamage = new HashMap<>();
        RayUtils.performOnRay(
                eyePos, endPos, 0.01, pos -> {
                    AABB box = AABB.ofSize(pos, 10, 10, 10);
                    level.getEntitiesOfClass(LivingEntity.class, box)
                            .stream()
                            .filter(living -> living != user)
                            .filter(living -> living.distanceToSqr(pos) <= 3 * 3)
                            .forEach(living -> entityToDamage.put(
                                    living,
                                    (150 / (living.position().distanceTo(pos) + 1))
                            ));
                }
        );
        entityToDamage.forEach((entity, damage) ->
                                       entity.hurt(entity.damageSources().sonicBoom(user), damage.floatValue())
        );

        Vec3 soundPos = user.getEyePosition().add(Vec3.directionFromRotation(user.getRotationVector()));
        level.playSound(
                null, soundPos.x(), soundPos.y(), soundPos.z(),
                SoundEvents.WARDEN_SONIC_BOOM, SoundSource.PLAYERS, 1, 1
        );
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
            if (level instanceof ServerLevel serverLevel) {
                InfectionSavedData infectionSavedData =
                        InfectionSavedData.getOrCreate(serverLevel.getServer());
                infectionSavedData.incrementInfectionLevel();
                player.sendSystemMessage(Component.literal(String.valueOf(infectionSavedData.getInfectionLevel())));
            }
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
