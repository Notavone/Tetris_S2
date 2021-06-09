package tetris.formes;

import java.util.ArrayList;
import java.util.Arrays;

import javafx.scene.paint.Color;

public class Carre extends Formes{
	
	public Carre(Color Couleur) {	
		
		this.ajouterCases(4,4,5,5,0,1,0,1,Couleur);
		
	}

	@Override
	public ArrayList<Integer> tourne() {
		return new ArrayList<>(Arrays.asList(0,0,0,0,0,0,0,0));
	}

}
