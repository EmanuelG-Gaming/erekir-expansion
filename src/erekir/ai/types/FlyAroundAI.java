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
           toPos.set(Tmp.v1).trns(Mathf.randomSeed(unit.id, 360f), patrolRadius);
           if (unit.within(toPos.x, toPos.y, unit.type.hitSize / 2f)) {
              toPos.setAngle(Mathf.random(0f, 360f));
           }
           moveTo(toPos, unit.type.hitSize / 4f);
           unit.lookAt(toPos);
        }
        
        Building build = unit.buildOn();

        //kill instantly on enemy building contact
        if (build != null && build.team != unit.team) {
            unit.kill();
        }
    }

    @Override
    public boolean retarget() {
        return timer.get(timerTarget, 4f);
    }
}