package com.gromit.gromitmod.listener;

import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import net.minecraft.block.*;
import net.minecraft.client.Minecraft;
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

    private long tickCounter;

    public ChunkMapper(Minecraft minecraft) {
        MinecraftForge.EVENT_BUS.register(this);
        instance = this;
        this.minecraft = minecraft;
    }

    @SubscribeEvent
    public void onChunkLoad(ChunkEvent.Load event) {
        if (scannedChunks.contains((((long) event.getChunk().xPosition) << 32) | (event.getChunk().zPosition & 0xffffffffL))) {
            return;
        }
        unScannedChunks.add(event.getChunk());
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (minecraft.thePlayer == null || minecraft.theWorld == null) return;
        tickCounter++;
        if (tickCounter % 1200 == 0) {
            generateCannonChunkKeys();
            scannedChunks.clear();
            chunkMap.clear();
        }

        for (Chunk chunk : unScannedChunks) {
            long chunkKey = (((long) chunk.xPosition) << 32) | (chunk.zPosition & 0xffffffffL);
            chunkMap.putIfAbsent(chunkKey, new CannonDetector());
            CannonDetector cannonDetector = chunkMap.get(chunkKey);
            for (byte x = 0; x < 16; x++) {
                for (byte z = 0; z < 16; z++) {
                    for (short y = 1; y < 256; y++) {
                        Block block = chunk.getBlock(x, y, z);
                        if (block instanceof BlockAir) continue;
                        if (block instanceof BlockDispenser) cannonDetector.dispensers++;
                        if (block instanceof BlockRedstoneRepeater) cannonDetector.repeaters++;
                        if (block instanceof BlockRedstoneWire) cannonDetector.redstone++;
                        if (block instanceof BlockPistonBase) cannonDetector.pistons++;
                        if (block instanceof BlockCarpet) cannonDetector.carpets++;
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
}
