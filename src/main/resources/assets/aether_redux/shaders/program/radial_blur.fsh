#version 150

uniform sampler2D DiffuseSampler;
uniform vec2 Center;
uniform float Radius;
uniform float BlurStrength;

in vec2 texCoord;
out vec4 fragColor;

const int NUM_SAMPLES = 20;

void main() {
    vec2 dir = texCoord - Center; // direction to center
    vec4 color = vec4(0.0);

    // sample texture
    for (int i = 0; i < NUM_SAMPLES; ++i) {
        float t = float(i) / float(NUM_SAMPLES - 1);
        vec2 offset = dir * t * Radius * (BlurStrength * 0.05);  // decrease blur strength, so that the base value can be 1.0
        color += texture(DiffuseSampler, texCoord + offset);
    }

    // get the final color by averaging the sample things
    color /= float(NUM_SAMPLES);

    // prevent odd stretching
    float dist = length(dir);
    float edgeFade = smoothstep(0.8, 1.0, dist);
    color = mix(color, vec4(1.0), edgeFade);

    fragColor = color;
}
