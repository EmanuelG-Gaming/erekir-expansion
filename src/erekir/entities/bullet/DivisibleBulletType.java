package erekir.entities.bullet;

import arc.audio.*;
import arc.math.*;
import arc.util.Time;
import arc.struct.Seq;
import mindustry.entities.*;
import mindustry.entities.bullet.*;
import mindustry.content.*;
import mindustry.gen.*; 

/** Frag bullets v2. */
public class DivisibleBulletType extends BasicBulletType{
   public int divisions = 3;
   public float spawnDelay = 0f;
   public float spawnInaccuracy = 0f;
   /** Bullets to be spawned. */
   public Seq<BulletType> bullets = new Seq<BulletType>();
   public boolean rotateShooting = false;
   /** True for clockwise rotation, otherwise counter-clockwise. */
   public boolean clockwise = true;
   
   public Sound spawnSound = Sounds.none;
   public float spawnSoundMin = 0.9f;
   public float spawnSoundMax = 1.1f;
   
   private static BulletType currentBullet = Bullets.placeholder;

   public DivisibleBulletType(float speed, float damage) {
       super(speed, damage);
   }
  
   @Override
   public void despawned(Bullet b) {
      shoot(b, b.x, b.y);
   }
   
   @Override
   public void hit(Bullet b, float x, float y) {
      super.hit(b, x, y);
      shoot(b, x, y);
   }
   
   public void shoot(Bullet b, float x, float y) {
      bullets.each(bul -> {
         int i = 0;
         Time.run(++i * spawnDelay, () -> {
            currentBullet = bul;
            float rot = 0;
            for (int d = 0; d < divisions; d++) {
               float angle = 360f / divisions * d;
               if (rotateShooting) {
                  release(
                    b, x, y,
                    clockwise
                    ? b.rotation() + angle - (rot += spawnInaccuracy)
                    : b.rotation() + angle + (rot += spawnInaccuracy)
                  );
               }
               else release(b, x, y, b.rotation() + angle + Mathf.range(spawnInaccuracy));
               spawnSound.at(x, y, Mathf.random(spawnSoundMin, spawnSoundMax));
            }
         });
      });
   }
   
   private void release(Bullet owner, float x, float y, float rotation) {
       currentBullet.create(owner, x, y, rotation);
   }
}