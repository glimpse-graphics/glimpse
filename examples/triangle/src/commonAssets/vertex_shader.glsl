#version 100

precision mediump float;

uniform mat4 uProjMatrix;
uniform mat4 uViewMatrix;
uniform mat4 uModelMatrix;

attribute vec3 aPos;
attribute vec2 aTexCoords;

varying vec2 vTexCoords;

void main() {
    vec4 position = vec4(aPos, 1.0);
    vTexCoords = aTexCoords;
    gl_Position = uProjMatrix * uViewMatrix * uModelMatrix * position;
}
