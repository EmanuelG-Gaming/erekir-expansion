const AI = require("lib/AIs");
const Event = require("lib/sectorEvents");
const ShootTriHelix  = require("entities/pattern/ShootTriHelix");

let healA = Tmp.c1.set(Pal.heal);
healA.a = 0.75;

/***Effects***/
const flarShieldDissolving = new Effect(120, e => {
  Draw.color(e.color);
  Draw.alpha(Math.max(Interp.pow5In.apply(e.fslope()) - 0.15, 0));
  Fill.poly(e.x, e.y, 6, e.rotation);
  e.scaled(20, s => {
     Draw.color(Tmp.c1.set(e.color).mul(1.25), e.color, s.fin());
     Draw.alpha(s.fout());
     Lines.circle(e.x, e.y, e.rotation * 1.45 * s.fin());
  });
}); 

const flerLaserHit = new Effect(14, e => {
   Draw.color(Color.white, Pal.lightishOrange, e.fin());
   Lines.stroke(0.5 + e.fout());

   Angles.randLenVectors(e.id, 2, 1 + e.fin() * 15, e.rotation, 50, (x, y) => {
      let ang = Mathf.angle(x, y);
      Lines.lineAngle(e.x + x, e.y + y, ang, e.fout() * 3 + 1);
   });
});

const flirEyeTrail = new Effect(40, e => {
	Draw.color(Color.valueOf("ff9c5a"));
	Fill.circle(e.x, e.y, e.rotation * e.fout());
});

const flirEyeShoot = new Effect(40, e => {
	let sideAngle = 90;
	Draw.color(Tmp.c1.set(Color.valueOf("ff9c5a")).mul(1.20));
	for (let i of Mathf.signs) {
		Drawf.tri(e.x, e.y, 13 * e.fout(), 24, e.rotation + sideAngle * i);
	}
});

const flirShockwave = new Effect(20, e => {
	Draw.color(Color.white, Color.lightGray, e.fin());
	let w = 12 * 1.5, h = 6 * 1.5;
	let interp = e.fin();
	Lines.stroke((1.5 + 1.2 * e.fout()) * Interp.pow5In.apply(e.fslope()));
	Lines.ellipse(
       e.x, e.y,
       5,
       w * interp, h * interp,
       e.rotation - 90
    );
});

const flirEyeDestruct = new Effect(75, e => {
	Draw.color(Color.valueOf("ff9c5a"));
    Lines.stroke(e.fslope() + 0.9);
    Draw.alpha(Interp.pow5Out.apply(e.fout()));
    Angles.randLenVectors(e.id, 14, e.fin() * 100, (x, y) => {
       let angle = Mathf.angle(x, y);
       Lines.lineAngle(e.x + x, e.y + y, angle, 5);
       Drawf.light(e.x + x, e.y + y, 5 * e.fin() + 6.5, Draw.getColor(), 0.9 * e.fout());
    });
    
    e.scaled(35, s => {
       Draw.alpha(s.fout());
       Lines.stroke(5.5 * s.fslope());
       Lines.circle(e.x, e.y, 45 * s.fin() + 10);
       Drawf.light(e.x, e.y, 45 * s.fin() + 12, Draw.getColor(), 0.9 * e.fout());
    });
    Draw.color(Color.gray);
    Draw.alpha(0.9);
    e.scaled(65, s => {
       Angles.randLenVectors(e.id, 16, 45 * s.fin(Interp.pow10Out), (x, y) => {
          Fill.circle(e.x + x, e.y + y, s.fout(Interp.pow5Out) * 5 + 2);
       });
    });
});

//just flirEyeShoot, but it's cooler
const florRailShoot = new Effect(60, e => {
	let sideAngle = 90;
	Draw.color(Pal.orangeSpark);
	for (let i of Mathf.signs) {
		Drawf.tri(e.x, e.y, 13 * e.fout(), 50, e.rotation + sideAngle * i);
	}
});

//smaller version of healingInferno
const healingFires = new Effect(40, e => {
	Draw.color(Pal.heal.cpy().mul(1.25), Pal.heal, Color.gray, e.fin());

    Angles.randLenVectors(e.id, 8, e.finpow() * 15 + 6, e.rotation, 10, (x, y) => {
        Fill.circle(e.x + x, e.y + y, 2 + e.fout() * 1.5);
    });
});

const menoShieldAppear = new Effect(40, e => {
	let args = e.data;
	Draw.color(Pal.heal);
	Draw.alpha(0.75 * e.fout());
	/*
     * args[0] - shield's size
     * args[1] - shield's cone
	*/
    Lines.arc(e.x, e.y, args[0] + 6 * e.fin(), args[1], e.rotation - 180 * args[1]);
});

const minoShoot = new Effect(60, e => {
   //make the code better, sheeple
   let rot = e.rotation;
   let len = 26, escapeLen = 15;
   let x = Angles.trnsx(rot, len), y = Angles.trnsy(rot, len);
   
   Draw.color(Color.white, Pal.heal, e.fin());
   Lines.stroke(1.4 * e.fslope());
   //what
   Tmp.v1.trns(rot + Math.sin(Time.time * 0.01) * 90, Mathf.dst(e.x, e.y, e.x + x, e.y + y));
   //try calculating the perpendicular opening vector
   Tmp.v2.trns(rot + 90, escapeLen * Interp.pow5In.apply(e.fin()));
   
   //unnecessary FAQ: why didn't you use the Mathf.signs loop? - ~~because it first didn't work, as if I've expected and the second curve started to become a line~~
   Lines.curve(
      e.x, e.y,
      e.x, e.y,
      e.x + Tmp.v1.x, e.y + Tmp.v1.y,
      e.x + x + Tmp.v2.x, e.y + y + Tmp.v2.y, 15
   );
   Lines.curve(
      e.x, e.y,
      e.x, e.y,
      e.x - Tmp.v1.x, e.y - Tmp.v1.y,
      e.x + x - Tmp.v2.x, e.y + y - Tmp.v2.y, 15
   );
});

const munoShine = new Effect(50, e => {
   Draw.color(Pal.heal.cpy().mul(1.5), Pal.heal, e.fin());

   Angles.randLenVectors(e.id, 7, e.finpow() * 32 + 18, (x, y) => {
      Fill.circle(e.x + x, e.y + y, 2 + e.fout() * 6);
   });
});

