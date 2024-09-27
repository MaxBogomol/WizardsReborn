package mod.maxbogomol.wizards_reborn.api.alchemy;

public class SteamUtil {

    public static int getAddSteamRemain(int currentSteam, int steam, int maxSteam) {
        int steamRemain = 0;
        if (maxSteam < (currentSteam + steam)) {
            steamRemain = (currentSteam + steam) - maxSteam;
        }
        return steamRemain;
    }

    public static int getRemoveSteamRemain(int currentSteam, int steam) {
        int steamRemain = 0;
        if (0 > (currentSteam - steam)) {
            steamRemain = -(currentSteam - steam);
        }
        return steamRemain;
    }

    public static boolean canAddSteam(int currentSteam, int steam, int maxSteam) {
        return (maxSteam >= (currentSteam + steam));
    }

    public static boolean canRemoveSteam(int currentSteam, int steam) {
        return (0 <= (currentSteam - steam));
    }
}