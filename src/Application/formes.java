package Application;

import java.util.ArrayList;

import javafx.scene.Scene;
import javafx.scene.paint.Color;

public abstract class formes {

    protected ArrayList<Case> cases = new ArrayList<>();

    protected double coefx1 = 0;
    protected double coefx2 = 0;
    protected double coefx3 = 0;
    protected double coefx4 = 0;
    protected double coefy1 = 0;
    protected double coefy2 = 0;
    protected double coefy3 = 0;
    protected double coefy4 = 0;

    protected int rotat = 1;

    protected Scene scene;
    protected Plateau plateau;

    protected boolean action = true;
    private static Color[] couleurs = {Color.CYAN, Color.DARKCYAN, Color.LIGHTGREEN, Color.PURPLE, Color.RED, Color.ORANGE, Color.YELLOW, Color.LIGHTGRAY};


    public void initialisation(int x1, int x2, int x3, int x4, int y1, int y2, int y3, int y4) {

        int couleur = (int) (Math.random() * (9 - 1));

        Case case1 = new Case(x1, y1);
        Case case2 = new Case(x2, y2);
        Case case3 = new Case(x3, y3);
        Case case4 = new Case(x4, y4);

        case1.setCouleur(couleurs[couleur]);
        case2.setCouleur(couleurs[couleur]);
        case3.setCouleur(couleurs[couleur]);
        case4.setCouleur(couleurs[couleur]);

        cases.add(case1);
        cases.add(case2);
        cases.add(case3);
        cases.add(case4);

        plateau.addCase(cases.get(0));
        plateau.addCase(cases.get(1));
        plateau.addCase(cases.get(2));
        plateau.addCase(cases.get(3));
    }


    public abstract void turn();

    public void show() {
        for (Case x : cases) {
            System.out.print(x);
        }
        System.out.println();
    }

    public void testturn(int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4) {

        boolean verifbas = true;
        boolean verifhaut = true;
        boolean verifdroite = true;
        boolean verifgauche = true;

        for (Case x : cases) {
            if (!plateau.limBas(x.getY())) {
                verifbas = false;
            }
            if (!plateau.limHaut(x.getY())) {
                verifhaut = false;
            }
            if (!plateau.limDroite(x.getX())) {
                verifdroite = false;
            }
            if (!plateau.limGauche(x.getX())) {
                verifgauche = false;
            }
        }

        if (verifbas) {


            if (!verifdroite) {
                this.gauche();
                this.gauche();
            }
            if (!verifgauche) {
                this.droite();
                this.droite();
            }
            if (!verifhaut) {
                this.descendre();
                this.descendre();
            }
            coefx1 += x1;
            coefx2 += x2;
            coefx3 += x3;
            coefx4 += x4;
            coefy1 += y1;
            coefy2 += y2;
            coefy3 += y3;
            coefy4 += y4;

            this.cases.get(0).setTranslateY(plateau.pos(cases.get(0).getX(), cases.get(0).getY()).height() * coefy1);
            this.cases.get(1).setTranslateY(plateau.pos(cases.get(1).getX(), cases.get(1).getY()).height() * coefy2);
            this.cases.get(2).setTranslateY(plateau.pos(cases.get(2).getX(), cases.get(2).getY()).height() * coefy3);
            this.cases.get(3).setTranslateY(plateau.pos(cases.get(3).getX(), cases.get(3).getY()).height() * coefy4);

            cases.get(0).setY(cases.get(0).getY() + y1);
            cases.get(1).setY(cases.get(1).getY() + y2);
            cases.get(2).setY(cases.get(2).getY() + y3);
            cases.get(3).setY(cases.get(3).getY() + y4);

            this.cases.get(0).setTranslateX(plateau.pos(cases.get(0).getX(), cases.get(0).getY()).width() * coefx1);
            this.cases.get(1).setTranslateX(plateau.pos(cases.get(1).getX(), cases.get(1).getY()).width() * coefx2);
            this.cases.get(2).setTranslateX(plateau.pos(cases.get(2).getX(), cases.get(2).getY()).width() * coefx3);
            this.cases.get(3).setTranslateX(plateau.pos(cases.get(3).getX(), cases.get(3).getY()).width() * coefx4);

            cases.get(0).setX(cases.get(0).getX() + x1);
            cases.get(1).setX(cases.get(1).getX() + x2);
            cases.get(2).setX(cases.get(2).getX() + x3);
            cases.get(3).setX(cases.get(3).getX() + x4);


            if (this.rotat == 4) {
                rotat = 1;
            } else {
                rotat++;
            }
        }


    }


