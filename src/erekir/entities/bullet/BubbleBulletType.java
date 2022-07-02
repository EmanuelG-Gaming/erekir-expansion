package erekir.entities.bullets;

import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.*;
import mindustry.gen.*;
import mindustry.entities.bullet.*;

/** The anti loaf of bread overseer bullet.
  *  No new functionality added. */
public class BubbleBulletType extends BulletType{
    public float reflectionLength = 2.5f;
    public float reflectionAngle = 45f;
    public float reflectionSize = 2f;
    /** Also applies for reflections. */
    public Color shellColor = Color.white;
    
    public BubbleBulletType(float speed, float damage) {
       super(speed, damage);
    }
    
    @Override
    public void draw(Bullet b) {
   	   super.draw(b);
   	   float rLen = reflectionLength, rAngle = reflectionAngle;
   	   float x = Angles.trnsx(rAngle, rLen), y = Angles.trnsy(rAngle, rLen);
   	   
   	   Draw.color(shellColor);
       Draw.z(layer);
       Lines.circle(b.x, b.y, hitSize);
       Fill.circle(b.x + x, b.y + y, reflectionSize);
       Draw.reset();
   }
   
   @Override
   public void drawLight(Bullet b) {
       //no
   }
}