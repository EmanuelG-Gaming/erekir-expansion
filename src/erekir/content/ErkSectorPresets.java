package erekir.content;

import mindustry.type.*;
import erekir.ctype.*;

import static mindustry.content.Planets.*;

public class ErkSectorPresets implements AltContentList{
    public static SectorPreset alternateApproach;

    @Override
    public void load() {
       alternateApproach = new SectorPreset("alternate-approach", erekir, 36){{
          captureWave = 19;
          difficulty = 3;
       }};
    }
}