package erekir.type.ammo;

import arc.math.geom.*;
import arc.graphics.*;
import mindustry.content.*;
import mindustry.content.Blocks;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.world.*;
import mindustry.world.blocks.environment.*;
import mindustry.type.*;

import static mindustry.Vars.*;

/** Allows direct extraction of ores as ammo.
 *  Partial thanks to @author Mythril382 */
public class OreAmmoType implements AmmoType{
    public int range = 5;
    public Block extractOre = Blocks.oreCopper;
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
       return ((OreBlock) extractOre).item.color;
    }
    
    @Override
    public void resupply(Unit unit) {
       float offsetRange = unit.hitSize + range;
      
       Tile unitOn = unit.tileOn();
       
       //TODO won't this lag?
       Geometry.circle(unitOn.x, unitOn.y, (int) offsetRange, (x, y) -> {
          Tile build = world.tile(x, y);
          if (build != null && build.overlay() == extractOre) {
              Fx.itemTransfer.at(build.x, build.y, 4, ((OreBlock) extractOre).itemDrop.color, unit);
              unit.ammo = Math.min(unit.ammo + ammoGain, unit.type.ammoCapacity);
          }
       });
    }
}