package erekir.ai.types;

import arc.util.*;
import arc.math.*;
import arc.math.geom.*;
import mindustry.ai.types.*;
import mindustry.entities.units.*;
import mindustry.gen.*;

public class FlyAroundAI extends AIController{
    public @Nullable Unit owner;
    public float patrolRadius;
    
    private Vec2 toPos;
    
    @Override
    public void updateMovement() {
        unloadPayloads();
        
        float time = unit instanceof TimedKillc ? ((TimedKillc) unit).time() : 1000000f;
        
        if (owner != null) {
           Tmp.v1.set(owner.x, owner.y);
           if (unit.within(Tmp.v1.x, Tmp.v1.y, unit.type.hitSize / 2f)) {
              toPos.set(Tmp.v1).trns(Mathf.randomSeed(unit.id, 0f, 360f), patrolRadius);
           }
           else if (unit.within(toPos.x, toPos.y, unit.type.hitSize / 2f)) {
              toPos.set(Tmp.v1).trns(Mathf.random(0f, 360f), patrolRadius);
           }
           moveTo(toPos, 1f, 3f);
           unit.lookAt(toPos);
        }
        else moveFront(time);
        
        Building build = unit.buildOn();

        //kill instantly on enemy building contact
        if (build != null && build.team != unit.team) {
            unit.kill();
        }
    }
   
    public void moveFront(float time) {
       unit.moveAt(vec.trns(unit.rotation, unit.type.missileAccelTime <= 0f ? unit.speed() : Mathf.pow(Math.min(time / unit.type.missileAccelTime, 1f), 2f) * unit.speed()));
    } 
    
    @Override
    public boolean retarget() {
        return timer.get(timerTarget, 4f);
    }
}