package erekir.type.ammo;

import arc.util.*;
import arc.math.geom.*;
import arc.graphics.*;
import mindustry.content.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.world.*;
import mindustry.world.blocks.liquid.*;
import mindustry.world.blocks.liquid.LiquidRouter.*;
import mindustry.type.*;

import static mindustry.Vars.*;

/** Interpretation of @author Mythril382's pull request. */
public class LiquidAmmoType implements AmmoType{
    public float range = 15f;
    public float ticks = 7f;
    public float liquidTake = 5f;
    public Liquid ammoLiquid = Liquids.water;

    public LiquidAmmoType(Liquid ammoLiquid) {
       this.ammoLiquid = ammoLiquid;
    }
    
    public LiquidAmmoType() {
      
    }
    
    @Override
    public String icon() {
       return ammoLiquid.emoji();
    }
    
    @Override 
    public Color color() {
       return ammoLiquid.color;
    }
    
    @Override
    public Color barColor() {
       return ammoLiquid.color;
    }
    
    @Override
    public void resupply(Unit unit) {
       float offsetRange = unit.hitSize / tilesize + range;
       float ux = unit.x / tilesize, uy = unit.y / tilesize;
       
       Geometry.circle((int) ux, (int) uy, (int) offsetRange, (x, y) -> {
          Tile tile = world.tile(x, y);
          
          if (tile != null) {
             if (tile.build != null && tile.build instanceof LiquidRouterBuild) {
                LiquidRouterBuild build = (LiquidRouterBuild) tile.build;
                if (unit.type.ammoCapacity - unit.ammo >= liquidTake && build.liquids != null && build.liquids.get(ammoLiquid) > 0) {
                   for (int i = 0; i < liquidTake; i++)
                      Time.run(i * ticks, () -> Fx.itemTransfer.at(build.tileX() * tilesize, build.tileY() * tilesize, 4, ammoLiquid.color, unit));
                   
                   build.liquids.remove(ammoLiquid, liquidTake);
                   unit.ammo = Math.min(unit.ammo + liquidTake, unit.type.ammoCapacity);
                }
             }
          }
       });
    }
}