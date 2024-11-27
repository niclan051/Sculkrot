package com.example.sculknrun.mixin;

import com.example.sculknrun.Sculknrun;
import com.example.sculknrun.effect.ModMobEffects;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.gui.overlay.ExtendedGui;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(ExtendedGui.class)
public abstract class ExtendedGuiMixin extends Gui {
    public ExtendedGuiMixin(Minecraft pMinecraft,
                            ItemRenderer pItemRenderer) {
        super(pMinecraft, pItemRenderer);
    }

    @ModifyArg(method = "renderFood", at = @At(value = "INVOKE",
                                               target = "Lnet/minecraft/client/gui/GuiGraphics;blitSprite(Lnet/minecraft/resources/ResourceLocation;IIII)V"))
    private ResourceLocation renderSculkHunger(ResourceLocation pSprite) {
        if (Minecraft.getInstance().player.hasEffect(ModMobEffects.SCULKED.get())) {
            return new ResourceLocation(Sculknrun.MODID, "sculk_food");
        }
        return pSprite;
    }
}
