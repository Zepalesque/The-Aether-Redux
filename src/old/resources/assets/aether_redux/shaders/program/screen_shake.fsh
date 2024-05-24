#version 150

uniform sampler2D DiffuseSampler;
uniform float Time;
uniform float AetherRedux_AdrenalineStrength;

in vec2 texCoord;
out vec4 fragColor;

float noise(vec2 uv) {
    return fract(sin(dot(uv, vec2(12.9898, 78.233))) * 43758.5453);
}

void main() {
    if (AetherRedux_AdrenalineStrength > 0.0) {
        // use noise to figure out how much shaking
    vec2 shakeAmount = vec2(noise(vec2(Time, 0.0)), noise(vec2(Time, 1.0))) * 0.001 * AetherRedux_AdrenalineStrength;

    // offset coords
    vec2 shakenTexCoord = texCoord + shakeAmount;

    // sample with offset coords
    fragColor = texture(DiffuseSampler, shakenTexCoord);
    } else {
        fragColor = texture(DiffuseSampler, texCoord);
    }
}
