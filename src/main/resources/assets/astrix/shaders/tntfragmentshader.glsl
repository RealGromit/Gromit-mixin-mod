#version 330 core

in vec4 fragColor;
in vec2 textureCoord;

uniform sampler2D atlas;

void main() {
    gl_FragColor = texture(atlas, textureCoord);
}