package game;

public class GamePlayer {
    private char playerSing;
    private boolean realPlayer = true;

    public GamePlayer(boolean isRealPlayer, char playerSing){
        this.realPlayer = isRealPlayer;
        this.playerSing = playerSing;
    }

    public boolean isRealPlayer(){return realPlayer;}

    public char getPlayerSing(){return playerSing;}
}
