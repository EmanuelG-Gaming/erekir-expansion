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
    public @Nullable Unit shooter;

    @Override
    public void updateMovement() {
        unloadPayloads();

        rand.setSeed(unit.id);
        
        float random = rand.random(36f, 55.5f);
        if (shooter != null) {
            unit.moveAt(vec.trns(unit.rotation, unit.speed()));
            vec2.set(shooter.x, shooter.y);
            super.updateMovement();
        }
        else {
            unit.circle((Position) vec2, random);
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