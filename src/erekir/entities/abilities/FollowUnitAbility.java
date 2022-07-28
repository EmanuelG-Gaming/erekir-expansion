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
    public int maxSpawnUnits = 3;
    
    public float spawnTime = 60f, spawnX, spawnY, patrolRadius = 50f;
    public Effect spawnEffect = Fx.spawn;
    public boolean parentizeEffects;

    protected float timer;
    protected Seq<Unit> spawnUnits = new Seq<Unit>();
    
    FollowUnitAbility() {}
    
    public FollowUnitAbility(UnitType spawnUnit, float spawnX, float spawnY, float spawnTime) {
       this.spawnUnit = spawnUnit;
       this.spawnX = spawnX;
       this.spawnY = spawnY;
       this.spawnTime = spawnTime;
    }
    
    @Override
    public void update(Unit unit) {
       consUnits(u -> {
          if (!u.isValid()) {
             spawnUnits.remove(u);
          }
       });
       
       if (spawnUnits.size < maxSpawnUnits) {
          if (timer >= spawnTime) {
             Vec2 v = new Vec2();
             v.trns(unit.rotation, v.angleTo(spawnX, spawnY)).add(unit.x, unit.y);
             
             spawnEffect.at(v.x, v.y, 0f, parentizeEffects ? unit : null);
             Unit u = spawnUnit.create(unit.team);
             u.set(v.x, v.y);
             u.rotation = unit.rotation;
                
             if (u.controller() instanceof FlyAroundAI) {
                FlyAroundAI ai = (FlyAroundAI) u.controller();
                ai.patrolUnit = unit;
                ai.offset = v.sub(unit.x, unit.y).nor();
                ai.patrolRadius = patrolRadius;
             }
               
             Events.fire(new UnitCreateEvent(u, null, unit));

             u.add();
             spawnUnits.add(u);
             
             timer %= spawnTime;
          }
          else {
             timer += Time.delta * state.rules.unitBuildSpeed(unit.team);
          }
       }
    }
    
    @Override
    public void death(Unit unit) {
       consUnits(u -> Call.unitDespawn(u));
       spawnUnits.clear();
    }
    
    @Override
    public void draw(Unit unit) {
       if (spawnUnits.size < maxSpawnUnits) {
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