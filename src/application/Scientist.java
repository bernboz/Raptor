package application;



import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Ellipse;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;


public class Scientist extends Piece {
	
	/* Piece generation attributes */
	private final static String SCIENTIST_LINK = "https://i.imgur.com/rqzwYYT.png";
	private ImagePattern pieceImage;
	private Ellipse pieceBack;
	private Ellipse pieceFront;
	private Color iconColor;
	
	/* Location attributes */
	private int oldLocX, oldLocY;
	private int locX, locY;
	
	/* Gameplay attributes */
	private boolean nearBaby;
	private BabyRaptor baby;
	private boolean offensiveAP;
	private boolean isAfraid;
		
	public Scientist(int x, int y) {
		// Sets Scientist imageLink and location and places on board
		super(SCIENTIST_LINK, x, y);		
		move(x, y);

		// Sets up Scientist piece
		pieceImage = getIconImagePattern();
		iconColor = Color.ORANGE;		
		pieceFront = generatePieceImage(pieceImage);
		pieceBack = generatePieceBack(iconColor);		
		getChildren().addAll(pieceBack, pieceFront);
		
		// Future attributes below
		// Gameplay attributes
		setOffensiveAP(true);

	}
	
	public String getScientistLink() {
		return SCIENTIST_LINK;
	}

	public boolean hasOffensiveAP() {
		return offensiveAP;
	}

	public void setOffensiveAP(boolean offensiveAP) {
		this.offensiveAP = offensiveAP;
	}

	public void setAfraid(boolean isAfraid) {
		this.isAfraid = isAfraid;
	}
	
	public boolean isAfraid() {
		return isAfraid;
	}
	
}
