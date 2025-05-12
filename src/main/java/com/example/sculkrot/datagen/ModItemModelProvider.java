package com.example.sculkrot.datagen;

import com.example.sculkrot.SculkrotMod;
import com.example.sculkrot.init.ModItems;
import com.example.sculkrot.common.item.QuasarItem;
import com.example.sculkrot.utils.RegistryUtils;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.neoforged.neoforge.client.model.generators.ItemModelBuilder;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.client.model.generators.loaders.SeparateTransformsModelBuilder;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.function.UnaryOperator;

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

        handheld32(ModItems.SCULKHANDER, "gui", "handheld");

        basicItem(ResourceLocation.fromNamespaceAndPath(SculkrotMod.MODID, "sculkinomicon"));
    }

    private void separateTransform(DeferredHolder<Item, ? extends Item> item) {
        item.unwrapKey().ifPresent(
                itemName -> {
                    ResourceLocation itemModelLoc = itemName.location().withPrefix("item/");
                    ItemModelBuilder gui = super.nested().parent(new ModelFile.UncheckedModelFile(itemModelLoc.withSuffix("_gui")));
                    ItemModelBuilder twoDim = super.nested().parent(new ModelFile.UncheckedModelFile(itemModelLoc.withSuffix("_handheld")));
                    super.withExistingParent(itemModelLoc.getPath(), mcLoc("item/handheld"))
                            .customLoader(SeparateTransformsModelBuilder::begin)
                            .perspective(ItemDisplayContext.GUI, gui)
                            .perspective(ItemDisplayContext.FIXED, twoDim)
                            .base(twoDim);
                });
    }

    private ItemModelBuilder basicItem(DeferredHolder<Item, ? extends Item> item, UnaryOperator<ResourceLocation> modelLocationModifier) {
        ResourceLocation name = item.getKey().location().withPrefix("item/");

        return getBuilder(modelLocationModifier.apply(name).getPath())
                .parent(new ModelFile.UncheckedModelFile("item/generated"))
                .texture("layer0", RegistryUtils.location(modelLocationModifier.apply(name).getPath()));
    }

    private ItemModelBuilder handheld(Item item) {
        return handheld(BuiltInRegistries.ITEM.getKey(item));
    }

    private ItemModelBuilder handheld(ResourceLocation loc) {
        return texturedWithExistingParent(loc, "item/handheld");
    }

    private ResourceLocation handheld(DeferredHolder<Item, ? extends Item> item, int x, UnaryOperator<ResourceLocation> guiLocationModifier, UnaryOperator<ResourceLocation> handheldLocationModifier) {
        ResourceLocation name = item.getKey().location().withPrefix("item/");
        super.withExistingParent(handheldLocationModifier.apply(name).getPath(), RegistryUtils.location("item/templates/handheld%sx".formatted(x)))
                .texture("layer0", name);
        separateTransform(item);
        basicItem(item, guiLocationModifier);
        return name;
    }

    private ResourceLocation handheld32(DeferredHolder<Item, ? extends Item> item, String guiLocationModifier, String handheldLocationModifier) {
        return handheld(item, 32, loc -> loc.withSuffix("_" + guiLocationModifier), loc -> loc.withSuffix("_" + handheldLocationModifier));
    }

    private ItemModelBuilder texturedWithExistingParent(ResourceLocation loc, String parent) {
        return withExistingParent(loc.toString(), parent)
                .texture("layer0", ResourceLocation.fromNamespaceAndPath(loc.getNamespace(), "item/" + loc.getPath()));
    }
}
