package tetris.application;

import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import tetris.scenes.GameScene;

public class Case extends Parent{

	private int emplacementX;
	private int emplacementY;
	private final Color couleur;

	public Case(int X, int Y, Color couleur, boolean isBackground) {
		
		this.couleur = couleur;
		int PIXEL = 35;
		
		emplacementX = X;
		emplacementY = Y;

		Group cellGroup = new Group();

		Rectangle square = new Rectangle(PIXEL, PIXEL);
		Polygon topShade = new Polygon();
		Polygon bottomShade = new Polygon();

		double shadeThick = (double) PIXEL / 7.5;

		if (!isBackground) {
			topShade.setOpacity(0.5);
			topShade.setFill(Color.WHITE);
			topShade.getPoints().addAll(0.0, 0.0,
					(double) PIXEL, 0.0,
					(double) PIXEL - shadeThick, shadeThick,
					shadeThick, shadeThick,
					shadeThick, (double) PIXEL - shadeThick,
					0.0, (double) PIXEL);

			bottomShade.setOpacity(0.5);
			bottomShade.setFill(Color.BLACK);
			bottomShade.getPoints().addAll(0.0, (double) PIXEL,
					(double) PIXEL, (double) PIXEL,
					(double) PIXEL, 0.0,
					(double) PIXEL - shadeThick, shadeThick,
					(double) PIXEL - shadeThick, (double) PIXEL - shadeThick,
					shadeThick, (double) PIXEL - shadeThick);

			square.setFill(couleur);

			cellGroup.getChildren().addAll(square, topShade, bottomShade);
		} else {
			topShade.setOpacity(0.1);
			topShade.setFill(Color.BLACK);
			topShade.getPoints().addAll(0.0, 0.0,
					(double) PIXEL, 0.0,
					(double) PIXEL - shadeThick, shadeThick,
					shadeThick, shadeThick,
					shadeThick, (double) PIXEL - shadeThick,
					0.0, (double) PIXEL);

			bottomShade.setOpacity(0.25);
			bottomShade.setFill(Color.BLACK);
			bottomShade.getPoints().addAll(0.0, (double) PIXEL,
					(double) PIXEL, (double) PIXEL,
					(double) PIXEL, 0.0,
					(double) PIXEL - shadeThick, shadeThick,
					(double) PIXEL - shadeThick, (double) PIXEL - shadeThick,
					shadeThick, (double) PIXEL - shadeThick);

			Rectangle topRec = new Rectangle(PIXEL, PIXEL / 2.65);
			topRec.setOpacity(0.05);
			topRec.setFill(Color.WHITE);

			Arc halfCircle = new Arc((double) PIXEL / 2.0, (double) PIXEL / 2.0, (double) PIXEL / 2.0, (double) PIXEL / 8.0, 0.0f, 180.0f);
			halfCircle.setOpacity(0.05);
			halfCircle.setFill(Color.WHITE);
			halfCircle.setType(ArcType.ROUND);
			halfCircle.setRotate(180.0);

			square.setFill(couleur);
			square.setOpacity((55.0 / 62.0 - ((double) Y + 30.0) / ((double) GameScene.LIGNES + 50)));

			cellGroup.getChildren().addAll(square, topShade, bottomShade, halfCircle, topRec);
		}

		this.getChildren().add(cellGroup);
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
