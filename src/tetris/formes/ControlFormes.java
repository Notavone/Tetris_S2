package tetris.formes;

import java.util.ArrayList;

import javafx.scene.paint.Color;
import tetris.application.Case;
import tetris.application.Verifications;
import tetris.scenes.GameScene;

public abstract class ControlFormes extends Verifications {

    ArrayList<Case> cases = new ArrayList<>();
    protected int quartTour = 0;

    public void ajouterCases(int x1, int x2, int x3, int x4, int y1, int y2, int y3, int y4, Color couleur) {

        cases.add(new Case(x1, y1, couleur, false));
        cases.add(new Case(x2, y2, couleur, false));
        cases.add(new Case(x3, y3, couleur, false));
        cases.add(new Case(x4, y4, couleur, false));

    }


    public Case getCase(int i) {
        return cases.get(i);
    }

    public void setCase(int i, Case carre) {
        cases.set(i, carre);
    }

    public void ajtQuart() {
        if (quartTour == 3) {
            quartTour = 0;
        } else {
            quartTour++;
        }
    }


    public void bas(GameScene plateau) {
        if (!occupeBas(plateau)) {
            for (int i = 0; i < 4; i++) {
                plateau.enleverCase(plateau.nouvCases().get(0));
                plateau.nouvForme().getCase(i).setEmplacementY(plateau.nouvForme().getCase(i).getY() + 1);
                plateau.ajouterCase(plateau.nouvForme().getCase(i));
            }
        } else {
            plateau.nouvForme().finDescente();
        }

    }

    public void droite(GameScene plateau) {

        if (!occupeDroit(plateau)) {
            for (int i = 0; i < 4; i++) {
                plateau.enleverCase(plateau.nouvCases().get(0));
                plateau.nouvForme().getCase(i).setEmplacementX(plateau.nouvForme().getCase(i).getX() + 1);
                plateau.ajouterCase(plateau.nouvForme().getCase(i));
            }
        }


    }

    public void gauche(GameScene plateau) {

        if (!occupeGauche(plateau)) {
            for (int i = 0; i < 4; i++) {
                plateau.enleverCase(plateau.nouvCases().get(0));
                plateau.nouvForme().getCase(i).setEmplacementX(plateau.nouvForme().getCase(i).getX() - 1);
                plateau.ajouterCase(plateau.nouvForme().getCase(i));
            }
        }

    }

    public void turn(GameScene plateau) {

        ArrayList<Integer> coord = plateau.nouvForme().tourne();

        if (occupeDroit(plateau, coord)) {
            gauche(plateau);
            gauche(plateau);
        }

        if (occupeGauche(plateau, coord)) {
            droite(plateau);
            droite(plateau);
        }

        if (!occupeHaut(plateau) && !verifieTurn(plateau, coord)) {
            for (int i = 0; i < 4; i++) {
                plateau.enleverCase(plateau.nouvCases().get(0));
                plateau.nouvForme().getCase(i).setEmplacementX(plateau.nouvForme().getCase(i).getX() + coord.get(i * 2));
                plateau.nouvForme().getCase(i).setEmplacementY(plateau.nouvForme().getCase(i).getY() + coord.get(i * 2 + 1));
                plateau.ajouterCase(plateau.nouvForme().getCase(i));
            }
            coord.clear();
            ajtQuart();
            plateau.nouvForme().setQuartTour(quartTour);
        }


    }


}
