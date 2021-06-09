package tetris.application;

import java.util.ArrayList;

import tetris.formes.Formes;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;
import tetris.save.FileSystem;
import tetris.save.Parser;
import tetris.save.Save;
import tetris.scenes.GameScene;

public class Jeu {
    GameScene plateau;
    Boolean perdu = false;
    Scene scene;
    Timeline play;
    Timeline normal;
    Text perduText;
    Timeline perduTest;

    Formes form;

    boolean droite = false;
    boolean gauche = false;
    boolean bas = false;
    boolean isSaved = false;

    Timeline go;

    public Jeu(GameScene plateau, Scene scene) {
        this.plateau = plateau;
        this.scene = scene;
        start();
    }

    public void start() {

        form = new Formes() {

            @Override
            public ArrayList<Integer> tourne() {
                return null;
            }
        };
        perduText = new Text("PERDU !");
        perduText.setFont(Font.font("Verdana", 70));
        perduText.setFill(Color.RED);
        perduText.setTextOrigin(VPos.CENTER);
        perduText.setLayoutX(100);
        perduText.setLayoutY(300);
        perduText.setVisible(false);

        plateau.getChildren().addAll(perduText);

        form.createFormes(plateau);
        commande();
        commandePlay();

        play = new Timeline(new KeyFrame(Duration.millis(10), ignored -> {
            if (plateau.nouvForme().getEnBas()) {
                commandeStop();
                plateau.nouvForme().remet();
                plateau.FinDeDescente();
                plateau.purgeLigne();
                form.createFormes(plateau);
                if (form.occupeBas(plateau)) {
                    perdu = true;
                }
                commandePlay();
            }
        }));
        play.setCycleCount(Timeline.INDEFINITE);
        play.play();

        perduTest = new Timeline(new KeyFrame(Duration.millis(10), ignored -> {
            if (perdu) {
                play.stop();
                go.stop();
            }
        }));
        perduTest.setCycleCount(Timeline.INDEFINITE);
        perduTest.play();

    }

    public void commande() {
        normal = new Timeline(new KeyFrame(Duration.millis(1000), ignored -> {
            if (!perdu) {
                plateau.nouvForme().bas(plateau);
            } else {
                if (!isSaved) {
                    perduText.setVisible(true);
                    Save.persist(plateau.getModel().getSaves(), plateau.getSave());
                    FileSystem.save(Parser.stringify(plateau.getModel().getSaves()));
                    isSaved = true;
                }
            }

        }));
        normal.setCycleCount(Timeline.INDEFINITE);

        Timeline accelere = new Timeline(new KeyFrame(Duration.millis(100), ignored -> {
            if (!perdu) {
                plateau.nouvForme().bas(plateau);
            } else {
                perduText.setVisible(true);
            }
        }));
        accelere.setCycleCount(Timeline.INDEFINITE);

        go = new Timeline(new KeyFrame(Duration.millis(100), ignored -> {

            if (droite) {
                plateau.nouvForme().droite(plateau);
            }
            if (gauche) {
                plateau.nouvForme().gauche(plateau);
            }

            if (!bas) {
                normal.play();
                accelere.pause();
            } else {
                normal.pause();
                accelere.play();
            }


        }));
        go.setCycleCount(Timeline.INDEFINITE);


        scene.setOnKeyPressed(e -> {
            switch (e.getCode()) {
                case LEFT:
                    gauche = true;
                    break;

                case RIGHT:
                    droite = true;
                    break;

                case DOWN:
                    bas = true;
                    break;

                case UP:
                    plateau.nouvForme().turn(plateau);
                    break;

                case SPACE:
                    while (!plateau.nouvForme().getEnBas()) {
                        plateau.nouvForme().bas(plateau);
                    }
                    break;

                default:
                    break;
            }
        });

        scene.setOnKeyReleased(e -> {
            switch (e.getCode()) {
                case LEFT:
                    gauche = false;
                    break;

                case RIGHT:
                    droite = false;
                    break;

                case DOWN:
                    bas = false;
                    break;

                default:
                    break;
            }
        });
    }

    public void commandePlay() {
        go.play();
    }

    public void commandeStop() {
        go.stop();
    }


}
