package application;

import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Ellipse;
import javafx.scene.paint.Color;


public class MotherRaptor extends Piece {
	// Mother Raptor
	
	/* Piece generation attributes */
	private static final String MOTHER_LINK = "https://i.imgur.com/OoVjoKF.png";
	private ImagePattern pieceImage;
	private Ellipse pieceBack;
	private Ellipse pieceFront;
	private Color iconColor;
	private static int tranqHits;
	
	public MotherRaptor(int x, int y) {
		// Sets MotherRaptor imageLink and location and places on board
		super(MOTHER_LINK, x, y);
		move(x,y);
		setTranqHits(0);
		// Sets up the MotherRaptor piece
		pieceImage = getIconImagePattern();		
		iconColor = Color.DARKGREEN;
		pieceFront = generatePieceImage(pieceImage);
		pieceBack = generatePieceBack(iconColor);
		getChildren().addAll(pieceBack, pieceFront);

		// Future attributes below
	}
	
	public String getMotherLink() {
		return MOTHER_LINK;
	}

	public static int getTranqHits() {
		return tranqHits;
	}

	public static void setTranqHits(int tranqHits) {
		MotherRaptor.tranqHits = tranqHits;
	}
	
	public void addTranqHit() {
		tranqHits++;
	}

}
