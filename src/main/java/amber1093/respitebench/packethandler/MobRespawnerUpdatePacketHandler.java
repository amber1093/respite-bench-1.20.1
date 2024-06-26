package amber1093.respitebench.packethandler;

import amber1093.respitebench.blockentity.MobRespawnerBlockEntity;
import amber1093.respitebench.logic.MobRespawnerLogic;
import amber1093.respitebench.packet.MobRespawnerUpdateC2SPacket;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking.PlayPacketHandler;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

@FunctionalInterface
public interface MobRespawnerUpdatePacketHandler
extends PlayPacketHandler<MobRespawnerUpdateC2SPacket> {
	static void updateMobRespawnerSettings(MobRespawnerUpdateC2SPacket packet, ServerPlayerEntity player) {
		
		World world = player.getWorld();
		BlockPos blockPos = packet.blockPos();
		BlockState blockState = world.getBlockState(blockPos);
		BlockEntity blockEntity = world.getBlockEntity(blockPos);
		if (blockEntity != null && blockEntity instanceof MobRespawnerBlockEntity) {
			MobRespawnerBlockEntity mobRespawnerBlockEntity = (MobRespawnerBlockEntity)blockEntity;
			MobRespawnerLogic logic = mobRespawnerBlockEntity.getLogic();

			mobRespawnerBlockEntity.updateSettings(packet);
			
			if (packet.shouldClearEntityData()) {
				logic.setSpawnEntry(null);
				logic.resetRenderedEntity();
				logic.getRenderedEntity(world, world.getRandom(), blockPos);
			}
			if (packet.shouldDisconnectEntities()) {
				logic.getConnectedEntitiesUuid().clear();
			}
			
			blockEntity.markDirty();
			world.updateListeners(blockPos, blockState, blockState, Block.NOTIFY_ALL); 
			world.emitGameEvent((Entity)player, GameEvent.BLOCK_CHANGE, blockPos);
		}
	}
}
