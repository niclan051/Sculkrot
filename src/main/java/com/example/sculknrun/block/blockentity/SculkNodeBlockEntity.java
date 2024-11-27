package com.example.sculknrun.block.blockentity;

import com.example.sculknrun.effect.ModMobEffects;
import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

public class SculkNodeBlockEntity extends BlockEntity {
    public SculkNodeBlockEntity(BlockEntityType<?> pType,
                                BlockPos pPos,
                                BlockState pBlockState) {
        super(pType, pPos, pBlockState);
    }

    public SculkNodeBlockEntity(BlockPos pos, BlockState blockState) {
        this(ModBlockEntityTypes.SCULK_NODE.get(), pos, blockState);
    }

    public static <T extends BlockEntity> void tick(Level level, BlockPos pos, BlockState blockState, T blockEntity) {
        AABB effectArea = AABB.encapsulatingFullBlocks(pos, pos).inflate(10);
        level.getEntitiesOfClass(LivingEntity.class, effectArea).forEach(livingEntity -> livingEntity.addEffect(
                new MobEffectInstance(ModMobEffects.SCULKED.get(), 20 * 10, 0, true, true)
        ));
    }
}
