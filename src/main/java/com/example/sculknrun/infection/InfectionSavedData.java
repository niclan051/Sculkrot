package com.example.sculknrun.infection;

import com.example.sculknrun.Sculknrun;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.saveddata.SavedData;
import net.neoforged.neoforge.network.PacketDistributor;

public class InfectionSavedData extends SavedData {
    private static final String DATA_KEY = Sculknrun.MODID;
    private static final String INFECTION_LEVEL_KEY = "infection_level";

    public static final Factory<InfectionSavedData> FACTORY =
            new Factory<>(InfectionSavedData::create, InfectionSavedData::load, null);

    private int infectionLevel;

    public static InfectionSavedData create() {
        return new InfectionSavedData(0);
    }

    protected InfectionSavedData(int infectionLevel) {
        this.infectionLevel = infectionLevel;
    }

    public void setInfectionLevel(int infectionLevel) {
        this.infectionLevel = Math.clamp(infectionLevel, 0, 10);
        this.setDirty();
        PacketDistributor.sendToAllPlayers(new UpdatePayload(this.infectionLevel));
    }

    public void incrementInfectionLevel() {
        this.setInfectionLevel(this.infectionLevel + 1);
    }

    public int getInfectionLevel() {
        return infectionLevel;
    }

    public static InfectionSavedData getOrCreate(MinecraftServer server) {
        return server.getLevel(Level.OVERWORLD).getDataStorage().computeIfAbsent(FACTORY, DATA_KEY);
    }

    public static InfectionSavedData load(CompoundTag tag, HolderLookup.Provider registries) {
        int infectionLevel = tag.getInt(INFECTION_LEVEL_KEY);
        return new InfectionSavedData(infectionLevel);
    }

    @Override
    public CompoundTag save(CompoundTag tag, HolderLookup.Provider registries) {
        tag.putInt(INFECTION_LEVEL_KEY, this.infectionLevel);
        return tag;
    }

    public record UpdatePayload(int infectionLevel) implements CustomPacketPayload {
        public static final CustomPacketPayload.Type<UpdatePayload> TYPE =
                new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(Sculknrun.MODID, "infection"));

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
