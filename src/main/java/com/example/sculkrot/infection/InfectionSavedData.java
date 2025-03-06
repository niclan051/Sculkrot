package com.example.sculkrot.infection;

import com.example.sculkrot.SculkrotMod;
import com.example.sculkrot.gameevent.ModGameEvents;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.saveddata.SavedData;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.server.ServerLifecycleHooks;

public class InfectionSavedData extends SavedData {
    private static final String DATA_KEY = SculkrotMod.MODID;
    private static final String INFECTION_LEVEL_KEY = "infection_level";

    public static final Factory<InfectionSavedData> FACTORY =
            new Factory<>(InfectionSavedData::create, InfectionSavedData::load, null);

    private int infectionLevel;

    protected InfectionSavedData(int infectionLevel) {
        this.infectionLevel = infectionLevel;
    }

    public static InfectionSavedData create() {
        return new InfectionSavedData(0);
    }

    public static InfectionSavedData getOrCreate(MinecraftServer server) {
        return server.getLevel(Level.OVERWORLD).getDataStorage().computeIfAbsent(FACTORY, DATA_KEY);
    }

    public static InfectionSavedData load(CompoundTag tag, HolderLookup.Provider registries) {
        int infectionLevel = tag.getInt(INFECTION_LEVEL_KEY);
        return new InfectionSavedData(infectionLevel);
    }

    public void incrementInfectionLevel() {
        this.setInfectionLevel(this.infectionLevel + 1);
    }

    public int getInfectionLevel() {
        return infectionLevel;
    }

    public void setInfectionLevel(int infectionLevel) {
        this.infectionLevel = Math.clamp(infectionLevel, 0, 10);
        this.setDirty();
        this.onInfectionLevelChanged(this.infectionLevel);
    }

    public void onInfectionLevelChanged(int newLevel) {
        PacketDistributor.sendToAllPlayers(new UpdatePayload(newLevel));
        ServerLifecycleHooks.getCurrentServer().getAllLevels().forEach(
                serverLevel -> serverLevel.players().stream().map(Entity::position)
                        .forEach(pos -> serverLevel.gameEvent(
                                         ModGameEvents.INFECTION_GROW, pos,
                                         new GameEvent.Context(null, null)
                                 )
                        ));
    }

    @Override
    public CompoundTag save(CompoundTag tag, HolderLookup.Provider registries) {
        tag.putInt(INFECTION_LEVEL_KEY, this.infectionLevel);
        return tag;
    }

    public record UpdatePayload(int infectionLevel) implements CustomPacketPayload {
        public static final CustomPacketPayload.Type<UpdatePayload> TYPE =
                new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(SculkrotMod.MODID, "infection"));

        public static final StreamCodec<ByteBuf, UpdatePayload> STREAM_CODEC = StreamCodec.composite(
                ByteBufCodecs.VAR_INT,
                UpdatePayload::infectionLevel,
                UpdatePayload::new
        );

        @Override
        public Type<? extends CustomPacketPayload> type() {
            return TYPE;
        }
    }
}
