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
    protected static final Vec2 vec2 = new Vec2();
    public @Nullable Position pos;
    
    @Override
    public void updateMovement() {
        unloadPayloads();

        rand.setSeed(unit.id);
        float random = rand.random(36f, 55.5f);
        float dst = unit.type.range * 0.8;
        if (unit.within(vec2.x, vec2.y, dat)) {
            vec2.set(pos.x, pos.y);
            circle((Position) vec2, random);
        }
        else {
            unit.moveAt(vec.trns(unit.rotation, unit.type.missileAccelTime <= 0f ? unit.speed() : Mathf.pow(Math.min(time / unit.type.missileAccelTime, 1f), 2f) * unit.speed()));
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