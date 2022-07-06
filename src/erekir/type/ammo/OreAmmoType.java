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
    public int ammoGain = 1;
    public Block extractOre = Blocks.oreCopper;

    public OreAmmoType(Block extractOre) {
       this.extractOre = extractOre;
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
       return ((OreBlock) extractOre).itemDrop.color;
    }
    
    @Override
    public Color barColor() {
       return ((OreBlock) extractOre).itemDrop.color;
    }
    
    @Override
    public void resupply(Unit unit) {
       float offsetRange = unit.hitSize / tilesize + range;
       float ux = unit.x / tilesize, uy = unit.y / tilesize;
       
       Geometry.circle((int) ux, (int) uy, (int) offsetRange, (x, y) -> {
          Tile tile = world.tile(x, y);
          
          if (tile != null && tile.overlay() == extractOre) {
             if (unit.type.ammoCapacity - unit.ammo >= ammoGain) {
                for (int i = 0; i < ammoGain; i++)
                   Fx.itemTransfer.at(tile.x * tilesize, tile.y * tilesize, 4, ((OreBlock) extractOre).itemDrop.color, unit);
                
                unit.ammo = Math.min(unit.ammo + ammoGain, unit.type.ammoCapacity);
             }
          }
       });
    }
}