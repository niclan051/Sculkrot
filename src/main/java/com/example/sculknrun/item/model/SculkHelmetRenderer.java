package com.example.sculknrun.item.model;

import com.example.sculknrun.Sculknrun;
import com.example.sculknrun.item.SculkHelmetItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.DefaultedItemGeoModel;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

public class SculkHelmetRenderer extends GeoArmorRenderer<SculkHelmetItem> {
    public SculkHelmetRenderer() {
        super(new DefaultedItemGeoModel<>(ResourceLocation.fromNamespaceAndPath(Sculknrun.MODID, "armor/sculk_helmet")));
    }
}
