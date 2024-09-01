package mod.maxbogomol.wizards_reborn.registry.common;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.monogram.Monogram;
import mod.maxbogomol.wizards_reborn.api.monogram.MonogramRecipe;
import mod.maxbogomol.wizards_reborn.api.monogram.Monograms;

import java.awt.*;

public class WizardsRebornMonograms {
    public static Monogram LUNAM = new Monogram(WizardsReborn.MOD_ID+":lunam", new Color(177, 211, 251));
    public static Monogram VITA = new Monogram(WizardsReborn.MOD_ID+":vita", new Color(245, 77, 127));
    public static Monogram SOLEM = new Monogram(WizardsReborn.MOD_ID+":solem", new Color(245, 251, 123));
    public static Monogram MORS = new Monogram(WizardsReborn.MOD_ID+":mors", new Color(122, 103, 128));
    public static Monogram MIRACULUM = new Monogram(WizardsReborn.MOD_ID+":miraculum", new Color(67, 148, 114));
    public static Monogram TEMPUS = new Monogram(WizardsReborn.MOD_ID+":tempus", new Color(214, 152, 137));
    public static Monogram STATERA = new Monogram(WizardsReborn.MOD_ID+":statera", new Color(85, 132, 227));
    public static Monogram ECLIPSIS = new Monogram(WizardsReborn.MOD_ID+":eclipsis", new Color(229, 249, 179));
    public static Monogram SICCITAS = new Monogram(WizardsReborn.MOD_ID+":siccitas", new Color(139, 223, 133));
    public static Monogram SOLSTITIUM = new Monogram(WizardsReborn.MOD_ID+":solstitium", new Color(228, 188, 67));
    public static Monogram FAMES = new Monogram(WizardsReborn.MOD_ID+":fames", new Color(151, 103, 135));
    public static Monogram RENAISSANCE = new Monogram(WizardsReborn.MOD_ID+":renaissance", new Color(124, 236, 197));
    public static Monogram BELLUM = new Monogram(WizardsReborn.MOD_ID+":bellum", new Color(138, 64, 76));
    public static Monogram LUX = new Monogram(WizardsReborn.MOD_ID+":lux", new Color(215, 235, 192));
    public static Monogram KARA = new Monogram(WizardsReborn.MOD_ID+":kara", new Color(133, 56, 89));
    public static Monogram DEGRADATIO = new Monogram(WizardsReborn.MOD_ID+":degradatio", new Color(134, 130, 93));
    public static Monogram PRAEDICTIONEM = new Monogram(WizardsReborn.MOD_ID+":praedictionem", new Color(255, 142, 94));
    public static Monogram EVOLUTIONIS = new Monogram(WizardsReborn.MOD_ID+":evolutionis", new Color(208, 132, 214));
    public static Monogram TENEBRIS = new Monogram(WizardsReborn.MOD_ID+":tenebris", new Color(62, 77, 111));
    public static Monogram UNIVERSUM = new Monogram(WizardsReborn.MOD_ID+":universum", new Color(120, 14, 212));

    public static void register() {
        Monograms.register(LUNAM);
        Monograms.register(VITA);
        Monograms.register(SOLEM);
        Monograms.register(MORS);
        Monograms.register(MIRACULUM);
        Monograms.register(TEMPUS);
        Monograms.register(STATERA);
        Monograms.register(ECLIPSIS);
        Monograms.register(SICCITAS);
        Monograms.register(SOLSTITIUM);
        Monograms.register(FAMES);
        Monograms.register(RENAISSANCE);
        Monograms.register(BELLUM);
        Monograms.register(LUX);
        Monograms.register(KARA);
        Monograms.register(DEGRADATIO);
        Monograms.register(PRAEDICTIONEM);
        Monograms.register(EVOLUTIONIS);
        Monograms.register(TENEBRIS);
        Monograms.register(UNIVERSUM);

        Monograms.addRecipe(new MonogramRecipe(MIRACULUM, LUNAM, VITA));
        Monograms.addRecipe(new MonogramRecipe(TEMPUS, LUNAM, SOLEM));
        Monograms.addRecipe(new MonogramRecipe(STATERA, MORS, VITA));
        Monograms.addRecipe(new MonogramRecipe(ECLIPSIS, MORS, LUNAM));
        Monograms.addRecipe(new MonogramRecipe(SICCITAS, SOLEM, MORS));
        Monograms.addRecipe(new MonogramRecipe(SOLSTITIUM, SOLEM, VITA));
        Monograms.addRecipe(new MonogramRecipe(FAMES, TEMPUS, SICCITAS));
        Monograms.addRecipe(new MonogramRecipe(RENAISSANCE, TEMPUS, MORS));
        Monograms.addRecipe(new MonogramRecipe(BELLUM, SOLSTITIUM, MORS));
        Monograms.addRecipe(new MonogramRecipe(LUX, VITA, SOLSTITIUM));
        Monograms.addRecipe(new MonogramRecipe(KARA, SICCITAS, STATERA));
        Monograms.addRecipe(new MonogramRecipe(DEGRADATIO, VITA, ECLIPSIS));
        Monograms.addRecipe(new MonogramRecipe(PRAEDICTIONEM, SOLSTITIUM, MIRACULUM));
        Monograms.addRecipe(new MonogramRecipe(EVOLUTIONIS, VITA, STATERA));
        Monograms.addRecipe(new MonogramRecipe(TENEBRIS, SOLEM, ECLIPSIS));
        Monograms.addRecipe(new MonogramRecipe(UNIVERSUM, STATERA, TEMPUS));
    }
}
