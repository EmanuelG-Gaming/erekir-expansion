package erekir.content;

import arc.graphics.*;
import arc.graphics.g2d.*;
import mindustry.type.*;
import mindustry.content.*;
import mindustry.world.*;
import mindustry.world.draw.*;
import mindustry.world.meta.*;
import mindustry.world.blocks.environment.Floor;
import mindustry.world.blocks.defense;
import mindustry.graphics.*;
import erekir.ctype.*;
import erekir.graphics.*;
import erekir.world.blocks.environment.*;
import erekir.world.blocks.production.*;

import static mindustry.type.ItemStack.with;

public class ErkBlocks implements AltContentList{
    public static Block
    
    //Environment
    berylDrop, tungDrop, graphiteDrop, sandDrop, thorDrop,
    angryArkyciteFloor,
    
    //Defense
    berylUpkeeper,
    
    //Gathering
    dGatherer, dCollector
     
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
         //TODO funny number and a very angry status effect
         status = StatusEffects.melting;
         statusDuration = 690f;
         drownTime = 140f;
         cacheLayer = ErekirShaders.arkyLayer;
         albedo = 0.9f;
      }};
      
      berylUpkeeper = new RegenProjector("berylUpkeeper"){{
          size = 2;
          range = 15;
          baseColor = ErkPal.greenishBeryl;

          consumePower(0.75f);

          healPercent = 4f / 60f;

          Color col = Color.valueOf("3b8f65");
          
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
    }
}