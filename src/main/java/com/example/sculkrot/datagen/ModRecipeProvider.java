package com.example.sculkrot.datagen;

import com.example.sculkrot.item.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;

import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends RecipeProvider {
    public ModRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void buildRecipes(RecipeOutput recipeOutput) {
        nineBlockStorageRecipesWithCustomPacking(
                recipeOutput, RecipeCategory.MISC, ModItems.RESIN_TEAR, RecipeCategory.MISC, ModItems.RESIN_INGOT,
                "resin_ingot_from_tears", "resin_ingot"
        );
    }
}
