package tetris;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import tetris.application.Jeu;
import tetris.handlers.UserModificationEventHandler;
import tetris.scenes.GameScene;
import tetris.scenes.HomeScene;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;


public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        Model model = new Model();
        stage.getIcons().add(new Image("file:icon/tetris.png"));
        stage.setTitle("Tetris");

        HomeScene homeScene = new HomeScene(model);
        Scene scene = new Scene(homeScene);
        Audio audio = new Audio("projetTUT.wav");

        homeScene.getExitButton().setOnAction(ignored -> stage.close());
        homeScene.getPlayButton().setOnAction(ignored -> {
            audio.setLoopPoint(Audio.GAME_LOOP);
            GameScene gameScene = new GameScene(model);
            scene.setRoot(gameScene);
            new Jeu(gameScene, scene);
        });
        homeScene.getChangeUserButton().setOnAction(ignored -> homeScene.getModificationPanel().setVisible(true));
        homeScene.getConfirmUserChangeButton().setOnAction(new UserModificationEventHandler(model, stage, homeScene));
        homeScene.getVolumeSlider().valueProperty().addListener((observableValue, number, t1) -> audio.setVolume((Double) number));

        stage.setScene(scene);
        stage.show();
    }
}