package Application;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;


public class Main extends Application {

// plateau: 	

    public static Case[][] plateauTab = new Case[10][18];
    public static boolean droite = false;
    public static boolean gauche = false;
    public static boolean bas = false;
    public static boolean haut = false;

//	pour les formes: 

    public static formes[] formes = new formes[1];
    public static ArrayList<Case> stockCase = new ArrayList<>();
    public static ArrayList<formes> anciennes = new ArrayList<>();


    @Override
    public void start(Stage primaryStage) {
        try {
            VBox root = new VBox();
            Scene scene = new Scene(root, 1000, 1000);

//			fond:
            scene.setFill(Color.CADETBLUE);

//			titre:
            Text texte1 = new Text("TETRIS");
            root.getChildren().add(texte1);

            TextField pseudo = new TextField();
            pseudo.setMinWidth(120);
            pseudo.setTranslateX(200);

            Button bouton = new Button();
            bouton.setOnAction(e -> System.out.println(pseudo.getText()));

            Image perso = new Image("File:pseudo.gif", 500, 500, true, true);
            ImageView iv = new ImageView(perso);
            iv.setTranslateX(250);
            iv.setTranslateY(-500);
            iv.setVisible(false);

            Plateau grid = new Plateau(scene, 10, 18);

            root.getChildren().addAll(grid.getName(), iv);

            creation(scene, grid);

//			actions clavier:

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
                        haut = true;
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

                    case UP:
                        haut = false;

                    default:
                        break;
                }
            });


//			jeu : 

            Timeline normal = new Timeline(new KeyFrame(Duration.millis(800), arg0 -> jeu(scene, grid)));
            normal.setCycleCount(Timeline.INDEFINITE);


            Timeline accelerate = new Timeline(new KeyFrame(Duration.millis(100), arg0 -> jeu(scene, grid)));
            accelerate.setCycleCount(Timeline.INDEFINITE);


            Timeline go = new Timeline(new KeyFrame(Duration.millis(60), arg0 -> {

                if (droite && formes[0].verifdroite(stockCase)) {
                    formes[0].droite();
                }
                if (gauche && formes[0].verifieGauche(stockCase)) {
                    formes[0].gauche();
                }
                if (haut) {
                    formes[0].turn();
                }

                if (!bas) {
                    normal.play();
                    accelerate.pause();
                } else {
                    normal.pause();
                    accelerate.play();
                }
            }));
            go.setCycleCount(Timeline.INDEFINITE);
            go.play();


            primaryStage.setTitle("Projet Tuteuré S2");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }


    public static void jeu(Scene scene, Plateau grid) {

        if (formes[0].action && formes[0].verifieBas(stockCase)) {
            formes[0].descendre();
        } else {

            anciennes.add(formes[0]);

            stockCase.add(formes[0].getCase1());
            stockCase.add(formes[0].getCase2());
            stockCase.add(formes[0].getCase3());
            stockCase.add(formes[0].getCase4());

            ligne(scene, grid);
            creation(scene, grid);
        }
    }


    public static void ligne(Scene scene, Plateau grid) {
        ArrayList<Integer> testLigne = new ArrayList<>();
        int compteur;
        boolean ligne = false;


//		test:
        for (int i = 1; i <= grid.getLignes(); i++) {
            compteur = 0;
            for (Case x : stockCase) {
                if (i == x.getY()) {
                    compteur++;
                }
            }
            if (compteur == grid.getColonnes()) {
                testLigne.add(i);
                ligne = true;
            }
        }


        if (ligne) {
//		suppression:

            for (int x : testLigne) {

                for (int i = stockCase.size() - 1; i >= 0; i--) {
                    if (x == stockCase.get(i).getY()) {
                        stockCase.get(i).setVisible(false);
                        stockCase.remove(stockCase.get(i));
                        for (formes formes : anciennes) {
                            formes.supprime(stockCase.get(i));
                        }
                    }
                }
            }


//		descente:
            for (formes x : anciennes) {
                if (x.verifieBas(stockCase)) {
                    x.descendre();
                }
                System.out.println("ok");
            }


        }


    }

    public static void creation(Scene scene, Plateau grid) {

//		creation nouvelle forme:

        int form = (int) (Math.random() * (8 - 1) + 1);
        int color = (int) (Math.random() * (7 - 1));

        switch (form) {
            case 1:
                formes[0] = new T(scene, grid);
                break;
            case 2:
                formes[0] = new Carre(scene, grid);
                break;
            case 3:
                formes[0] = new Barre(scene, grid);
                break;
            case 4:
                formes[0] = new Ldroit(scene, grid);
                break;
            case 5:
                formes[0] = new Lretourne(scene, grid);
                break;
            case 6:
                formes[0] = new S(scene, grid);
                break;
            case 7:
                formes[0] = new Z(scene, grid);
                break;
            default:
                break;

        }
    }
}




