package com.gromit.gromitmod.utils.primitivewrapper;

import java.util.Arrays;

public class GromitPairInteger {

    private final int left;
    private final int right;

    public GromitPairInteger(int left, int right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof GromitPairInteger)) return false;
        GromitPairInteger gromitPairInteger = (GromitPairInteger) obj;
        return left == gromitPairInteger.left && right == gromitPairInteger.right;
    }

    @Override
    public int hashCode() {return Arrays.hashCode(new int[] {left, right});}

    public int getLeft() {
        return left;
    }

    public int getRight() {
        return right;
    }
}
