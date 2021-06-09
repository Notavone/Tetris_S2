package tetris.scenes;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import tetris.Model;
import java.util.Objects;

public class HomeScene extends VBox {
    private final Text userDisplay;
    private final TextField userNameInput;
    private final HBox modificationPanel;
    private final VBox scorePanel;
    private final Button changeUserButton;
    private final Button confirmUserChangeButton;
    private final Button playButton;
    private final Button exitButton;
    private final Slider volumeSlider;

    public HomeScene(Model model) {
        this.getStylesheets().add(Objects.requireNonNull(getClass().getResource("stylesheet.css")).toExternalForm());

        this.userDisplay = new Text(model.getPlayer());
        this.scorePanel = new VBox();
        this.changeUserButton = new Button("Modifier");
        this.confirmUserChangeButton = new Button("Valider");

        this.volumeSlider = new Slider(0, 100, 50);
        volumeSlider.setBlockIncrement(5);
        volumeSlider.setMinorTickCount(2);
        volumeSlider.setMajorTickUnit(20);
        volumeSlider.setShowTickMarks(true);

        this.userNameInput = new TextField();
        userNameInput.setPromptText("Nouvel utilisateur");

        this.playButton = new Button("Jouer");
        playButton.getStyleClass().add("generalButton");

        this.exitButton = new Button("QUITTER");
        exitButton.getStyleClass().add("generalButton");

        this.modificationPanel = new HBox(userNameInput, confirmUserChangeButton);
        modificationPanel.setSpacing(10);
        modificationPanel.getStyleClass().add("spacer");
        modificationPanel.setAlignment(Pos.CENTER);
        modificationPanel.setVisible(false);

        ImageView userImage = new ImageView(new Image("file:icon/user.png"));
        HBox userPanel = new HBox(userImage, new Text("Utilisateur : "), userDisplay, changeUserButton);
        userPanel.setSpacing(10);
        userPanel.getStyleClass().add("spacer");
        userPanel.setAlignment(Pos.CENTER);

        HBox userPane = new HBox(userPanel, modificationPanel);
        scorePanel.getStyleClass().add("vBoxScore");

        HBox volumePanel = new HBox(new Text("Volume :"), volumeSlider);

        VBox vBoxBoutons = new VBox(playButton, exitButton, volumePanel);
        vBoxBoutons.setAlignment(Pos.CENTER);

        this.getChildren().addAll(userPane, scorePanel, vBoxBoutons);
    }

    public Text getUserDisplay() {
        return userDisplay;
    }

    public TextField getUserNameInput() {
        return userNameInput;
    }

    public HBox getModificationPanel() {
        return modificationPanel;
    }

    public VBox getScorePanel() {
        return scorePanel;
    }

    public Button getChangeUserButton() {
        return changeUserButton;
    }

    public Button getConfirmUserChangeButton() {
        return confirmUserChangeButton;
    }

    public Button getPlayButton() {
        return playButton;
    }

    public Slider getVolumeSlider() {
        return volumeSlider;
    }

    public Button getExitButton() {
        return exitButton;
    }
}
