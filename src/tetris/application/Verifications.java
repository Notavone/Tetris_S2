package tetris.application;

import tetris.scenes.GameScene;

import java.util.ArrayList;

public class Verifications {

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

    public boolean verifieTurn(GameScene plateau, ArrayList<Integer> list) {

        for (int i = 0; i < 4; i++) {
            for (Case x : plateau.occupe()) {
                if (plateau.nouvForme().getCase(i).getY() + list.get(i * 2 + 1) == x.getY() && plateau.nouvForme().getCase(i).getX() + list.get(i * 2) == x.getX()) {
                    return true;
                }
            }
        }

        return false;
    }

    public boolean occupeDroit(GameScene plateau, ArrayList<Integer> coord) {

        for (int i = 0; i < 4; i++) {
            if (plateau.nouvForme().getCase(i).getX() + coord.get(i * 2) > GameScene.COLONNES - 1) {
                return true;
            }
        }

        return false;

    }

    public boolean occupeGauche(GameScene plateau, ArrayList<Integer> coord) {

        for (int i = 0; i < 4; i++) {
            if (plateau.nouvForme().getCase(i).getX() + coord.get(i * 2 + 1) < 0) {
                return true;
            }
        }

        return false;

    }


}