const youWillFixTheSoulContainerNOW = new Effect(180, e => {
	let args = e.data;
	let length = args.length - 1;
	if (length + 1 < 2 * 3) return;
	
	let h = e.fin();
	//sk drawer steal
    let x = h * (e.x - Core.camera.position.x);
    let y = h * (e.y - Core.camera.position.y);
    
	Draw.color(Color.valueOf("ff9c5a"), Pal.lightOrange, e.fin());
	let polies = 10;
    for (let I = 0; I < polies; I++) { 
       Draw.alpha(1 / (I + 1) * e.fout());
       let px = e.x + x * (I / polies), py = e.y + y * (I / polies);
	   //clearly stolen
	   for (let i = 0; i < length - 1; i += 2) {
	      Lines.line(
             px + args[i - 2],
             py + args[i - 1],
             px + args[i],
             py + args[i + 1]
          );
       }
       //now try connecting the last vertice to the first vertice
	   Lines.line(
	       px + args[length - 1],
	       py + args[length],
	       px + args[0],
	       py + args[1]
	   );
    }
});

//quite similar to flerLaserHit
const gemHit = new Effect(14, e => {
   Draw.color(Pal.heal, Color.valueOf("93de7e"), e.fin());
   Lines.stroke(0.5 + e.fout());

   Angles.randLenVectors(e.id, 4, 1 + e.fin() * 15, e.rotation, 50, (x, y) => {
      let ang = Mathf.angle(x, y);
      Lines.lineAngle(e.x + x, e.y + y, ang, e.fout() * 3.5 + 1.4);
   });
});

/***Bullets***/
//attendant thing
const flerArcingLaser = extend(BasicBulletType, {
    damage: 20, 
    backColor: Color.valueOf("ff9c5a"), 
    trailColor: Color.valueOf("ff9c5a"), 
    frontColor: Color.white,
    trailLength: 7,
    trailSize: 1.35,
    healColor: Color.valueOf("ff9c5a"),
    healPercent: 0.5, 
    collidesTeam: true,
    despawnEffect: Fx.none,
    hitEffect: flerLaserHit,
    shootEffect: Fx.shockwave,
    width: 6,
    height: 6.5,
    lifetime: 60,
    speed: 4,
    hitSize: 11.5,
    homingPower: 0.05,
    homingRange: 0,
    init(b) {
       if (!b) return;
       let v = new Vec2();
       v.set(b.x, b.y);
       if (b.owner instanceof Unitc) {
          v.set(b.owner.aimX, b.owner.aimY);
       } else if (b.owner instanceof Turret.TurretBuild) {
          v.set(b.owner.targetPos);
       } 
       b.data = v;
    },
    update(b) {
       this.super$update(b);
       /*
        :sunflower: hard-working and caretaking idea :sunny:
        :zap: unoriginal and merciless idea :thunder_cloud_rain:
       */
       if (b.fdata != 1) {
          let v = b.data;
          b.vel.setAngle(Angles.moveToward(b.rotation(), b.angleTo(v.x, v.y), Time.delta * 261 * this.homingPower * b.fin()));
          if (b.within(v.x, v.y, b.hitSize)) b.fdata = 1;
       }
    },
}); 

const flirBolt = extend(LaserBoltBulletType, {
	width: 5,
	height: 16,
	backColor: Color.valueOf("ff9c5a"),
	frontColor: Color.white,
    damage: 30,
    splashDamage: 40,
    splashDamageRadius: 32,
    lifetime: 50,
    speed: 6.5,
    despawnEffect: Fx.none,
    hitEffect: flirEyeDestruct,
    smokeEffect: Fx.shootSmallSmoke,
    shootEffect: new MultiEffect(flirEyeShoot, flirShockwave),
    hitSound: Sounds.explosion,
    colors: [ Color.valueOf("ec745855"), Color.valueOf("ec7458aa"), Color.valueOf("ff9c5a"), Color.white ],
    draw(b) {
       let w = this.width, h = this.height;
       Tmp.v1.trns(b.rotation(), h);
       
       for (let i in this.colors) {
          Lines.stroke((w *= 0.6) * b.fout() * 2.5);
          Draw.color(this.colors[i]);
          Lines.line(b.x, b.y, b.x + Tmp.v1.x, b.y + Tmp.v1.y, false);
          Fill.circle(b.x, b.y, Lines.getStroke() / 2);
          Fill.circle(b.x + Tmp.v1.x, b.y + Tmp.v1.y, Lines.getStroke() / 2);
       }
    },
});

const florRail = extend(RailBulletType, {
   shootEffect: new MultiEffect(florRailShoot, flirShockwave), 
   length: 350, 
   updateEffectSeg: 30,
   pierceEffect: Fx.railHit,
   updateEffect: Fx.railTrail,
   hitEffect: Fx.massiveExplosion,
   smokeEffect: Fx.shootBig,
   damage: 360,
   pierceDamageFactor: 0.7, 
});

const florMissile = extend(MissileBulletType, {
	frontColor: Pal.unitFront,
    backColor: Pal.unitBack,
    trailLength: 10,
    trailSize: 2,
    trailColor: Pal.unitBack,
    trailChance: 0,
    damage: 35,
    splashDamage: 45,
    splashDamageRadius: 20,
    homingPower: 0.35,
    homingRange: 0,
    speed: 5,
    width: 8,
    height: 11,
    lifetime: 120,
    hitEffect: Fx.blastExplosion,
    despawnEffect: Fx.blastExplosion, 
    inaccuracy: 40,
    length: 30,
    phaseSpeed: 8,
    //rtr geometry
    init(b) {
       if (!b) return;
       let x = 0, y = 0;
       let angle = 0;
       let points = new Seq();
       
       //what 
       points.add(new Vec2(b.x, b.y)); 
       for (let i = 0; i < this.length / 2; i++) {
          angle += Mathf.range(this.inaccuracy);
          x += Angles.trnsx(b.rotation() + angle, 50);
          y += Angles.trnsy(b.rotation() + angle, 50);
          points.add(new Vec2(b.x + x, b.y + y));
       }
       b.data = points;
       
       //for testing. you may remove this
       /*
       b.data.each(p => {
       	Fx.spawn.at(p.x, p.y);
       });
       */
    },
    update(b) {
   	this.super$update(b);
       let index = 0;
       if (b.fdata != 1) {
      	let vec = b.data;
          let sin = Mathf.floor(Mathf.absin(Time.time, this.phaseSpeed, vec.size - 0.001));
      	Tmp.v1.set(b.x, b.y);
          
          if (vec != null) {
             Tmp.v1.set(vec.get(sin).x, vec.get(sin).y);
          }
          else b.fdata = 1;
      
          b.vel.setAngle(Angles.moveToward(b.rotation(), b.angleTo(Tmp.v1.x, Tmp.v1.y), Time.delta * 261 * this.homingPower * b.fin()));
          
          if (b.within(Tmp.v1.x, Tmp.v1.y, 16)) b.fdata = 1; 
       }
    },
});

