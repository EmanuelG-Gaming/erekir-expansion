package erekir.world.blocks.environment;

import arc.math.*;
import arc.graphics.*;
import arc.graphics.g2d.*;
import mindustry.gen.*;
import mindustry.world.*;
import mindustry.world.blocks.storage.CoreBlock.*;
import mindustry.world.meta.*;
import mindustry.type.*;
import mindustry.content.*;
import erekir.ui.button.Pickup;

import static mindustry.Vars.*;

public class ItemProp extends Block{
    public Item dropItem = Items.copper;
    public float rotationOffset = 360f;
    public int amount = 1;
    
    public ItemProp(String name) {
        super(name);
        update = true;
        breakable = true;
        alwaysReplace = false;
        envEnabled = Env.any;
        replaceable = false;
        rebuildable = false;
        drawDisabled = false;
        canOverdrive = false;
        inEditor = true;
        targetable = false;
        instantDeconstruct = true;
        breakEffect = Fx.none;
        breakSound = Sounds.none;
        destroyEffect = Fx.none;
        destroySound = Sounds.none;
        hasShadow = false;
        //drawRubble = false;
    }
    
    @Override 
    public int minimapColor(Tile tile) {
        return dropItem.color.rgba();
    }
    
    @Override
    public void drawBase(Tile tile) {
        for (int i = 0; i < amount; i++) {
           float spreadX = Mathf.randomSeedRange(tile.worldx() + i, tilesize - 2);
           float spreadY = Mathf.randomSeedRange(tile.worldy() + i * 2, tilesize - 2);
           float rot = Mathf.randomSeed(tile.pos() + i, rotationOffset);
           
           Draw.rect(dropItem.fullIcon, tile.worldx() + spreadX, tile.worldy() + spreadY, itemSize, itemSize, rot);
        }
    }
    
    @Override
    public void init() {
        super.init();
        if (dropItem != null) {
            setup(dropItem);
        } else {
            throw new IllegalArgumentException("slippery fingers");
        }
    }
    
    public void setup(Item itm) {
        this.itemDrop = itm;
        //this.mapColor.set(itm.color); 
    }
    
    public class DropBuild extends Building{
        public ItemStack stack = new ItemStack();
        
        public void addButton() {
            Pickup.createPickupButton(this, () -> { gather(player.unit()); this.kill(); });
        }
        
        public void gather(Unit unit) {
           if (unit != null) {
              Item drop = dropItem;
              CoreBuild core = unit.closestCore();
              if (core != null) {
                 if (unit.within(core, unit.type.range)) {
                    if (core.acceptStack(unit.stack.item, unit.stack.amount, unit) > 0) {
                       Call.transferItemTo(unit, unit.stack.item, unit.stack.amount, unit.x, unit.y, core);
                    }
                 } else {
                    for (int i = 0; i < stack.amount; i++) {
                       Fx.itemTransfer.at(x, y, 4, drop.color, unit);
                    }
                    unit.stack.amount = Math.min(unit.stack.amount + stack.amount, unit.type.itemCapacity);
                    unit.stack.item = stack.item;
                 }
              }
           }
        }
        
        @Override
        public void created() {
            stack.amount = amount;
            stack.item = itemDrop;
        }
        
        @Override
        public void drawCracks() {
          
        }
    }
}