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
import erekir.entities.bullet.*;
import erekir.entities.pattern.*;
import erekir.ctype.*;

import static mindustry.Vars.*;

public class ErkUnitTypes implements AltContentList{
    public static UnitType
    
    gem, geode;
    
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
          rippleScale = 0.2f;
          
	        legMoveSpace = 1f;
          hovering = true;
          legPhysicsLayer = false;
          allowLegStep = true;
          
          shadowElevation = 0.1f;
          groundLayer = Layer.legUnit - 1f;
          researchCostMultiplier = 0;
          
          constructor = LegsUnit::create;
          weapons.add(new Weapon("erekir-expansion-gem-weapon"){{
             reload = 35f;
             mirror = true;
             top = true;
             x = 3.2f;
             y = -1.65f;
             shootCone = 360f;
             bullet = new BasicBulletType(5f, 9.5f){{
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
       
       geode = new ErekirUnitType("geode"){{
          health = 800;
	        armor = 1;
	        speed = 0.78f;
	        hitSize = 10f;
	        aimDst = 2.4f;
          range = 160f;
	        drag = 0.06f;
	        accel = 0.08f;
	        flying = false;
	        rotateSpeed = 1.75f;

          stepShake = 0f;

          legCount = 4;
          legLength = 8f;
          lockLegBase = true;
          legContinuousMove = true;
          legExtension = -3f;
          legBaseOffset = 5f;
          legMaxLength = 1.1f;
          legMinLength = 0.2f;
          legLengthScl = 0.95f;
          legForwardScl = 0.7f;
            
	        legMoveSpace = 1f;
          hovering = true;
          legPhysicsLayer = false;

          shadowElevation = 0.1f;
          groundLayer = Layer.legUnit - 1f;
          researchCostMultiplier = 0;
          
          constructor = LegsUnit::create;
          weapons.add(
             new Weapon("geodeShield"){{
                reload = 250f;
                mirror = false;
                top = true;
                x = 0f;
                y = 0f;
                shootSound = ErkSounds.fieldRelease;
                shootCone = 360;
                shootY = 0f;
                bullet = new CarapaceBulletType(){{
                   lifetime = 70f;
                   hitSize = 65f;
                   layer = Layer.weather;
                   ejectEffect = Fx.none;
                   hitEffect = Fx.none;
                   despawnEffect = Fx.none;
                   pushBackEffect = ErkFx.gemHit;
                }};
            }},
            new Weapon("geodeMissile"){{
               reload = 25f;
               mirror = true;
               top = true;
               x = 3.5f;
               y = -1.3f;
               shootSound = Sounds.missile;
               bullet = new MissileBulletType(3f, 12.5f){{
                  frontColor = Color.white;
                  backColor = Color.valueOf("93de7e");
                  trailColor = Pal.heal;
                  trailChance = 0.45f;
                  splashDamage = 13.5f;
                  splashDamageRadius = 14f;
                  homingRange = 0f;
                  width = 8f;
                  height = 11f;
                  lifetime = 50f;
                  //TODO placeholder effect?
                  hitEffect = ErkFx.gemHit;
                  despawnEffect = ErkFx.gemHit;
               }};
            }}
          );
       }};
    }
}