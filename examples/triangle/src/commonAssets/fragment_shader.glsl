#version 100

precision mediump float;

varying vec2 vTexCoords;

void main() {
    float yellow = 1.0 - vTexCoords.y;
    float blue = vTexCoords.y;
    float green = yellow * vTexCoords.x;
    float red = yellow * (1.0 - vTexCoords.x);
    gl_FragColor = vec4(red, green, blue, 1.0);
}
