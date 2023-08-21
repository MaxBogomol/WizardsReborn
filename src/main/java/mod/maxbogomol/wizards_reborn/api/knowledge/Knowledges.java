package mod.maxbogomol.wizards_reborn.api.knowledge;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Knowledges {
    public static Map<String, Knowledge> knowledges = new HashMap<String, Knowledge>();
    public static ArrayList<Knowledge> knowledgeList = new ArrayList<Knowledge>();

    public static void addSpell(String id, Knowledge spell) {
        knowledges.put(id, spell);
        knowledgeList.add(spell);
    }

    public static Knowledge getSpell(int id) {
        return knowledges.get(id);
    }

    public static Knowledge getSpell(String id) {
        return knowledges.get(id);
    }

    public static void register(Knowledge spell) {
        knowledges.put(spell.getId(), spell);
        knowledgeList.add(spell);
    }

    public static int size() {
        return knowledges.size();
    }

    public static ArrayList<Knowledge> getSpells() {
        return knowledgeList;
    }
}
