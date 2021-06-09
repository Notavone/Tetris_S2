package tetris.scenes;

import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import tetris.Model;
import tetris.application.Case;
import tetris.formes.Formes;
import tetris.save.Save;

import java.util.ArrayList;

public class GameScene extends VBox {
    private final Model model;
    private final Save save;
    ArrayList<Case> nouv = new ArrayList<>();
    ArrayList<Formes> formes = new ArrayList<>();
    ArrayList<Case> pose = new ArrayList<>();

    GridPane grille;

    public static final int COLONNES = 10;
    public static final int LIGNES = 18;

    public GameScene(Model model) {
        this.model = model;
        this.grille = new GridPane();
        this.save = new Save(model.getPlayer(), 0, "", "");
        grille.setVgap(1);
        grille.setHgap(1);
        grille.setTranslateX(100);
        grille.setTranslateY(100);

        for (int i = 0; i < COLONNES; i++) {
            for (int j = 0; j < LIGNES; j++) {
                grille.add(new Case(i, j, Color.DARKBLUE), i, j);
            }
        }

        this.getChildren().addAll(grille);
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
                if (c.getY() < i) {
                    provisoir2.add(c);
                }
            }
            if (compteur == COLONNES) {
                nblignes++;
                this.enleverLigne(i);
                for (Case c : provisoir) {
                    pose.remove(c);
                }
            }
        }
        for (Case c : pose) {
            grille.getChildren().remove(c);
        }

        for (int i = 0; i < pose.size(); i++) {
            x = pose.get(i).getX();
            y = provisoir2.contains(pose.get(i)) ? pose.get(i).getY() + nblignes : pose.get(i).getY();
            couleur = pose.get(i).getCouleur();
            pose.set(i, new Case(x, y, couleur));
        }

        for (Case c : pose) {
            grille.add(c, c.getX(), c.getY());
        }

        save.incrementScore(nblignes);

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
}
