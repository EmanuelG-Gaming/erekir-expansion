package erekir.content;

import arc.graphics.*;
import arc.graphics.Color;
import arc.graphics.g2d.*;
import arc.math.*;
import mindustry.graphics.*;
import mindustry.type.*;
import mindustry.type.unit.*;
import mindustry.gen.*;
import mindustry.content.*;
import mindustry.ctype.*;
import mindustry.entities.bullet.*;
import mindustry.entities.effect.*;
import erekir.graphics.*;
import erekir.entities.bullet.*;
import erekir.entities.pattern.*;
import erekir.entities.effect.*;
import erekir.ctype.*;
import erekir.ai.types.*;
import erekir.type.ammo.OreAmmoType;

import static mindustry.Vars.*;

public class ErkUnitTypes implements AltContentList{
    public static UnitType
    
    //ground insect
    gem, geode,
    
    //flying
    aggregate,
    //why
    agglomerateMissile, agglomerate, spread;
    
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
                backColor = trailColor = ErkPal.greenishBeryl;
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
          health = 1200;
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
                   frontColor = Color.white;
                   backColor = ErkPal.greenishBeryl;
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
               bullet = new MissileBulletType(3f, 16f){{
                  frontColor = Color.white;
                  backColor = trailColor = ErkPal.greenishBeryl;
                  trailChance = 0.45f;
                  splashDamage = 19.5f;
                  splashDamageRadius = 17f;
                  shootEffect = new EllipseEffect(){{
                     lifetime = 20f;
                     colorFrom = Pal.heal;
                     colorTo = ErkPal.greenishBeryl;
                     offsetY = 2f;
                     particles = 6;
                     range = 8f;
                  }};
                  homingRange = 0f;
                  width = 8f;
                  height = 11f;
                  lifetime = 50f;
                  hitEffect = ErkFx.berylMissileHit;
                  despawnEffect = ErkFx.berylMissileHit;
               }};
            }}
          );
       }};
       
       aggregate = new ErekirUnitType("aggregate"){{
          health = 650;
	        speed = 2.4f;
 	        hitSize = 9;
	        drag = 0.01f;
	        accel = 0.35f;
	        flying = true;
          aimDst = 2f;
          range = 200f;
          engineOffset = 5.75f;
          targetAir = true;
          
          constructor = UnitEntity::create;
          weapons.add(new Weapon(){{
             reload = 15f;
             mirror = true;
             top = true;
             x = 2.3f;
             y = -0.9f;
             //flar
             bullet = new BasicBulletType(3f, 12f){{
                backColor = trailColor = ErkPal.greenishBeryl;
                frontColor = Color.white;
                trailLength = 5;
                despawnEffect = ErkFx.gemHit;
                hitEffect = ErkFx.gemHit;
                shootEffect = Fx.none;
                smokeEffect = Fx.shootSmallSmoke;
                width = 6.25f;
                height = 7f;
                lifetime = 80f;
             }};
          }});
       }};
       
       agglomerateMissile = new MissileUnitType("agglomerate-missile"){{
           trailColor = engineColor = ErkPal.greenishBeryl;
           engineSize = 1.75f;
           engineOffset = 2.25f;
           engineLayer = Layer.effect;
           hitSize = 3;
           speed = 3.5f;
           lifetime = 60f * 2.5f;
           outlineColor = Pal.darkOutline;
           health = 45;
           lowAltitude = true;
           rotateSpeed = 4.35f;
           controller = u -> new MourningAI();
           
           weapons.add(new Weapon(){{
              shootCone = 360f;
              mirror = false;
              reload = 1f;
              shootOnDeath = true;
              bullet = new ExplosionBulletType(35f, 25f){{
                 shootEffect = new MultiEffect(Fx.massiveExplosion, new EllipseEffect(){{
                     lifetime = 55f;
                     colorFrom = Color.white;
                     colorTo = ErkPal.greenishBeryl;
                     offsetX = 3f;
                     particles = 15;
                     range = 45f;
                     drawer = (e, dx, dy) -> {
                         float angle = Mathf.angle(dx, dy);
                         Fill.circle(e.x + dx, e.y + dy, 5f * e.fout(Interp.pow5Out) / 2f + 2f);
                         Lines.lineAngle(e.x + dx * 2f, e.y + dy * 2f, angle, 5f * e.fout() + 1.5f);
                     };
                 }});
              }};
           }});
       }};
       
       agglomerate = new ErekirUnitType("agglomerate"){{
          health = 900;
	        speed = 2.1f;
 	        hitSize = 12;
	        drag = 0.03f;
	        accel = 0.24f;
	        flying = true;
          aimDst = 2f;
          range = 200f;
          engineOffset = 9.5f;
          targetAir = true;
          ammoType = new OreAmmoType(Blocks.wallOreBeryllium, 8);
          
          constructor = UnitEntity::create;
          weapons.add(new Weapon(){{
             reload = 70f;
             mirror = false;
             top = false;
             x = 0f;
             y = 0f;
             shootY = 0f;
             bullet = new BulletType(){{
                shootEffect = Fx.none;
                smokeEffect = Fx.none;
                shake = 1f;
                speed = 0f;
                keepVelocity = false;
                ammoMultiplier = 0.25f;
                spawnUnit = agglomerateMissile;
             }};
          }});
       }};
       
       spread = new ErekirUnitType("spread"){{
          health = 560;
	        speed = 2.6f;
 	        hitSize = 7;
	        drag = 0.055f;
	        accel = 0.35f;
	        flying = true;
          aimDst = 2f;
          range = 150f;
          engineOffset = 5.75f;
          targetAir = true;
          
          ammoType = new OreAmmoType(Blocks.wallOreTungsten, 16);
          constructor = UnitEntity::create;
          weapons.add(new Weapon(){{
             reload = 50f;
             mirror = false;
             top = true;
             x = 0f;
             y = 0f;
             bullet = new DivisibleBulletType(4f, 23f){{
                knockback = 5f;
                width = 23f;
                hitSize = 6.5f;
                height = 18f;
                shootEffect = Fx.shootBigColor;
                smokeEffect = Fx.shootSmokeSquareSparse;
                hitColor = backColor = trailColor = Color.valueOf("ea8878");
                frontColor = Color.valueOf("feb380");
                trailWidth = 5f;
                trailLength = 3;
                divisions = 4;
                spawnDelay = 2f;
                spawnInaccuracy = 7.5f;
                hitEffect = despawnEffect = Fx.hitSquaresColor;
                
                bullets.add(
                new BasicBulletType(3f, 6f){{
                    width = 14f;
                    hitSize = 3.5f;
                    height = 10.5f;
                    hitColor = backColor = trailColor = Color.valueOf("ea8878");
                    frontColor = Color.valueOf("feb380");
                    trailWidth = 4f;
                    trailLength = 3;
                    hitEffect = despawnEffect = Fx.none;
                }},
                new BasicBulletType(2.5f, 5.5f){{
                    width = 12f;
                    hitSize = 3.35f;
                    height = 9f;
                    hitColor = backColor = trailColor = Color.valueOf("ea8878");
                    frontColor = Color.valueOf("feb380");
                    trailWidth = 3.5f;
                    trailLength = 3;
                    hitEffect = despawnEffect = Fx.none;
                }});
             }};
          }});
       }};
    }
}