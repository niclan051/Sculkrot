package com.example.sculkrot.datagen;

import com.example.sculkrot.init.ModBlocks;
import com.example.sculkrot.init.ModMobEffects;
import com.example.sculkrot.init.ModItems;
import com.example.sculkrot.init.data.ModDamageTypes;
import net.minecraft.core.Holder;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.LanguageProvider;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.NoSuchElementException;

import static com.example.sculkrot.SculkrotMod.MODID;

public class ModLanguageProvider extends LanguageProvider {
    public ModLanguageProvider(PackOutput output, String modid) {
        super(output, modid, "en_us");
    }

    @Override
    protected void addTranslations() {
        this.addEffect(ModMobEffects.SCULKED, "Sculked");

        for (DeferredHolder<Item, ? extends Item> registry : ModItems.getItems()) {
            if (registry.get() instanceof BlockItem) continue;
            this.item(registry);
        }
        for (DeferredHolder<Block, ? extends Block> registry : ModBlocks.getBlocks()) {
            this.block(registry);
        }

        damageType(ModDamageTypes.SCULK, "%s succumbed to sculk");
        damageTypeItem(ModDamageTypes.SCULK, "%s succumbed to sculk using %s");
        damageTypePlayer(ModDamageTypes.SCULK, "%s succumbed to sculk whilst trying to escape %s");

        this.add("sculkrot.patchouli.sculkinomicon.name", "Sculkinomicon");
        this.add("sculkrot.patchouli.sculkinomicon.landing", "Welcome to the Sculkinomicon");
    }

    private void damageType(ResourceKey<DamageType> resourceKey, String name) {
        String key = "death.attack.%s.%s".formatted(MODID, resourceKey.location().getPath());
        this.add(key, name);
    }
    private void damageTypeItem(ResourceKey<DamageType> resourceKey, String name) {
        String key = "death.attack.%s.%s.item".formatted(MODID, resourceKey.location().getPath());
        this.add(key, name);
    }
    private void damageTypePlayer(ResourceKey<DamageType> resourceKey, String name) {
        String key = "death.attack.%s.%s.player".formatted(MODID, resourceKey.location().getPath());
        this.add(key, name);
    }

    private void tab(Holder<CreativeModeTab> tabHolder) {
        this.add(tabHolder, "itemGroup");
    }

    private void block(Holder<Block> blockHolder) {
        this.add(blockHolder, "block");
    }

    private void item(Holder<Item> itemHolder) {
        this.add(itemHolder, "item");
    }

    private void string(String key, String value) {
        super.add(key, value);
    }

    private void add(Holder<?> holder, String type) {
        ResourceKey<?> resourceKey = holder.unwrapKey().orElseThrow(() -> new NoSuchElementException("No respective key. Check log"));
        ResourceLocation path = resourceKey.location();
        super.add(path.toLanguageKey(type), this.transform(path));
    }

    private String transform(ResourceLocation id) {
        return this.transform(id.getPath());
    }

    private String transform(String path) {
        int pathLength = path.length();
        StringBuilder stringBuilder = new StringBuilder(pathLength).append(Character.toUpperCase(path.charAt(0)));
        for (int i = 1; i < pathLength; i++) {
            char posChar = path.charAt(i);
            if (posChar == '_') {
                stringBuilder.append(' ');
            } else if (path.charAt(i - 1) == '_') {
                stringBuilder.append(Character.toUpperCase(posChar));
            } else stringBuilder.append(posChar);
        }
        return stringBuilder.toString();
    }
}
