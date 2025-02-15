package com.example.sculknrun.block.blockentity;

import com.example.sculknrun.particle.ModParticleTypes;
import com.example.sculknrun.potion.ModPotions;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class SculkshroomBlockEntity extends BlockEntity {
    private int cooldownTicks;

    public SculkshroomBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState blockState) {
        super(type, pos, blockState);
    }

    public SculkshroomBlockEntity(BlockPos pos, BlockState blockState) {
        this(ModBlockEntityTypes.SCULKSHROOM.get(), pos, blockState);
    }

    public static <T extends BlockEntity> void tick(Level level, BlockPos blockPos,
                                                    BlockState blockState, T blockEntity) {
        if (level.isClientSide()) {
            return;
        }

        if (!(blockEntity instanceof SculkshroomBlockEntity sculkshroomBlockEntity)) {
            return;
        }

        if (sculkshroomBlockEntity.isOnCooldown()) {
            sculkshroomBlockEntity.tickCooldown();
            return;
        }

        Vec3 centerPos = blockPos.getCenter();
        AreaEffectCloud effectCloud = new AreaEffectCloud(level, centerPos.x(), centerPos.y(), centerPos.z());
        effectCloud.setDuration(5);
        effectCloud.setRadius(3);
        effectCloud.setParticle(ModParticleTypes.RESIN_TEARS.get());
        effectCloud.setPotionContents(new PotionContents(ModPotions.STRONG_SCULKED));
        level.addFreshEntity(effectCloud);

        sculkshroomBlockEntity.setCooldownTicks(5);
    }

    public void tickCooldown() {
        this.cooldownTicks--;
    }
    public void setCooldownTicks(int cooldownTicks) {
        this.cooldownTicks = cooldownTicks;
    }

    public boolean isOnCooldown() {
        return cooldownTicks > 0;
    }
}
