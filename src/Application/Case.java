package Application;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.Parent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Case extends Parent {

    private int emplacementX;
    private int emplacementY;
    private SimpleDoubleProperty h;
    private SimpleDoubleProperty l;
    private Color couleur;
    private Rectangle fond;

    public Case(int X, int Y) {
        emplacementX = X;
        emplacementY = Y;
        this.couleur = Color.DARKBLUE;

        h = new SimpleDoubleProperty();
        l = new SimpleDoubleProperty();
        h.set(12);
        l.set(12);
        l.bind(h);

//		fond:
        fond = new Rectangle(0, 0, 12, 12);
        fond.setFill(couleur);
        fond.heightProperty().bind(h);
        fond.widthProperty().bind(l);
        this.getChildren().add(fond);
    }


    public void couleurOrigin() {
        this.couleur = Color.DARKBLUE;
        fond.setFill(couleur);
    }

    public void setCouleur(Color couleur) {
        this.couleur = couleur;
        fond.setFill(couleur);
    }

    public Color getCouleur() {
        return couleur;
    }

    public int getX() {
        return emplacementX;
    }

    public int getY() {
        return emplacementY;
    }

    public double height() {
        return fond.getHeight();
    }

    public double width() {
        return fond.getWidth();
    }

    public void setX(int X) {
        this.emplacementX = X;
    }

    public void setY(int Y) {
        this.emplacementY = Y;
    }

    public SimpleDoubleProperty HProperty() {
        return h;
    }

    public SimpleDoubleProperty LProperty() {
        return l;
    }
}
