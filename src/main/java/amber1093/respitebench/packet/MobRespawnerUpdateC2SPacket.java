package amber1093.respitebench.packet;

import amber1093.respitebench.RespiteBench;
import net.fabricmc.fabric.api.networking.v1.FabricPacket;
import net.fabricmc.fabric.api.networking.v1.PacketType;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.math.BlockPos;

public record MobRespawnerUpdateC2SPacket(
		BlockPos blockPos,
		int maxConnectedEntities,
		int spawnCount,
		int requiredPlayerRange,
		int spawnRange,
		boolean shouldClearEntityData,
		boolean shouldDisconnectEntities,
		boolean enabled,
		boolean oneOff
) implements FabricPacket {

	public static final PacketType<MobRespawnerUpdateC2SPacket> TYPE = (
		PacketType.create(
			RespiteBench.MOB_RESPAWNER_UPDATE_PACKET_ID,
			MobRespawnerUpdateC2SPacket::new
		)
	);

	public MobRespawnerUpdateC2SPacket(PacketByteBuf buf) {
		this(
			buf.readBlockPos(),
			buf.readInt(),
			buf.readInt(),
			buf.readInt(),
			buf.readInt(),
			buf.readBoolean(),
			buf.readBoolean(),
			buf.readBoolean(),
			buf.readBoolean()
		);
	}

	@Override
	public void write(PacketByteBuf buf) {
		buf.writeBlockPos(blockPos);
		buf.writeInt(maxConnectedEntities);
		buf.writeInt(spawnCount);
		buf.writeInt(requiredPlayerRange);
		buf.writeInt(spawnRange);
		buf.writeBoolean(shouldClearEntityData);
		buf.writeBoolean(shouldDisconnectEntities);
		buf.writeBoolean(enabled);
		buf.writeBoolean(oneOff);
	}

	@Override
	public PacketType<MobRespawnerUpdateC2SPacket> getType() {
		return TYPE;
	}

}