package erekir;

import mindustry.mod.*;
import erekir.content.*;

public class ErekirExpansion extends Mod{

    public ErekirExpansion() {
        Log.info("Loaded Erekir buoyancy");
    }
    
    private final ContentList[] erekirContent = {
       new ErkUnitTypes()
    };
    
    @Override
    public void loadContent() {
        // load everything from the array
        for (ContentList list : erekirContent) list.load();
    }

}
