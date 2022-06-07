package erekir.world.blocks.environment;

import arc.math.*;
import arc.graphics.*;
import arc.graphics.g2d.*;
import mindustry.gen.*;
import mindustry.world.*;
import mindustry.world.blocks.storage.CoreBlock;
import mindustry.type.*;
import mindustry.content.*;
import erekir.ui.button.Pickup;

import static mindustry.Vars.*;

public class ItemProp extends Block{
    public Item dropItem = Items.copper;
    public float rotation = 30f;
    
    public ItemProp(String name, Item drop) {
        super(name);
        dropItem = drop;
        update = true;
        breakable = true;
        alwaysReplace = false;
        instantDeconstruct = true;
        breakEffect = Fx.none;
        breakSound = Sounds.none;
        hasShadow = false;
    }
    
    @Override 
    public int minimapColor(Tile tile) {
        return dropItem.color.rgba();
    }
    
    @Override
    public void drawBase(Tile tile) {
        Draw.rect(dropItem.fullIcon, tile.worldx(), tile.worldy(), itemSize, itemSize, Mathf.randomSeed(tile.pos(), rotation));
    }
    
    public class DropBuild extends Building{
        public void addButton() {
            Pickup.createPickupButton(this, () -> { gather(player.unit()); this.kill(); });
        }
        
        public void gather(Unit unit) {
           if (unit != null) {
              Item drop = dropItem;
              Fx.itemTransfer.at(x, y, 4, drop.color, unit);
              CoreBuild core = unit.closestCore();
              if (core != null) {
                 if (unit.within(core, unit.type.range)) {
                    if (core.acceptStack(unit.stack.item, unit.stack.amount, unit) > 0) {
                       Call.transferItemTo(unit, unit.stack.item, unit.stack.amount, unit.x, unit.y, core);
                    }
                 } else {
                    unit.stack.amount = Math.min(unit.stack.amount + 1, unit.type.itemCapacity);
                    unit.stack.item = drop;
                 }
              }
           }
        }
        
        @Override
        public void drawCracks() {
          
        }
    }
}