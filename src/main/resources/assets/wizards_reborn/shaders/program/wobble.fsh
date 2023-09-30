#version 150

uniform sampler2D DiffuseSampler;

in vec2 texCoord;
in vec2 oneTexel;

uniform vec2 InSize;

uniform float Time;
uniform vec2 Frequency;
uniform vec2 WobbleAmount;

out vec4 fragColor;

vec3 hue(float h)
{
    float t = 1;

    float r = abs(h * (6.0 / t)  - (3.0 / t) ) - (1.0 / t) ;
    float g = (2.0 / t) - abs(h * (6.0 / t)  - (2.0 / t));
    float b = (2.0 / t) - abs(h * (6.0 / t)  - (4.0 / t));
    return clamp(vec3(r,g,b), 0.0, 1.0);
}

vec3 HSVtoRGB(vec3 hsv) {
    return ((hue(hsv.x) - 1.0) * hsv.y + 1.0) * hsv.z;
}

vec3 RGBtoHSV(vec3 rgb) {
    vec3 hsv = vec3(0.0);
    hsv.z = max(rgb.r, max(rgb.g, rgb.b));
    float min = min(rgb.r, min(rgb.g, rgb.b));
    float c = hsv.z - min;

    if (c != 0.0)
    {
        hsv.y = c / hsv.z;
        vec3 delta = (hsv.z - rgb) / c;
        delta.rgb -= delta.brg;
        delta.rg += vec2(2.0, 4.0);
        if (rgb.r >= hsv.z) {
            hsv.x = delta.b;
        } else if (rgb.g >= hsv.z) {
            hsv.x = delta.r;
        } else {
            hsv.x = delta.g;
        }
        hsv.x = fract(hsv.x / 6.0);
    }
    return hsv;
}

void main() {
    vec4 rgb = texture(DiffuseSampler, texCoord);
    vec3 hsv = RGBtoHSV(rgb.rgb);
    hsv.x = fract(hsv.x + Time);
    fragColor = vec4(HSVtoRGB(hsv), 1.0);
}

float applyDistortion(vec2 uv,float zoom,float distortion, float gooeyness,float wibble)
{
    float gameTime = Time * 10;
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

//void main()
//{
//    vec4 rgb = texture(DiffuseSampler, texCoord);
//    float goo = applyDistortion(oneTexel, 1, 10, 1, 10);
//    vec4 col = rgb;
//    col.a *= clamp(1.-goo, 0.,1.);
//    fragColor = col;
//}