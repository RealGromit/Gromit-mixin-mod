#version 330 core
layout (location = 0) in vec3 aPos;
layout (location = 1) in vec3 aOffset;
layout (location = 2) in vec2 aTexCoord;

out vec2 texCoord;

uniform mat4 projection;

void main() {
    gl_Position = projection * vec4(aPos + aOffset, 1);
    texCoord = aTexCoord;
}