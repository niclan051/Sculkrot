package com.example.sculkrot.datagen;

import com.example.sculkrot.SculkrotMod;
import com.example.sculkrot.item.ModItems;
import com.example.sculkrot.item.QuasarItem;
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
        var quasar = texturedWithExistingParent(QuasarItem.MODEL_STANDBY, "item/crossbow");

        quasar.override()
              .predicate(QuasarItem.PULLING, 1f)
              .predicate(QuasarItem.PULL, 0.33f)
              .model(texturedWithExistingParent(QuasarItem.MODEL_CHARGE_1, quasar.getLocation().toString()))
              .end();

        quasar.override()
              .predicate(QuasarItem.PULLING, 1f)
              .predicate(QuasarItem.PULL, 0.66f)
              .model(texturedWithExistingParent(QuasarItem.MODEL_CHARGE_2, quasar.getLocation().toString()))
              .end();

        quasar.override()
              .predicate(QuasarItem.PULLING, 1f)
              .predicate(QuasarItem.PULL, 1f)
              .model(texturedWithExistingParent(QuasarItem.MODEL_CHARGE_3, quasar.getLocation().toString()))
              .end();

        quasar.override()
              .predicate(QuasarItem.CHARGED, 1f)
              .model(texturedWithExistingParent(QuasarItem.MODEL_CHARGED, quasar.getLocation().toString()))
              .end();

        basicItem(ModItems.SUPERSONIC_BOLT.asItem());
        basicItem(ModItems.SCULK_WINE.asItem());
        basicItem(ModItems.SCULK_HELMET.asItem());
        basicItem(ModItems.RESIN_TEAR.asItem());
        basicItem(ModItems.RESIN_INGOT.asItem());

        handheld(ModItems.SCULKHANDER1.asItem());
        handheld(ModItems.SCULKHANDER2.asItem());

        basicItem(ResourceLocation.fromNamespaceAndPath(SculkrotMod.MODID, "sculkinomicon"));
    }

    private ItemModelBuilder handheld(Item item) {
        return handheld(BuiltInRegistries.ITEM.getKey(item));
    }

    private ItemModelBuilder handheld(ResourceLocation loc) {
        return texturedWithExistingParent(loc, "item/handheld");
    }

    private ItemModelBuilder texturedWithExistingParent(ResourceLocation loc, String parent) {
        return withExistingParent(loc.toString(), parent)
                .texture("layer0", ResourceLocation.fromNamespaceAndPath(loc.getNamespace(), "item/" + loc.getPath()));
    }
}
