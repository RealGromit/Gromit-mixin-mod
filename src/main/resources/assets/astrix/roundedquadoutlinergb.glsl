#version 110

uniform vec2 center;
uniform vec2 size;
uniform float radius;
uniform float feather;
uniform float iframe;
uniform vec3 color;

float getDistance(vec2 deltaFromCenter, vec2 size, float radius) {
    return length(max(deltaFromCenter - size + radius, 0.0)) - radius;
}

void main() {
    vec2 p = (gl_FragCoord.xy - center) / vec2(1080, 1920);
    float tau = 6.28318530718;
    float a = atan(p.x, p.y);
    float r = length(p) * 0.75;
    vec2 uv = vec2(a / tau, r);

    float xCol = (uv.x - (iframe / 120.0)) * 3.0;
    xCol = mod(xCol, 3.0);
    vec3 horColour = vec3(0);

    if (xCol < 1.0) {
        horColour.r += 1.0 - xCol;
        horColour.g += xCol;
    } else if (xCol < 2.0) {
        xCol -= 1.0;
        horColour.g += 1.0 - xCol;
        horColour.b += xCol;
    } else {
        xCol -= 2.0;
        horColour.b += 1.0 - xCol;
        horColour.r += xCol;
    }

    float distance = 1.0 - getDistance(abs(gl_FragCoord.xy - center), size / 2.0, radius);
    float step = smoothstep(-feather, 0.0, distance) - smoothstep(0.0, feather, distance);
    vec3 mixer = mix(horColour, color, pow(step, 9.0));
    gl_FragColor = vec4(mixer * 4.0, step);
}