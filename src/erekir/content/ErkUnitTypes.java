package erekir.content;

import arc.graphics.*;
import arc.graphics.Color;
import mindustry.graphics.*;
import mindustry.type.*;
import mindustry.type.unit.ErekirUnitType;
import mindustry.gen.*;
import mindustry.content.Fx;
import mindustry.ctype.*;
import mindustry.entities.bullet.*;
import erekir.entities.pattern.*;
import erekir.ctype.*;

import static mindustry.Vars.*;

public class ErkUnitTypes implements AltContentList{
    public static UnitType
    
    gem;
    
    @Override
    public void load() {
       gem = new ErekirUnitType("gem"){{
          health = 650;
       	  armor = 1;
	        speed = 0.78f;
	        hitSize = 9.5f;
	        aimDst = 2.4f;
          range = 160;
	        drag = 0.06f;
	        accel = 0.08f;
	        flying = false;
	        rotateSpeed = 1.75f;
	        
          legStraightness = 0.3f;
          stepShake = 0f;

          legCount = 6;
          legLength = 8f;
          lockLegBase = true;
          legContinuousMove = true;
          legExtension = -2f;
          legBaseOffset = 3f;
          legMinLength = 0.2f;
          legMaxLength = 1.1f;
          legLengthScl = 0.96f;
          legForwardScl = 1.1f;
          legGroupSize = 3;
          rippleScale = 0.2;
          
	        legMoveSpace = 1f;
          hovering = true;
          legPhysicsLayer = false;
          allowLegStep = true;
          
          shadowElevation = 0.1f;
          groundLayer = Layer.legUnit - 1f;
          researchCostMultiplier = 0;
          
          constructor = LegsUnit::create;
          weapons.add(new Weapon("erekir-expansion-gem-weapon"){{
             reload = 40f;
             mirror = true;
             top = true;
             x = 3.2f;
             y = -1.65f;
             shootCone = 360f;
             bullet = new BasicBulletType(5f, 17f){{
                backColor = Color.valueOf("93de7e");
                trailColor = Color.valueOf("93de7e");
                frontColor = Color.white;
                trailLength = 5;
                despawnEffect = ErkFx.gemHit;
                hitEffect = ErkFx.gemHit;
                shootEffect = Fx.none;
                width = 6.25f;
                height = 7f;
                lifetime = 60f;
                homingPower = 0.07f;
                homingRange = 8f * tilesize;
             }};
             
             shoot = new ShootTriHelix(){{
                 shots = 1;
                 scl = 4.5f;
                 mag = 1.2f;
             }};
          }});
       }};
    }
}