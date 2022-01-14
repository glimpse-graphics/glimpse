#version 100

precision mediump float;

uniform mat4 uProjMatrix;
uniform mat4 uViewMatrix;
uniform mat4 uModelMatrix;
uniform mat3 uNormalMatrix;

attribute vec3 aPos;
attribute vec2 aTexCoords;
attribute vec3 aNormal;
attribute vec3 aTangent;
attribute vec3 aBitangent;

varying vec3 vLightPosTan;
varying vec3 vPosTan;
varying vec2 vTexCoords;

void main() {
    vec4 position = vec4(aPos, 1.0);

    vec3 normal = normalize(uNormalMatrix * aNormal);
    vec3 tangent = normalize(uNormalMatrix * aTangent);
    tangent = normalize(tangent - dot(tangent, normal) * normal);
    vec3 bitangent = normalize(uNormalMatrix * aBitangent);
    bitangent = normalize(bitangent - dot(bitangent, normal) * normal);

    mat3 tbnMatrix = mat3(
        vec3(tangent.x, bitangent.x, normal.x),
        vec3(tangent.y, bitangent.y, normal.y),
        vec3(tangent.z, bitangent.z, normal.z)
    );

    vLightPosTan = tbnMatrix * vec3(-10.0, -10.0, 10.0);
    vPosTan = tbnMatrix * vec3(uModelMatrix * position);
    vTexCoords = aTexCoords;

    gl_Position = uProjMatrix * uViewMatrix * uModelMatrix * position;
}
