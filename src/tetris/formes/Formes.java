package tetris.formes;

import java.util.ArrayList;

import javafx.scene.paint.Color;
import tetris.application.Case;
import tetris.scenes.GameScene;

public abstract class Formes extends ControlFormes{
	
	public Formes() {}
	
	ArrayList<Case> cases = new ArrayList<>();
	
	
	 int quart = 0;
	 boolean enBas;
	
	Color[] couleurs = {Color.CYAN , Color.DARKCYAN, Color.LIGHTGREEN , Color.PURPLE , Color.RED , Color.ORANGE , Color.YELLOW, Color.LIGHTGRAY};
	
	
	public void createFormes(GameScene plateau) {
		
		enBas = false;
		
		int forme = (int) (Math.random() * (7));
		int color = (int) (Math.random() * (8));

		switch (forme) {
			case 0 -> plateau.ajouterForm(new Barre(couleurs[color]));
			case 1 -> plateau.ajouterForm(new Carre(couleurs[color]));
			case 2 -> plateau.ajouterForm(new T(couleurs[color]));
			case 3 -> plateau.ajouterForm(new Z(couleurs[color]));
			case 4 -> plateau.ajouterForm(new S(couleurs[color]));
			case 5 -> plateau.ajouterForm(new Ldroit(couleurs[color]));
			case 6 -> plateau.ajouterForm(new Lretourne(couleurs[color]));
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

}
