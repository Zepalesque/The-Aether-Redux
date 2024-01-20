#version 150

uniform sampler2D DiffuseSampler;
uniform float Time; // You'll need to pass in the current time

in vec2 texCoord;
out vec4 fragColor;

// Simple noise function
float noise(vec2 uv) {
    return fract(sin(dot(uv, vec2(12.9898, 78.233))) * 43758.5453);
}

void main() {
    // Calculate the shake amount based on the noise function
    vec2 shakeAmount = vec2(noise(vec2(Time, 0.0)), noise(vec2(Time, 1.0))) * 0.001;

    // Offset the texture coordinates
    vec2 shakenTexCoord = texCoord + shakeAmount;

    // Sample the texture using the offset coordinates
    fragColor = texture(DiffuseSampler, shakenTexCoord);
}
