package splendor.client;

public class NewSession {
    public String creator;
    public String game;
    public String savegame;

    public NewSession(String creator, String game, String savegame) {
        this.creator = creator;
        this.game = game;
        this.savegame = savegame;
    }
}