package erekir.entities.bullet;

import arc.math.*;
import arc.math.geom.*;
import arc.util.*;
import mindustry.gen.*;
import mindustry.world.blocks.defense.turrets.Turret.*;
import mindustry.entities.bullet.*;

/** A bullet that points toward the mouse once initialized. */
public class MouseFollowBulletType extends BasicBulletType{
   
   public MouseFollowBulletType(float speed, float damage) {
       super(speed, damage);
       //do not use current homing
       homingRange = 0f;
   }
   
   @Override
   public void init(Bullet b) {
      super.init(b);
      
      Vec2 v = new Vec2();
      v.set(b.x, b.y);
      
      if (b.owner instanceof Unitc) {
         Unitc owner = (Unitc) b.owner;
         v.set(owner.aimX(), owner.aimY());
      }
      else if (b.owner instanceof Turret.TurretBuild) {
         Vec2 t = ((TurretBuild) b.owner).targetPos;
         v.set(t);
      } 
      float d = b.dst2(v);
      if (d > Math.pow(range, 2)) {
         float angle = v.angleTo(b);
         //snap to the closest range
         v.set(Tmp.v1.set(v.x, v.y).trns(angle, (float) Math.sqrt(d) - range));
      }
      b.data = v;
   }
   
   @Override
   public void update(Bullet b) {
      super.update(b);
      
      if (b.fdata != 1) {
         Vec2 v = (Vec2) b.data;
         b.vel.setAngle(Angles.moveToward(b.rotation(), b.angleTo(v.x, v.y), Time.delta * 261f * homingPower * b.fin()));
         
         if (b.within(v.x, v.y, b.hitSize)) b.fdata = 1;
      }
   }
}