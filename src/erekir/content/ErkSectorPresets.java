package erekir.content;

import mindustry.type.*;
import erekir.ctype.*;

import static mindustry.content.Planets.*;

public class ErkSectorPresets implements AltContentList{
    public static SectorPreset alternativeApproach;

    @Override
    public void load() {
       alternativeApproach = new SectorPreset("alternative-approach", erekir, 36){{
          captureWave = 19;
          difficulty = 3;
       }};
    }
}