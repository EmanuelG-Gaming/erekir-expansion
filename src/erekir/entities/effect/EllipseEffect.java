package erekir.entities.effect;

import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.*;
import mindustry.entities.*;

/** Renders particles with an x-y squish. */
public class EllipseEffect extends Effect{
   public Color colorFrom = Color.white, colorTo = Color.white;
   public int particles = 30;
   /** Squishes. */
   public float offsetX = 1f, offsetY = 1f;
   public float range = 100f;

   public EllipseEffect() {
      clip = 100f;
      lifetime = 30;
   }
   
   @Override
   public void render(EffectContainer e) {
      float fin = e.fin();
     
      Draw.color(colorFrom, colorTo, fin);
      Lines.stroke(e.fslope() + 0.5f);
      Angles.randLenVectors(e.id, particles, fin * range, (x, y) -> {
          float dx = x / offsetX, dy = y / offsetY;
          float angle = Mathf.angle(dx, dy);
          Lines.lineAngle(e.x + dx, e.y + dy, angle, 5f);
      });
   }
}