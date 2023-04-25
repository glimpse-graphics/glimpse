#version 310 es

precision mediump float;

uniform sampler2D uTexture;
uniform sampler2D uNormalMap;

in vec3 vCameraPosTan;
in vec3 vPosTan;
in vec2 vTexCoords;

out vec4 outColor;

void main() {
    vec3 cameraDirection = normalize(vCameraPosTan - vPosTan);

    vec4 color = texture(uTexture, vTexCoords);
    vec3 normal = texture(uNormalMap, vTexCoords).rgb;
    normal = normalize(normal * 2.0 - 1.0);

    float exposure = max(dot(cameraDirection, normal), 0.0) * 0.6 + 0.4;

    outColor = vec4(color.rgb * exposure, 1.0);
}
