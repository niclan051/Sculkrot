package com.example.sculkrot.datagen;

import com.example.sculkrot.init.ModBlocks;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(PackOutput output, String modid,
                                 ExistingFileHelper exFileHelper) {
        super(output, modid, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        simpleBlockWithItem(
                ModBlocks.SCULK_NODE.get(), models().cubeBottomTop(
                        "sculk_node", modLoc("block/sculk_node_side"),
                        modLoc("block/sculk_node_bottom"),
                        modLoc("block/sculk_node_top")
                )
        );
    }
}
