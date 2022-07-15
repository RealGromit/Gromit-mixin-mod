#version 330 core

in vec2 texCoord;

uniform sampler2D atlas;

void main() {
    gl_FragColor = texture(atlas, texCoord);
}