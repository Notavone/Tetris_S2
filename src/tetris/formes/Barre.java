package tetris.formes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import javafx.scene.paint.Color;
import tetris.scenes.GameScene;

public class Barre extends Formes {
	
	
	public Barre(GameScene plateau) {
		super(plateau);
		Color couleur = super.randomCouleur();
		this.ajouterCases(3, 4, 5, 6, 0, 0, 0, 0, couleur);
	}
	
	
	

	@Override
	public ArrayList<Integer> tourne() {
		return switch (quart) {
			case 0 -> new ArrayList<>(Arrays.asList(2, -2, 1, -1, 0, 0, -1, 1));
			case 1 -> new ArrayList<>(Arrays.asList(-2, 2, -1, 1, 0, 0, 1, -1));
			case 2 -> new ArrayList<>(Arrays.asList(1, 1, 0, 0, -1, -1, -2, -2));
			case 3 -> new ArrayList<>(Arrays.asList(-1, -1, 0, 0, 1, 1, 2, 2));
			default -> new ArrayList<>(Collections.emptyList());
		};
	}
	
	
}
