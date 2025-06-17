#version 150

uniform sampler2D DiffuseSampler;

in vec2 texCoord;
in vec2 oneTexel;

uniform float totalTicks;
uniform float speed;
uniform float distortion;
uniform float fade;
uniform float time;

out vec4 fragColor;

vec2 applyDistortion(vec2 uv, float s1, float s2, float time, float distortion) {
    uv.x += cos(uv.y * s1 + time * 1.4) * distortion;
    uv.y += sin(uv.x * s2 + time * 1.4) * distortion;
    return uv;
}

float random(in vec2 _st) {
    return fract(sin(dot(_st.xy, vec2(12.9898,78.233))) * 43758.5453123);
}

void main() {
    vec2 uv = texCoord;
    float timeDistortion = totalTicks * speed;
    vec2 uvDistortion = applyDistortion(uv, 3.0, 7.0, timeDistortion, (distortion / 2) * fade);
    fragColor = vec4(texture(DiffuseSampler, uvDistortion).rgb, 1.0);

    if (uvDistortion.x < 0.0 || uvDistortion.x > 1.0 || uvDistortion.y < 0.0 || uvDistortion.y > 1.0) {
        vec2 st = uv;
        vec2 aspect = uv.xy / min(uv.x, uv.y);
        st *= aspect;

        float r = random(st + time);
        float g = random(st + 1 + time);
        float b = random(st + 2 + time);
        fragColor = vec4(r, g, b, 1.0);
    }
}
