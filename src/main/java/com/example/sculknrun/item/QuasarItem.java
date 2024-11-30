package com.example.sculknrun.item;

import com.example.sculknrun.Sculknrun;
import com.example.sculknrun.item.component.ModDataComponentTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

@SuppressWarnings("NullableProblems") // SHUT THE FUCK UPPP
public class QuasarItem extends Item {
    public static final ResourceLocation MODEL_STANDBY = ResourceLocation.fromNamespaceAndPath(Sculknrun.MODID, "quasar");
    public static final ResourceLocation MODEL_CHARGE_1 = ResourceLocation.fromNamespaceAndPath(Sculknrun.MODID, "quasar_charge_1");
    public static final ResourceLocation MODEL_CHARGE_2 = ResourceLocation.fromNamespaceAndPath(Sculknrun.MODID, "quasar_charge_2");
    public static final ResourceLocation MODEL_CHARGE_3 = ResourceLocation.fromNamespaceAndPath(Sculknrun.MODID, "quasar_charge_3");
    public static final ResourceLocation MODEL_CHARGED = ResourceLocation.fromNamespaceAndPath(Sculknrun.MODID, "quasar_charged");
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
    }


    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return isCharged(stack) ? super.getUseDuration(stack, entity) : 32;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        ItemStack stack = player.getItemInHand(usedHand);
        if (!level.isClientSide()) {
            if (isCharged(stack)) {
                setCharged(stack, false);
                shoot(level, player, stack);
                return InteractionResultHolder.consume(stack);
            }
            else {
                player.startUsingItem(usedHand);
                player.sendSystemMessage(Component.literal("started using"));
                return InteractionResultHolder.consume(stack);
            }
        }
        return InteractionResultHolder.fail(stack);
    }

    @Override
    public void releaseUsing(ItemStack stack, Level level, LivingEntity livingEntity, int timeLeft) {
        if (!level.isClientSide() && timeLeft <= 0 && !isCharged(stack)) {
            setCharged(stack, true);
            livingEntity.sendSystemMessage(Component.literal("released"));
        }
    }

    @Override
    public boolean useOnRelease(ItemStack stack) {
        return stack.is(this);
    }
}
