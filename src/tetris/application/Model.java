package tetris.application;

import tetris.save.FileSystem;
import tetris.save.Parser;
import tetris.save.Save;

import java.text.SimpleDateFormat;
import java.util.Date;
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

    public Save getSave() {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
        return new Save(player, 0, dateFormat.format(date), timeFormat.format(date));
    }
}
