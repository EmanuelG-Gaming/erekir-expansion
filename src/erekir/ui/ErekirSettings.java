package erekir.ui;

import arc.Core;
import erekir.ui.dialogs.*;

import static mindustry.Vars.*;

public class ErekirSettings{
   private ButtonIconsDialog dialog;
   
   public static void load() {
      dialog = new ButtonIconsDialog();
      
      ui.settings.addCategory("Erekir expansion", "erekir-expansion-gem-full", t -> {
          t.button("Button icons", dialog::show).size(280f, 60f);
          
          //also display small images
          t.checkPref(
             "displaySmall", Core.settings.getBool("erekir-expansion-displaySmall"),
             bool -> Core.settings.put("erekir-expansion-displaySmall", bool)
          );
      });
   }
}