const proximityFire = extend(BasicBulletType, {
   hitSize: 3,
   width: 0,
   height: 0,
   lifetime: 7,
   damage: 11.5,
   speed: 3,
   pierce: true,
   collidesTeam: true,
   healPercent: 0.9,
   statusDuration: Time.toSeconds * 4,
   shootEffect: healingFires,
   ejectEffect: Fx.none,
   hitEffect: Fx.hitFlamePlasma,
   despawnEffect: Fx.none,
   status: StatusEffects.burning,
   keepVelocity: false,
   hittable: false,
});

//complementary lightning branch
const menoBranch = extend(LightningBulletType, {
   lightningColor: Pal.heal,
   lightningLength: 5,
   speed: 0.001, 
   lifetime: 20,
   damage: 2,
   buildingDamageMultiplier: 0.5,
});

const menoCascade = extend(LightningBulletType, {
   lightningColor: Pal.heal,
   lightningLength: 15,
   lightningType: menoBranch,
   speed: 0.001, 
   lifetime: 50,
   damage: 10,
   buildingDamageMultiplier: 0.5,
   status: StatusEffects.shocked,
});

const minoBeam = extend(ContinuousLaserBulletType, {
   absorbable: true,
   keepVelocity: true,
   length: 20,
   width: 5,
   damage: 6.5,
   colors: [ healA, Pal.heal, Color.white ],
   hitColor: Pal.heal,
   lightColor: healA,
   shootEffect: minoShoot,
   smokeEffect: Fx.greenLaserChargeSmall,
   speed: 4,
   lifetime: 100,
   drag: 0.035,
   shake: 0,
   oscScl: 1,
   oscMag: 0.5,
   fadeTime: 120,
   makeFire: false,
   incendChance: 0.15,
   collidesTeam: true,
   healPercent: 0.3,
});

const munoArray = extend(BasicBulletType, {
	//quad moment
	sprite: "large-bomb",
    width: 30,
    height: 30,
    maxRange: 30,
    ignoreRotation: true,
    backColor: Pal.heal,
    frontColor: Color.white,
    mixColorTo: Color.white,
    hitSound: Sounds.plasmaboom,
    shootCone: 180,
    ejectEffect: Fx.none,
    hitShake: 4,
    collidesAir: false,
    lifetime: 30,
    despawnEffect: munoShine,
    hitEffect: Fx.massiveExplosion,
    keepVelocity: false,
    spin: 2,
    shrinkX: 0.7,
    shrinkY: 0.7,
    speed: 0,
    collides: false,
    healPercent: 4,
    damage: 25,
    splashDamage: 40,
    splashDamageRadius: 50,
});

const munoSlicer = extend(BasicBulletType, {
    damage: 12.5, 
    healPercent: 0.5, 
    collidesTeam: true,
    absorbable: false,
    despawnEffect: Fx.none,
    shootEffect: Fx.none,
    backColor: Pal.heal,
    trailColor: Pal.heal,
    trailWidth: 5,
    trailLength: 60,
    frontColor: Color.white,
    lifetime: 120,
    speed: 4,
    pierce: true,
    //pierceBuilding: true, //for fun
    weaveMag: 4,
    weaveScale: 4,
    width: 5,
    height: 5,
    size: 6,
    hitSize: 12,
    
    bulletRang: 100,
    maxLen: 2,
    max: 12,
    draw(b) {
       this.super$draw(b);
       Draw.z(Layer.bullet);
       Draw.color(Color.white, Pal.heal, b.fin());
       Fill.circle(b.x, b.y, this.size);
       Draw.z();
       Draw.reset();
    },
    update(b) {
       this.super$update(b);
       let bulletRange = this.bulletRang;
       
       //endless rusting moment
       if (b.fdata != 1) {
          Tmp.v1.set(b.x, b.y);
          if (b.owner instanceof Unitc) {
             Tmp.v1.set(b.owner.aimX, b.owner.aimY);
          } else if (b.owner instanceof Turret.TurretBuild) {
             Tmp.v1.set(b.owner.targetPos);
          }
          b.vel.setAngle(Angles.moveToward(b.rotation(), b.angleTo(Tmp.v1.x, Tmp.v1.y), Time.delta * 261 * b.fin()));
          Groups.bullet.intersect(b.x - bulletRange, b.y - bulletRange, bulletRange * 2, bulletRange * 2).each(bul => {
             if (bul != null && bul.team != b.team && bul.within(b.x, b.y, bulletRange) && bul.damage < this.max && bul.type.absorbable && !(bul instanceof LiquidBulletType)) {
                Tmp.v2.set(b.x, b.y).sub(bul).nor().scl(bul.type.speed);
                Tmp.v2.setAngle(Angles.moveToward(b.rotation(), b.angleTo(bul.x, bul.y), Time.delta * 261 * 0.55 * b.fin()));
                //steel!!!
                if (this.weaveMag > 0) {
                   //b.speed is actually different from this.speed, becuase it can actually change, when the bullet exists
               	b.vel.add(Tmp.v2).limit(this.maxLen + this.speed).rotate(
                   Mathf.sin(b.time + Mathf.PI * this.weaveScale / 2, this.weaveScale, this.weaveMag * (Mathf.randomSeed(b.id, 0, 1) == 1 ? -1 : 1)) * Time.delta
                   );
                } else {
               	b.vel.add(Tmp.v2).limit(this.maxLen + this.speed);
                }
                if (bul.within(b.x, b.y, this.trueSize())) {
                   bul.absorb();
                }
             }
          });
          
          if (b.within(Tmp.v1.x, Tmp.v1.y, b.hitSize)) b.fdata = 1;
       }
   },
   trueSize() {
   	return this.size + Math.sqrt(this.hitSize);
   },
});

