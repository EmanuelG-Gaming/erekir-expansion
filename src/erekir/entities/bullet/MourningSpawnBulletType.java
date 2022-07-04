package erekir.entities.bullet;

import arc.math.*;
import arc.math.geom.*;
import mindustry.gen.*;
import mindustry.entities.bullet.*;
import erekir.ai.types.*;

import static mindustry.Vars.*;

/** A bullet type that can assign something to its
 *  despawn unit. */
public class MourningSpawnBulletType extends BasicBulletType{
   public float despawnAngleOffset = 15f;
   
   public MourningSpawnBulletType(float speed, float damage) {
       super(speed, damage);
   }
   
   @Override
   public void createUnits(Bullet b, float x, float y) {
      if (despawnUnit != null) {
         for (int i = 0; i < despawnUnitCount; i++) {
            if (!net.client()) {
               Vec2 v = new Vec2();
               v.set(x + Mathf.range(despawnUnitRadius), y + Mathf.range(despawnUnitRadius));
               
               Unit unit = despawnUnit.create(b.team);
               unit.set(v.x, v.y);
               unit.rotation = b.rotation() + Mathf.range(despawnAngleOffset); 
               
               //assign unit circle position
               if (unit.controller() instanceof MourningAI) {
                  MourningAI ai = (MourningAI) unit.controller();
                  ai.pos = (Position) v;
                  ai.circleRadius = despawnUnitRadius;
               }
               
               unit.add();
            }
         }
      }
   }
}