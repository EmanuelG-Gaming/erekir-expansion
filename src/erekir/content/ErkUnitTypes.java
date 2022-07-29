package erekir.content;

import arc.audio.*;
import arc.util.*;
import arc.graphics.*;
import arc.graphics.Color;
import arc.graphics.g2d.*;
import arc.math.*;
import mindustry.graphics.*;
import mindustry.type.*;
import mindustry.type.unit.*;
import mindustry.type.ammo.*;
import mindustry.gen.*;
import mindustry.content.*;
import mindustry.ctype.*;
import mindustry.entities.*;
import mindustry.entities.bullet.*;
import mindustry.entities.pattern.*;
import mindustry.entities.effect.*;
import mindustry.ai.types.*;
import erekir.graphics.*;
import erekir.entities.bullet.*;
import erekir.entities.pattern.*;
import erekir.entities.effect.*;
import erekir.entities.abilities.*;
import erekir.ctype.*;
import erekir.ai.types.*;
import erekir.type.ammo.*;

import static mindustry.Vars.*;

public class ErkUnitTypes implements AltContentList{
    public static UnitType
    
    //ground insect
    gem, geode, mineralMissile, mineral,
    
    //flying
    aggregate, agglomerateMissile, agglomerate, accumulate,
    attractor,
    
    spread, apart, shredderMissile, shredder,
    
    melt;
    