const proximityField = extend(BasicBulletType, {
   hitSize: 50,
   width: 0,
   height: 0,
   lifetime: 180,
   healColor: Pal.heal,
   backColor: Pal.heal,
   frontColor: Color.white,
   damage: 8,
   speed: 0,
   pierce: true,
   collidesTeam: true,
   healPercent: 0.6,
   ejectEffect: Fx.none,
   hitEffect: Fx.hitFlamePlasma,
   despawnEffect: Fx.none,
   keepVelocity: false,
   hittable: false,
   draw(b) {
   	this.super$draw(b);
       //barrier mod moment
       let size = Interp.pow10Out.apply(b.fslope()) * this.hitSize;
       Draw.color(this.frontColor, this.backColor, b.fin());
       Draw.alpha(Interp.pow10Out.apply(b.fslope()));
       Draw.z(Layer.effect);
       Lines.circle(b.x, b.y, size);
       
       Draw.alpha(0.90 * b.fout() * b.fslope());
       Fill.circle(b.x, b.y, size * b.finpow());
       Draw.z();
       Draw.reset();
   },
   update(b) {
       this.super$update(b);
       //spammy code
       Units.nearbyEnemies(b.team, b.x, b.y, b.hitSize, enemy => {
          if (enemy != null && enemy.within(b.x, b.y, b.hitSize) && enemy.isValid()) {
             if (b.timer.get(1, 5)) {
                Tmp.v1.set(enemy.x, enemy.y);
                if (enemy instanceof Healthc) enemy.damage(b.damage);
                this.hitEffect.at(enemy.x, enemy.y, b.angleTo(Tmp.v1.x, Tmp.v1.y));
             }
          }
       });
       Units.nearbyBuildings(b.x, b.y, b.hitSize, build => {
      	if (build != null && build.within(b.x, b.y, b.hitSize)) {
      	   if (b.timer.get(1, 5)) {
                if (build.team != b.team) {
                	Tmp.v1.set(build.x, build.y);
                    if (build instanceof Healthc) build.damage(b.damage);
                    this.hitEffect.at(build.x, build.y, b.angleTo(Tmp.v1.x, Tmp.v1.y));
                }
                else if (this.healPercent > 0) {
               	if (build instanceof Healthc && build.damaged()) {
                      // Ah yes, rewrite 99% of heal codes
                      build.heal(this.healPercent / 100 * build.maxHealth);
                      Fx.healBlockFull.at(build.x, build.y, build.block.size, this.healColor);
                   }
                }
             }
          }
       });	
   },
   drawLight(b) {
   	//no
   },
});

const manoMelt = extend(LiquidBulletType, {
    damage: 20,
    speed: 3.5,
    drag: 0.020,
    shootEffect: Fx.shootSmall,
    lifetime: 57,
    liquid: Liquids.slag,
});

const gemBullet = extend(BasicBulletType, {
    damage: 17, 
    backColor: Color.valueOf("93de7e"), 
    trailColor: Color.valueOf("93de7e"), 
    frontColor: Color.white,
    trailLength: 5,
    trailSize: 0.7,
    despawnEffect: Fx.none,
    hitEffect: gemHit,
    shootEffect: Fx.none,
    width: 6.25,
    height: 7,
    lifetime: 60,
    speed: 5,
    homingPower: 0.07,
    homingRange: 8 * Vars.tilesize,
});

const geodeCapsule = extend(BasicBulletType, {
   hitSize: 65,
   width: 0,
   height: 0,
   lifetime: 45,
   backColor: Color.valueOf("93de7e"),
   frontColor: Color.white,
   damage: 20,
   speed: 0,
   pierce: true,
   pierceBuilding: true,
   ejectEffect: Fx.none,
   hitEffect: Fx.none,
   despawnEffect: Fx.none,
   keepVelocity: false,
   hittable: false,
   absorbable: false,
   deflectPower: 0.015,
   draw(b) {
   	this.super$draw(b);
       //spam
       Tmp.c1.set(Color.white);
       Tmp.c1.a = Interp.pow3Out.apply(b.fout());
       Tmp.c2.set(this.backColor);
       Tmp.c2.a = Interp.pow5Out.apply(b.fout());
       
       let c1 = Tmp.c1, c2 = Tmp.c2;
       Draw.z(Layer.weather);
       Fill.light(b.x, b.y, 24, (b.hitSize + 10) * b.fin(), c1, c2);
       /*
       Draw.z(Layer.bullet + 0.01);
       
       Draw.color(Pal.accent);
       Lines.stroke(1.25 * b.fout());
       Lines.poly(b.x, b.y, 24, b.hitSize + 10);
       */
       Draw.reset();
   },
   update(b) {
       this.super$update(b);
       const enemies = unit => {
          if (unit != null && unit.within(b.x, b.y, b.hitSize) && unit.isValid()) {
             let overlap = (unit.hitSize / 2 + b.hitSize) - unit.dst(b);
             
             Tmp.v1.set(b).sub(unit).nor().scl((overlap + 0.01) * 60);
             Tmp.v1.setAngle(b.angleTo(unit));
             unit.vel.setZero();
             unit.impulse(Tmp.v1);
            
             if (Mathf.chance(0.2)) {
                gemHit.at(unit.x, unit.y, b.angleTo(unit));
             }
          }
       };
       const bullets = bul => {
      	if (bul != null && bul.team != b.team && bul.owner != b.owner) { 
      	   bul.vel.setAngle(Angles.moveToward(bul.rotation(), b.angleTo(bul), Time.delta * 261 * this.deflectPower));
          }
       };
       
       let size = b.hitSize + 10;
       Units.nearbyEnemies(b.team, b.x, b.y, b.hitSize + 10, enemies);	
       Groups.bullet.intersect(b.x - size, b.y - size, size * 2, size * 2).each(bullets);
   },
   drawLight(b) {
   	//no
   },
});

/***Weapons***/
const flerDivergent = extend(Weapon, "flerDivergent", {
    reload: 100,
    alternate: false,
    ejectEffect: Fx.none,
    top: false,
    shots: 1,
    shootSound: Sounds.laser,
    mirror: false,
    x: 0,
    y: 0,
    rotate: false,
    bullet: flerArcingLaser,
    inaccuracy: 45,
    shoot: extend(ShootHelix, {
       shots: 3,
       scl: 8.5,
       mag: 1.35,
    }),
});

const flirEye = extend(Weapon, "flirEye", {
    reload: 110,
    shots: 1,
    inaccuracy: 2.75,
    alternate: false,
    ejectEffect: Fx.none,
    top: false,
    shootSound: Sounds.laser,
    mirror: false,
    x: 0,
    y: 0,
    rotate: true,
    bullet: flirBolt
}); 

const florLong = extend(Weapon, "florLong", {
    reload: 200,
    shots: 1,
    alternate: true,
    mirror: true,
    ejectEffect: Fx.none,
    top: false,
    shootSound: Sounds.railgun,
    x: 7.5,
    y: -12,
    rotate: true,
    rotateSpeed: 1.6,
    bullet: florRail
}); 

