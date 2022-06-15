package erekir.ui.dialogs;

import arc.Core;
import arc.scene.*;
import arc.scene.ui.layout.*;
import arc.scene.style.*;
import mindustry.graphics.*;
import mindustry.ui.*;
import mindustry.ui.dialogs.*;
import mindustry.gen.*;

import static mindustry.Vars.*;

public class ButtonIconsDialog extends BaseDialog{
   private static float buttonW = 48f, buttonH = 48f;
   private static int rowCount = 6;
   
   public ButtonIconsDialog() {
      super(Core.bundle.get("erekir-expansion-buttonIcons" + ".name"));
      addCloseButton();
      
      cont.add("Button icons (requires restart)").color(Pal.accent).padBottom(8f).row();
          
      cont.pane(Styles.defaultPane, t -> {
         //row indice
         int[] r = {0}; 
         Icon.icons.each((name, icon) -> {
            if (!Core.settings.getBool("erekir-expansion-displaySmall") && name.contains("small")) return;
            
            t.button(new TextureRegionDrawable(icon), Styles.cleari, () -> {
               Core.settings.put("erekir-expansion-buttonIcon", name);
               ui.showInfo("Changing the button icon to " + name + ".");
            }).size(buttonW, buttonH).margin(4f).pad(2f);
            
            if (++r[0] % rowCount == 0) t.row();
         });
      }).size(buttonW * rowCount + 6f, buttonH * 25f + 6f);
   }
}