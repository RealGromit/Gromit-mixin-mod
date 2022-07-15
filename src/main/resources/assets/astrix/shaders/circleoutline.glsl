#version 110

uniform vec2 center;
uniform float radius;
uniform float linewidth;
uniform float feather;
uniform float iframe;
uniform vec4 color;
uniform bool rgb;

void main() {
    float step = 1.0 - smoothstep(-feather, feather, abs(distance(gl_FragCoord.xy, center) - radius) - linewidth);

    if (rgb) {
        vec2 p = (gl_FragCoord.xy - center) / vec2(1080, 1920);
        float tau = 6.28318530718;
        float a = atan(p.x, p.y);
        vec2 uv = vec2(a / tau, 0.0);

        float xCol = (uv.x - iframe / 120.0) * 3.0;
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

        gl_FragColor = vec4(mix(horColour * 5.0, color.rgb, step), color.a * step);
    } else {
        gl_FragColor = vec4(color.rgb, color.a * step);
    }
}