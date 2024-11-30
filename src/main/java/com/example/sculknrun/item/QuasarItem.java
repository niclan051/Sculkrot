package com.example.sculknrun.item;

import com.example.sculknrun.Sculknrun;
import net.minecraft.nbt.CompoundTag;
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
    public static final ResourceLocation MODEL_STANDBY = new ResourceLocation(Sculknrun.MODID, "quasar_standby");
    public static final ResourceLocation MODEL_CHARGE_1 = new ResourceLocation(Sculknrun.MODID, "quasar_charge_1");
    public static final ResourceLocation MODEL_CHARGE_2 = new ResourceLocation(Sculknrun.MODID, "quasar_charge_2");
    public static final ResourceLocation MODEL_CHARGE_3 = new ResourceLocation(Sculknrun.MODID, "quasar_charge_3");
    public static final ResourceLocation MODEL_CHARGED = new ResourceLocation(Sculknrun.MODID, "quasar_charged");
    public static final ResourceLocation PULLING = new ResourceLocation(Sculknrun.MODID, "pulling");
    public static final ResourceLocation PULL = new ResourceLocation(Sculknrun.MODID, "pull");
    public static final ResourceLocation CHARGED = new ResourceLocation(Sculknrun.MODID, "charged");

    public static final String KEY_CHARGED = "sculknrun_Charged";

    public QuasarItem(Properties pProperties) {
        super(pProperties);
    }

    public static boolean isCharged(ItemStack stack) {
        CompoundTag nbt = stack.getOrCreateTag();
        return nbt.contains(KEY_CHARGED) && nbt.getBoolean(KEY_CHARGED);
    }

    public static void setCharged(ItemStack stack, boolean charged) {
        CompoundTag nbt = stack.getOrCreateTag();
        nbt.putBoolean(KEY_CHARGED, charged);
    }

    public static void shoot(Level level, LivingEntity user, ItemStack stack) {
        user.sendSystemMessage(Component.literal("thy end is now"));
    }


    @Override
    public int getUseDuration(ItemStack stack) {
        return isCharged(stack)  ? super.getUseDuration(stack) : 32;
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
