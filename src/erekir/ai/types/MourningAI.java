package erekir.ai.types;

import arc.util.*;
import arc.math.*;
import arc.math.geom.*;
import mindustry.ai.types.*;
import mindustry.entities.units.*;
import mindustry.gen.*;

/** Copied and modified from MissileAI.java. */
public class MourningAI extends AIController{
    public float circleRadius = 1f;
    public @Nullable Position pos;
    
    protected static final Rand rand = new Rand();
    
    @Override
    public void updateMovement() {
        unloadPayloads();
        
        float time = unit instanceof TimedKillc ? ((TimedKillc) unit).time() : 1000000f;

        rand.setSeed(unit.id);
        float random = rand.random(circleRadius, 45f);
        
        float dst = circleRadius * 0.8f;
        if (pos != null) {
           Tmp.v1.set(pos.getX(), pos.getY());
           if (unit.within(Tmp.v1.x, Tmp.v1.y, dst)) {
              circle((Position) Tmp.v1, random);
           } else {
              moveFront(time);
           }
        }
        else moveFront(time);
        
        Building build = unit.buildOn();

        //kill instantly on enemy building contact
        if (build != null && build.team != unit.team) {
            unit.kill();
        }
    }
    
    /** Pretty much reusing the same thing. */
    public void moveFront(float time) {
       unit.moveAt(vec.trns(unit.rotation, unit.type.missileAccelTime <= 0f ? unit.speed() : Mathf.pow(Math.min(time / unit.type.missileAccelTime, 1f), 2f) * unit.speed()));
    }

    @Override
    public boolean retarget() {
        //perhaps
        return timer.get(timerTarget, 4f);
    }
}