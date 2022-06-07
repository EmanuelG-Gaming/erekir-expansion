package erekir.world.blocks.environment;

import arc.math.*;
import arc.graphics.*;
import arc.graphics.g2d.*;
import mindustry.gen.*;
import mindustry.world.*;
import mindustry.type.*;
import mindustry.content.*;

import static mindustry.Vars.*;

public class ItemProp extends Block{
    public Item dropItem = Items.copper;
    public float rotation = 15f;
    
    public ItemProp(String name, Item drop) {
        super(name);
        dropItem = drop;
        breakable = false;
        alwaysReplace = false;
        instantDeconstruct = true;
        variants = 1;
        breakEffect = Fx.breakProp;
        breakSound = Sounds.none;
        hasShadow = false;
    }
    
    @Override 
    public int minimapColor(Tile tile) {
        return dropItem.color.rgba();
    }
    
    @Override
    public void drawBase(Tile tile) {
        Draw.rect(dropItem.fullRegion, tile.worldx(), tile.worldy(), itemSize, itemSize, Mathf.randomSeed(tile.pos(), rotation));
    }
}