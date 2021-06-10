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
		int size = 35;

		emplacementX = X;
		emplacementY = Y;

		Group cellGroup = new Group();

		Rectangle square = new Rectangle(size, size);
		Polygon topShade = new Polygon();
		Polygon bottomShade = new Polygon();

		double ombreSize = (double) size / 7.5;

		if (!isBackground) {
			topShade.setOpacity(0.5);
			topShade.setFill(Color.WHITE);
			topShade.getPoints().addAll(0.0, 0.0,
					(double) size, 0.0,
					(double) size - ombreSize, ombreSize,
					ombreSize, ombreSize,
					ombreSize, (double) size - ombreSize,
					0.0, (double) size);

			bottomShade.setOpacity(0.5);
			bottomShade.setFill(Color.BLACK);
			bottomShade.getPoints().addAll(0.0, (double) size,
					(double) size, (double) size,
					(double) size, 0.0,
					(double) size - ombreSize, ombreSize,
					(double) size - ombreSize, (double) size - ombreSize,
					ombreSize, (double) size - ombreSize);

			square.setFill(couleur);

			cellGroup.getChildren().addAll(square, topShade, bottomShade);
		} else {
			topShade.setOpacity(0.1);
			topShade.setFill(Color.BLACK);
			topShade.getPoints().addAll(0.0, 0.0,
					(double) size, 0.0,
					(double) size - ombreSize, ombreSize,
					ombreSize, ombreSize,
					ombreSize, (double) size - ombreSize,
					0.0, (double) size);

			bottomShade.setOpacity(0.25);
			bottomShade.setFill(Color.BLACK);
			bottomShade.getPoints().addAll(0.0, (double) size,
					(double) size, (double) size,
					(double) size, 0.0,
					(double) size - ombreSize, ombreSize,
					(double) size - ombreSize, (double) size - ombreSize,
					ombreSize, (double) size - ombreSize);

			Rectangle topRec = new Rectangle(size, size / 2.65);
			topRec.setOpacity(0.05);
			topRec.setFill(Color.WHITE);

			Arc halfCircle = new Arc((double) size / 2.0, (double) size / 2.0, (double) size / 2.0, (double) size / 8.0, 0.0f, 180.0f);
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
