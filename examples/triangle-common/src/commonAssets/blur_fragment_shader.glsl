#version 310 es

precision mediump float;

uniform vec2 uScreenSize;
uniform sampler2D uImage;
uniform sampler2D uDepth;

in vec2 vTexCoords;

out vec4 outColor;

void main() {

    vec4 color = vec4(0.0, 0.0, 0.0, 0.0);

    float depth = texture(uDepth, vTexCoords).x;
    float radius = min(100.0 * abs(depth - 0.96), 5.0);

    vec2 diff = radius / uScreenSize;

    float weight = 1.0 / 25.0;

    for (int dx = -2; dx <= 2; dx++) {
        for (int dy = -2; dy <= 2; dy++) {
            vec2 texCoords = vTexCoords + vec2(float(dx) * diff.x, float(dy) * diff.y);
            color += texture(uImage, texCoords) * weight;
        }
    }

    outColor = color;
}
