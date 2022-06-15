package erekir.ui;

import arc.Core;
import arc.scene.*;
import arc.scene.ui.layout.*;
import arc.scene.style.*;
import mindustry.graphics.*;
import mindustry.ui.*;
import mindustry.gen.*;

import static mindustry.Vars.*;

public class ErekirSettings{
   private static float buttonW = 48f, buttonH = 48f;
   private static int rowCount = 6;
   
   public static void load() {
      ui.settings.addCategory("Erekir expansion", "erekir-expansion-gem-full", t -> {
          t.defaults().padBottom(8);
          t.add("Button icons (requires restart)").color(Pal.accent).row();
          
          t.pane(Styles.defaultPane, t2 -> {
             //row indice
             int[] r = {0}; 
             Icon.icons.each((name, icon) -> {
                if (!Core.settings.getBool("erekir-expansion-displaySmall") && name.contains("small")) return;
                t2.button(new TextureRegionDrawable(icon), Styles.cleari, () -> {
                    Core.settings.put("erekir-expansion-buttonIcon", name);
                    ui.showInfo("Changing the button icon to " + name + ".");
                }).size(buttonW, buttonH).margin(4f).pad(0f);
                
                if (++r[0] % rowCount == 0) t2.row();
             });
          }).size(buttonW * rowCount + 6f, buttonH * 25f + 6f);
          
          //also display small images
          t.checkPref(
             "displaySmall", Core.settings.getBool("erekir-expansion-displaySmall"),
             bool -> Core.settings.put("erekir-expansion-displaySmall", bool)
          );
      });
   }
}