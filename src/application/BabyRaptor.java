package application;

import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Ellipse;

public class BabyRaptor extends Piece {
	// Baby Raptor
	
	/* Piece generation attributes */
	private static final String BABY_LINK = "https://i.imgur.com/CYJQdGS.png";
	private ImagePattern pieceImage;
	private Ellipse pieceBack;
	private Ellipse pieceFront;
	private Color iconColor;
	private boolean asleep;
	
	public BabyRaptor(int x, int y) {
		// Sets BabyRaptor imageLink and location and places on board
		super(BABY_LINK, x, y);
		move(x,y);
		setAsleep(false);
		// Sets up BabyRaptor piece
		pieceImage = getIconImagePattern();		
		iconColor = Color.CHARTREUSE;
		pieceFront = generatePieceImage(pieceImage);
		pieceBack = generatePieceBack(iconColor);
		getChildren().addAll(pieceBack, pieceFront);
		
		// Future attributes below
	}
	
	public String getBabyLink() {
		return BABY_LINK;
	}

	public boolean isAsleep() {
		return asleep;
	}

	public void setAsleep(boolean asleep) {
		this.asleep = asleep;
	}

}
