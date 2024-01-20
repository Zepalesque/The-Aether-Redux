#version 150

uniform sampler2D DiffuseSampler;
uniform vec2 InSize;
uniform vec2 Center;
uniform float Radius;
uniform float BlurStrength;  // New uniform to control the blur strength

in vec2 texCoord;
out vec4 fragColor;

const int NUM_SAMPLES = 20; // Number of samples for the blur

void main() {
vec2 dir = texCoord - Center; // Direction from center to current pixel
vec4 color = vec4(0.0);

// Sample the texture multiple times around the current pixel
for (int i = 0; i < NUM_SAMPLES; ++i) {
float t = float(i) / float(NUM_SAMPLES - 1);
vec2 offset = dir * t * Radius * (BlurStrength * 0.1);  // Multiply BlurStrength by 0.1
color += texture(DiffuseSampler, texCoord + offset);
}

// Average the samples to get the final color
color /= float(NUM_SAMPLES);

// Apply a fade-out effect towards the edges
float dist = length(dir);
float edgeFade = smoothstep(0.8, 1.0, dist);
color = mix(color, vec4(1.0), edgeFade);

fragColor = color;
}
