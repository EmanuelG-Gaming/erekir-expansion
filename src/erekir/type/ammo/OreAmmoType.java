package erekir.type.ammo;

import arc.math.geom.*;
import arc.graphics.*;
import mindustry.content.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.world.blocks.environment.*;
import mindustry.type.ammo.*;

import static mindustry.Vars.*;

/** Allows direct extraction of ores as ammo.
 *  Partial thanks to @author Mythril382 */
public class OreAmmoType extends AmmoType{
    public float range = 75f;
    public OreBlock extractOre = Blocks.oreCopper;
    //public float resupplyTime = 5f;
    public int ammoGain = 1;
    public float totalItems = 20;

    public OreAmmoType(Block extractOre, float totalItems) {
       this.extractOre = extractOre;
       this.totalItems = totalItems;
    }
    
    public OreAmmoType() {
      
    }
    
    @Override
    public String icon() {
       //TODO maybe include the environment block (for wall ores)
       return extractOre.emoji();
    }
    
    @Override 
    public Color color() {
       return Pal.ammo;
    }
    
    @Override
    public Color barColor() {
       return extractOre.item.color;
    }
    
    @Override
    public void resupply(Unit unit) {
       float offsetRange = unit.hitSize + range;
      
       //TODO won't this lag?
       Geometry.circle(unit.x, unit.y, offsetRange, (x, y) -> {
          Tile build = world.tile(x, y);
          if (build != null && build.overlay() == extractOre) {
              Fx.itemTransfer.at(build.x, build.y, 4, extractOre.itemDrop.color, unit);
              unit.ammo = Math.min(unit.ammo + ammoGain, unit.type.ammoCapacity);
          }
       });
    }
}