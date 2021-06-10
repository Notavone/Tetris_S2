package tetris.scenes;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;
import tetris.application.Audio;
import tetris.application.Model;
import tetris.application.Case;
import tetris.formes.*;
import tetris.save.Save;

import java.util.ArrayList;
import java.util.Objects;

public class GameScene extends VBox {
    private final Save save;
    private final Text scoreField;
    private final Text completedLinesField;
    private final Text multiplicateurField;
    private final Scene scene;
    private final Audio audio;
    private final StackPane stackPane;
    private final Rectangle boardShade;
    private final BorderPane pauseCenter, gameOverCenter;
    private int linesCleared;
    private final ArrayList<Case> nouv = new ArrayList<>();
    private final ArrayList<Formes> formes = new ArrayList<>();
    private final ArrayList<Case> pose = new ArrayList<>();
    private final ArrayList<Case> nextFormList = new ArrayList<>();
    private final GridPane grille;
    private final GridPane previewGrille;
    private final Button menuButton;
    private Timeline play;
    private Formes form;
    private Formes nextForm;

    public static final int COLONNES = 10;
    public static final int LIGNES = 20;
    boolean droite = false;
    boolean gauche = false;
    boolean bas = false;

    Timeline handleDirection;
    int multiplicateur;

    public GameScene(Model model, Scene scene, Slider volumeSlider, Audio audio) {
        this.getStylesheets().add(Objects.requireNonNull(getClass().getResource("stylesheet.css")).toExternalForm());

        this.scene = scene;
        this.grille = new GridPane();
        grille.getStyleClass().add("grille");
        grille.getStyleClass().add("background");

        previewGrille = new GridPane();
        previewGrille.getStyleClass().add("grille");
        for (int i = 0; i < 4; i++) {
            previewGrille.add(new Rectangle(35, 35, Color.TRANSPARENT), i, 0);
        }
        previewGrille.setStyle("-fx-padding: 25 15 10 0;");

        this.audio = audio;
        this.save = model.createSave();
        this.linesCleared = 0;
        this.multiplicateur = 1;

        for (int i = 0; i < COLONNES; i++) {
            for (int j = 0; j < LIGNES; j++) {
                grille.add(new Case(i, j, Color.GRAY, true), i, j);
            }
        }

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
        completedLinesField = new Text("0");
        completedLinesField.getStyleClass().add("score");
        multiplicateurField = new Text("x1");
        multiplicateurField.getStyleClass().add("score");

        Text lblScore = new Text("Score");
        lblScore.getStyleClass().add("lbl");
        Text lblCompletedLines = new Text("Lignes complétées");
        lblCompletedLines.getStyleClass().add("lbl");
        Text lblMultiplicateur = new Text("Multiplicateur");
        lblMultiplicateur.getStyleClass().add("lbl");

        Text lblNextForm = new Text("Pièce suivante");
        lblNextForm.getStyleClass().add("lbl");

        menuButton = new Button("Menu principal");
        menuButton.getStyleClass().add("btnMain");

        VBox topSidePanel = new VBox();
        topSidePanel.getStyleClass().add("background");
        topSidePanel.getChildren().addAll(menuButton, scoreField, lblScore, completedLinesField, lblCompletedLines, multiplicateurField, lblMultiplicateur, previewGrille, lblNextForm);

        BorderPane borderPane = new BorderPane();
        borderPane.getStyleClass().add("background");
        borderPane.setTop(topSidePanel);
        borderPane.setBottom(bottomSidePanel);

        stackPane = new StackPane();
        ImageView pauseImg = new ImageView(new Image("file:icon/pause.png"));
        ImageView gameOverImg = new ImageView(new Image("file:icon/gameover.png"));

        pauseCenter = new BorderPane();
        pauseCenter.setCenter(pauseImg);

        gameOverCenter = new BorderPane();
        gameOverCenter.setCenter(gameOverImg);

        boardShade = new Rectangle(COLONNES * 35 + 2 * (COLONNES - 1), LIGNES * 35 + 2 * (LIGNES - 1));
        boardShade.setOpacity(0.5);
        boardShade.setFill(Color.BLACK);
        boardShade.toFront();

        stackPane.getChildren().addAll(grille);

        HBox main = new HBox();
        main.getStyleClass().add("background");
        main.setPadding(new Insets(5.0));
        main.setSpacing(25.0);

        main.getChildren().addAll(stackPane, borderPane);

        this.getChildren().addAll(main);
        start();
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

        nextFormDefine();
        nextForm(nextForm);
    }

