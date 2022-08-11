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
    public static final Seq<FollowUnitAbility> all = new Seq<>();
   
    protected float timer;
    protected Seq<Unit> spawnUnits = new Seq<Unit>();
    protected int sum;
    
    FollowUnitAbility() {}
    
    public FollowUnitAbility(UnitType spawnUnit, float spawnX, float spawnY, float spawnTime) {
       this.sum = all.size * this.maxSpawnUnits;
       this.spawnUnit = spawnUnit;
       this.spawnX = spawnX;
       this.spawnY = spawnY;
       this.spawnTime = spawnTime;
       all.add(this);
    }
    
    @Override
    public void update(Unit unit) {
       Seq<Unit> owner = ownUnits(unit);
       consUnits(owner, u -> {
          if (!u.isValid()) {
             owner.remove(u);
          }
       });
       
       if (spawnUnits.size < sum) {
          if (timer >= spawnTime) {
             float x = unit.x + Angles.trnsx(unit.rotation, spawnY, spawnX), y = unit.y + Angles.trnsy(unit.rotation, spawnY, spawnX);
          
             spawnEffect.at(x, y, 0f, parentizeEffects ? unit : null);
             Unit u = spawnUnit.create(unit.team);
             u.set(x, y);
             u.rotation = unit.rotation;
              
             Vec2 v = new Vec2();
             if (u.controller() instanceof FlyAroundAI) {
                FlyAroundAI ai = (FlyAroundAI) u.controller();
                ai.patrolUnit = unit;
                ai.offset = new Vec2(spawnX, spawnY);
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
       consUnits(ownUnits(unit), u -> Call.unitDespawn(u));
       ownUnits(unit).clear();
    }
    
    @Override
    public void draw(Unit unit) {
       if (spawnUnits.size < sum) {
          Draw.draw(Draw.z(), () -> {
             float x = unit.x + Angles.trnsx(unit.rotation, spawnY, spawnX), y = unit.y + Angles.trnsy(unit.rotation, spawnY, spawnX);
             Drawf.construct(x, y, spawnUnit.fullIcon, unit.rotation - 90, timer / spawnTime, 1f, timer);
          });
       }
    }
    
    public void consUnits(Seq<Unit> units, Cons<Unit> unit) {
       units.each(u -> unit.get(u));
    }
    
    public Seq<Unit> ownUnits(Unit unit) {
       //it could be faster
       return spawnUnits.select(u -> ((FlyAroundAI) u.controller()).patrolUnit == unit);
    }
    
    @Override
    public String localized() {
       return Core.bundle.format("ability.erekir-expansion-unitWandering", spawnUnit.localizedName);
    }
}