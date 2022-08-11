package erekir.room;

import arc.struct.*;
import arc.util.*;
import arc.math.geom.*;
import mindustry.content.*;
import mindustry.world.*;
import mindustry.world.blocks.defense.turrets.*;
import mindustry.world.blocks.defense.turrets.Turret.*;
import mindustry.world.blocks.defense.turrets.ItemTurret.*;
import mindustry.world.blocks.defense.turrets.ItemTurret.ItemEntry;
import mindustry.world.blocks.defense.turrets.LiquidTurret.*;
import mindustry.world.blocks.defense.turrets.ContinuousLiquidTurret.*;
import mindustry.world.blocks.defense.turrets.PowerTurret.*;
import mindustry.world.blocks.power.Battery.*;
import mindustry.game.*;
import mindustry.type.*;
import mindustry.Vars;

public class DefenseRoom extends BaseRoom{
   
   public DefenseRoom(int px, int py, int w, int h) {
      super(px, py, w, h);
   }
   
   @Override
   public void generate() {
      super.generate();
      int dx = x, dy = y;
      
      Seq<Block> turrets = Vars.content.blocks().select(b -> b instanceof Turret);
      Turret tur = (Turret) turrets.random(rand);
      
      //turret in the center
      Tile tile = Vars.world.tile(dx, dy);
      if (tile != null) {
         tile.setBlock(tur, Team.sharded, 0);
         TurretBuild t = (TurretBuild) tile.build;
         //probably a bad code
         if (t != null) {
            if (t instanceof ItemTurretBuild) {
               //random choosen item
               Seq<Item> itemRecipes = Vars.content.items().select(i -> ((ItemTurret) tur).ammoTypes.containsKey(i));
               Item item = itemRecipes.random(rand);
               
               for (int i = 0; i < tur.maxAmmo; i++) {
                  t.handleItem(null, item);
               }
            }
            else if (t instanceof LiquidTurretBuild) {
               //random choosen liquid
               Seq<Liquid> liquidRecipes = Vars.content.liquids().select(i -> ((LiquidTurret) tur).ammoTypes.containsKey(i));
               Liquid l = liquidRecipes.random(rand);
               t.liquids.add(l, tur.maxAmmo);
            }
            else if (t instanceof PowerTurretBuild) {
               //if power turret, just place full batteries around
               for (int w = dx - tur.size - 1; w <= dx + tur.size + 1; w++) for (int h = dy - tur.size - 1; h <= dy + tur.size + 1; h++) {
                  Tile tile2 = Vars.world.tile(w, h);
                  if (tile2 != null && tile2.block() == Blocks.air) {
                     tile2.setBlock(Blocks.battery, Team.sharded, 0);
                     BatteryBuild b = (BatteryBuild) tile2.build;
                     if (b != null) b.power.status = 1;
                  }
               }
            }
            if (tur.heatRequirement > 0f) {
               for (int i = 0; i < t.sideHeat.length; i++) {
                  t.sideHeat[i] = tur.heatRequirement * tur.maxHeatEfficiency;
               }
            }
         }
      }
   }
}
