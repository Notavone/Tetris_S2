package tetris.formes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import javafx.scene.paint.Color;
import tetris.scenes.GameScene;

public class Ldroit extends Formes {
	
	public Ldroit(GameScene plateau) {
		super(plateau);
		Color couleur = super.randomCouleur();
		this.ajouterCases(3,4,5,5,1,1,1,0,couleur);
		
	}

	@Override
	public ArrayList<Integer> tourne() {
		return switch (quart) {
			case 0 -> new ArrayList<>(Arrays.asList(1, -1, 0, 0, -1, 1, 0, 2));
			case 1 -> new ArrayList<>(Arrays.asList(1, 1, 0, 0, -1, -1, -2, 0));
			case 2 -> new ArrayList<>(Arrays.asList(-1, 1, 0, 0, 1, -1, 0, -2));
			case 3 -> new ArrayList<>(Arrays.asList(-1, -1, 0, 0, 1, 1, 2, 0));
			default -> new ArrayList<>(Collections.emptyList());
		};
	}

}
