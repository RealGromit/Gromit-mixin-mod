#version 110

uniform vec2 center;
uniform float radius;
uniform float feather;
uniform float iframe;
uniform vec3 color;

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

    float delta = distance(gl_FragCoord.xy, center);
    float step = smoothstep(radius - feather, radius, delta) - smoothstep(radius, radius + feather, delta);
    gl_FragColor = vec4(mix(horColour * 4.0, color, pow(step, 2.0)), step);
}