const florLauncher = extend(Weapon, "florLauncher", {
    reload: 12,
    shots: 1,
    alternate: true,
    mirror: true,
    ejectEffect: Fx.none,
    top: true,
    shootSound: Sounds.missile,
    x: 6.5,
    y: 7,
    rotate: true,
    bullet: florMissile
}); 

const proximityFlame = extend(Weapon, "proximityFlame", {
    reload: 8,
    shots: 1,
    alternate: true,
    ejectEffect: Fx.none,
    top: false,
    shootSound: Sounds.flame,
    mirror: true,
    x: 2,
    y: 0,
    rotate: false,
    bullet: proximityFire
}); 

const menoShock = extend(Weapon, "menoShock", {
    reload: 30,
    alternate: false,
    ejectEffect: Fx.none,
    top: false,
    shots: 1,
    inaccuracy: 0.30 * 180,
    shootSound: Sounds.spark,
    mirror: false,
    x: 0,
    y: 0,
    rotate: true,
    rotateSpeed: 4.5,
    bullet: menoCascade
});

const minoLaser = extend(Weapon, "minoLaser", {
    reload: 80,
    shots: 1,
    alternate: true,
    ejectEffect: Fx.none,
    top: false,
    shootSound: Sounds.laser,
    mirror: true,
    x: 3,
    y: 0,
    rotate: false,
    bullet: minoBeam,
    shoot: extend(ShootPattern, {
        firstShotDelay: Fx.greenLaserChargeSmall.lifetime - 1,
    }),
}); 

const munoBomber = extend(Weapon, "munoBomber", {
    reload: 40,
    shots: 1,
    alternate: true,
    ejectEffect: Fx.none,
    top: false,
    shootSound: Sounds.mineDeploy,
    x: 0,
    y: 0,
    rotate: false,
    bullet: munoArray
}); 

const munoKnife = extend(Weapon, "munoKnife", {
    reload: 20,
    shots: 1,
    alternate: true,
    mirror: true,
    ejectEffect: Fx.none,
    top: true,
    shootSound: Sounds.pew,
    x: 6,
    y: - 4.5,
    rotate: true,
    bullet: munoSlicer
}); 

const manoSprayer = extend(Weapon, "manoSprayer", {
    reload: 20,
    alternate: true,
    mirror: true,
    ejectEffect: Fx.none,
    top: true,
    shootSound: Sounds.flame,
    x: 9,
    y: -10.0,
    rotate: true,
    bullet: manoMelt,
    shoot: extend(ShootHelix, {
       shots: 1,
       scl: 5.5,
       mag: 2.5,
    }),
}); 

const gemTendril = extend(Weapon, "flar-expansion-gem-weapon", {
   reload: 40,
   mirror: true,
   top: true,
   x: 3.2,
   y: -1.65,
   //baseRotation: -35,
   shootCone: 360,
   bullet: gemBullet,
   shoot: new ShootTriHelix({
      shots: 1,
      scl: 4.5,
      mag: 1.2,
   }).pattern,
});

const geodeShield = extend(Weapon, "geodeShield", {
   reload: 50,
   mirror: false,
   top: true,
   x: 0,
   y: 0,
   shootSound: Sounds.laser,
   shootCone: 360,
   shootY: 0,
   bullet: geodeCapsule,
});

/***Abilities***/
const randomElectricFlashes = extend(Ability, {
  points: new Seq(),
  aoe: 200,
  len: 8 / 2,
  shineColor: Pal.heal,
  sectors: 3,
  reload: 12,
  lightningDamage: 25,
  
  timer: 0,
  onProximity: false,
  strokeScl: 0,
  RandomElectricFlashes() {},
  localized() {
    return Core.bundle.format("ability-flar-expansion-rndElectricFlashes", this.lightningDamage, Mathf.floor(this.aoe / 8), this.len);
  },
  draw(unit) {
  	let sin = Mathf.absin(Time.time, 10, 1);
      let orbRadius = 6.5;
      Draw.color(this.shineColor);
      //layering issue
      Draw.z(unit.isFlying() ? Layer.flyingUnit : Layer.groundUnit + 0.01);
      Lines.stroke(2.25 * sin * this.strokeScl);
      for (let i = 0; i < this.sectors; i++) {
         let rotateSpeed = 2.5;
     	let angle = unit.rotation + i * 360 / this.sectors + Time.time * rotateSpeed;
         let sectorScl = 1 / this.sectors - 0.1;
         Lines.arc(unit.x, unit.y, 10, sectorScl, angle);
      }
      Fill.circle(unit.x, unit.y, orbRadius);
      Draw.color();
      Fill.circle(unit.x, unit.y, orbRadius / 2);
      
      Draw.color(this.shineColor);
      Draw.alpha(this.strokeScl);
      Lines.circle(unit.x, unit.y, this.aoe);
      Draw.alpha(this.strokeScl * 0.09);
      Fill.circle(unit.x, unit.y, this.aoe - Lines.getStroke());
      Drawf.light(unit.x, unit.y, this.aoe * 1.5, this.shineColor, this.onProximity ? 0.2 : 0);
      Draw.z();
      Draw.reset();
  },
  update(unit) {
  	let rot = unit.rotation;
      let collidesGround = true, collidesAir = true;
      let radius = 20;
      
      this.strokeScl = Mathf.lerpDelta(this.strokeScl, this.onProximity ? 1 : 0, 0.06);
      
      if ((this.timer += Time.delta) >= this.reload && this.onProximity) {
         this.points.clear();
         this.onProximity = false;
         
         //another filler line
         this.points.add(new Vec2(unit.x, unit.y));
         for (let i = 0; i < this.len; i++) {
            rot += Mathf.range(360);
            this.points.add(
                new Vec2(unit.x + Angles.trnsx(rot, this.aoe), unit.y + Angles.trnsy(rot, this.aoe))
            );
         }
         for (let i = 0; i < this.points.size - 1; i++) {
        	let cur = this.points.get(i);
            let next = this.points.get(i + 1);
            Geometry.iterateLine(0, cur.x, cur.y, next.x, next.y, radius, (x, y) => {
                Damage.damage(unit.team, x, y, radius, this.lightningDamage, collidesGround, collidesAir);
                Fx.hitLaser.at(x, y);
            });
         }
         Fx.lightning.at(unit.x, unit.y, 0, this.shineColor, this.points); 
         Sounds.spark.at(unit.x, unit.y);
         
         this.timer %= this.reload;
      }
      let target = Units.closestEnemy(unit.team, unit.getX(), unit.getX(), this.aoe, u => u.checkTarget(collidesGround, collidesAir));
      if (target != null && target.within(unit.x, unit.y, this.aoe) && target.isValid()) {
      	this.onProximity = true;
      }
      else {
      	this.onProximity = false;
      }
  },
});

