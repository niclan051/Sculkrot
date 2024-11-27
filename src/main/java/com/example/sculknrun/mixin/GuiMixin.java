package com.example.sculknrun.mixin;

import com.example.sculknrun.Sculknrun;
import com.example.sculknrun.effect.ModMobEffects;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(Gui.class)
public abstract class GuiMixin {
    @ModifyArg(method = "renderHeart", at = @At(value = "INVOKE",
                                               target = "Lnet/minecraft/client/gui/GuiGraphics;blitSprite(Lnet/minecraft/resources/ResourceLocation;IIII)V"))
    private ResourceLocation renderSculkHunger(ResourceLocation pSprite) {
        if (Minecraft.getInstance().player.hasEffect(ModMobEffects.SCULKED.get())) {
            return new ResourceLocation(Sculknrun.MODID, "sculk_heart");
        }
        return pSprite;
    }
}
