package mod.maxbogomol.wizards_reborn.api.alchemy;

import net.minecraft.core.Direction;

public interface ISteamTileEntity {
    int getSteam();
    int getMaxSteam();

    void setSteam(int steam);
    void addSteam(int steam);
    void removeSteam(int steam);

    boolean canSteamTransfer(Direction side);
    boolean canSteamConnection(Direction side);
}
