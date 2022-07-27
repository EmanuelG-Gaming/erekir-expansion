package erekir.entities.abilities;

import arc.Core;
import arc.struct.*;
import arc.func.*;
import arc.util.*;
import arc.math.*;
import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.Events;
import arc.math.geom.*;
import mindustry.gen.*;
import mindustry.content.*;
import mindustry.game.EventType.*;
import mindustry.type.*;
import mindustry.entities.*;
import mindustry.entities.abilities.*;
import mindustry.graphics.*;
import erekir.ai.types.*;

import static mindustry.Vars.*;

public class FollowUnitAbility extends Ability{
    public UnitType spawnUnit;
    public int maxSpawnUnits = 5;
    
    public float spawnTime = 60f, spawnX, spawnY, patrolRadius = 50f;
    public Effect spawnEffect = Fx.spawn;
    public boolean parentizeEffects;

    protected float timer;
    private Seq<Unit> spawnUnits = new Seq<Unit>(maxSpawnUnits);
    
    FollowUnitAbility() {}
    
    public FollowUnitAbility(UnitType spawnUnit, float spawnX, float spawnY, float spawnTime) {
        this.spawnUnit = spawnUnit;
        this.spawnX = spawnX;
        this.spawnY = spawnY;
        this.spawnTime = spawnTime;
    }
    
    @Override
    public void update(Unit unit) {
       if (!net.client()) {
          if (spawnUnits.size <= maxSpawnUnits) {
             timer += Time.delta * state.rules.unitBuildSpeed(unit.team);
       
             if (timer >= spawnTime) {
                float x = unit.x + Angles.trnsx(unit.rotation, spawnY, spawnX), y = unit.y + Angles.trnsy(unit.rotation, spawnY, spawnX);
          
                spawnEffect.at(x, y, 0f, parentizeEffects ? unit : null);
                Unit u = spawnUnit.create(unit.team);
                u.set(x, y);
                u.rotation = unit.rotation;
                
                //TODO the thing
                if (unit.controller() instanceof FlyAroundAI) {
                   FlyAroundAI ai = (FlyAroundAI) unit.controller();
                   ai.owner = unit;
                   ai.patrolRadius = patrolRadius;
                }
               
                Events.fire(new UnitCreateEvent(u, null, unit));

                u.add();
                spawnUnits.add(u);
             
                timer %= spawnTime;
             }
          }
          consUnits(u -> {
             if (!u.isValid()) {
                spawnUnits.remove(u);
             }
          });
       } 
    }
    
    @Override
    public void death(Unit unit) {
       consUnits(u -> Call.unitDespawn(u));
       spawnUnits.clear();
    }
    
    @Override
    public void draw(Unit unit) {
       if (spawnUnits.size <= maxSpawnUnits) {
          Draw.draw(Draw.z(), () -> {
             float x = unit.x + Angles.trnsx(unit.rotation, spawnY, spawnX), y = unit.y + Angles.trnsy(unit.rotation, spawnY, spawnX);
             Drawf.construct(x, y, spawnUnit.fullIcon, unit.rotation - 90, timer / spawnTime, 1f, timer);
          });
       }
    }
    
    public void consUnits(Cons<Unit> units) {
       spawnUnits.each(u -> units.get(u));
    }
    
    @Override
    public String localized() {
       return Core.bundle.format("ability.erekir-expansion-unitWandering", spawnUnit.localizedName);
    }
}