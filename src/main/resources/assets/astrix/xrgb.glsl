#version 110

uniform float iframe;

void main() {
	float xCol = (gl_FragCoord.x / 1920.0 - (iframe / 180.0)) * 3.0;
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

    gl_FragColor = vec4(horColour, 1);
}