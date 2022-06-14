package erekir.util;

import arc.func.*;
import arc.math.geom.*;
import mindustry.game.*;
import mindustry.game.Teams.*;
import mindustry.gen.*;
import erekir.world.blocks.environment.ItemProp.*;

public class ErkUtil{
   
   /** Iterates over every building and checks if it's a drop build. */
   public static void allDrops(Cons<ItemProp.ItemBuild> e) {
      for (Building build : Groups.build) {
         if (build instanceof ItemProp.DropBuild) {
            DropBuild drop = (ItemProp.ItemBuild) build;
            e.get(drop);
         }
      } 
   }
   
   /* Iterates over every building of a team and checks if they're within a certain range. */
   public static void dropsWithin(Team team, float x, float y, float range, Cons<ItemProp.ItemBuild> e) {
      for (Building build : Groups.build) {
         if (build instanceof ItemProp.DropBuild) {
            DropBuild drop = (ItemProp.DropBuild) build;
            
            if (drop.team == team && drop.within(x, y, range)) {
               e.get(drop);
            }
         }
      } 
   }
   
   public static void dropsWithin(Team team, Position pos, float range, Cons<ItemProp.DropBuild> e) {
      for (Building build : Groups.build) {
         if (build instanceof ItemProp.DropBuild) {
            DropBuild drop = (ItemProp.DropBuild) build;
            
            if (drop.team == team && drop.within(pos.x, pos.y, range)) {
               e.get(drop);
            }
         }
      } 
   }
   
   public static boolean dropWithin(ItemProp.DropBuild build, float x, float y, float range) {
       return build.within(x, y, range);
   }
   
   public static boolean dropWithin(ItemProp.DropBuild build, Position pos, float range) {
       return dropWithin(build, pos.x, pos.y, range);
   }
   
   /* Whenever a building has the overlay button. */
   public static  boolean containsButton(ItemProp.DropBuild build) {
       return build.containsButton;
   }
}