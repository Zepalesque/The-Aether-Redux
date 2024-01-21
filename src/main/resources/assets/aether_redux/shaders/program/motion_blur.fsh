#version 150

uniform sampler2D DiffuseSampler;
uniform sampler2D PrevSampler;

uniform float AetherRedux_AdrenalineStrength;

in vec2 texCoord;
out vec4 fragColor;

void main() {
    if (AetherRedux_AdrenalineStrength > 0.0) {
        // Sample the current frame
        vec4 currentColor = texture(DiffuseSampler, texCoord);

        // Sample the previous frame
        vec4 previousColor = texture(PrevSampler, texCoord);

        // Blend the frames together
        vec4 finalColor = mix(previousColor, currentColor, 1.0 - (AetherRedux_AdrenalineStrength * 0.25));

        // Output the final color
        fragColor = finalColor;
    } else {
        fragColor = texture(DiffuseSampler, texCoord);
    }
}