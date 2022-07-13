package erekir.ai.types;

import arc.util.*;
import arc.math.*;
import arc.math.geom.*;
import mindustry.ai.types.*;
import mindustry.entities.units.*;
import mindustry.gen.*;

/** Copied and modified from MissileAI.java. */
public class MourningAI extends AIController{
    protected static final Rand rand = new Rand();
    public @Nullable Position pos;
    public float circleRadius = 1f;
    
    @Override
    public void updateMovement() {
        unloadPayloads();
        
        float time = unit instanceof TimedKillc ? ((TimedKillc) unit).time() : 1000000f;

        rand.setSeed(unit.id);
        float random = rand.random(circleRadius * 1.2f);
        
        float dst = circleRadius * 0.8f;
        if (pos != null) {
           if (unit.within(pos.getX(), pos.getY(), dst)) {
              circle(pos, random);
           } else {
              unit.moveAt(vec.trns(unit.rotation, unit.type.missileAccelTime <= 0f ? unit.speed() : Mathf.pow(Math.min(time / unit.type.missileAccelTime, 1f), 2f) * unit.speed()));
           }
        }
        
        Building build = unit.buildOn();

        //kill instantly on enemy building contact
        if (build != null && build.team != unit.team) {
            unit.kill();
        }
    }

    @Override
    public boolean retarget() {
        //perhaps
        return timer.get(timerTarget, 4f);
    }
}