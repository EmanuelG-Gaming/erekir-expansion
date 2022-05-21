package erekir.entities;

import arc.math.*;
import mindustry.entities.pattern.ShootPattern;

/**No need for a singular triangle wave*/
public class ShootTriHelix extends ShootPattern{
   public float scl = 4f, mag = 20f;
   
   @Override
   public void shoot(int totalShots, BulletHandler handler) {
      for (int i = 0; i < shots; i++) {
         for (int s : Mathf.signs) {
            float a = mag * s, p = scl;
            handler.shoot(
               0, 0, 0,
               firstShotDelay + shotDelay * i,
               b -> b.moveRelative(0f, 4f * a / p * Mathf.floor(Mathf.mod(b.time - p / 4f, p) - p / 2f) - a)
            );
         }
      }
   }
}