package erekir.ui.button;

import arc.Core.*;
import arc.util.*;
import arc.math.*;
import arc.math.geom.*;
import mindustry.ui.*;
import mindustry.gen.*;

import static mindustry.Vars.*;

public class Pickup{
   private static int buttonW = 40;
   private static int buttonH = 40;
   private static final Runnable run = new Runnable();
   
   public static void createPickupButton(Posc pos, Drawable icon, Runnable r) {
       Table table = new Table(Styles.cleari).margin(4f);
       run = r;
       table.update(() -> {
           if (state.isMenu() || pos == null) table.remove();
           Vec2 v = Core.camera.project(pos.x, pos.y);
           table.setPosition(v.x, v.y, Align.center);
           
           Unit plr = player.unit();
           if (plr != null) {
              float d = plr.dst(pos);
              table.actions(Actions.alpha(1f - Mathf.clamp(d / 16f - 1.5f)));
              if (plr.within(pos.x, pos.y, 20f)) {
                 table.touchable = Touchable.enabled;
              }
              else table.touchable = Touchable.disabled;
           }
        });
        table.button(icon, run).size(buttonW, buttonH).margin(4f).pad(4f);
        table.pack();
        table.act(0);
        
        //make sure it's at the back
        Core.scene.root.addChildAt(0, table);

        table.getChildren().first().act(0);
   }
   
   public static void createPickupButton(Posc pos, Runnable r) {
       createPickupButton(pos, Icon.download, r);
   }
}