    public void descendre() {

        boolean verif = true;

        if (action) {
            for (Case x : cases) {
                if (!plateau.limBas(x.getY())) {
                    verif = false;
                }
            }
        }

        if (verif) {


            coefy1 += 1.009;
            coefy2 += 1.009;
            coefy3 += 1.009;
            coefy4 += 1.009;

            this.cases.get(0).setTranslateY(plateau.pos(cases.get(0).getX(), cases.get(0).getY()).height() * coefy1);
            this.cases.get(1).setTranslateY(plateau.pos(cases.get(1).getX(), cases.get(1).getY()).height() * coefy2);
            this.cases.get(2).setTranslateY(plateau.pos(cases.get(2).getX(), cases.get(2).getY()).height() * coefy3);
            this.cases.get(3).setTranslateY(plateau.pos(cases.get(3).getX(), cases.get(3).getY()).height() * coefy4);


            cases.get(0).setY(cases.get(0).getY() + 1);
            cases.get(1).setY(cases.get(1).getY() + 1);
            cases.get(2).setY(cases.get(2).getY() + 1);
            cases.get(3).setY(cases.get(3).getY() + 1);

        } else {
            this.action = false;
        }
    }


    public void droite() {

        boolean verif = true;

        for (Case x : cases) {
            if (!plateau.limDroite(x.getX())) {
                verif = false;
            }
        }

        if (verif) {

            coefx1 += 1.009;
            coefx2 += 1.009;
            coefx3 += 1.009;
            coefx4 += 1.009;

            this.cases.get(0).setTranslateX(plateau.pos(cases.get(0).getX(), cases.get(0).getY()).height() * coefx1);
            this.cases.get(1).setTranslateX(plateau.pos(cases.get(1).getX(), cases.get(1).getY()).height() * coefx2);
            this.cases.get(2).setTranslateX(plateau.pos(cases.get(2).getX(), cases.get(2).getY()).height() * coefx3);
            this.cases.get(3).setTranslateX(plateau.pos(cases.get(3).getX(), cases.get(3).getY()).height() * coefx4);

            cases.get(0).setX(cases.get(0).getX() + 1);
            cases.get(1).setX(cases.get(1).getX() + 1);
            cases.get(2).setX(cases.get(2).getX() + 1);
            cases.get(3).setX(cases.get(3).getX() + 1);
        }

    }

    public void gauche() {

        boolean verif = true;

        for (Case x : cases) {
            if (!plateau.limGauche(x.getX())) {
                verif = false;
            }
        }

        if (verif) {

            coefx1 -= 1.009;
            coefx2 -= 1.009;
            coefx3 -= 1.009;
            coefx4 -= 1.009;

            this.cases.get(0).setTranslateX(plateau.pos(cases.get(0).getX(), cases.get(0).getY()).height() * coefx1);
            this.cases.get(1).setTranslateX(plateau.pos(cases.get(1).getX(), cases.get(1).getY()).height() * coefx2);
            this.cases.get(2).setTranslateX(plateau.pos(cases.get(2).getX(), cases.get(2).getY()).height() * coefx3);
            this.cases.get(3).setTranslateX(plateau.pos(cases.get(3).getX(), cases.get(3).getY()).height() * coefx4);

            cases.get(0).setX(cases.get(0).getX() - 1);
            cases.get(1).setX(cases.get(1).getX() - 1);
            cases.get(2).setX(cases.get(2).getX() - 1);
            cases.get(3).setX(cases.get(3).getX() - 1);
        }


    }


    public boolean premiereDescente() {
        return this.action;
    }

    public Case getCase1() {
        return cases.get(0);
    }

    public Case getCase2() {
        return cases.get(1);
    }

    public Case getCase3() {
        return cases.get(2);
    }

    public Case getCase4() {
        return cases.get(3);
    }


    public boolean verifdroite(ArrayList<Case> list) {
        for (Case x : list) {
            if (x.getX() == cases.get(0).getX() + 1 && x.getY() == cases.get(0).getY()
                    || x.getX() == cases.get(1).getX() + 1 && x.getY() == cases.get(1).getY()
                    || x.getX() == cases.get(2).getX() + 1 && x.getY() == cases.get(2).getY()
                    || x.getX() == cases.get(3).getX() + 1 && x.getY() == cases.get(3).getY()
            ) {
                return false;
            }
        }
        return true;
    }

    public boolean verifieGauche(ArrayList<Case> list) {
        for (Case x : list) {
            if (x.getX() == cases.get(0).getX() - 1 && x.getY() == cases.get(0).getY()
                    || x.getX() == cases.get(1).getX() - 1 && x.getY() == cases.get(1).getY()
                    || x.getX() == cases.get(2).getX() - 1 && x.getY() == cases.get(2).getY()
                    || x.getX() == cases.get(3).getX() - 1 && x.getY() == cases.get(3).getY()
            ) {
                return false;
            }
        }
        return true;
    }

    public boolean verifieBas(ArrayList<Case> list) {
        for (Case x : list) {
            if (x.getY() == cases.get(0).getY() + 1 && x.getX() == cases.get(0).getX()
                    || x.getY() == cases.get(1).getY() + 1 && x.getX() == cases.get(1).getX()
                    || x.getY() == cases.get(2).getY() + 1 && x.getX() == cases.get(2).getX()
                    || x.getY() == cases.get(3).getY() + 1 && x.getX() == cases.get(3).getX()
            ) {
                return false;
            }
        }
        return true;
    }

