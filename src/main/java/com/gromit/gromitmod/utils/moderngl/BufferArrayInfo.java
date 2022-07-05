package com.gromit.gromitmod.utils.moderngl;

import lombok.Getter;

import java.util.function.Consumer;

public class BufferArrayInfo {

    @Getter private final int layout;
    @Getter private final int size;
    @Getter private final int type;
    @Getter private final int stride;
    @Getter private final int bufferOffset;
    @Getter private final Consumer<BufferArrayInfo> consumer;

    public BufferArrayInfo(int layout, int size, int type, int stride, int bufferOffset, Consumer<BufferArrayInfo> consumer) {
        this.layout = layout;
        this.size = size;
        this.type = type;
        this.stride = stride;
        this.bufferOffset = bufferOffset;
        this.consumer = consumer;
    }
}
