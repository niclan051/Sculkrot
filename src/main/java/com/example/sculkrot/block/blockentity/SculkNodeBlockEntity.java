package com.example.sculkrot.block.blockentity;

import com.example.sculkrot.effect.ModMobEffects;
import com.example.sculkrot.gameevent.ModGameEvents;
import com.example.sculkrot.infection.InfectionSavedData;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.SculkSpreader;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.BlockPositionSource;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.gameevent.GameEventListener;
import net.minecraft.world.level.gameevent.PositionSource;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class SculkNodeBlockEntity extends BlockEntity
        implements GameEventListener.Provider<SculkNodeBlockEntity.SculkNodeListener> {
    private final SculkNodeListener sculkNodeListener;

    public SculkNodeBlockEntity(BlockEntityType<?> pType, BlockPos pPos, BlockState pBlockState) {
        super(pType, pPos, pBlockState);
        this.sculkNodeListener = new SculkNodeListener(new BlockPositionSource(pPos));
    }

    public SculkNodeBlockEntity(BlockPos pos, BlockState blockState) {
        this(ModBlockEntityTypes.SCULK_NODE.get(), pos, blockState);
    }

    public static <T extends BlockEntity> void tick(Level level, BlockPos blockPos, BlockState blockState,
                                                    T blockEntity) {
        applySculkedEffect(level, blockPos);
        tickSpreadSculk(level, blockPos, (SculkNodeBlockEntity) blockEntity);
    }

    private static void applySculkedEffect(Level level, BlockPos blockPos) {
        AABB effectArea = AABB.encapsulatingFullBlocks(blockPos, blockPos).inflate(10);
        level.getEntitiesOfClass(LivingEntity.class, effectArea).forEach(livingEntity -> {

            int lvl = (int) (lerp(
                    50, 0,
                    Math.clamp((livingEntity.position().distanceTo(blockPos.getCenter()) - 1) / 10.0, 0, 1)
            ));
            livingEntity.addEffect(
                    new MobEffectInstance(ModMobEffects.SCULKED, 20 * 10, lvl, true, true)
            );
        });
    }

    private static double lerp(double a, double b, double t) {
        return a * (1.0 - t) + (b * t);
    }

    private static void tickSpreadSculk(Level level, BlockPos blockPos, SculkNodeBlockEntity blockEntity) {
        blockEntity.sculkNodeListener.sculkSpreader.updateCursors(level, blockPos, level.getRandom(), true);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        this.sculkNodeListener.sculkSpreader.load(tag);
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        this.sculkNodeListener.sculkSpreader.save(tag);
    }

    @Override
    public SculkNodeListener getListener() {
        return this.sculkNodeListener;
    }

    public class SculkNodeListener implements GameEventListener {
        private final SculkSpreader sculkSpreader = SculkSpreader.createLevelSpreader();
        private final PositionSource listenerSource;

        private SculkNodeListener(PositionSource listenerSource) {
            this.listenerSource = listenerSource;
        }

        @Override
        public PositionSource getListenerSource() {
            return this.listenerSource;
        }

        @Override
        public int getListenerRadius() {
            return 128;
        }

        @Override
        public boolean handleGameEvent(ServerLevel level, Holder<GameEvent> gameEvent, GameEvent.Context context,
                                       Vec3 pos) {
            if (!gameEvent.is(ModGameEvents.INFECTION_GROW)) {
                return false;
            }
            for (Direction direction : Direction.values()) {
                InfectionSavedData infectionData = InfectionSavedData.getOrCreate(level.getServer());
                int charge = (int) (Math.pow(infectionData.getInfectionLevel() / 2.0, 1.5) * 20);
                this.sculkSpreader.addCursors(SculkNodeBlockEntity.this.getBlockPos().relative(direction), charge);
            }
            return true;
        }
    }
}