/*Attack unit section*/
const coverance = extend(UnitType, "boid", {
	 health: 150,
	 speed: 1.9,
	 hitSize: 7,
	 drag: 0.01,
	 accel: 0.08,
	 flying: true,
     aimDst: 0.5,
     range: 40,
     engineSize: 1.35,
     engineOffset: 3.65,
     commandRadius: 69,
});

coverance.constructor = () => extend(UnitEntity, {
	poly: new Polygon(), // moment
	draw() {
	   this.super$draw();
	   //polygon filling tutorial
	   let vert = this.poly.getVertices();
	   let len = vert.length - 1;
	   
	   let x = this.x / 8, y = this.y / 8;
	   Draw.color(Color.valueOf("ff9c5a"));
	   Draw.z(Layer.effect);
	   //draw each vertices of the poly
	   if (vert.length >= 6) {
	      for (let i = 0; i < vert.length; i += 2) {
		      Lines.line(
                 x + vert[i - 2],
                 y + vert[i - 1],
                 x + vert[i],
                 y + vert[i + 1]
              );
	      }
	      //now try connecting the last vertice to the first vertice
	      Lines.line(
              x + vert[len - 1],
	          y + vert[len],
	          x + vert[0],
	          y + vert[1]
	      );
	      /*
	      Draw.alpha(0.75);
	      //"the last q"
	      for (let i = 2; i < len - 3; i += 4){
             Fill.quad(
                 x + vert[0], y + vert[1],
                 x + vert[i], y + vert[i + 1],
                 x + vert[i + 2], y + vert[i + 3],
                 x + vert[i + 4], y + vert[i + 5]
             );
          }
          */
       }
       Draw.z();
	   Draw.reset();
	},
	update() {
	   this.super$update();
	   let player = Vars.player.unit();
	   if (this.controller.isBeingControlled(player) && this != player) {
		  //no
	   }
	},
	commandNearby(formation, include) {
	   let plr = Vars.player.unit();
	   let form = new Formation(new Vec3(plr.x, plr.y, plr.rotation), new SquareFormation());
	   let vert = this.poly.getVertices();
	   let newVerts = [];
	
	   plr.units.clear();
	   Units.nearby(plr.team, plr.x, plr.y, coverance.commandRadius, u => {
		  if (u.isAI() && u != plr && u.type.flying == plr.type.flying && u.hitSize <= coverance.hitSize * 1.1) {
			 plr.units.add(u);
		  }
	   });
	
	   if (plr.units.isEmpty()) return;
	   plr.units.sort(u => u.dst(plr));
	   plr.units.truncate(plr.type.commandLimit).each(u => {
		   newVerts.push(u.x, u.y);
	   });
	
	   if (plr.units.size > 2) { 
          this.poly.setVertices(newVerts);
          Event.showMoving("Observe.", 2, this);
       }
	   youWillFixTheSoulContainerNOW.at(this.x, this.y, 0, vert);
	   this.command(form, plr.units);
	},
});

const flar = extend(UnitType, "flar", {
	 health: 105,
	 speed: 3.15,
	 hitSize: 6,
	 drag: 0.01,
	 accel: 0.35,
	 flying: true,
     aimDst: 0.5,
     range: 80,
     engineSize: 1.35,
}); 
flar.constructor = () => extend(UnitEntity, {});
flar.defaultController = AI.rotateAI;

const fler = extend(UnitType, "fler", {
	 health: 230,
	 speed: 2.0,
	 hitSize: 8,
	 aimDst: 1.8,
     range: 120,
	 drag: 0.01,
	 accel: 0.15,
	 flying: true,
     engineOffset: 9,
}); 
fler.weapons.add(flerDivergent);
fler.constructor = () => extend(UnitEntity, {});

const flir = extend(UnitType, "flir", {
	 health: 670,
	 armor: 3,
	 speed: 0.9,
	 hitSize: 24,
	 drag: 0.01,
	 accel: 0.10,
	 flying: true,
	 lowAltitude: true,
	 rotateShooting: false,
     aimDst: 2,
     range: 200,
     engineOffset: 16.5,
     engineSize: 4,
     
     eyeballRad: 4,
     radInline: 2,
     corneaLength: 1,
     corneaSize: 1.2,
});
flir.weapons.add(flirEye);

flir.constructor = () => extend(UnitEntity, {
   corneaAngle: 0,
   draw() {
     this.super$draw();
     let dest = this.angleTo(this.aimX, this.aimY);
     let destFall = this.rotation - (90 + 180);
     let offsetX = Angles.trnsx(this.corneaAngle, flir.corneaLength),
         offsetY = Angles.trnsy(this.corneaAngle, flir.corneaLength);
     let deadAlpha = 1;
     if (this.dead) {
        this.corneaAngle = Mathf.range(0, 360);
        deadAlpha = this.elevation;
     } else {
     	if (this.rotateShooting) {
             this.corneaAngle = this.rotation;
         } else this.corneaAngle = Angles.moveToward(this.corneaAngle, dest, flir.rotateSpeed * Time.delta);
         if (!Vars.state.isPaused() && this.corneaAngle != dest) {
         	flirEyeTrail.at(this.x + offsetX, this.y + offsetY, flir.corneaSize);
         }
     }
     Draw.color(Color.valueOf("ff9c5a"));
     Draw.alpha(deadAlpha);
     Draw.z(Layer.effect);
     Lines.stroke(flir.radInline / 2);
     Lines.circle(this.x, this.y, flir.eyeballRad);
     Fill.circle(this.x + offsetX, this.y + offsetY, flir.corneaSize);
     Draw.z();
     Draw.reset();
   },
});

const flor = extend(UnitType, "flor", {
	 health: 6600,
	 armor: 7,
	 speed: 0.95,
	 hitSize: 33,
	 aimDst: 2.4,
     range: 110,
	 drag: 0.01,
	 accel: 0.15,
	 flying: true,
	 rotateSpeed: 1.25,
     engineOffset: 26.4,
     engineSize: 5.8,
}); 
flor.weapons.addAll(florLong, florLauncher);

flor.constructor = () => extend(UnitEntity, {});

