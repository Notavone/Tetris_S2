package tetris.formes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import javafx.scene.paint.Color;

public class Lretourne extends Formes{
	
    public Lretourne(Color Couleur) {	
    	
		this.ajouterCases(3,3,4,5,0,1,1,1,Couleur);
		
	}


	@Override
	public ArrayList<Integer> tourne() {
		return switch (quart) {
			case 0 -> new ArrayList<>(Arrays.asList(2, 0, 1, -1, 0, 0, -1, 1));
			case 1 -> new ArrayList<>(Arrays.asList(0, 2, 1, 1, 0, 0, -1, -1));
			case 2 -> new ArrayList<>(Arrays.asList(-2, 0, -1, 1, 0, 0, 1, -1));
			case 3 -> new ArrayList<>(Arrays.asList(0, -2, -1, -1, 0, 0, 1, 1));
			default -> new ArrayList<>(Collections.emptyList());
		};
	}
}
