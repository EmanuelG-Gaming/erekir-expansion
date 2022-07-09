package erekir.content;

import arc.util.*;
import arc.graphics.*;
import arc.graphics.g2d.*;
import mindustry.type.*;
import mindustry.content.*;
import mindustry.world.*;
import mindustry.world.consumers.*;
import mindustry.world.draw.*;
import mindustry.world.meta.*;
import mindustry.world.blocks.environment.Floor;
import mindustry.world.blocks.defense.*;
import mindustry.entities.*;
import mindustry.entities.effect.*;
import mindustry.entities.bullet.*;
import mindustry.graphics.*;
import erekir.ctype.*;
import erekir.graphics.*;
import erekir.world.blocks.environment.*;
import erekir.world.blocks.production.*;
import erekir.world.blocks.defense.*;
import erekir.world.blocks.defense.turrets.*;

import static mindustry.type.ItemStack.with;

public class ErkBlocks implements AltContentList{
    public static Block
    
    //Environment
    berylDrop, tungDrop, graphiteDrop, sandDrop, thorDrop,
    angryArkyciteFloor, pooledNeoplasm, 
    
    //Defense
    berylUpkeeper,
    
    //Gathering
    dGatherer, dCollector,
    
    //Turrets
    fissure
     
    ;
    
    @Override
    public void load() {
      berylDrop = new ItemProp("berylDrop"){{
          dropItem = Items.beryllium;
          amount = 4;
      }};
      
      tungDrop = new ItemProp("tungDrop"){{
          dropItem = Items.tungsten;
          amount = 2;
      }};
      
      graphiteDrop = new ItemProp("graphiteDrop"){{
          dropItem = Items.graphite;
          amount = 3;
      }};
      
      sandDrop = new ItemProp("sandDrop"){{
          dropItem = Items.sand;
          amount = 5;
      }};
      thorDrop = new ItemProp("thoriumDrop"){{
          dropItem = Items.thorium;
          amount = 1;
      }};
      
      angryArkyciteFloor = new Floor("powerful-arkycite-floor"){{
         speedMultiplier = 0.2f;
         variants = 0;
         liquidDrop = ErkLiquids.angryArkycite;
         liquidMultiplier = 1.2f;
         isLiquid = true;
         status = ErkStatusEffects.metalloclast;
         statusDuration = 600f;
         drownTime = 140f;
         cacheLayer = ErekirShaders.arkyLayer;
         albedo = 0.9f;
      }};
      
      pooledNeoplasm = new Floor("pooled-neoplasm"){{
         speedMultiplier = 0.4f;
         variants = 0;
         liquidDrop = Liquids.neoplasm;
         liquidMultiplier = 0.5f;
         isLiquid = true;
         drownTime = 90f;
         cacheLayer = ErekirShaders.neoplasmLayer;
      }};
      
      berylUpkeeper = new RegenProjector("berylUpkeeper"){{
          size = 2;
          range = 15;
          baseColor = ErkPal.greenishBeryl;

          consumePower(0.75f);

          healPercent = 2.3f / 60f;
          effect = ErkFx.regenParticleBeryl;
          
          Color col = ErkPal.greenishBeryl.cpy().mul(1.15f);
          
          drawer = new DrawMulti(new DrawDefault(), new DrawGlowRegion(){{
             color = ErkPal.greenishBeryl;
          }}, new DrawPulseShape(false){{
             layer = Layer.effect;
             color = col;
          }}, new DrawShape(){{
             layer = Layer.effect;
             radius = 2.5f;
             useWarmupRadius = true;
             timeScl = 2f;
             color = col;
          }});
          requirements(Category.effect, with(Items.beryllium, 100, Items.graphite, 65, Items.tungsten, 40));
      }};
      
      dGatherer = new DirectionalGatherer("directionalGatherer"){{
          size = 1;
          rotateDraw = false;
          regionRotated1 = 1;
          requirements(Category.production, with(Items.beryllium, 15));
      }};
      
      dCollector = new DirectionalGatherer("directionalCollector"){{
          size = 2;
          length = 24;
          consumePower(75f / 60f);
          rotateDraw = false;
          regionRotated1 = 1;
          requirements(Category.production, with(Items.beryllium, 75, Items.tungsten, 30, Items.graphite, 60));
      }};
      
      fissure = new ErekirTurret("fissure"){{
          requirements(Category.turret, with(Items.beryllium, 80, Items.graphite, 60));
          
          Effect e = new MultiEffect(Fx.shootBigColor, Fx.colorSparkBig);

          ammo(
          Items.beryllium, new BasicBulletType(5.5f, 40){{
              width = 10f;
              hitSize = 5f;
              height = 16f;
              shootEffect = e;
              smokeEffect = Fx.shootBigSmoke;
              ammoMultiplier = 1;
              hitColor = backColor = trailColor = Pal.berylShot;
              frontColor = Color.white;
              trailWidth = 2.1f;
              trailLength = 10;
              hitEffect = despawnEffect = ErkFx.hitSquaresColorSmall;
              buildingDamageMultiplier = 0.8f;
          }}
          );
          shoot = new ShootAlternate(9f / 4f);
          shootY = 6.5f;
          
          coolantMultiplier = 6f;

          shake = 1f;
          ammoPerShot = 1;
          outlineColor = Pal.darkOutline;
          size = 2;
          envEnabled |= Env.space;
          reload = 12f;
          recoil = 1f;
          range = 160;
          shootCone = 10f;
          scaledHealth = 180;
          rotateSpeed = 2.4f;
          researchCostMultiplier = 0.05f;

          coolant = consume(new ConsumeLiquid(Liquids.water, 15f / 60f));
      }};
    }
}