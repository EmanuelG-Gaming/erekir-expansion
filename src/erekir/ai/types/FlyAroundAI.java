package erekir.ai.types;

import arc.util.*;
import arc.math.*;
import arc.math.geom.*;
import mindustry.ai.types.*;
import mindustry.entities.units.*;
import mindustry.gen.*;

public class FlyAroundAI extends AIController{
    public @Nullable Unit patrolUnit;
    public @Nullable Vec2 offset;
    
    public float patrolRadius;
    
    private @Nullable Vec2 to;
    private int totalWaypoints = 0;
    
    @Override
    public void updateMovement() {
        unloadPayloads();
        
        float time = unit instanceof TimedKillc ? ((TimedKillc) unit).time() : 1000000f;
        float size = unit.type.hitSize * 2.5f;
        
        //nullable spam prevention
        if (offset == null) offset = new Vec2();
        if (to == null) to = new Vec2();
        
        if (patrolUnit != null && patrolUnit.isValid()) {
           Tmp.v1.set(patrolUnit.x + offset.x, patrolUnit.y + offset.y);
           if (totalWaypoints == 0) {
              to.trns(Mathf.randomSeed(unit.id, 0f, 360f), patrolRadius);
           }

           if (unit.within(Tmp.v1.x + to.x, Tmp.v1.y + to.y, size)) {
              to.trns(Mathf.randomSeed(totalWaypoints, 0f, 360f), patrolRadius);
              totalWaypoints++;
           }
           Vec2 v = new Vec2(Tmp.v1).add(to);
           moveTo(v, 1f, 3f);
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