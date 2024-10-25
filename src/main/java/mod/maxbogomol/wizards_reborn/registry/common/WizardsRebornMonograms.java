package mod.maxbogomol.wizards_reborn.registry.common;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.monogram.Monogram;
import mod.maxbogomol.wizards_reborn.api.monogram.MonogramRecipe;
import mod.maxbogomol.wizards_reborn.api.monogram.MonogramHandler;

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
        MonogramHandler.register(LUNAM);
        MonogramHandler.register(VITA);
        MonogramHandler.register(SOLEM);
        MonogramHandler.register(MORS);
        MonogramHandler.register(MIRACULUM);
        MonogramHandler.register(TEMPUS);
        MonogramHandler.register(STATERA);
        MonogramHandler.register(ECLIPSIS);
        MonogramHandler.register(SICCITAS);
        MonogramHandler.register(SOLSTITIUM);
        MonogramHandler.register(FAMES);
        MonogramHandler.register(RENAISSANCE);
        MonogramHandler.register(BELLUM);
        MonogramHandler.register(LUX);
        MonogramHandler.register(KARA);
        MonogramHandler.register(DEGRADATIO);
        MonogramHandler.register(PRAEDICTIONEM);
        MonogramHandler.register(EVOLUTIONIS);
        MonogramHandler.register(TENEBRIS);
        MonogramHandler.register(UNIVERSUM);

        MonogramHandler.addRecipe(new MonogramRecipe(MIRACULUM, LUNAM, VITA));
        MonogramHandler.addRecipe(new MonogramRecipe(TEMPUS, LUNAM, SOLEM));
        MonogramHandler.addRecipe(new MonogramRecipe(STATERA, MORS, VITA));
        MonogramHandler.addRecipe(new MonogramRecipe(ECLIPSIS, MORS, LUNAM));
        MonogramHandler.addRecipe(new MonogramRecipe(SICCITAS, SOLEM, MORS));
        MonogramHandler.addRecipe(new MonogramRecipe(SOLSTITIUM, SOLEM, VITA));
        MonogramHandler.addRecipe(new MonogramRecipe(FAMES, TEMPUS, SICCITAS));
        MonogramHandler.addRecipe(new MonogramRecipe(RENAISSANCE, TEMPUS, MORS));
        MonogramHandler.addRecipe(new MonogramRecipe(BELLUM, SOLSTITIUM, MORS));
        MonogramHandler.addRecipe(new MonogramRecipe(LUX, VITA, SOLSTITIUM));
        MonogramHandler.addRecipe(new MonogramRecipe(KARA, SICCITAS, STATERA));
        MonogramHandler.addRecipe(new MonogramRecipe(DEGRADATIO, VITA, ECLIPSIS));
        MonogramHandler.addRecipe(new MonogramRecipe(PRAEDICTIONEM, SOLSTITIUM, MIRACULUM));
        MonogramHandler.addRecipe(new MonogramRecipe(EVOLUTIONIS, VITA, STATERA));
        MonogramHandler.addRecipe(new MonogramRecipe(TENEBRIS, SOLEM, ECLIPSIS));
        MonogramHandler.addRecipe(new MonogramRecipe(UNIVERSUM, STATERA, TEMPUS));
    }
}
