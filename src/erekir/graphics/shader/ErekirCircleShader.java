package erekir.graphics.shader;

import arc.Core;
import arc.util.*;
import arc.graphics.*;
import arc.graphics.Texture.*;
import arc.graphics.gl.*;
import mindustry.graphics.Shaders;

import static mindustry.Vars.*;
import static mindustry.graphics.Shaders.getShaderFi;

public class ErekirCircleShader extends Shader{
   Texture circleTex;
   public Color ambient = new Color(0.0f, 0.0f, 0.01f, 0.99f);
   
   public ErekirCircleShader(String frag) {
       super(Core.files.internal("shaders/screenspace.vert"), tree.get("shaders/" + frag + ".frag"));
       loadTexture();
   }

   public ErekirCircleShader(String vert, String frag) {
       super(vert, frag);
       loadTexture();
   }

   public String textureName() {
       return "hollowCircle";
   }

   public void loadTexture() {
       String path = "sprites/effects/";
       Core.assets.load(path + textureName() + ".png", Texture.class).loaded = t -> {
           t.setFilter(Texture.TextureFilter.linear);
           t.setWrap(Texture.TextureWrap.repeat);
       };
   }

   @Override
   public void apply() {
      setUniformf("u_ambient", ambient);

      if (hasUniform("u_texture")) {
         if (circleTex == null) circleTex = Core.assets.get("sprites/" + textureName() + ".png", Texture.class);
         circleTex.bind(1);
         renderer.effectBuffer.getTexture().bind(0);

         setUniformi("u_texture", 1);
      }
   }
}