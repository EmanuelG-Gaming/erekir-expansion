package erekir.world.blocks.environment;

import arc.math.*;
import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.util.*;
import mindustry.gen.*;
import mindustry.world.*;
import mindustry.world.blocks.storage.CoreBlock.*;
import mindustry.world.meta.*;
import mindustry.type.*;
import mindustry.content.*;
import erekir.ui.button.Pickup;

import static mindustry.Vars.*;

public class ItemProp extends Block{
    public @Nullable Item dropItem;
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
    
    public ItemProp(String name, Item itm) {
        super(name);
        localizedName = itm.localizedName;
        dropItem = itm;
        mapColor.set(itm.color);
        useColor = true;
    }
    
    public ItemProp(Item itm) {
        this(itm.name + "Drop", itm);
    }
    
    @Override 
    public int minimapColor(Tile tile) {
        return dropItem.color.rgba();
    }
    
    @Override
    public void drawBase(Tile tile) {
        DropBuild build = (DropBuild) tile.build;
        ItemStack stack = build.stack;
        for (int i = 0; i < stack.amount; i++) {
           float spreadX = Mathf.randomSeedRange(tile.pos() + i, scatterX);
           float spreadY = Mathf.randomSeedRange(tile.pos() + i * 2, scatterY);
           float rot = Mathf.randomSeed(tile.pos() + i, rotationOffset);
           
           Draw.rect(stack.item.fullIcon, tile.worldx() + spreadX, tile.worldy() + spreadY, itemSize, itemSize, rot);
        }
    }
    
    @Override
    public void init() {
        super.init();
        if (dropItem != null) {
            setup(dropItem);
        } else {
            throw new IllegalArgumentException("slippery fingers. cannot have none items.");
        }
    }
    
    @Override
    public TextureRegion[] icons() {
       //java
       return new TextureRegion[]{dropItem.fullIcon};
    } 
    
    
    public void setup(Item itm) {
        this.itemDrop = itm;
        //this.mapColor.set(itm.color); 
    }
    
    public class DropBuild extends Building{
        public ItemStack stack = new ItemStack();
        public boolean containsButton = false;
        
        public void addButton() {
           Pickup.createPickupButton(this, () -> gather(player.unit(), 1));
           containsButton = true;
        }
        
        @Override
        public void updateTile() {
           super.updateTile();
           
           if (handleStackKill()) kill();
        }
        
        public void gather(Unit unit, int itemTake) {
           //prevent item overrides
           if (unit.stack.item != stack.item && unit.stack.amount != 0) return;
           
           if (unit != null) {
              if (unit.type.itemCapacity - unit.stack.amount >= itemTake) {
                 //the unit should gather the items first
                 unit.stack.amount = Math.min(unit.stack.amount + itemTake, unit.type.itemCapacity);
                 unit.stack.item = stack.item;
                 
                 CoreBuild core = unit.closestCore();
                 if (core != null && unit.within(core, unit.type.range)) {
                    transfer(unit, core);
                 } else {
                    for (int i = 0; i < itemTake; i++) Fx.itemTransfer.at(x, y, 4, stack.item.color, unit);
                 }
                 
                 stack.amount = Math.max(stack.amount - itemTake, 0);
              }
           }
        }
        
        public void gather(Building build, int itemTake) {
           if (build.block.itemCapacity - build.items.get(stack.item).amount >= itemTake) {
              build.items.add(stack.item, itemTake);
              stack.amount = Math.max(stack.amount - itemTake, 0);
           }
        }
        
        public void transfer(Unit unit, Building build) {
           if (build.acceptStack(unit.stack.item, unit.stack.amount, unit) > 0) {
              Call.transferItemTo(unit, unit.stack.item, unit.stack.amount, unit.x, unit.y, build);
           } 
        }
        
        public boolean handleStackKill() {
           return stack.amount <= 0;
        }
        
        @Override
        public void created() {
           stack.amount = amount;
           stack.item = itemDrop;
        }
    }
}