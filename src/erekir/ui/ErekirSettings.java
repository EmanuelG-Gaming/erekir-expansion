package erekir.ui;

import arc.Core;
import erekir.ui.dialogs.*;

import static mindustry.Vars.*;

public class ErekirSettings{
   public static void load() {
      ui.settings.addCategory("Erekir expansion", "erekir-expansion-gem-full", t -> {
          t.button("Button icons", ButtonIconsDialog::show).size(280f, 60f);
          
          //also display small images
          t.checkPref(
             "displaySmall", Core.settings.getBool("erekir-expansion-displaySmall"),
             bool -> Core.settings.put("erekir-expansion-displaySmall", bool)
          );
      });
   }
}