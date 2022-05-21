package erekir;

import arc.util.Time;
import arc.util.Log;
import mindustry.content.*;
import mindustry.world.blocks.units.UnitFactory;
import mindustry.mod.*;
import mindustry.game.EventType.*;
import erekir.content.*;
import erekir.ctype.*;

public class ErekirExpansion extends Mod{

    public ErekirExpansion() {
        Log.info("Loaded Erekir buoyancy");
        
        Events.on(ContentInitEvent.class, e -> {
           ((UnitFactory) Blocks.mechFabricator).plans.add(
               new UnitPlan(ErkUnitTypes.gem, (float) 35 * Time.toSeconds, ItemStack.with(Items.beryllium, 300, Items.silicon, 35))
           );
           Blocks.mechFabricator.init(); 
        });
    }
    
    private final AltContentList[] erekirContent = {
       new ErkUnitTypes()
    };
    
    @Override
    public void loadContent() {
        // load everything from the array
        for (AltContentList list : erekirContent) list.load();
    }

}
