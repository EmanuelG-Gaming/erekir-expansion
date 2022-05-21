package erekir.content;

import arc.*;
import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.*;
import mindustry.entities.Effect;
import mindustry.graphics.*;

public class ErkFx {
  public static final Effect
  
  gemHit = new Effect(55f, e -> {
     Draw.color(Pal.heal, Color.valueOf("93de7e"), e.fin());
     Lines.stroke(0.5f + e.fout());

     Angles.randLenVectors(e.id, 4, 1f + e.fin() * 15f, e.rotation, 50f, (x, y) -> {
         float ang = Mathf.angle(x, y);
         Lines.lineAngle(e.x + x, e.y + y, ang, e.fout() * 3.5f + 1.4f);
     });
  });
}