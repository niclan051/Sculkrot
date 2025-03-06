package com.example.sculkrot.block;

import com.example.sculkrot.particle.ModParticleTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.VineBlock;
import net.minecraft.world.level.block.state.BlockState;

public class ResinTearsBlock extends VineBlock {
    public ResinTearsBlock(Properties properties) {
        super(properties);
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        double x = pos.getX() + random.nextDouble();
        double y = pos.getY() + 0.7;
        double z = pos.getZ() + random.nextDouble();
        if (random.nextDouble() > 0.7) {
            level.addParticle(ModParticleTypes.RESIN_TEARS.get(), x, y, z, 0, 0, 0);
        }
    }
}
