#version 150

#moj_import <fluffy_fur:common.glsl>

uniform sampler2D DiffuseSampler;
uniform sampler2D PrevSampler;

in vec2 texCoord;
in vec2 oneTexel;

uniform vec3 Phosphor;
uniform float totalTicks;
uniform float speed;
uniform float distortion;
uniform float fade;

out vec4 fragColor;

vec2 applyDistortion(vec2 uv, float s1, float s2, float time, float distortion) {
    uv.x += cos(uv.y * s1 + time * 1.4) * distortion;
    uv.y += sin(uv.x * s2 + time * 1.4) * distortion;
    uv.x -= cos(uv.y * s1 + time * 1.4) * distortion;
    uv.x -= cos(uv.x * s1 + time * 1.4) * distortion;
    return uv;
}

void main() {
    vec2 uv = texCoord;
    float time = totalTicks * speed;
    vec2 currUV = applyDistortion(uv, 5.2, 5.1, time, (distortion / 2) * fade);
    vec4 CurrTexel = vec4(texture(DiffuseSampler, currUV).rgb, 1.0);
    vec2 prewUV = applyDistortion(uv, 3.2, 3.1, time, distortion * fade);
    vec4 PrevTexel = vec4(texture(PrevSampler, prewUV).rgb, 1.0);
    vec3 color = vec3(max(PrevTexel.rgb * Phosphor, CurrTexel.rgb));
    vec3 hsv = RGBtoHSV(color.rgb);
    hsv.x = fract(hsv.x + (time * (speed / 2) + length(uv - vec2(0.5, 0.5))));
    vec3 c = HSVtoRGB(hsv);
    vec3 f = mix(texture(DiffuseSampler, texCoord).rgb, c, fade);
    fragColor = vec4(f, 1.0);
}
