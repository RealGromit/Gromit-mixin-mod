package com.gromit.gromitmod.utils.moderngl;

public interface GLBindableObject<T> {

    T bind();

    T unbind();
}