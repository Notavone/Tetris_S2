package tetris;

import tetris.save.FileSystem;
import tetris.save.Parser;
import tetris.save.Save;

import java.util.List;

public class Model {
    private static final String DEFAULT_PLAYER = "?";
    private final List<Save> saves;
    private String player;

    public Model() {
        this.saves = Parser.parse(FileSystem.load());
        this.player = DEFAULT_PLAYER;
    }

    public List<Save> getSaves() {
        return saves;
    }

    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    public List<Save> getPlayerSaves() {
        return Save.filterByUserName(saves, player);
    }
}
