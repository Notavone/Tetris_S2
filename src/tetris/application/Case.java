package tetris.application;

import javafx.scene.Parent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Case extends Parent{

	private int emplacementX;
	private int emplacementY;
	private final Color couleur;

	public Case(int X, int Y, Color couleur) {
		
		this.couleur = couleur;
		
		emplacementX = X;
		emplacementY = Y;

		Rectangle fond = new Rectangle(0, 0, 35, 35);
		fond.setFill(couleur);
		
		this.getChildren().add(fond);
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
	
	
	
	
	public void setEmplacementX(int x) {
		this.emplacementX = x;
	}
	
	public void setEmplacementY(int y) {
		this.emplacementY = y;
	}
	
	
}
