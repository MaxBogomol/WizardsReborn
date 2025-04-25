package mod.maxbogomol.wizards_reborn.api.knowledge;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class KnowledgeHandler {
    public static Map<String, Knowledge> knowledges = new HashMap<>();
    public static ArrayList<Knowledge> knowledgeList = new ArrayList<>();

    public static ArrayList<Knowledge> tickKnowledgeList = new ArrayList<>();
    public static ArrayList<Knowledge> itemKnowledgeList = new ArrayList<>();
    public static boolean tickKnowledgeInit = false;
    public static boolean itemKnowledgeInit = false;

    public static void addKnowledge(String id, Knowledge knowledge) {
        knowledges.put(id, knowledge);
        knowledgeList.add(knowledge);
    }

    public static Knowledge getKnowledge(int id) {
        return knowledges.get(id);
    }

    public static Knowledge getKnowledge(String id) {
        return knowledges.get(id);
    }

    public static void register(Knowledge knowledge) {
        knowledges.put(knowledge.getId(), knowledge);
        knowledgeList.add(knowledge);
    }

    public static int size() {
        return knowledges.size();
    }

    public static ArrayList<Knowledge> getKnowledges() {
        return knowledgeList;
    }

    public static void addTickKnowledge(Knowledge knowledge) {
        tickKnowledgeList.add(knowledge);
    }

    public static ArrayList<Knowledge> getTickKnowledgeList() {
        return tickKnowledgeList;
    }

    public static void initTickKnowledgeList() {
        if (!tickKnowledgeInit) {
            for (Knowledge knowledge : getKnowledges()) {
                if (knowledge.getKnowledgeType() == KnowledgeTypes.TICK) {
                    addItemKnowledge(knowledge);
                }
            }
            tickKnowledgeInit = true;
        }
    }

    public static void tickKnowledgeListTrigger(Player player) {
        initTickKnowledgeList();
        ArrayList<Knowledge> knowledges = new ArrayList<>(getTickKnowledgeList());
        Set<Knowledge> set = KnowledgeUtil.getKnowledges(player);
        if (set != null) knowledges.removeAll(set);
        for (Knowledge knowledge : knowledges) {
            if (knowledge instanceof ITickKnowledge iTickKnowledge) {
                if (iTickKnowledge.canReceived(player)) {
                    knowledge.add(player);
                }
            }
        }
    }

    public static void addItemKnowledge(Knowledge knowledge) {
        itemKnowledgeList.add(knowledge);
    }

    public static ArrayList<Knowledge> getItemKnowledgeList() {
        return itemKnowledgeList;
    }

    public static void initItemKnowledgeList() {
        if (!itemKnowledgeInit) {
            for (Knowledge knowledge : getKnowledges()) {
                if (knowledge.getKnowledgeType() == KnowledgeTypes.ITEM) {
                    addItemKnowledge(knowledge);
                }
            }
            itemKnowledgeInit = true;
        }
    }

    public static void itemKnowledgeListTrigger(Player player, ItemStack itemStack) {
        initItemKnowledgeList();
        ArrayList<Knowledge> knowledges = new ArrayList<>(getItemKnowledgeList());
        Set<Knowledge> set = KnowledgeUtil.getKnowledges(player);
        if (set != null) knowledges.removeAll(set);
        for (Knowledge knowledge : knowledges) {
            if (knowledge instanceof IItemKnowledge iItemKnowledge) {
                if (iItemKnowledge.canReceived(player, itemStack)) {
                    knowledge.add(player);
                }
            }
        }
    }
}
