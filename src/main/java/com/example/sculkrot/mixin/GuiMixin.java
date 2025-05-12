package com.example.sculkrot.mixin;

import com.example.sculkrot.SculkrotMod;
import com.example.sculkrot.init.ModMobEffects;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Gui.class)
public abstract class GuiMixin {
    @ModifyArg(method = "renderHeart", at = @At(value = "INVOKE",
                                                target = "Lnet/minecraft/client/gui/GuiGraphics;blitSprite" +
                                                         "(Lnet/minecraft/resources/ResourceLocation;IIII)V"))
    private ResourceLocation renderSculkHealth(ResourceLocation pSprite) {
        if (Minecraft.getInstance().player.hasEffect(ModMobEffects.SCULKED)) {
            return ResourceLocation.fromNamespaceAndPath(SculkrotMod.MODID, "sculk_heart");
        }
        return pSprite;
    }

    @ModifyArg(method = "renderFood", at = @At(value = "INVOKE",
                                               target = "Lnet/minecraft/client/gui/GuiGraphics;blitSprite" +
                                                        "(Lnet/minecraft/resources/ResourceLocation;IIII)V"))
    private ResourceLocation renderSculkHunger(ResourceLocation pSprite) {
        if (Minecraft.getInstance().player.hasEffect(ModMobEffects.SCULKED)) {
            return ResourceLocation.fromNamespaceAndPath(SculkrotMod.MODID, "sculk_food");
        }
        return pSprite;
    }

    @Inject(method = "renderItemHotbar", at = @At(value = "TAIL"))
    private void renderSculkHotbar(GuiGraphics guiGraphics, DeltaTracker deltaTracker, CallbackInfo ci) {
        var player = Minecraft.getInstance().player;
        if (player.hasEffect(ModMobEffects.SCULKED)) {
            int lvl = 1 + player.getEffect(ModMobEffects.SCULKED).getAmplifier();

            int startX = guiGraphics.guiWidth() / 2 - 91;
            int startY = guiGraphics.guiHeight() - 22;
            int width = 182;
            int height = 22;

            double uWidth = (width * Math.min(1, lvl / 50.0));
            ResourceLocation sculkHotbar = ResourceLocation.fromNamespaceAndPath(SculkrotMod.MODID, "sculk_hotbar");

            guiGraphics.pose().pushPose();
            {
                guiGraphics.pose().translate(0, 0, 1000);
                guiGraphics.blitSprite(
                        sculkHotbar,
                        width, height,
                        0, 0,
                        startX, startY,
                        0, (int) uWidth, height
                );
            }
            guiGraphics.pose().popPose();
        }
    }


    @Inject(method = "render", at = @At("TAIL"))
    private void renderInfectionBar(GuiGraphics guiGraphics, DeltaTracker deltaTracker, CallbackInfo ci) {
        int startX = 20;
        int startY = 20;
        int padding = 5;

        int numberWidth = 32;
        int numberHeight = 10;
        int barHeight = 16;
        int barWidth = 86;

        int middleY = startY + (Math.max(barHeight, numberHeight)) / 2;

        ResourceLocation background = ResourceLocation.fromNamespaceAndPath(
                SculkrotMod.MODID,
                "infection/infection_background"
        );
        ResourceLocation bar = ResourceLocation.fromNamespaceAndPath(SculkrotMod.MODID, "infection/infection_bar");
        ResourceLocation number = ResourceLocation.fromNamespaceAndPath(
                SculkrotMod.MODID,
                "infection/infection_number_" + SculkrotMod.infectionLevel
        );

        int numberY = middleY - numberHeight / 2;
        guiGraphics.blitSprite(number, startX, numberY, numberWidth, numberHeight);

        int barX = startX + numberWidth + padding;
        int barY = middleY - barHeight / 2;
        guiGraphics.blitSprite(background, barX, barY, barWidth, barHeight);
        guiGraphics.blitSprite(
                bar,
                barWidth, barHeight,
                0, 0,
                barX, barY,
                0,
                (int) (barWidth * SculkrotMod.infectionLevel / 10.0), barHeight
        );

    }
}
