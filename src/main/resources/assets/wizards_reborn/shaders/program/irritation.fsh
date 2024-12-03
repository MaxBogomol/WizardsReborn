#version 150

uniform sampler2D DiffuseSampler;

in vec2 texCoord;
in vec2 oneTexel;

uniform float totalTicks;
uniform float speed;
uniform float fade;
uniform float startTime;

out vec4 fragColor;

float applyDistortion(vec2 uv, float zoom, float distortion, float gooeyness, float wibble, float time, float speed) {
    float gameTime = time * speed;
    float s = sin(gameTime*0.1);
    float s2 = 0.5+sin(gameTime*1.8);
    vec2 d = uv*(distortion+s*.3);
    d.x += gameTime*0.25+sin(d.x+d.y + gameTime*0.3)*wibble;
    d.y += gameTime*0.25+sin(d.x + gameTime*0.3)*wibble;
    float v1=length(0.5-fract(d.xy))+gooeyness;
    d = (1.0-zoom)*0.5+(uv*zoom);
    float v2=length(0.5-fract(d.xy));
    v1 *= 1.0-v2*v1;
    v1 = v1*v1*v1;
    v1 *= 1.9+s2*0.4;
    return v1;
}

void main() {
    float time = totalTicks * speed;
    float goo = applyDistortion(texCoord, 0.7 * fade, 10 * fade, 1.0, 2.0, totalTicks + startTime, speed);
    float a = 0.55 * clamp(1.-goo, 0.,1.);
    float r = mix(texture(DiffuseSampler, texCoord).r, (255.0 / 255.0), a);
    float g = mix(texture(DiffuseSampler, texCoord).g, (119.0 / 255.0), a);
    float b = mix(texture(DiffuseSampler, texCoord).b, (167.0 / 255.0), a);
    fragColor = vec4(r, g, b, 1.0);
}
