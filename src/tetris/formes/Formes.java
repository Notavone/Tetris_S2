package tetris.formes;

import javafx.scene.paint.Color;
import tetris.application.Case;
import tetris.scenes.GameScene;

import java.util.ArrayList;

public abstract class Formes {
    private final GameScene plateau;
    ArrayList<Case> cases = new ArrayList<>();
    protected int quartTour = 0;

    public Formes(GameScene plateau) {
        this.plateau = plateau;
    }

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

        if (occupeDroit(coord, plateau)) {
            gauche(plateau);
            gauche(plateau);
        }

        if (occupeGauche(coord, plateau)) {
            droite(plateau);
            droite(plateau);
        }

        if (!occupeHaut(plateau) && !verifieTurn(coord, plateau)) {
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

    public boolean occupeGauche(GameScene plateau) {
        for (int i = 0; i < 4; i++) {
            if (plateau.nouvForme().getCase(i).getX() - 1 < 0) {
                return true;
            }
            for (Case x : plateau.occupe()) {
                if (plateau.nouvForme().getCase(i).getY() == x.getY() && plateau.nouvForme().getCase(i).getX() - 1 == x.getX()) {
                    return true;
                }
            }
        }
        return false;
    }


    public boolean occupeDroit(GameScene plateau) {

        for (int i = 0; i < 4; i++) {
            if (plateau.nouvForme().getCase(i).getX() + 1 > GameScene.COLONNES - 1) {
                return true;
            }
            for (Case x : plateau.occupe()) {
                if (plateau.nouvForme().getCase(i).getY() == x.getY() && plateau.nouvForme().getCase(i).getX() + 1 == x.getX()) {
                    return true;
                }
            }
        }

        return false;
    }


    public boolean occupeBas(GameScene plateau) {

        for (int i = 0; i < 4; i++) {
            if (plateau.nouvForme().getCase(i).getY() + 1 > GameScene.LIGNES - 1) {
                return true;
            }
            for (Case x : plateau.occupe()) {
                if (plateau.nouvForme().getCase(i).getY() + 1 == x.getY() && plateau.nouvForme().getCase(i).getX() == x.getX()) {
                    return true;
                }
            }
        }

        return false;
    }

    public boolean occupeHaut(GameScene plateau) {

        for (int i = 0; i < 4; i++) {
            if (plateau.nouvForme().getCase(i).getY() - 2 < 0) {
                return true;
            }
        }

        return false;
    }

    public boolean verifieTurn(ArrayList<Integer> list, GameScene plateau) {

        for (int i = 0; i < 4; i++) {
            for (Case x : plateau.occupe()) {
                if (plateau.nouvForme().getCase(i).getY() + list.get(i * 2 + 1) == x.getY() && plateau.nouvForme().getCase(i).getX() + list.get(i * 2) == x.getX()) {
                    return true;
                }
            }
        }

        return false;
    }

    public boolean occupeDroit(ArrayList<Integer> coord, GameScene plateau) {

        for (int i = 0; i < 4; i++) {
            if (plateau.nouvForme().getCase(i).getX() + coord.get(i * 2) > GameScene.COLONNES - 1) {
                return true;
            }
        }

        return false;
    }

    public boolean occupeGauche(ArrayList<Integer> coord, GameScene plateau) {

        for (int i = 0; i < 4; i++) {
            if (plateau.nouvForme().getCase(i).getX() + coord.get(i * 2 + 1) < 0) {
                return true;
            }
        }

        return false;
    }


    int quart = 0;
    boolean enBas;

    public static Color[] couleurs = {
            Color.rgb(248, 121, 41),
            Color.rgb(11, 165, 223),
            Color.rgb(192, 58, 180),
            Color.rgb(135, 212, 47),
            Color.rgb(215, 23, 53),
            Color.rgb(44, 87, 220),
            Color.rgb(251, 187, 49)
    };


    public void createFormes(GameScene plateau) {

        enBas = false;

        if (plateau.occupe().isEmpty()) {
            int forme = (int) (Math.random() * (7));

            switch (forme) {
                case 0 -> plateau.ajouterForm(new Barre(plateau));
                case 1 -> plateau.ajouterForm(new Carre(plateau));
                case 2 -> plateau.ajouterForm(new T(plateau));
                case 3 -> plateau.ajouterForm(new Z(plateau));
                case 4 -> plateau.ajouterForm(new S(plateau));
                case 5 -> plateau.ajouterForm(new Ldroit(plateau));
                case 6 -> plateau.ajouterForm(new Lretourne(plateau));
            }
        } else {
            plateau.ajouterForm(plateau.getNextForm());
        }
    }

    public abstract ArrayList<Integer> tourne();

    public void setQuartTour(int i) {
        quart = i;
    }

    public void finDescente() {
        enBas = true;
    }

    public void remet() {
        enBas = false;
    }

    public boolean getEnBas() {
        return this.enBas;
    }


    public Color randomCouleur() {
        return couleurs[(int) Math.floor(Math.random() * couleurs.length)];
    }
}
