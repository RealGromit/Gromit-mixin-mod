#version 110

uniform vec2 position;
uniform vec2 size;
uniform float radius;
uniform float linewidth;
uniform float feather;
uniform vec4 color;

float getDistance(vec2 deltaFromCenter, vec2 size, float radius) {
    return length(max(abs(deltaFromCenter) - size + radius, 0.0)) - radius;
}

void main() {
    vec2 center = vec2(position.x + size.x / 2.0, position.y - size.y / 2.0);

    float distance = getDistance(gl_FragCoord.xy - center, size / 2.0, radius);
    float smoothedAlpha = 1.0 - smoothstep(-feather, feather, abs(distance) - linewidth);

    gl_FragColor = vec4(color.rgb, color.a * smoothedAlpha);
}