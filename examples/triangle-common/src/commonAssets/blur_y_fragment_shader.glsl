#version 310 es

#define KERNEL_SIZE 25

precision mediump float;

float kernel[KERNEL_SIZE] = float[](
    4.269444577415069e-9,
    7.172582314377488e-8,
    9.421271824347815e-7,
    0.000009677357610807646,
    0.00007774890833041669,
    0.0004886419246555484,
    0.0024027321953559743,
    0.009244615182412161,
    0.02783468109888333,
    0.06559072725313139,
    0.12097744231648057,
    0.17466644491589045,
    0.19741254144959838,
    0.17466644491589045,
    0.12097744231648057,
    0.06559072725313139,
    0.02783468109888333,
    0.009244615182412161,
    0.0024027321953559743,
    0.0004886419246555484,
    0.00007774890833041669,
    0.000009677357610807646,
    9.421271824347815e-7,
    7.172582314377488e-8,
    4.269444577415069e-9
);

uniform vec2 uScreenSize;
uniform sampler2D uImage;
uniform sampler2D uPosition;

in vec2 vTexCoords;

out vec4 outColor;

void main() {

    vec4 color = vec4(0.0, 0.0, 0.0, 0.0);

    vec4 position = texture(uPosition, vTexCoords);
    float distanceFromCenter = length(position.xy);
    float depth = (position.a < 0.01) ? 0.0 : position.z - 0.978;
    float radius = min(3000.0 * depth * depth * distanceFromCenter, 12.0);

    vec2 diff = radius / uScreenSize;

    float weight = 1.0 / 25.0;

    for (int row = 0; row < KERNEL_SIZE; row++) {
        float dy = float(row - KERNEL_SIZE / 2) * diff.y;
        vec2 texCoords = vTexCoords + vec2(0.0, dy);
        color += texture(uImage, vTexCoords) * kernel[row];
    }

    outColor = color;
}
