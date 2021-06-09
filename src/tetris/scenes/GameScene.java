package tetris.scenes;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Slider;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.util.Duration;
import tetris.application.Model;
import tetris.application.Case;
import tetris.formes.Formes;
import tetris.save.FileSystem;
import tetris.save.Parser;
import tetris.save.Save;

import java.util.ArrayList;
import java.util.Objects;

public class GameScene extends VBox {
    private final Model model;
    private final Save save;
    private final Text scoreField;
    private final Text levelField;
    private final Text multiplicateurField;
    private final Scene scene;
    private int linesCleared;
    ArrayList<Case> nouv = new ArrayList<>();
    ArrayList<Formes> formes = new ArrayList<>();
    ArrayList<Case> pose = new ArrayList<>();

    GridPane grille;

    public static final int COLONNES = 10;
    public static final int LIGNES = 20;

    Boolean perdu = false;
    Timeline play;
    Timeline normal;
    Text perduText;

    Formes form;

    boolean droite = false;
    boolean gauche = false;
    boolean bas = false;
    boolean isSaved = false;

    Timeline go;
    int multiplicateur;


    public GameScene(Model model, Scene scene, Slider volumeSlider) {
        this.getStylesheets().add(Objects.requireNonNull(getClass().getResource("stylesheet.css")).toExternalForm());

        this.model = model;
        this.scene = scene;
        this.grille = new GridPane();
        grille.getStyleClass().add("grille");
        grille.getStyleClass().add("background");

        this.save = model.getSave();
        this.linesCleared = 0;
        this.multiplicateur = 1;

        for (int i = 0; i < COLONNES; i++) {
            for (int j = 0; j < LIGNES; j++) {
                grille.add(new Case(i, j, Color.rgb(75, 75, 75), true), i, j);
            }
        }

        perduText = new Text("coucou");

        HBox topPanel = new HBox(new Text("Tetris"));
        topPanel.getStyleClass().add("title");

        VBox bottomSidePanel = new VBox();
        bottomSidePanel.getStyleClass().add("background");
        bottomSidePanel.getStyleClass().add("lbl");

        Text volumeField = new Text("Volume :");
        volumeField.getStyleClass().add("lbl");
        bottomSidePanel.getChildren().addAll(volumeField, volumeSlider);

        scoreField = new Text(save.getScore());
        scoreField.getStyleClass().add("score");
        levelField = new Text("0");
        levelField.getStyleClass().add("score");
        multiplicateurField = new Text("1");
        multiplicateurField.getStyleClass().add("score");

        Text lblScore = new Text("Score");
        lblScore.getStyleClass().add("lbl");
        Text lblLevel = new Text("Lignes complétées");
        lblLevel.getStyleClass().add("lbl");
        Text lblMultiplicateur = new Text("Multiplicateur");
        lblMultiplicateur.getStyleClass().add("lbl");

        VBox topSidePanel = new VBox();
        topSidePanel.getStyleClass().add("background");
        topSidePanel.getChildren().addAll(scoreField, lblScore, levelField, lblLevel, multiplicateurField, lblMultiplicateur);

        BorderPane borderPane = new BorderPane();
        borderPane.getStyleClass().add("background");
        borderPane.setTop(topSidePanel);
        borderPane.setBottom(bottomSidePanel);

        StackPane stackPane = new StackPane();
        stackPane.getChildren().add(grille);

        HBox main = new HBox();
        main.getStyleClass().add("background");
        main.setPadding(new Insets(5.0));
        main.setSpacing(25.0);
        main.getChildren().addAll(stackPane, borderPane);

        this.getChildren().addAll(main);
        start();
    }

    public Model getModel() {
        return model;
    }

    public Save getSave() {
        return save;
    }

    public void ajouterCase(Case ajt) {
        grille.add(ajt, ajt.getX(), ajt.getY());
        nouv.add(ajt);
    }

    public void enleverCase(Case enl) {

        grille.getChildren().remove(enl);
        nouv.remove(enl);
    }

    public void ajouterForm(Formes form) {
        formes.add(form);

        for (int i = 0; i < 4; i++) {
            this.ajouterCase(form.getCase(i));
        }

    }

    public void enleverLigne(int y) {
        for (Case aCase : pose) {
            if (aCase.getY() == y) {
                grille.getChildren().remove(aCase);
            }
        }

    }

