#define HIGHP

//colors
#define S1 vec4(250.0, 145.0, 75.0, 255.0) / 255.0
#define S2 vec4(158.0, 24.0, 44.0, 255.0) / 255.0

#define NSCALE 130.5 / 2.0
#define DSCALE 110.5 / 2.0

uniform sampler2D u_texture;
uniform sampler2D u_noise;

uniform vec2 u_campos;
uniform vec2 u_resolution;
uniform float u_time;

varying vec2 v_texCoords;

void main() {
    vec2 c = v_texCoords.xy;
    vec2 coords = (c * u_resolution) + u_campos);

    float atime = u_time / 15000.0;
    float noise = (texture2D(u_noise, (coords) / DSCALE + texture2D(u_noise, (coords) / DSCALE))) / 2.0;

    noise = abs(noise - 0.5) * 7.0 + 0.23;

    c += (vec2(
        texture2D(u_noise, (coords) / NSCALE,
        texture2D(u_noise, (coords) / NSCALE
    ) - vec2(0.5)) * 20.0 / u_resolution;

    vec4 color = texture2D(u_texture, c);

    if (noise > 0.85) {
       if (color.g >= (S2).g - 0.1) {
          color.rgb = S1;
       }
    }

    gl_FragColor = color;
}