#version 310 es

precision mediump float;

in vec3 aPos;
in vec2 aTexCoords;

out vec2 vTexCoords;

void main() {
    vTexCoords = aTexCoords;

    gl_Position = vec4(aPos, 1.0);
}
