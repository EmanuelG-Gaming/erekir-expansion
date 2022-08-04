package erekir.ui.dialogs;

import arc.Core;
import arc.util.*;
import arc.graphics.*;
import arc.struct.*;
import arc.scene.*;
import arc.scene.ui.*; 
import arc.scene.ui.layout.*;
import arc.scene.style.*;
import mindustry.ui.*;
import mindustry.ui.dialogs.*;
import mindustry.gen.*;

import static mindustry.Vars.*;

/** Copies a sequence of a server address. */
public class CopyServersDialog extends BaseDialog{
   private Seq<CopyServer> servers = new Seq<CopyServer>();
   
   public CopyServersDialog() {
       super("@erekir-expansion-servers");
       
       addCloseButton();
       
       servers = Seq.with(new CopyServer("Confined Erekir server", "It is a test.\nMay not be always online.", "[200:3c6:8ef8:4614:f271:34dd:637a:c5e0]"));
       
       cont.pane(Styles.defaultPane, p -> {
           servers.each(s -> {
              p.table(Tex.button, t -> {
                 t.button(Icon.book, () -> {
                    Core.app.setClipboardText(s.address);
                    if (s.getNet()) {
                       ui.showInfo("Copied, although it uses Yggdrasil for that.")
                    }
                    else ui.showInfo("@copied");
                 }).size(80f).left();

                 t.table(Styles.none, t2 -> {
                    addStat(t2, "Name: ", s.name);
                    addStat(t2, "Description: ", s.description);
                    if (s.details != null) {
                       addStat(t2, "Details: ", s.details);
                    }
                    //no
                 }).padLeft(12f).expandX().left();
              }).pad(16f).fillX().row();
          });
      }).size(600f, servers.size * (120f + 16f));
   }
   
   public void addStat(Table table, String name, String value) {
       String val = value.trim();
       table.add(name).right();
       table.add(val == "" ? "<none>" : val).color(Color.gray).left().row();
   }
   
   public class CopyServer{
      /** Never null. */
      public String name, description, address;
      
      /** Server's details. Optional. */
      public @Nullable String details;
      
      /** Whether or not this server is hosted on the network. */
      private boolean yggdrasil;
      
      public CopyServer(String name, String description, String address) {
         this.name = name;
         this.description = description;
         this.address = address;
      }
      
      public CopyServer(String name, String description, String address, String details) {
         this.name = name;
         this.description = description;
         this.address = address;
         this.details = details;
      }
      
      /** Checks if the address contains the square brackets
       *  and returns this state. */
      public boolean getNet() {
         yggdrasil = address.contains("[]");
         return yggdrasil;
      }
   }
}