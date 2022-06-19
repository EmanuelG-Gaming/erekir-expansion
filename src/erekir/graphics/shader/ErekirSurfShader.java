package erekir.graphics.shader;

import arc.Core;
import arc.util.*;
import arc.graphics.*;
import arc.graphics.Texture.*;
import arc.graphics.gl.*;
import mindustry.graphics.Shaders;

import static mindustry.Vars.*;
import static mindustry.graphics.Shaders.getShaderFi;

/** @author xStaBUx */
public class ErekirSurfShader extends Shader{
   Texture noiseTex;

   public ErekirSurfShader(String frag) {
       super(Core.files.internal("shaders/screenspace.vert"), tree.get("shaders/" + frag + ".frag"));
       loadNoise();
   }

   public ErekirSurfShader(String vert, String frag) {
       super(vert, frag);
       loadNoise();
   }

   public String textureName() {
       return "noise";
   }

   public void loadNoise() {
       Core.assets.load("sprites/" + textureName() + ".png", Texture.class).loaded = t -> {
           t.setFilter(Texture.TextureFilter.linear);
           t.setWrap(Texture.TextureWrap.repeat);
       };
    }

    @Override
    public void apply() {
        float w = Core.camera.width, h = Core.camera.height;
        
        setUniformf("u_campos", Core.camera.position.x - w / 2, Core.camera.position.y - h / 2);
        setUniformf("u_resolution", w, h);
        setUniformf("u_time", Time.time);

        if (hasUniform("u_noise")) {
           if (noiseTex == null) noiseTex = Core.assets.get("sprites/" + textureName() + ".png", Texture.class);
           noiseTex.bind(1);
           renderer.effectBuffer.getTexture().bind(0);

           setUniformi("u_noise", 1);
        }
    }
}