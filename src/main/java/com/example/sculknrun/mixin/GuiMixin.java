package com.example.sculknrun.mixin;

import com.example.sculknrun.Sculknrun;
import com.example.sculknrun.effect.ModMobEffects;
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
            return ResourceLocation.fromNamespaceAndPath(Sculknrun.MODID, "sculk_heart");
        }
        return pSprite;
    }

    @ModifyArg(method = "renderFood", at = @At(value = "INVOKE",
                                               target = "Lnet/minecraft/client/gui/GuiGraphics;blitSprite" +
                                                        "(Lnet/minecraft/resources/ResourceLocation;IIII)V"))
    private ResourceLocation renderSculkHunger(ResourceLocation pSprite) {
        if (Minecraft.getInstance().player.hasEffect(ModMobEffects.SCULKED)) {
            return ResourceLocation.fromNamespaceAndPath(Sculknrun.MODID, "sculk_food");
        }
        return pSprite;
    }

    @Inject(method = "render", at = @At("TAIL"))
    private void drawSomeShitIdk(GuiGraphics guiGraphics, DeltaTracker deltaTracker, CallbackInfo ci) {
        ResourceLocation background = ResourceLocation.fromNamespaceAndPath(Sculknrun.MODID, "infection_background");
        ResourceLocation bar = ResourceLocation.fromNamespaceAndPath(Sculknrun.MODID, "infection_bar");
        guiGraphics.blitSprite(background, 20, 20, 86, 16);
        guiGraphics.blitSprite(bar,
                               86, 16,
                               0, 0,
                               20, 20,
                               0,
                               (int) (86 * Sculknrun.infectionLevel / 10.0), 16
        );
    }
}
