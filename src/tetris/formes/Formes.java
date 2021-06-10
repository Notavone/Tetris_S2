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
			int color = (int) (Math.random() * (couleurs.length));

			switch (forme) {
				case 0 -> plateau.ajouterForm(new Barre(couleurs[color]));
				case 1 -> plateau.ajouterForm(new Carre(couleurs[color]));
				case 2 -> plateau.ajouterForm(new T(couleurs[color]));
				case 3 -> plateau.ajouterForm(new Z(couleurs[color]));
				case 4 -> plateau.ajouterForm(new S(couleurs[color]));
				case 5 -> plateau.ajouterForm(new Ldroit(couleurs[color]));
				case 6 -> plateau.ajouterForm(new Lretourne(couleurs[color]));
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

}
