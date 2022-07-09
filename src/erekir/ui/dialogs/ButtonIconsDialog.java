package erekir.ui.dialogs;

import arc.Core;
import arc.scene.*;
import arc.scene.ui.*; 
import arc.scene.ui.layout.*;
import arc.scene.style.*;
import mindustry.graphics.*;
import mindustry.ui.*;
import mindustry.ui.dialogs.*;
import mindustry.gen.*;

import static mindustry.Vars.*;

public class ButtonIconsDialog extends BaseDialog{
   private static float buttonSize = 48f;
   private static int rowCount = 12;
   private static String nothing = "nothingness";
   
   String selection;
   
   public ButtonIconsDialog() {
      super("Button icons");
      cont.add("Button icons (requires reloading)").color(Pal.accent).padBottom(10f).row();
   
      cont.pane(Styles.defaultPane, t -> {
         //row indice
         int[] r = {0}; 
         Icon.icons.each((name, icon) -> {
            if (Core.settings.getBool("erekir-expansion-displaySmall") == false && name.contains("Small")) return;
            addButton(t, new TextureRegionDrawable(icon), name);
            
            if (++r[0] % rowCount == 0) t.row();
         });
         addButton(t, new TextureRegionDrawable(Core.atlas.find("erekir-expansion-" + nothing)), nothing);
         
      }).size(buttonSize * rowCount + 6f, buttonSize * 6f + 6f).row();
      
      closeOnBack();
      
      buttons.defaults().size(150f, 64f);
      addCloseButton();
      buttons.button("@ok", Icon.ok, () -> {
          if (selection != null) {
             Core.settings.put("erekir-expansion-buttonIcon", selection);
             ui.showInfo(
                selection == nothing
                ? Core.bundle.get("erekir-expansion-nothing")
                : Core.bundle.format("erekir-expansion-changingButton", selection)
             );
          }
          hide();
      });
   }
   
   public void addButton(Table table, Drawable icon, String name) {
      ImageButton button = table.button(icon, Styles.squareTogglei, () -> selection = name).size(buttonSize).margin(4f).pad(2f).checked(b -> selection == name).get();
      table.add(button);
   }
}