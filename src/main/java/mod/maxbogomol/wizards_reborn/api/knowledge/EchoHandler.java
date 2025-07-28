package mod.maxbogomol.wizards_reborn.api.knowledge;

import net.minecraft.world.entity.player.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class EchoHandler {
    public static Map<String, Echo> echoes = new HashMap<>();
    public static ArrayList<Echo> echoList = new ArrayList<>();

    public static void addEcho(String id, Echo echo) {
        echoes.put(id, echo);
        echoList.add(echo);
    }

    public static Echo getEcho(int id) {
        return echoes.get(id);
    }

    public static Echo getEcho(String id) {
        return echoes.get(id);
    }

    public static void register(Echo echo) {
        echoes.put(echo.getId(), echo);
        echoList.add(echo);
    }

    public static int size() {
        return echoes.size();
    }

    public static ArrayList<Echo> getEchoes() {
        return echoList;
    }

    public static void tick(Player player) {
        if (player.isAlive()) {
            ArrayList<EchoStack> echoes = new ArrayList<>(KnowledgeUtil.getEchoes(player));
            for (EchoStack echo : echoes) {
                echo.getEcho().tick(player, echo);
            }
        }
    }
}