/*Support Unit Section*/
const proximity = extend(UnitType, "proximity", {
	 health: 120,
	 speed: 2.1,
	 hitSize: 6,
	 drag: 0.01,
	 accel: 0.01,
	 flying: true,
     aimDst: 0.5,
     range: 40,
     engineSize: 1.35,
     engineOffset: 3.65,
     
     destroyBullet: proximityField,
});
proximity.weapons.add(proximityFlame);

proximity.constructor = () => extend(UnitEntity, {
    destroy() {
       this.super$destroy();
       proximity.destroyBullet.create(this, this.team, this.x, this.y, this.baseRotation - 90, 0.1);
    },
}); 

//partly inspired from sk
const proximityShot = extend(BasicBulletType, {
    damage: 40.5, 
    absorbable: false,
    reflectable: false,
    despawnEffect: Fx.none,
    shootEffect: Fx.spawn,
    backColor: Color.white,
    frontColor: Color.white,
    trailColor: Pal.accent,
    trailChance: 0.4,
    lifetime: 120,
    speed: 2.3,
    drag: 0.0001,
    hitSize: proximity.hitSize,
    spawnUnit: proximity,
    thrustForceMultiplier: 0.75,
    draw(b) {
   	this.super$draw(b);
       let ic = this.spawnUnit.fullIcon;
       
       Draw.color(this.frontColor);
       Draw.z(Layer.flyingUnit + 0.01);
       Draw.rect(ic, b.x, b.y, b.rotation() - 90);
       Draw.color(Pal.shadow);
       Draw.mixcol(Pal.shadow, 1);
       Draw.z(Layer.darkness);
       Draw.rect(ic, b.x + this.spawnUnit.shadowTX, b.y + this.spawnUnit.shadowTY, b.rotation() - 90);
       Draw.mixcol();
       Draw.z();
       Draw.reset();
    },
    despawned(b) {
       this.super$despawned(b);
   	this.spawn(b);
    },
    hit(b) {
       this.spawn(b);
    },
    spawn(b) {
   	let u = this.spawnUnit.create(b.team);
   	u.set(b.x, b.y);
       u.rotation = b.rotation();
       u.vel.trns(u.rotation, b.vel.len() * this.thrustForceMultiplier);
       u.apply(StatusEffects.unmoving, 30);
       //very ineffective
       Events.fire(new UnitCreateEvent(u, b.owner instanceof Building ? b.owner : null, b.owner instanceof Unit ? b.owner : null));
       if (!Vars.net.client()) {
          u.add();
       }
       Time.run(60, run(() => {
          if (Mathf.chance(0.1)) {
             Event.showMoving("I can admit I was thrown there.", 2, u);
          }
       }));
    },
    drawLight(b) {
       //nope
    },
});

const manoLauncher = extend(Weapon, "manoLauncher", {
    reload: 600,
    shots: 1,
    ignoreRotation: true,
    alternate: true,
    mirror: true,
    ejectEffect: Fx.none,
    top: false,
    shootSound: Sounds.shootBig,
    x: 16.0,
    y: 3,
    rotate: false,
    bullet: proximityShot,
    draw(unit, mount) {
       let vec = new Vec2();
       let rot = unit.rotation - 90;
       vec.trns(unit.rotation, -(mount.reload / this.reload * 7.5));
       let dx = this.x, dy = this.y;
       
       let x = unit.x + (Angles.trnsx(rot, dx, dy) + vec.x), y = unit.y + (Angles.trnsy(rot, dx, dy) + vec.y);
       if (Units.canCreate(unit.team, proximity)) {
          Draw.draw(Draw.z(), () => {
              Drawf.construct(x, y, proximity.fullIcon, rot, 1 - mount.reload / this.reload, 1, this.reload - mount.reload);
          });
       }
    },
}); 

const meno = extend(UnitType, "meno", {
	 health: 70,
	 speed: 1.85,
	 hitSize: 6,
	 drag: 0.01,
	 accel: 0.35,
	 flying: true,
	 rotateShooting: false,
	 rotateSpeed: 4,
	 buildSpeed: 0.2,
     aimDst: 0.5,
     range: 60,
     engineSize: 1.35,
     engineOffset: 3.65,
     
     maxShields: 70,
     setStats() {
        this.super$setStats();
        // Basically some messy UI code here
        // flar bos
        const shield = new StatValue({
           display(d) {
           	d.table(Styles.none, t => {
           	    let width = 160 * 2;
                   t.defaults().padBottom(4);
           	    t.add("The Shield").padLeft(4).row();
                   t.table(Styles.none, tt => {
                   	tt.defaults().left();
                   	tt.add("Shield points:").color(Color.lightGray);
                       tt.add(new Bar(
                          () => "70",
                          () => Pal.heal,
                          () => 1 
                       )).size(130, 25).padLeft(4);
                   }).padLeft(4).left().growX().row();
                   t.add("Average passive health regen: " + (0.005 * 60) + "/tick").color(Color.lightGray).padLeft(4).left().row();
                   t.image(Tex.whiteui, Pal.gray).growX().size(width, 3.5).row();
                   t.pane(Styles.defaultPane, p => {
                   	 p.labelWrap(
                            Core.bundle.get("flar-expansion-shield.description"),
                        ).size(width, 0).color(Color.lightGray);
                   }).size(width + 10, 120);
               }).margin(4).padLeft(2).center();
           },
        });
        
        this.stats.add(Stat.abilities, shield);
     },
     display(unit, table) {
        this.super$display(unit, table);
        //nothing really special here, just another health bar
        table.table(Styles.none, bars => {
            bars.defaults().growX().height(20).pad(4);

            bars.add(new Bar(
               () => Core.bundle.get("flar-expansion-shieldHeath"),
               () => Pal.heal,
               () => unit.getShields() / this.maxShields
            ).blink(Color.white));
        }).growX();
     },
});
meno.defaultController = () => extend(BuilderAI, {});
meno.weapons.add(menoShock);

