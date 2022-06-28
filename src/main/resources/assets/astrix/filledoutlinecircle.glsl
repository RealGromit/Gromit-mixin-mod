#version 110

uniform vec2 position;
uniform float radius;
uniform float linewidth;
uniform float feather;
uniform vec3 innercolor;
uniform vec3 outercolor;

void main() {
    vec2 center = vec2(position.x + radius, position.y - radius);

    float delta = distance(gl_FragCoord.xy, center);
    float step = 1.0 - smoothstep(-feather, feather, abs(delta - radius) - linewidth);
    float stepbro = 1.0 - smoothstep(radius + linewidth - feather, radius + linewidth, delta);
    gl_FragColor = vec4(mix(innercolor, outercolor, step), stepbro);
}