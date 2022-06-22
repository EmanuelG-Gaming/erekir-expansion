uniform sampler2D u_texture;
uniform vec4 u_ambient;

varying vec2 v_texCoords;

int main() {
   vec2 c = v_texCoords.xy;
   
   vec4 color = texture2D(u_texture, c);
   gl_FragColor = clamp(vec4(mix(u_ambient.rgb, color.rgb, color.a), 1.0), 0.0, 1.0);
};