meno.constructor = () => extend(UnitEntity, {
  shldAngle: 0,
  shldPoints: 0,
  shldCone: 0.30, //0 - nothing, 1 - a full circle
  regenerating: false,
  draw() {
    this.super$draw();
    let dest = this.angleTo(this.aimX, this.aimY);
    if (!this.dead) {
  	if (this.rotateShooting) {
        this.shldAngle = this.rotation;
      } else this.shldAngle = Angles.moveToward(this.shldAngle, dest, meno.rotateSpeed * Time.delta * this.speedMultiplier);
    }
    let alpha = Mathf.clamp(0, this.shldPoints, meno.maxShields) / meno.maxShields;
    
    Draw.color(Pal.heal);
    Draw.z(Layer.effect);
    Draw.alpha(Math.max(alpha - 0.5, 0));
    Lines.lineAngle(this.x, this.y, this.shldAngle - this.shieldToDeg(), this.size());
    //now try to offset the line, so that it looks like it's on the shield
    Lines.lineAngle(this.x, this.y, this.shldAngle + this.shieldToDeg() - Math.PI * Lines.getStroke() * 2.5, this.size());
    Draw.alpha(alpha);
    Lines.arc(this.x, this.y, this.size(), this.shldCone, this.shldAngle - this.shieldToDeg());
    Draw.z();
    Draw.reset();
  },
  update() {
    this.super$update();
    let size = this.size();
    Groups.bullet.intersect(this.x - size, this.y - size, size * 2, size * 2).each(b => {
    	if (b != null && b.team != this.team && b.owner != this) {
    	   //totally avant stuff here
    	   let temp = Angles.angle(this.x, this.y, b.x, b.y);
           let tempDst = Mathf.dst(b.x, b.y, this.x, this.y);
           if (tempDst <= Math.pow(size, 2) && Angles.within(temp, this.shldAngle, this.shieldToDeg())) {
    	      // phase's wall deflect goes brr
              let penX = Math.abs(this.x - b.x), penY = Math.abs(this.y - b.y);
              if (this.shldPoints > 0) {
                 if (b.damage < meno.maxShields) {
                   if (b.type.reflectable) {
                     b.trns(-b.vel.x, -b.vel.y);
                     if (penX > penY) {
                        b.vel.x *= -1;
                     } else {
                        b.vel.y *= -1;
                     }
                     b.owner = this;
                     b.team = this.team;
                     b.time += 1; 
                     this.shldPoints -= Math.max(0, b.damage);
                   }
                 }
                 else {
                 	b.absorb();
                     this.shldPoints = 0;
                     let reply = "BOOM!"
                     Event.showMoving(reply, 1, this);
                 }
                 if (this.regenerating) {
                    menoShieldAppear.at(this.x, this.y, this.shldAngle, [size, this.shldCone]);
                 }
                 this.regenerating = false;
              }
           }
    	}
    });
    if (this.regenerating) this.shldPoints = Mathf.lerpDelta(this.shldPoints, meno.maxShields, 0.005);
    else {
       //wait three seconds before regenerating
       //really broke, sometimes it gets called over multiple times, making the shield wobble continously
       Timer.schedule(() => {
          this.regenerating = true;
       }, 3);
    }
  },
  //size for shields
  size() {
    return meno.hitSize * 1.25;
  },
  //shield's cone to degrees
  shieldToDeg() {
    return this.shldCone * 180;
  },
  getShields() {
    return this.shldPoints;
  },
});

const mino = extend(UnitType, "mino", {
	 health: 230,
	 armor: 1,
	 speed: 1.25,
	 hitSize: 9.25,
	 drag: 0.01,
	 accel: 0.35,
	 flying: true,
	 range: 90,
     aimDst: 10,
     engineOffset: 8.50,
});
mino.weapons.add(minoLaser);

mino.constructor = () => extend(UnitEntity, {});

const muno = extend(UnitType, "muno", {
	 health: 550,
	 armor: 2,
	 speed: 1.4,
	 hitSize: 24,
	 drag: 0.01,
	 accel: 0.10,
	 flying: true,
	 circleTarget: true,
     aimDst: 2,
     range: 100,
     engineOffset: 16.5,
     engineSize: 4,
});
muno.weapons.add(munoBomber);
muno.weapons.add(munoKnife);

muno.constructor = () => extend(UnitEntity, {});

const mano = extend(UnitType, "mano", {
	 health: 6300,
	 armor: 4,
	 speed: 0.78,
	 hitSize: 34,
	 aimDst: 2.4,
     range: 110,
	 drag: 0.01,
	 accel: 0.15,
	 flying: true,
	 rotateSpeed: 1.25,
	 buildSpeed: 1.45,
     engineOffset: 20,
     engineSize: 6.5,
}); 
mano.weapons.add(manoSprayer);
mano.weapons.add(manoLauncher);
mano.abilities.add(randomElectricFlashes);

mano.constructor = () => extend(UnitEntity, {});

/*Erekir region*/
//merui spam
const gem = extend(ErekirUnitType, "gem", {
	 health: 650,
	 armor: 1,
	 speed: 0.78,
	 hitSize: 9.5,
	 aimDst: 2.4,
     range: 160,
	 drag: 0.06,
	 accel: 0.08,
	 flying: false,
	 rotateSpeed: 1.75,
	
	 legStraightness: 0.3, 
     stepShake: 0, 

     legCount: 6,
     legLength: 8,
     lockLegBase: true,
     legContinuousMove: true, 
     legExtension: -2,
     legBaseOffset: 3,
     legMaxLength: 1.1, 
     legMinLength: 0.2,
     legLengthScl: 0.96,
     legForwardScl: 1.1,
     legGroupSize: 3,
     rippleScale: 0.2,
            
	 legMoveSpace: 1,
     allowLegStep: true,
     hovering: true,
     legPhysicsLayer: false,

     shadowElevation: 0.1,
     groundLayer: Layer.legUnit - 1,
     researchCostMultiplier: 0,
});
gem.weapons.add(gemTendril);
gem.defaultController = AI.backwardsAI;

gem.constructor = () => extend(LegsUnit, {});

const geode = extend(ErekirUnitType, "geode", {
	 health: 800,
	 armor: 1,
	 speed: 0.78,
	 hitSize: 10,
	 aimDst: 2.4,
     range: 160,
	 drag: 0.06,
	 accel: 0.08,
	 flying: false,
	 rotateSpeed: 1.75,

     stepShake: 0, 

     legCount: 4,
     legLength: 8,
     lockLegBase: true,
     legContinuousMove: true, 
     legExtension: -3,
     legBaseOffset: 5,
     legMaxLength: 1.1, 
     legMinLength: 0.2,
     legLengthScl: 0.95,
     legForwardScl: 0.7,
            
	 legMoveSpace: 1,
     hovering: true,
     legPhysicsLayer: false,

     shadowElevation: 0.1,
     groundLayer: Layer.legUnit - 1,
     researchCostMultiplier: 0,
});
geode.weapons.add(geodeShield);

geode.constructor = () => extend(LegsUnit, {});

module.exports = {
   coverance: coverance,
   flar: flar, fler: fler,
   flir: flir, flor: flor,
   proximity: proximity,
   meno: meno, mino: mino,
   muno: muno, mano: mano,
   gem: gem, geode: geode
};