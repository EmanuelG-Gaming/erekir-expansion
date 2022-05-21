package erekir.entities.bullet;

import arc.func.*;
import mindustry.entities.*;
import mindustry.entities.Units;
import mindustry.entities.bullet.*;

/** Barrier 2*/
public class CarapaceBulletType extends BulletType{
   public float deflectPower = 0.05f;
   
   public CarapaceBulletType() {
      super();
      speed = 0f;
      width = height = 0f;
      frontColor = Color.white;
      backColor = Color.valueOf("93de7e");
      keepVelocity = false;
   }
   
   @Override
   public void draw(Bullet b) {
   	  super.draw(b);
   	  
      //spam
      Tmp.c1.set(frontColor);
      Tmp.c1.a = Interp.pow3Out.apply(b.fout());
      Tmp.c2.set(backColor);
      Tmp.c2.a = Interp.pow5Out.apply(b.fout());
       
      Draw.z(Layer.weather);
      Fill.light(b.x, b.y, 24f, (hitSize + 10f) * b.fin(), Tmp.c1, Tmp.c2);
      Draw.reset();
   }
   
   @Override
   public void update(Bullet b) {
      super.update(b);
      Cons<Unit> enemies = unit -> {
         if (unit != null && unit.within(b.x, b.y, hitSize) && unit.isValid()) {
            float overlap = ((Physicsc) unit).hitSize / 2 + ((Physicsc) b).hitSize) - unit.dst(b);
             
            Tmp.v1.set(b).sub(unit).nor().scl((overlap + 0.01f) * 60f);
            Tmp.v1.setAngle(b.angleTo(unit));
            unit.vel.setZero();
            unit.impulse(Tmp.v1);
            
            if (Mathf.chance(0.2f)) {
               ErkFx.gemHit.at(unit.x, unit.y, b.angleTo(unit));
            }
         }
       };
       
       Cons<Bullet> bullets = bul -> {
        	if (bul != null && bul.team != b.team && bul.owner != b.owner) { 
      	     bul.vel.setAngle(Angles.moveToward(bul.rotation(), b.angleTo(bul), Time.delta * 261f * deflectPower));
          }
       };
       
       float size = b.hitSize + 10f;
       Units.nearbyEnemies(b.team, b.x, b.y, size, enemies);	
       Groups.bullet.intersect(b.x - size, b.y - size, size * 2, size * 2).each(bullets);
   }
   
   @Override
   public void drawLight(Bullet b) {
      //no
   }
}