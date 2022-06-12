package com.gromit.gromitmod.listener;

import com.gromit.gromitmod.utils.MathUtils;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import net.minecraft.block.*;
import net.minecraft.client.Minecraft;
import net.minecraft.util.BlockPos;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.ChunkEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class ChunkMapper {

    private static ChunkMapper instance;
    private final Minecraft minecraft;
    private final Long2ObjectOpenHashMap<CannonDetector> chunkMap = new Long2ObjectOpenHashMap<>();
    private final Set<Chunk> unScannedChunks = ConcurrentHashMap.newKeySet();
    private final HashSet<Long> scannedChunks = new HashSet<>();
    private final HashSet<Long> cannonChunks = new HashSet<>();
    private final HashSet<Long> playerUnscannedChunks = new HashSet<>();

    private long tickCounter;

    public ChunkMapper(Minecraft minecraft) {
        MinecraftForge.EVENT_BUS.register(this);
        instance = this;
        this.minecraft = minecraft;
    }

    @SubscribeEvent
    public void onChunkLoad(ChunkEvent.Load event) {
        if (scannedChunks.contains(MathUtils.int2Long(event.getChunk().xPosition, event.getChunk().zPosition))) return;
        unScannedChunks.add(event.getChunk());
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (minecraft.thePlayer == null || minecraft.theWorld == null) return;
        tickCounter++;
        if (tickCounter % 1200 == 0) {
            generateCannonChunkKeys();
            scannedChunks.clear();
            tickCounter = 0;
        }
        if (tickCounter % 10 == 0) chunkMapDispatcher();
        if (tickCounter % 400 == 0) mapChunksAroundPlayer();

        for (Chunk chunk : unScannedChunks) {
            long chunkKey = MathUtils.int2Long(chunk.xPosition, chunk.zPosition);
            chunkMap.putIfAbsent(chunkKey, new CannonDetector());
            CannonDetector cannonDetector = chunkMap.get(chunkKey);
            cannonDetector.clear();

            for (byte x = 0; x < 16; x++) {
                for (byte z = 0; z < 16; z++) {
                    for (short y = 1; y < 256; y++) {
                        Block block = chunk.getBlock(x, y, z);
                        if (block instanceof BlockAir) {}
                        else if (block instanceof BlockDispenser) cannonDetector.dispensers++;
                        else if (block instanceof BlockRedstoneRepeater) cannonDetector.repeaters++;
                        else if (block instanceof BlockRedstoneWire) cannonDetector.redstone++;
                        else if (block instanceof BlockPistonBase) cannonDetector.pistons++;
                        else if (block instanceof BlockCarpet) cannonDetector.carpets++;
                    }
                }
            }
            chunkMap.put(chunkKey, cannonDetector);
            unScannedChunks.remove(chunk);
            scannedChunks.add(chunkKey);
            break;
        }
    }

    @SubscribeEvent
    public void onWorldUnload(WorldEvent.Unload event) {
        scannedChunks.clear();
        unScannedChunks.clear();
        chunkMap.clear();
    }

    private void generateCannonChunkKeys() {
        ObjectIterator<Long2ObjectMap.Entry<CannonDetector>> iterator = chunkMap.long2ObjectEntrySet().fastIterator();
        while (iterator.hasNext()) {
            Long2ObjectMap.Entry<CannonDetector> pair = iterator.next();
            CannonDetector cannonDetector = pair.getValue();
            if (cannonDetector.dispensers > 30 && cannonDetector.repeaters > 12 && cannonDetector.redstone > 10)
                cannonChunks.add(pair.getLongKey());

            else if (cannonDetector.carpets > 6 && cannonDetector.pistons > 6)
                cannonChunks.add(pair.getLongKey());
        }
    }

    private void chunkMapDispatcher() {
        if (playerUnscannedChunks.isEmpty()) return;
        for (long chunkKey : playerUnscannedChunks) {
            int posX = (int) (chunkKey >> 32) << 4;
            int posZ = (int) chunkKey << 4;

            chunkMap.putIfAbsent(chunkKey, new CannonDetector());
            CannonDetector cannonDetector = chunkMap.get(chunkKey);
            cannonDetector.clear();

            for (int x = posX; x < posX + 16; x++) {
                for (int z = posZ; z < posZ + 16; z++) {
                    for (short y = 1; y < 256; y++) {
                        Block block = minecraft.theWorld.getBlockState(new BlockPos(x, y, z)).getBlock();
                        if (block instanceof BlockAir) {}
                        else if (block instanceof BlockDispenser) cannonDetector.dispensers++;
                        else if (block instanceof BlockRedstoneRepeater) cannonDetector.repeaters++;
                        else if (block instanceof BlockRedstoneWire) cannonDetector.redstone++;
                        else if (block instanceof BlockPistonBase) cannonDetector.pistons++;
                        else if (block instanceof BlockCarpet) cannonDetector.carpets++;
                    }
                }
            }
            playerUnscannedChunks.remove(chunkKey);
            chunkMap.put(chunkKey, cannonDetector);
            break;
        }
    }

    private void mapChunksAroundPlayer() {
        int chunkX = minecraft.thePlayer.chunkCoordX - 2;
        int chunkZ = minecraft.thePlayer.chunkCoordZ - 2;
        for (byte x = 0; x < 5; x++) {
            for (byte z = 0; z < 5; z++) {
                playerUnscannedChunks.add(MathUtils.int2Long(chunkX + x, chunkZ + z));
            }
        }
    }

    public HashSet<Long> getCannonChunks() {
        return cannonChunks;
    }

    public static ChunkMapper getInstance() {
        return instance;
    }
}

class CannonDetector {

    public short dispensers;
    public short repeaters;
    public short redstone;
    public short pistons;
    public short carpets;

    public void clear() {
        dispensers = 0;
        repeaters = 0;
        redstone = 0;
        pistons = 0;
        carpets = 0;
    }
}
