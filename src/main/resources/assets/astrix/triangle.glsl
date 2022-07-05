#version 330 core
layout (location = 0) in vec2 aPos;
layout (location = 1) in vec3 aColor;
layout (location = 2) in vec2 offset;

out vec4 color;

void main() {
    gl_Position = vec4(aPos + offset, 0, 1);
    color = vec4(aColor, 1);
}