    public boolean verifieHaut(ArrayList<Case> list) {
        for (Case x : list) {
            if (x.getY() == cases.get(0).getY() - 1 && x.getX() == cases.get(0).getX()
                    || x.getY() == cases.get(1).getY() - 1 && x.getX() == cases.get(1).getX()
                    || x.getY() == cases.get(2).getY() - 1 && x.getX() == cases.get(2).getX()
                    || x.getY() == cases.get(3).getY() - 1 && x.getX() == cases.get(3).getX()
            ) {
                return false;
            }
        }
        return true;
    }


    public void supprime(Case carre) {
        if (cases.contains(carre)) {
            for (int i = cases.size() - 1; i >= 0; i--) {
                if (cases.get(i).equals(carre)) {
                    cases.remove(i);
                }
            }
        }
    }

    public boolean disparition() {
        return cases.isEmpty();
    }

}


class T extends formes {
    public T(Scene scene, Plateau plateau) {
        super.scene = scene;
        super.plateau = plateau;
        initialisation(3, 4, 5, 4, 1, 1, 1, 0);
    }

    @Override
    public void turn() {

        switch (rotat) {
            case 1 -> testturn(1, -1, 0, 0, -1, 1, 1, 1);
            case 2 -> testturn(1, 1, 0, 0, -1, -1, -1, 1);
            case 3 -> testturn(-1, 1, 0, 0, 1, -1, -1, -1);
            case 4 -> testturn(-1, -1, 0, 0, 1, 1, 1, -1);
        }
    }


}

class Carre extends formes {
    public Carre(Scene scene, Plateau plateau) {
        super.scene = scene;
        super.plateau = plateau;
        initialisation(4, 4, 5, 5, 0, 1, 0, 1);
    }

    @Override
    public void turn() {
    }

}

class Barre extends formes {
    public Barre(Scene scene, Plateau plateau) {
        super.scene = scene;
        super.plateau = plateau;
        initialisation(3, 4, 5, 6, 0, 0, 0, 0);
    }

    @Override
    public void turn() {

        switch (rotat) {
            case 1 -> testturn(2, -2, 1, -1, 0, 0, -1, 1);
            case 2 -> testturn(-2, 2, -1, 1, 0, 0, 1, -1);
            case 3 -> testturn(1, 1, 0, 0, -1, -1, -2, -2);
            case 4 -> testturn(-1, -1, 0, 0, 1, 1, 2, 2);
        }

    }
}

class Ldroit extends formes {
    public Ldroit(Scene scene, Plateau plateau) {
        super.scene = scene;
        super.plateau = plateau;
        initialisation(3, 4, 5, 5, 1, 1, 1, 0);
    }

    @Override
    public void turn() {

        switch (rotat) {
            case 1 -> testturn(1, -1, 0, 0, -1, 1, 0, 2);
            case 2 -> testturn(1, 1, 0, 0, -1, -1, -2, 0);
            case 3 -> testturn(-1, 1, 0, 0, 1, -1, 0, -2);
            case 4 -> testturn(-1, -1, 0, 0, 1, 1, 2, 0);
        }

    }
}

class Lretourne extends formes {
    public Lretourne(Scene scene, Plateau plateau) {
        super.scene = scene;
        super.plateau = plateau;
        initialisation(3, 3, 4, 5, 0, 1, 1, 1);
    }

    @Override
    public void turn() {

        switch (rotat) {
            case 1 -> testturn(2, 0, 1, -1, 0, 0, -1, 1);
            case 2 -> testturn(0, 2, 1, 1, 0, 0, -1, -1);
            case 3 -> testturn(-2, 0, -1, 1, 0, 0, 1, -1);
            case 4 -> testturn(0, -2, -1, -1, 0, 0, 1, 1);
        }

    }
}

class S extends formes {
    public S(Scene scene, Plateau plateau) {
        super.scene = scene;
        super.plateau = plateau;
        initialisation(3, 4, 4, 5, 1, 1, 0, 0);
    }

    @Override
    public void turn() {

        switch (rotat) {
            case 1 -> testturn(2, 0, 1, -1, 0, 0, -1, -1);
            case 2 -> testturn(-2, 0, -1, 1, 0, 0, 1, 1);
            case 3 -> testturn(1, 1, 0, 0, -1, 1, -2, 0);
            case 4 -> testturn(-1, -1, 0, 0, 1, -1, 2, 0);
        }

    }
}

class Z extends formes {
    public Z(Scene scene, Plateau plateau) {
        super.scene = scene;
        super.plateau = plateau;
        initialisation(3, 4, 4, 5, 0, 0, 1, 1);
    }

    @Override
    public void turn() {

        switch (rotat) {
            case 1 -> testturn(2, 0, 1, 1, 0, 0, -1, 1);
            case 2 -> testturn(-2, 0, -1, -1, 0, 0, 1, -1);
            case 3 -> testturn(1, -1, 0, 0, -1, -1, -2, 0);
            case 4 -> testturn(-1, 1, 0, 0, 1, 1, 2, 0);
        }

    }
}
































