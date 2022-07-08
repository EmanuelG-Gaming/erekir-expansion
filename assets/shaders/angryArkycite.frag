#define HIGHP

//colors
#define S1 vec4(85.0, 120.0, 68.0, 255.0) / 255.0
#define S2 vec3(126.0, 163.0, 70.0) / 255.0
#define S3 vec3(191.0, 201.0, 101.0) / 255.0

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
    vec2 coords = (c * u_resolution) + u_campos;

    vec4 orig = texture2D(u_texture, c);

    float atime = u_time / 15000.0;
    float noise = (texture2D(u_noise, (coords) / DSCALE + vec2(atime) * vec2(-0.9, 0.8)).r + texture2D(u_noise, (coords) / DSCALE + vec2(atime * 1.1) * vec2(0.8, -1.0)).r) / 2.0;

    noise = abs(noise - 0.5) * 7.0 + 0.23;

    float btime = u_time / 9000.0;

    c += (vec2(
        texture2D(u_noise, (coords) / NSCALE + vec2(btime) * vec2(-0.9, 0.8)).r,
        texture2D(u_noise, (coords) / NSCALE + vec2(btime * 1.1) * vec2(0.8, -1.0)).r
    ) - vec2(0.5)) * 20.0 / u_resolution;

    vec4 color = texture2D(u_texture, c);

    if (noise > 0.85) {
       if (color.g >= (S2).g - 0.1) {
          color.rgb = S3;
       } else {
          color.rgb = S2;
       }
    } else if (noise > 0.5) {
       color.rgb = S2;
    }

    if (orig.g > 0.01) {
       color = max(S1, color);
    }

    gl_FragColor = color;
}