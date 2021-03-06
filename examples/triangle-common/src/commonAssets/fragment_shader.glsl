#version 100

precision mediump float;

uniform sampler2D uTexture;
uniform sampler2D uNormalMap;

varying vec3 vLightPosTan;
varying vec3 vPosTan;
varying vec2 vTexCoords;

void main() {
    vec3 cameraDirection = normalize(vLightPosTan - vPosTan);

    vec4 color = texture2D(uTexture, vTexCoords);
    vec3 normal = texture2D(uNormalMap, vTexCoords).rgb;
    normal = normalize(normal * 2.0 - 1.0);

    if (!gl_FrontFacing) {
        normal = -normal;
        color = vec4(0.5, 0.5, 0.5, color.a);
    }

    float exposure = max(dot(cameraDirection, normal), 0.0) * 0.6 + 0.4;

    gl_FragColor = vec4(color.rgb * exposure, color.a);
}
