package mod.maxbogomol.wizards_reborn.api.spell;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Spells {
    public static Map<String, Spell> spells = new HashMap<String, Spell>();
    public static ArrayList<Spell> spellList = new ArrayList<Spell>();

    public static void addSpell(String id, Spell spell) {
        spells.put(id, spell);
        spellList.add(spell);
    }

    public static Spell getSpell(int id) {
        return spells.get(id);
    }

    public static Spell getSpell(String id) {
        return spells.get(id);
    }

    public static void register(Spell spell) {
        spells.put(spell.getId(), spell);
        spellList.add(spell);
    }

    public static int size() {
        return spells.size();
    }

    public static ArrayList<Spell> getSpells() {
        return spellList;
    }
}
