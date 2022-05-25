package com.gromit.gromitmod.utils.primitivewrapper;

import java.util.Arrays;

public class GromitPairDouble {

    private final double left;
    private final double right;

    public GromitPairDouble(double left, double right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof GromitPairDouble)) return false;
        GromitPairDouble gromitPairDouble = (GromitPairDouble) obj;
        return left == gromitPairDouble.left && right == gromitPairDouble.right;
    }

    @Override
    public int hashCode() {return Arrays.hashCode(new double[] {left, right});}

    public double getLeft() {
        return left;
    }

    public double getRight() {
        return right;
    }
}
