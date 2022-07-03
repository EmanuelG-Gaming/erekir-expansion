package erekir.entities.bullet;

import arc.math.*;
import arc.math.geom.*;
import mindustry.gen.*;
import mindustry.entities.bullet.*;
import erekir.ai.types.*;

import static mindustry.Vars.*;

/** A bullet type that can assign something to its
 *  despawn unit. */
public class MourningSpawnBulletType extends BulletType{
   public float despawnAngleOffset = 15f;
   protected static final Vec2 v = new Vec2();
   
   public MourningSpawnBulletType(float speed, float damage) {
       super(speed, damage);
   }
   
   @Override
   public void createUnits(Bullet b, float x, float y) {
      if (despawnUnit != null) {
         for (int i = 0; i < despawnUnitCount; i++) {
            if (!net.client()) {
               Unit unit = despawnUnit.create(b.team);
               v.set(x, y);
               unit.set(v.x + Mathf.range(despawnUnitRadius), v.y + Mathf.range(despawnUnitRadius));
               unit.rotation = b.rotation() + Mathf.range(despawnAngleOffset); 
               
               //assign unit circle position
               if (unit.controller() instanceof MourningAI) {
                  MourningAI ai = (MorningAI) unit.controller();
                  ai.pos = (Position) v;
               }
               
               unit.add();
            }
         }
      }
   }
}