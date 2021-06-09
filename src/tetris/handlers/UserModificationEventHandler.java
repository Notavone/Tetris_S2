package tetris.handlers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import tetris.application.Model;
import tetris.save.Save;
import tetris.scenes.HomeScene;

import java.util.Collections;
import java.util.List;

public class UserModificationEventHandler implements EventHandler<ActionEvent> {
    private final Stage stage;
    private final Model model;
    private final TextField userInput;
    private final Text userDisplayText;
    private final HBox modificationBox;
    private final VBox scoreBox;

    public UserModificationEventHandler(Model model, Stage stage, HomeScene scene) {
        this.model = model;
        this.stage = stage;
        this.userInput = scene.getUserNameInput();
        this.userDisplayText = scene.getUserDisplay();
        this.modificationBox = scene.getModificationPanel();
        this.scoreBox = scene.getScorePanel();
    }

    @Override
    public void handle(ActionEvent event) {
        String user = userInput.getText();
        userDisplayText.setText(user);
        model.setPlayer(user);
        modificationBox.setVisible(false);

        scoreBox.getChildren().clear();

        Text textBestScores = new Text("Meilleurs Scores");
        textBestScores.getStyleClass().addAll("bold", "big");
        scoreBox.getChildren().add(textBestScores);
        List<Save> saves = model.getPlayerSaves();
        saves.sort(Collections.reverseOrder());
        for (int i = 0; i < Math.min(saves.size(), Save.MAX_NUMBER_OF_SAVE); i++) {
            Save save = saves.get(i);

            Text scoreText = new Text(save.getScore());
            scoreText.getStyleClass().add("bold");
            Text dateText = new Text(save.getDate());
            dateText.getStyleClass().add("bold");
            Text heureText = new Text(save.getHeure());
            heureText.getStyleClass().add("bold");

            HBox scoreBox = new HBox(new Text((i + 1) + ". "), scoreText, new Text(" le "), dateText, new Text(" à "), heureText);
            scoreBox.getStyleClass().add("tScores");
            this.scoreBox.getChildren().add(scoreBox);
        }

        if (saves.size() == 0) {
            scoreBox.getChildren().add(new HBox(new Text("Aucune sauvegarde, jouez une partie pour en créer une !")));
        }

        stage.sizeToScene();
    }
}
