package erekir.ui;

import arc.Core;
import arc.scene.*;
import arc.scene.ui.*;
import mindustry.ui.*;
import mindustry.ui.dialogs.*;
import mindustry.ui.dialogs.SettingsMenuDialog.*;
import mindustry.ui.dialogs.SettingsMenuDialog.SettingsTable.*; 
import erekir.ui.dialogs.*;

import static mindustry.Vars.*;

public class ErekirSettings{
   private static ButtonIconsDialog buttonDialog;
   private static CopyServersDialog serverDialog;

   public static void load() {
      buttonDialog = new ButtonIconsDialog();
      serverDialog = new CopyServersDialog();
      
      ui.settings.addCategory("Erekir expansion", "erekir-expansion-gem-full", t -> {
          t.pref(new ButtonSetting("erekir-expansion-buttonIcons", buttonDialog));
          t.pref(new ButtonSetting("erekir-expansion-servers", serverDialog));
          
          //also display small images
          t.checkPref(
             "displaySmall", Core.settings.getBool("erekir-expansion-displaySmall"),
             bool -> Core.settings.put("erekir-expansion-displaySmall", bool)
          );
      });
   }
   
   //shamelessly stolen from testing utils
   static class ButtonSetting extends Setting{
      private BaseDialog dialog;
      
      public ButtonSetting(String name, BaseDialog dial) {
         super(name);
         title = "setting." + name + ".name";
         dialog = dial;
      }
      
      @Override
      public void add(SettingsTable table) {
          TextButton b = table.button(Core.bundle.get(title), dialog::show).size(280f, 60f).get();
          table.row();
          
          addDesc(b);
      }
   }
}