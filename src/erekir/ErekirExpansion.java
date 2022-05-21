package erekir;

import arc.util.Log;
import mindustry.mod.*;
import erekir.content.*;
import erekir.ctype.*;

public class ErekirExpansion extends Mod{

    public ErekirExpansion() {
        Log.info("Loaded Erekir buoyancy");
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
