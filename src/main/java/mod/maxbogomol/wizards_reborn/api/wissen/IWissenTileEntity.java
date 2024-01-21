package mod.maxbogomol.wizards_reborn.api.wissen;

public interface IWissenTileEntity {
    int getWissen();
    int getMaxWissen();

    boolean canSendWissen();
    boolean canReceiveWissen();

    boolean canConnectSendWissen();
    boolean canConnectReceiveWissen();

    void setWissen(int wissen);
    void addWissen(int wissen);
    void removeWissen(int wissen);
}
