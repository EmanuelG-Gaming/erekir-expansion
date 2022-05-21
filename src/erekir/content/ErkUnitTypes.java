package erekir.content;

import arc.graphics.*;
import arc.graphics.Color;
import mindustry.graphics.*;
import mindustry.type.unit.ErekirUnitType;
import mindustry.content.Fx;
import mindustry.ctype.*;
import erekir.entities.pattern.*;

public class ErkUnitTypes implements ContentList{
    public static UnitType
    
    gem;
    
    @Override
    public void load() {
       gem = new ErekirUnitType("gem"){{
          health = 800;
       	  armor = 1;
	        speed = 0.78f;
	        hitSize = 10f;
	        aimDst = 2.4f;
          range = 160;
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
          legMinLength = 1.1f;
          legMinLength = 0.2f;
          legLengthScl = 0.95f;
          legForwardScl = 0.7f;
            
	        legMoveSpace = 1f;
          hovering = true;
          legPhysicsLayer = false;

          shadowElevation = 0.1f;
          groundLayer = Layer.legUnit - 1f;
          researchCostMultiplier = 0;
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
                trailLength: 5f;
                trailSize = 0.7f;
                despawnEffect = gemHit;
                hitEffect = gemHit;
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