    public void nextForm(Formes form) {
        for (Case x : nextFormList) {
            previewGrille.getChildren().remove(x);
        }
        nextFormList.clear();

        for (int i = 0; i < 4; i++) {
            nextFormList.add(form.getCase(i));
            previewGrille.add(form.getCase(i), form.getCase(i).getX() - 3, form.getCase(i).getY());
        }
    }

    public void nextFormDefine() {
        int forme = (int) (Math.random() * (7));

        switch (forme) {
            case 0 -> nextForm = new Barre(this);
            case 1 -> nextForm = new Carre(this);
            case 2 -> nextForm = new T(this);
            case 3 -> nextForm = new Z(this);
            case 4 -> nextForm = new S(this);
            case 5 -> nextForm = new Ldroit(this);
            case 6 -> nextForm = new Lretourne(this);
        }
    }

    public Formes getNextForm() {
        return nextForm;
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
        completedLinesField.setText(String.valueOf(linesCleared));
        multiplicateurField.setText("x" + multiplicateur);
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
        form = new Formes(this  ) {

            @Override
            public ArrayList<Integer> tourne() {
                return null;
            }
        };

        form.createFormes(this);

        Timeline tickSlow = new Timeline(new KeyFrame(Duration.millis(1000), ignored -> {
            if (!bas) tick();
        }));
        tickSlow.setCycleCount(Timeline.INDEFINITE);
        tickSlow.play();

        Timeline tickFast = new Timeline(new KeyFrame(Duration.millis(100), ignored -> {
            if (bas) tick();
        }));
        tickFast.setCycleCount(Timeline.INDEFINITE);
        tickFast.play();

        handleDirection = new Timeline(new KeyFrame(Duration.millis(100), ignored -> {
            if (droite) this.nouvForme().droite(this);
            if (gauche) this.nouvForme().gauche(this);
        }));
        handleDirection.setCycleCount(Timeline.INDEFINITE);
        handleDirection.play();

        scene.setOnKeyPressed(e -> {
            switch (e.getCode()) {
                case Q -> gauche = true;
                case D -> droite = true;
                case S -> bas = true;
                case Z -> this.nouvForme().turn(this);
                case F -> {
                    while (!this.nouvForme().getEnBas()) {
                        this.nouvForme().bas(this);
                    }
                }
                case ESCAPE -> {
                    if (tickSlow.getStatus() == Animation.Status.RUNNING) {
                        stackPane.getChildren().addAll(boardShade, pauseCenter);
                        audio.stop();
                        tickSlow.pause();
                        handleDirection.pause();
                    } else {
                        stackPane.getChildren().removeAll(boardShade, pauseCenter);
                        audio.play();
                        tickSlow.play();
                        handleDirection.play();
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
                handleDirection.stop();
                this.nouvForme().remet();
                this.FinDeDescente();
                this.purgeLigne();
                form.createFormes(this);
                if (form.occupeBas(this)) {
                    play.stop();
                    handleDirection.stop();
                    tickSlow.stop();
                    tickFast.stop();
                    stackPane.getChildren().addAll(boardShade, gameOverCenter);
                    audio.stop();
                }
                handleDirection.play();
            }
        }));
        play.setCycleCount(Timeline.INDEFINITE);
        play.play();
    }

    public Button getMenuButton() {
        return menuButton;
    }

    private void tick() {
        this.nouvForme().bas(this);
    }
}
