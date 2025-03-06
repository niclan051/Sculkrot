package com.example.sculkrot.item.model;

import com.example.sculkrot.SculkrotMod;
import com.example.sculkrot.item.SculkHelmetItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.DefaultedItemGeoModel;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

public class SculkHelmetRenderer extends GeoArmorRenderer<SculkHelmetItem> {
    public SculkHelmetRenderer() {
        super(new DefaultedItemGeoModel<>(ResourceLocation.fromNamespaceAndPath(SculkrotMod.MODID, "armor/sculk_helmet")));
    }
}