    public void FinDeDescente() {

        for (int i = 0; i < 4; i++) {
            pose.add(nouv.get(0));
            this.nouvForme().setCase(i, nouv.get(0));
            enleverCase(nouv.get(0));
        }

        for (int i = 1; i < 5; i++) {
            grille.add(pose.get(pose.size() - i), pose.get(pose.size() - i).getX(), pose.get(pose.size() - i).getY());
        }

    }

    public void purgeLigne() {
        int compteur;
        int nblignes = 0;
        int x;
        int y;
        Color couleur;
        int max = 0;

        ArrayList<Case> provisoir = new ArrayList<>();
        ArrayList<Case> provisoir2 = new ArrayList<>();

        for (int i = 0; i < LIGNES; i++) {
            compteur = 0;
            provisoir.clear();

            for (Case c : this.occupe()) {
                if (c.getY() == i) {
                    compteur++;
                    provisoir.add(c);
                }
            }
            if (compteur == COLONNES) {
                if (i > max) {
                    max = i;
                }
                nblignes++;
                this.enleverLigne(i);
                for (Case c : provisoir) {
                    pose.remove(c);
                }
            }
        }
        for (Case c : this.occupe()) {
            if (c.getY() < max) {
                provisoir2.add(c);
            }
        }

        for (Case c : pose) {
            grille.getChildren().remove(c);
        }

        for (int i = 0; i < pose.size(); i++) {
            x = pose.get(i).getX();
            y = provisoir2.contains(pose.get(i)) ? pose.get(i).getY() + nblignes : pose.get(i).getY();
            couleur = pose.get(i).getCouleur();
            pose.set(i, new Case(x, y, couleur, false));
        }

        for (Case c : pose) {
            grille.add(c, c.getX(), c.getY());
        }

        linesCleared += nblignes;
        multiplicateur = 1 + linesCleared / 10;
        save.incrementScore(nblignes, multiplicateur);
        scoreField.setText(save.getScore());
        levelField.setText(String.valueOf(linesCleared));
    }

    public ArrayList<Case> nouvCases() {
        return nouv;
    }

    public ArrayList<Case> occupe() {
        return pose;
    }

    public Formes nouvForme() {
        return formes.get(formes.size() - 1);
    }

    public void start() {
        form = new Formes() {

            @Override
            public ArrayList<Integer> tourne() {
                return null;
            }
        };

        form.createFormes(this);

        normal = new Timeline(new KeyFrame(Duration.millis(1000), ignored -> {
            if (!perdu) {
                this.nouvForme().bas(this);
            } else {
                normal.stop();
                play.stop();
                go.stop();
                perduText.setVisible(true);
                Save.persist(this.getModel().getSaves(), this.getSave());
                FileSystem.save(Parser.stringify(this.getModel().getSaves()));
                isSaved = true;
            }
        }));
        normal.setCycleCount(Timeline.INDEFINITE);

        Timeline accelere = new Timeline(new KeyFrame(Duration.millis(100), ignored -> {
            if (!perdu) {
                this.nouvForme().bas(this);
            } else {
                perduText.setVisible(true);
            }
        }));
        accelere.setCycleCount(Timeline.INDEFINITE);

        go = new Timeline(new KeyFrame(Duration.millis(100), ignored -> {
            if (droite) this.nouvForme().droite(this);
            if (gauche) this.nouvForme().gauche(this);

            if (!bas) {
                normal.play();
                accelere.pause();
            } else {
                normal.pause();
                accelere.play();
            }
        }));
        go.setCycleCount(Timeline.INDEFINITE);
        go.play();

        scene.setOnKeyPressed(e -> {
            switch (e.getCode()) {
                case Q -> gauche = true;
                case D -> droite = true;
                case S -> bas = true;
                case Z -> this.nouvForme().turn(this);
                case SPACE -> {
                    while (!this.nouvForme().getEnBas()) {
                        this.nouvForme().bas(this);
                    }
                }
            }
        });

        scene.setOnKeyReleased(e -> {
            switch (e.getCode()) {
                case Q -> gauche = false;
                case D -> droite = false;
                case S -> bas = false;
            }
        });

        play = new Timeline(new KeyFrame(Duration.millis(10), ignored -> {
            if (this.nouvForme().getEnBas()) {
                go.stop();
                this.nouvForme().remet();
                this.FinDeDescente();
                this.purgeLigne();
                form.createFormes(this);
                if (form.occupeBas(this)) {
                    perdu = true;
                }
                go.play();
            }
        }));
        play.setCycleCount(Timeline.INDEFINITE);
        play.play();
    }
}
