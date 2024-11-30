package com.example.sculknrun.datagen;

import com.example.sculknrun.Sculknrun;
import com.example.sculknrun.item.QuasarItem;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.client.model.generators.ItemModelBuilder;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output, String modid,
                                ExistingFileHelper existingFileHelper) {
        super(output, modid, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        var quasar = customTextureWithExistingParent(QuasarItem.MODEL_STANDBY, "item/crossbow");

        quasar.override()
              .predicate(QuasarItem.PULLING, 1f)
              .predicate(QuasarItem.PULL, 0.33f)
              .model(customTextureWithExistingParent(QuasarItem.MODEL_CHARGE_1, quasar.getLocation().toString()))
              .end();

        quasar.override()
              .predicate(QuasarItem.PULLING, 1f)
              .predicate(QuasarItem.PULL, 0.66f)
              .model(customTextureWithExistingParent(QuasarItem.MODEL_CHARGE_2, quasar.getLocation().toString()))
              .end();

        quasar.override()
              .predicate(QuasarItem.PULLING, 1f)
              .predicate(QuasarItem.PULL, 1f)
              .model(customTextureWithExistingParent(QuasarItem.MODEL_CHARGE_3, quasar.getLocation().toString()))
              .end();

        quasar.override()
              .predicate(QuasarItem.CHARGED, 1f)
              .model(customTextureWithExistingParent(QuasarItem.MODEL_CHARGED, quasar.getLocation().toString()))
              .end();
    }

    private ItemModelBuilder handheld(Item item) {
        return handheld(BuiltInRegistries.ITEM.getKey(item));
    }

    private ItemModelBuilder handheld(ResourceLocation loc) {
        return customTextureWithExistingParent(loc, "item/handheld");
    }

    private ItemModelBuilder customTextureWithExistingParent(ResourceLocation loc, String parent) {
        return withExistingParent(loc.toString(), parent)
                .texture("layer0", new ResourceLocation(loc.getNamespace(), "item/" + loc.getPath()));
    }
}
