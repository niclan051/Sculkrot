package com.example.sculkrot.mixin;

import com.example.sculkrot.SculkrotMod;
import com.example.sculkrot.effect.ModMobEffects;
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
                "infection/infection_background");
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
        guiGraphics.blitSprite(bar,
                               barWidth, barHeight,
                               0, 0,
                               barX, barY,
                               0,
                               (int) (barWidth * SculkrotMod.infectionLevel / 10.0), barHeight
        );

    }
}
