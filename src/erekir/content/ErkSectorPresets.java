package erekir.content;

import mindustry.type.*;
import erekir.ctype.*;

import static mindustry.content.Planets.*;

public class ErkSectorPresets implements AltContentList{
    public static SectorPreset
       alternateApproach, deadEnd;

    @Override
    public void load() {
       alternateApproach = new SectorPreset("alternate-approach", erekir, 36){{
          captureWave = 19;
          difficulty = 3;
       }};
       
       deadEnd = new SectorPreset("dead-end", erekir, 78){{
          captureWave = 20;
          difficulty = 4;
       }};
    }
}