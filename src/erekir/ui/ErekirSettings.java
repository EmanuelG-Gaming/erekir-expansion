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
      ui.settings.addCategory("Erekir expansion", "erekir-expansion-gem", t -> {
          t.defaults().padBottom(4).row();
          t.add("Button icons (requires restart)").color(Pal.accent);
          
          t.pane(Styles.defaultPane, t2 -> {
             Icon.icons.each((name, region) -> {
                //row indice
                int r = 0;
                t2.button(new TextureRegionDrawable(region), Styles.cleari, () -> {
                   Core.settings.put("erekir-expansion-buttonIcon", name);
                }).size(buttonW, buttonH).margin(4f).pad(0f);
                
                if (++r % rowCount == 0) t2.row();
             });
          }).size(buttonW * rowCount + 6f, buttonH * 25f + 6f);
      });
   }
}