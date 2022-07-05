package com.gromit.gromitmod.utils.moderngl;

import lombok.Getter;

public class BufferArrayInfo {

    @Getter private final int layout;
    @Getter private final int size;
    @Getter private final int type;
    @Getter private final int stride;
    @Getter private final int bufferOffset;

    public BufferArrayInfo(int layout, int size, int type, int stride, int bufferOffset) {
        this.layout = layout;
        this.size = size;
        this.type = type;
        this.stride = stride;
        this.bufferOffset = bufferOffset;
    }
}