    @Override
    public void load() {
       
       //region beryllium - ground
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
          ammoType = new ItemAmmoType(Items.beryllium);
          
          constructor = LegsUnit::create;
          weapons.add(new Weapon("erekir-expansion-gem-weapon"){{
             reload = 35f;
             mirror = true;
             top = true;
             x = 3.2f;
             y = -1.65f;
             shootCone = 360f;
             bullet = new BasicBulletType(5f, 14.5f){{
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
          ammoType = new ItemAmmoType(Items.graphite);
          
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
       
       mineralMissile = new MissileUnitType("mineral-missile"){{
           trailColor = engineColor = ErkPal.greenishBeryl;
           engineSize = 1.9f;
           engineOffset = 3.5f;
           engineLayer = Layer.effect;
           hitSize = 4;
           speed = 3f;
           lifetime = 60f * 35f;
           outlineColor = Pal.darkOutline;
           health = 120;
           lowAltitude = true;
           rotateSpeed = 4.35f;
           controller = u -> new FlyAroundAI();
           
           weapons.add(new Weapon(){{
              shootCone = 360f;
              mirror = false;
              reload = 1f;
              shootOnDeath = true;
              bullet = new ExplosionBulletType(55f, 30f){{
                 shootEffect = new MultiEffect(Fx.massiveExplosion, new Effect(45f, e -> {
                     Draw.color(Color.white, ErkPal.greenishBeryl, e.fin());

                     Angles.randLenVectors(e.id, 18, e.finpow() * 60f, (x, y) -> {
                        float angle = Mathf.angle(x, y);
                        Lines.line(e.x + x, e.y + y, angle, e.fout(Interp.pow5Out) * 6.5f + 1.2f);
                     });
                 }));
              }};
           }});
       }};
       
       mineral = new ErekirUnitType("mineral"){{
          health = 3000;
       	  armor = 7;
	        speed = 0.60f;
	        hitSize = 19f;
	        aimDst = 2.1f;
          range = 160;
	        drag = 0.06f;
	        accel = 0.08f;
	        flying = false;
	        rotateSpeed = 1.95f;
	        
          legStraightness = 0.3f;
          stepShake = 0f;

          legCount = 6;
          legLength = 18f;
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
          ammoType = new PowerAmmoType(1200);
          
          abilities.add(new FollowUnitAbility(mineralMissile, 0f, -2f, (float) 3 * Time.toSeconds){{
             patrolRadius = 140f;
             maxSpawnUnits = 8;
          }});
          
          constructor = LegsUnit::create;
          weapons.add(new Weapon(){{
             reload = 40f;
             mirror = true;
             top = true;
             x = 4.5f;
             y = -5f;
             bullet = new BasicBulletType(3f, 45f){{
                backColor = trailColor = ErkPal.greenishBeryl;
                frontColor = Color.white;
                trailLength = 11;
                despawnEffect = ErkFx.gemHit;
                hitEffect = ErkFx.gemHit;
                shootEffect = Fx.none;
                smokeEffect = Fx.shootBigSmoke2;
                hitSound = Sounds.explosion;
                width = 9.5f;
                height = 14f;
                lifetime = 80f;
                
                int count = 6;
                for (int j = 0; j < count; j++) {
                   float spd = speed;
                   float fin = 1f + (j + 1) / (float) count;
                   float lt = lifetime / Mathf.lerp(fin, 1f, 0.5f);
                   spawnBullets.add(new LaserBoltBulletType(spd * fin, 15f){{
                    	hitColor = backColor = Pal.berylShot;
	                    frontColor = Color.white;
                      lifetime = lt;
                      hitEffect = Fx.hitLaserColor;
                   }});
                }
             }};
          }});
       }};
       
       //region beryllium - air
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
          ammoType = new ItemAmmoType(Items.beryllium);
          
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
           controller = u -> new MissileAI();
           
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
          health = 950;
	        speed = 2.1f;
 	        hitSize = 12;
	        drag = 0.03f;
	        accel = 0.24f;
	        flying = true;
          aimDst = 2f;
          range = 200f;
          engineOffset = 9.5f;
          targetAir = true;
          ammoType = new OreAmmoType(Blocks.wallOreBeryllium);
          
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
       
       int payloadSize = 2;
       accumulate = new ErekirUnitType("accumulate"){{
          health = 2450;
	        speed = 1.4f;
 	        hitSize = 20;
	        drag = 0.03f;
	        accel = 0.095f;
	        flying = true;
          aimDst = 1.46f;
          range = 170f;
          engineOffset = 11f;
          engineSize = 3.55f;
          targetAir = true;
          lowAltitude = true;
          payloadCapacity = (payloadSize * payloadSize) * (8 * 8);
          
          setEnginesMirror(
             new UnitEngine(28 / 4f, 36 / 4f, 2.7f, 45f),
             new UnitEngine(36 / 4f, -28 / 4f, 2.7f, 315f)
          );
            
          ammoType = new ItemAmmoType(Items.beryllium);
          
          constructor = PayloadUnit::create;
          weapons.add(
             new Weapon(){{
                reload = 40f;
                mirror = true;
                alternate = true;
                top = false;
                x = 3.5f;
                y = -4f;
                bullet = new BasicBulletType(6.5f, 24.5f){{
                   backColor = trailColor = ErkPal.greenishBeryl;
                   frontColor = Color.white;
                   trailLength = 9;
                   trailWidth = 1.5f;
                   hitEffect = despawnEffect = ErkFx.gemHit;
                   shootEffect = Fx.none;
                   smokeEffect = Fx.shootSmallSmoke;
                   width = 8.5f;
                   height = 12.5f;
                   lifetime = 30f;
                }};
                shoot = new ShootFactorial(3, 6.5f){{
                   shots = 3;
                   shotDelay = 2f;
                }};
             }},
             
             new Weapon(){{
                reload = 100f;
                mirror = true;
                top = true;
                x = 5f;
                y = 3.8f;
                shootSound = ErkSounds.fieldRelease;
                shootCone = 15;
                shootY = 0f;
                bullet = new CarapaceBulletType(){{
                   lifetime = 60f;
                   hitSize = 20f;
                   damage = 17f;
                   speed = 2f;
                   frontColor = Color.white;
                   backColor = ErkPal.greenishBeryl;
                   layer = Layer.weather;
                   ejectEffect = Fx.none;
                   hitEffect = Fx.none;
                   despawnEffect = Fx.none;
                   pushBackEffect = ErkFx.gemHit;
                   shootEffect = Fx.shootBig;
                   smokeEffect = Fx.shootBigSmoke2;
               }};
            }}
          );
       }};
       
       attractor = new ErekirUnitType("attractor"){{
          health = 13000;
	        speed = 0.7f;
 	        hitSize = 45;
	        drag = 0.03f;
	        accel = 0.095f;
	        flying = true;
          aimDst = 1.46f;
          range = 300f;
          deathSound = ErkSounds.explosionlarge;
          engineOffset = 24f;
          engineSize = 6.5f;
          targetAir = true;
          lowAltitude = true;
          rotateSpeed = 2.3f;
          
          ammoType = new ItemAmmoType(Items.beryllium);
          
          setEnginesMirror(
             new UnitEngine(37 / 4f, -60 / 4f, 4.5f, 315f),
             new UnitEngine((37 + 34) / 4f, -60 / 4f, 2.7f, 315f)
          );
           
          constructor = UnitEntity::create;
          weapons.add(new Weapon(){{
             reload = 140f;
             mirror = false;
             top = true;
             x = 0f;
             y = 0f;
             bullet = new DivisibleBulletType(5f, 90f){{
                sprite = "missile-large";
                backColor = trailColor = ErkPal.greenishBeryl;
                frontColor = Color.white;
                trailLength = 17;
                trailWidth = 3.5f;
                hitEffect = despawnEffect = ErkFx.hugeShatterBeryl;
                hitSound = Sounds.explosionbig;
                shootSound = Sounds.shootSnap;
                splashDamage = 102f;
                splashDamageRadius = 45f;
                shootEffect = new Effect(40f, e -> {
                   Draw.color(ErkPal.greenishBeryl);
                   float sideAngle = 90f;
                   
                   for (int i : Mathf.signs) {
	                   	Drawf.tri(e.x, e.y, 12f * e.fout(), 21f, e.rotation + sideAngle * i);
	                   	Drawf.tri(e.x, e.y, 12f * e.fout() / 1.4f, 21f / 1.4f, e.rotation + (sideAngle - 35f) * i);
                   }
                });
                smokeEffect = Fx.shootBigSmoke;
                width = 16f;
                height = 23.5f;
                lifetime = 80f;
                divisions = 8;
                
                //comically large amount of bullets
                bullets.add(
                new DivisibleBulletType(6.5f, 26f){{
                    backColor = trailColor = ErkPal.greenishBeryl;
                    frontColor = Color.white;
                    trailLength = 9;
                    trailWidth = 1.9f;
                    hitEffect = despawnEffect = ErkFx.gemHit;
                    shootEffect = Fx.none;
                    smokeEffect = Fx.shootSmallSmoke;
                    width = 12f;
                    height = 17.5f;
                    lifetime = 20f;
                    divisions = 3;
                       
                    bullets.add(
                    new BasicBulletType(2.0f, 12f){{
                        backColor = trailColor = ErkPal.greenishBeryl;
                        frontColor = Color.white;
                        trailLength = 7;
                        trailWidth = 1.2f;
                        hitEffect = despawnEffect = ErkFx.gemHit;
                        shootEffect = Fx.none;
                        smokeEffect = Fx.shootSmallSmoke;
                        width = 6.5f;
                        height = 11.5f;
                        lifetime = 25f;
                    }});
                }});
             }};
          }},
          new Weapon(){{
             reload = 20f;
             mirror = true;
             top = true;
             x = 9f;
             y = -10f;
             bullet = new BasicBulletType(1.5f, 15f){{
                sprite = "large-orb";
                width = height = 0f;
                hitSize = 5;
                backColor = trailColor = ErkPal.greenishBeryl;
                hitEffect = despawnEffect = Fx.none;
                shootSound = Sounds.spark;
                lifetime = 80f;
                
                intervalBullet = new LightningBulletType(){{
                    damage = 11;
                    ammoMultiplier = 1f;
                    lightningColor = ErkPal.greenishBeryl;
                    lightningLength = 12;

                    buildingDamageMultiplier = 0.15f;

                    lightningType = new BulletType(0.0001f, 0f){{
                        lifetime = Fx.lightning.lifetime;
                        hitEffect = Fx.hitLancer;
                        despawnEffect = Fx.none;
                        status = StatusEffects.shocked;
                        statusDuration = 10f;
                        hittable = false;
                        lightColor = Color.white;
                        buildingDamageMultiplier = 0.15f;
                    }};
                 }};

                 bulletInterval = 1.7f;
                 intervalRandomSpread = 15f;
             }};
          }});
       }};
       
       //region tungsten - air
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
          ammoType = new ItemAmmoType(Items.graphite);
          
          constructor = UnitEntity::create;
          weapons.add(new Weapon(){{
             reload = 50f;
             mirror = false;
             top = true;
             x = 0f;
             y = 0f;
             bullet = new DivisibleBulletType(4f, 25f){{
                knockback = 2.5f;
                width = 23f;
                hitSize = 6.5f;
                height = 18f;
                shootEffect = Fx.shootBigColor;
                smokeEffect = Fx.shootSmokeSquareSparse;
                hitColor = backColor = trailColor = Color.valueOf("ea8878");
                frontColor = Color.valueOf("feb380");
                trailWidth = 5f;
                trailLength = 3;
                divisions = 3;
                spawnDelay = 8f;
                spawnInaccuracy = 7.5f;
                hitEffect = despawnEffect = Fx.hitSquaresColor;
                
                bullets.add(
                new BasicBulletType(3f, 6f){{
                    width = 14f;
                    hitSize = 6.0f;
                    height = 10.5f;
                    hitColor = backColor = trailColor = Color.valueOf("ea8878");
                    frontColor = Color.valueOf("feb380");
                    trailWidth = 4f;
                    trailLength = 3;
                    hitEffect = despawnEffect = ErkFx.hitSquaresColorSmall;
                }},
                new BasicBulletType(2.5f, 5.5f){{
                    width = 12f;
                    hitSize = 5.9f;
                    height = 9f;
                    hitColor = backColor = trailColor = Color.valueOf("ea8878");
                    frontColor = Color.valueOf("feb380");
                    trailWidth = 3.5f;
                    trailLength = 3;
                    hitEffect = despawnEffect = ErkFx.hitSquaresColorSmall;
                }},
                new BasicBulletType(1.9f, 4.5f){{
                    width = 9f;
                    hitSize = 5.5f;
                    height = 7.5f;
                    hitColor = backColor = trailColor = Color.valueOf("ea8878");
                    frontColor = Color.valueOf("feb380");
                    trailWidth = 2.7f;
                    trailLength = 3;
                    hitEffect = despawnEffect = ErkFx.hitSquaresColorSmall;
                }});
             }};
          }});
       }};
       
       apart = new ErekirUnitType("apart"){{
          health = 840;
	        speed = 1.9f;
 	        hitSize = 12;
	        drag = 0.065f;
	        accel = 0.25f;
	        flying = true;
          aimDst = 1.55f;
          range = 160f;
          engineOffset = 9.25f;
          targetAir = true;
          ammoType = new OreAmmoType(Blocks.wallOreTungsten);
          
          constructor = UnitEntity::create;
          weapons.add(new Weapon(){{
             reload = 45f;
             mirror = true;
             top = true;
             alternate = true;
             x = 2.75f;
             y = -5f;
             velocityRnd = 0.17f;
             shoot = new ShootSpread(8, 2.5f);
             
             bullet = new BasicBulletType(6f, 12.5f){{
                knockback = 1.5f;
                width = 14f;
                hitSize = 6.0f;
                height = 10.5f;
                lifetime = 30f;
                hitColor = backColor = trailColor = Color.valueOf("ea8878");
                frontColor = Color.valueOf("feb380");
                trailWidth = 4f;
                trailLength = 3;
                hitEffect = despawnEffect = ErkFx.hitSquaresColorSmall;
                shootEffect = Fx.shootBigColor;
                smokeEffect = Fx.shootSmokeSquareSparse;
                recoil = 0.2f;
                buildingDamageMultiplier = 0.35f;
             }};
          }});
      }};
      
      shredderMissile = new MissileUnitType("shredder-missile"){{
           trailColor = engineColor = Color.valueOf("feb380");
           engineSize = 1.8f;
           engineOffset = 1.85f;
           engineLayer = Layer.effect;
           hitSize = 4;
           speed = 3.3f;
           lifetime = 60f * 2.5f;
           outlineColor = Pal.darkOutline;
           health = 65;
           lowAltitude = true;
           rotateSpeed = 4.35f;
           controller = u -> new MourningAI();
           
           weapons.add(new Weapon(){{
              shootCone = 360f;
              mirror = false;
              reload = 1f;
              shootOnDeath = true;
              bullet = new ExplosionBulletType(50f, 28f){{
                 shootEffect = new MultiEffect(Fx.massiveExplosion, new EllipseEffect(){{
                     lifetime = 35f;
                     colorFrom = Color.white;
                     colorTo = Color.valueOf("feb380");
                     offsetY = 2f;
                     particles = 13;
                     range = 45f;
                     drawer = (e, dx, dy) -> {
                         float angle = Mathf.angle(dx, dy);
                         Fill.square(e.x + dx, e.y + dy, e.fout() * 5.0f + 2.0f, angle);
                         Lines.lineAngle(e.x + dx * 2f, e.y + dy * 2f, angle, 5f * e.fout() + 1.5f);
                     };
                 }});
              }};
           }});
      }};
       
      shredder = new ErekirUnitType("shredder"){{
          health = 1800;
	        speed = 1.1f;
 	        hitSize = 12;
	        drag = 0.03f;
	        accel = 0.24f;
	        flying = true;
          aimDst = 3f;
          range = 250f;
          engineOffset = 12.5f;
          engineSize = 2.65f;
          targetAir = true;
          lowAltitude = true;
          ammoType = new OreAmmoType(Blocks.wallOreTungsten);
          
          constructor = UnitEntity::create;
          weapons.add(new Weapon(){{
             reload = 200f;
             mirror = false;
             top = true;
             x = 0f;
             y = 6f;
             bullet = new MourningSpawnBulletType(6f, 65f){{
                knockback = 5f;
                width = 27f;
                hitSize = 8f;
                height = 24.5f;
                shootEffect = Fx.shootBigColor;
                smokeEffect = Fx.shootSmokeSquareSparse;
                hitColor = backColor = trailColor = Color.valueOf("ea8878");
                frontColor = Color.valueOf("feb380");
                trailWidth = 6.5f;
                trailLength = 4;
                hitEffect = despawnEffect = Fx.hitSquaresColor;
                despawnUnit = shredderMissile;
                despawnUnitCount = 5;
                despawnUnitRadius = 26f;
            }};
         }});
      }};
      
      melt = new ErekirUnitType("melt"){{
          health = 700;
	        speed = 2.3f;
 	        hitSize = 7;
	        drag = 0.055f;
	        accel = 0.35f;
	        flying = true;
          aimDst = 1f;
          range = 50f;
          engineOffset = 5.75f;
          targetAir = false;
          ammoType = new LiquidAmmoType(Liquids.ozone);
          ammoCapacity = 40;
          
          constructor = UnitEntity::create;
          weapons.add(new Weapon(){{
             reload = 9f;
             mirror = false;
             top = true;
             x = 0f;
             y = 0f;
             shootSound = Sounds.flame;
             bullet = new BasicBulletType(6f, 20f){{
                hitSize = 3f;
                width = height = 0f;
                lifetime = 8f;
                pierce = true;
                ejectEffect = Fx.none;
                shootEffect = ErkFx.meltPlasma;
                hitColor = Color.valueOf("ba67c2");
                hitEffect = ErkFx.hitSquaresColorSmall;
                despawnEffect = Fx.none;
                keepVelocity = false;
                hittable = false;
             }};
          }});
       }};
   }
}