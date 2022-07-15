#version 330 core

layout (location = 0) in vec3 pos;
layout (location = 1) in vec3 color;
layout (location = 2) in vec2 uv;

out vec4 fragColor;
out vec2 textureCoord;

uniform mat4 projection;

void main() {
    gl_Position = projection * vec4(pos, 1);
    textureCoord = uv;
    fragColor = vec4(color, 1);
}