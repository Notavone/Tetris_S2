package Application;

import javafx.scene.Scene;
import javafx.scene.layout.GridPane;

public class Plateau {

    protected static int colones;
    protected static int lignes;
    public static Case[][] plateauTab;
    public GridPane plateau;
    public static Scene scene;

    public Plateau(Scene scene, int colones, int lignes) {
        Plateau.colones = colones;
        Plateau.lignes = lignes;
        Plateau.scene = scene;

        plateauTab = new Case[colones][lignes];

        GridPane Plateau = new GridPane();
        Plateau.setTranslateX(100);
        Plateau.setTranslateY(100);


        for (int i = 0; i < colones; i++) {
            for (int j = 0; j < lignes; j++) {
                plateauTab[i][j] = new Case(i, j);
                Plateau.add(plateauTab[i][j], i, j);
                plateauTab[i][j].HProperty().bind(scene.heightProperty().subtract(120 + 50).divide(20));
            }
        }

        this.plateau = Plateau;
    }

    public GridPane getName() {
        return this.plateau;
    }

    public void addCase(Case carre) {
        plateau.add(carre, carre.getX(), carre.getY());
        carre.HProperty().bind(scene.heightProperty().subtract(120 + 50).divide(20));
        carre.setCouleur(carre.getCouleur());
    }

    public Case pos(int x, int y) {
        return plateauTab[x][y];
    }

    public int getColonnes() {
        return colones;
    }

    public int getLignes() {
        return lignes;
    }

    public boolean limDroite(double carre) {
        return (carre < this.getColonnes() - 1);
    }

    public boolean limGauche(double carre) {
        return (carre > 0);
    }

    public boolean limBas(double carre) {
        return (carre < this.getLignes() - 1);
    }

    public boolean limHaut(double carre) {
        return (carre > 0);
    }
}
