package erekir.content;

import arc.Core;
import arc.util.*;
import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.*;
import mindustry.content.*;
import mindustry.entities.Effect;
import mindustry.graphics.*;
import erekir.graphics.*;

public class ErkFx {
  public static final Effect
  
  //when all the effects use the same color method
  gemHit = new Effect(14f, e -> {
     Draw.color(Color.white, ErkPal.greenishBeryl, e.fin());
     Lines.stroke(0.5f + e.fout());

     Angles.randLenVectors(e.id, 4, 1f + e.fin() * 15f, e.rotation, 50f, (x, y) -> {
         float ang = Mathf.angle(x, y);
         Lines.lineAngle(e.x + x, e.y + y, ang, e.fout() * 3.5f + 1.4f);
     });
  }),
  
  berylMissileHit = new Effect(14f, e -> {
     Draw.color(Color.white, ErkPal.greenishBeryl, e.fin());

     e.scaled(7f, s -> {
         Lines.stroke(1f + s.fout());
         Lines.circle(e.x, e.y, s.fin() * 23f);
     });

     Lines.stroke(0.5f + e.fout());

     Angles.randLenVectors(e.id, 7, e.fin() * 24f, (x, y) -> {
         float ang = Mathf.angle(x, y);
         Lines.lineAngle(e.x + x, e.y + y, ang, e.fout() * 4.5f + 1.4f);
     });

     Drawf.light(e.x, e.y, 30f, ErkPal.greenishBeryl, 0.6f * e.fout());
  }),
  
  hitSquaresColorSmall = new Effect(14f, e -> {
     Draw.color(Color.white, e.color, e.fin());

     e.scaled(7f, s -> {
        Lines.stroke(0.5f + s.fout());
        Lines.circle(e.x, e.y, s.fin() * 3.5f);
     });

     Lines.stroke(0.5f + e.fout());

     Angles.randLenVectors(e.id, 4, e.fin() * 9f, (x, y) -> {
        float ang = Mathf.angle(x, y);
        Fill.square(e.x + x, e.y + y, e.fout() * 3.2f, ang);
     });

     Drawf.light(e.x, e.y, 12f, e.color, 0.6f * e.fout());
  }),
  
  regenParticleBeryl = new Effect(100f, e -> {
      Draw.color(ErkPal.greenishBeryl);

      Fill.square(e.x, e.y, e.fslope() * 1.5f + 0.14f, 45f);
  }),
  
  meltPlasma = new Effect(40f, e -> {
      Color pal = Liquids.ozone.color;
      
      Draw.color(pal.cpy().mul(1.25f), pal, Color.gray, e.fin());
      Draw.alpha(0.85f * e.fout());
      
      float h = 20f, heightIncrease = e.fin();
      
      Angles.randLenVectors(e.id, 8, e.finpow() * 30f + 6f, e.rotation, 10f, (x, y) -> {
          float px = e.x + x - Core.camera.position.x;
          float py = e.y + y - Core.camera.position.y;
          
          //height vector
          Tmp.v1.set(e.x + x, e.y + y).trns(Angles.angle(Tmp.v1.x, Tmp.v1.y, px, py), h * heightIncrease);
          Fill.circle(e.x + x + Tmp.v1.x, e.y + y + Tmp.v1.y, 2.5f + e.fout() * 1.5f);
      });
  }).layer(Layer.flyingUnit + 0.01f);
}