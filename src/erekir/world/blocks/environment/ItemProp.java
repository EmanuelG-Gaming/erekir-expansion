package erekir.world.blocks.environment;

import arc.Core;
import arc.math.*;
import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.util.io.*;
import mindustry.gen.*;
import mindustry.world.*;
import mindustry.world.blocks.storage.CoreBlock.*;
import mindustry.world.meta.*;
import mindustry.type.*;
import mindustry.content.*;
import erekir.ui.button.Pickup;

import static mindustry.Vars.*;

/** Inner class for loot interactions. Mostly contains utility methods. */
public class ItemProp extends Block{
    public Item dropItem = Items.copper;
    public float rotationOffset = 360f;
    /** The amount of items attributed when this block is created. */
    public int amount = 1;
    
    /** The scatteredness of the resources on this block. */
    public float scatterX = 8f / 2f, scatterY = 8f / 2f;
    
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
        destroyEffect = breakEffect = Fx.none;
        destroySound = breakSound = Sounds.missile;
        drawTeamOverlay = false;
        hasShadow = false;
        //partial thanks to meep for this
        createRubble = false;
        drawCracks = false;
    }
    
    @Override 
    public int minimapColor(Tile tile) {
        return dropItem.color.rgba();
    }
    
    @Override
    public void drawBase(Tile tile) {
       DropBuild build = (DropBuild) tile.build;
       for (int i = 0; i < build.currentAmount; i++) {
          float spreadX = Mathf.randomSeedRange(tile.pos() + i, scatterX);
          float spreadY = Mathf.randomSeedRange(tile.pos() + i * 2, scatterY);
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
            throw new IllegalArgumentException("slippery fingers.");
        }
    }
    
    @Override
    public TextureRegion[] icons() {
       //java
       return new TextureRegion[]{Core.atlas.find("erekir-expansion-nothingness")};
    }
    
    
    public void setup(Item itm) {
        dropItem = itm;
        //this.mapColor.set(itm.color); 
    }
    
    public class DropBuild extends Building{
        public int currentAmount;
        public boolean containsButton = false;
        
        @Override
        public void created() {
           currentAmount = amount;
        }
        
        public void addButton() {
           Pickup.createPickupButton(this, () -> gather(player.unit(), 1));
           containsButton = true;
        }
        
        @Override
        public void updateTile() {
           super.updateTile();
        }
        
        public void gather(Unit unit, int itemTake) {
           //prevent item overrides
           if (unit.stack.item != dropItem && unit.stack.amount != 0) return;
           
           if (unit != null) {
              if (unit.type.itemCapacity - unit.stack.amount >= itemTake) {
                 //the unit should gather the items first
                 unit.stack.amount = Math.min(unit.stack.amount + itemTake, unit.type.itemCapacity);
                 unit.stack.item = dropItem;
                 
                 CoreBuild core = unit.closestCore();
                 if (core != null && unit.within(core, unit.type.range)) {
                    transfer(unit, core);
                 } else {
                    for (int i = 0; i < itemTake; i++) Fx.itemTransfer.at(x, y, 4, dropItem.color, unit);
                 }
                 
                 currentAmount = Math.min(currentAmount - itemTake, itemCapacity);
              }
           }
           handleStackKill();
        }
        
        public void gather(Building build, int itemTake) {
           if (build.block.itemCapacity - build.items.get(dropItem) >= itemTake) {
              build.items.add(dropItem, itemTake);
              currentAmount = Math.min(currentAmount - itemTake, itemCapacity);
           }
           handleStackKill();
        }
        
        public void transfer(Unit unit, Building build) {
           if (build.acceptStack(unit.stack.item, unit.stack.amount, unit) > 0) {
              Call.transferItemTo(unit, unit.stack.item, unit.stack.amount, unit.x, unit.y, build);
           } 
        }
        
        public void handleStackKill() {
           if (currentAmount <= 0) kill();
        }
        
        @Override
        public byte version() {
           return 1;
        }

        @Override
        public void write(Writes write) {
           super.write(write);
           write.i(currentAmount);
        }
      
        @Override
        public void read(Reads read, byte revision) {
           super.read(read, revision);
           currentAmount = read.i();
        }
    }
}