package mod.maxbogomol.wizards_reborn.api.alchemy;

public class SteamUtils {
    public static int getAddSteamRemain(int current_steam, int steam, int max_steam) {
        int steam_remain = 0;
        if (max_steam < (current_steam + steam)) {
            steam_remain = (current_steam + steam) - max_steam;
        }
        return steam_remain;
    }

    public static int getRemoveSteamRemain(int current_steam, int steam) {
        int steam_remain = 0;
        if (0 > (current_steam - steam)) {
            steam_remain = -(current_steam - steam);
        }
        return steam_remain;
    }

    public static boolean canAddSteam(int current_steam, int steam, int max_steam) {
        return (max_steam >= (current_steam + steam));
    }

    public static boolean canRemoveSteam(int current_steam, int steam) {
        return (0 <= (current_steam - steam));
    }
}