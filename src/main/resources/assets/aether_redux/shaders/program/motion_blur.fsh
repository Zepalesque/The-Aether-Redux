#version 150

uniform sampler2D DiffuseSampler;
uniform sampler2D PrevSampler;

in vec2 texCoord;
out vec4 fragColor;

void main() {
    // Sample the current frame
    vec4 currentColor = texture(DiffuseSampler, texCoord);

    // Sample the previous frame
    vec4 previousColor = texture(PrevSampler, texCoord);

    // Blend the frames together
    vec4 finalColor = mix(previousColor, currentColor, 0.75); // Adjust the third parameter to control the amount of motion blur

    // Output the final color
    fragColor = finalColor;
}