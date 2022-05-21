package erekir;

import arc.Events;
import arc.util.Time;
import arc.util.Log;
import mindustry.content.*;
import mindustry.world.blocks.units.UnitFactory;
import mindustry.mod.*;
import mindustry.game.EventType.*;
import erekir.content.*;
import erekir.ctype.*;

import static mindustry.type.ItemStack.with;

public class ErekirExpansion extends Mod{

    public ErekirExpansion() {
        Log.info("Loaded Erekir buoyancy");
        
        Events.on(ContentInitEvent.class, e -> {
           ((UnitFactory) Blocks.mechFabricator).plans.add(
               new UnitFactory.UnitPlan(ErkUnitTypes.gem, (float) 35 * Time.toSeconds, with(Items.beryllium, 300, Items.silicon, 35))
           );
           // silly
           Blocks.mechFabricator.configurable = true;
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
