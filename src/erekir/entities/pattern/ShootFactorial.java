package erekir.entities.pattern;

import mindustry.entities.pattern.ShootPattern;

/** Shoots bullets in an ever-increasing pattern. */
public class ShootFactorial extends ShootPattern{
   public float spread = 5f;
   public int iterations = 5;
   
   public ShootFactorial(int iterations, float spread) {
      this.iterations = iterations;
      this.spread = spread;
   }
    
   public ShootFactorial() {
     
   }
   
   @Override
   public void shoot(int totalShots, BulletHandler handler) {
      for (int i = 0; i < shots; i++) {
         for (int I = 0; I < iterations; I++) {
            float angleOffset = i * spread - (shots - 1) * spread / 2f;
            handler.shoot(
               0, 0, angleOffset,
               firstShotDelay + shotDelay * I
            );
         }
      }
   }
}