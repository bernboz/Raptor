package application;

import java.util.ArrayList;

import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Ellipse;

public class Piece extends StackPane {

	/* MY ATTRIBUTES */
	private final String SCIENTIST_LINK = "https://i.imgur.com/rqzwYYT.png";
	//private final String MOTHER_RAPTOR_LINK = "https://i.imgur.com/OoVjoKF.png";
	private final String BABY_RAPTOR_LINK = "https://i.imgur.com/CYJQdGS.png";
	// ImagePattern imagePattern = new ImagePattern(new Image(scientist));

	/* info from subclass */

	private String imageLink; // allow subclass to modify imageLink
	Piece piece;
	boolean nearBaby;
	boolean nearScientist;
	private boolean selected;

	/*-------------------*/
	private ImagePattern iconImagePattern;
	private String figureText;
	private String iconImageLink;
	private Color fillColor;
	/*******************/
	protected Space[][] board = /*FinalBoard*/Board.getBoard();
	private Space space;
	private double mouseX, mouseY; // for draggable location
	private double oldX, oldY; // for draggable location
	private int oldLocX, oldLocY; // for piece location
	private int newLocX, newLocY; // for piece location
	private ArrayList<Piece> pieceStorage = Storage.getPieceStorage();

	public Piece() {
	}

	public Piece(String imageLink, int x, int y) {
		setSelected(false);
		// Starts the pieces on the board as specific type for checks
		if (this instanceof MotherRaptor) {
			board[x][y].setMother((MotherRaptor) this);
			
		}
		
		if (this instanceof Scientist) {
			board[x][y].setScientist((Scientist) this);
		}
		
		if (this instanceof BabyRaptor) {
			board[x][y].setBaby((BabyRaptor) this);
		}
		
		setIconImagePattern(imageLink);
		
		
		setOnMouseClicked(e -> {
			Storage.setPiece(this);
			Storage.addToPieceStorage();
			oldLocX = Board.toBoard(getOldX());
			oldLocY = Board.toBoard(getOldY());
			System.out.println("Check on Click PieceStorage size: " + pieceStorage.size());
			GamePhase phase = GameLoop.getPhase();
			CardAction cardAction = GameLoop.getCardAction();
			TopUI.setActionPoints(ScientistAction.getScientistActionPoints(), RaptorAction.getRaptorActionPoints());
			
			if (phase == GamePhase.RAPTOR_CARD) {
				if (cardAction == CardAction.FEAR_1) {
					RaptorCardAction.fear1(this);
					pieceStorage.clear();
				}
				if (cardAction == CardAction.FEAR_2) {
					RaptorCardAction.fear2(this);
					pieceStorage.clear();
				}
				if (cardAction == CardAction.RECOVERY_1) {
					RaptorCardAction.recovery(this, 2);
					pieceStorage.clear();
				}
				if (cardAction == CardAction.RECOVERY_2) {
					RaptorCardAction.recovery(this, 3);
					pieceStorage.clear();
				}
			}
			
			if (phase == GamePhase.DISAPPEAR) {
				if (cardAction == CardAction.DIS_AND_OBS) {
					if (this instanceof MotherRaptor) {
						RaptorCardAction.disappear(this, board, oldLocX, oldLocY);
						pieceStorage.clear();
					}
				}
			}
			
			if (phase == GamePhase.SCIENTIST_CARD) {
				if (cardAction == CardAction.SLEEPING_GAS_1) {
					ScientistCardAction.sleepingGas1(this);
					pieceStorage.clear();
				}
				if (cardAction == CardAction.SLEEPING_GAS_2) {
					ScientistCardAction.sleepingGas2(this);
					pieceStorage.clear();
				}
			}
			
			// Handles actions, such as which piece is selected and which piece will be targeted
			if (pieceStorage.size() > 1) {
				Piece selectedPiece = pieceStorage.get(0);
				Piece targetPiece = pieceStorage.get(1);
				System.out.println("Selected Piece: " + selectedPiece);
				System.out.println("Target Piece: " + targetPiece);
				
				if (pieceStorage.size() > 2)
					pieceStorage.clear();
				
				//------------------------------------------------------------------------------------------------------//
				if (phase == GamePhase.RAPTOR_ACTIONS) {
					if(selectedPiece instanceof MotherRaptor) {
						if (targetPiece instanceof Scientist) {
							RaptorAction.eatScientist(selectedPiece, targetPiece, board, newLocX, newLocY);
							pieceStorage.clear();
						} else {
							pieceStorage.clear();
						}
						if (targetPiece instanceof BabyRaptor) {
							RaptorAction.wakeBaby(selectedPiece, targetPiece);
							pieceStorage.clear();
						} else {
							pieceStorage.clear();
						}
					}
					if (selectedPiece instanceof BabyRaptor && targetPiece instanceof BabyRaptor) 
						pieceStorage.clear();
					if (selectedPiece instanceof MotherRaptor && targetPiece instanceof MotherRaptor)
						pieceStorage.clear();
				}
				//-MotherRaptor removes fire----------------------------------------------------------------------------//
				// TODO: NEED TO CREATE FIRE TOKEN. FIRE TOKEN CREATED, JUST NEED TO ADD
				//------------------------------------------------------------------------------------------------------//

				//-Scientist tranqs a BabyRaptor if not already tranqed or captures BabyRaptor if it is tranqed---------//
				if (phase == GamePhase.SCIENTIST_ACTIONS) {
					if (selectedPiece instanceof Scientist) {
						// Tranqualize the BabyRaptor if it isn't already asleep
						if (targetPiece instanceof BabyRaptor && !(((BabyRaptor) targetPiece).isAsleep())) {
							ScientistAction.tranqBaby(selectedPiece, targetPiece);
							pieceStorage.clear();
						} else if (targetPiece instanceof BabyRaptor && (((BabyRaptor) targetPiece).isAsleep())) {
							ScientistAction.captureBaby((Scientist) selectedPiece, (BabyRaptor) targetPiece, board/*, newLocX, newLocY*/);
							pieceStorage.clear();
						} else if (targetPiece instanceof MotherRaptor) {
							newLocX = selectedPiece.oldLocX; // X-Location of MotherRaptor
							newLocY = selectedPiece.oldLocY; // Y-Location of MotherRaptor
							ScientistAction.tranqMother((Scientist) selectedPiece, (MotherRaptor) targetPiece, board, newLocX, newLocY, oldLocX, oldLocY);
							pieceStorage.clear();
						} else if (targetPiece instanceof Scientist) {
							if (((Scientist) targetPiece).isAfraid()) {
								((Scientist) targetPiece).setAfraid(false);
								pieceStorage.clear();
							} else {
								pieceStorage.clear();
							}
						} else {
							pieceStorage.clear();
						}
					}
				}

			}
			
			if(!isSelected()) {
				setSelected(true);
				//add this piece to some kind of list that the Space class can select from
				System.out.println("Selected: " + this);
				System.out.println("oldLocX: " + oldLocX);
				System.out.println("oldLocY: " + oldLocY);

			}

		});
	}
	
	/* PIECE METHODS */

	public void move(int x, int y) {
		oldX = x * Board.SPACE_SIZE;
		oldY = y * Board.SPACE_SIZE;
		relocate(oldX, oldY);
	}

	// Creates an ellipse that represents piece background
	public Ellipse generatePieceBack(Color iconColor) {

		Ellipse background = new Ellipse(Board.SPACE_SIZE * 0.3125, Board.SPACE_SIZE * 0.26);
		background.setFill(iconColor);
		background.setStroke(Color.BLACK);
		background.setStrokeWidth(Board.SPACE_SIZE * 0.03);
		background.setTranslateX((Board.SPACE_SIZE - Board.SPACE_SIZE * 0.3125 * 2) / 2);
		background.setTranslateY((Board.SPACE_SIZE - Board.SPACE_SIZE * 0.26 * 2) / 2 + Board.SPACE_SIZE * 0.07);
		
		return background;
	}
	
	// Creates an ellipse that represents a piece image
	public Ellipse generatePieceImage(ImagePattern image) {
		
		Ellipse pieceImage = new Ellipse(Board.SPACE_SIZE * 0.3125, Board.SPACE_SIZE * 0.26);
		pieceImage.setFill(image/*tempColor*/); // this needs to be an image pattern
		pieceImage.setStroke(Color.BLACK);
		pieceImage.setStrokeWidth(Board.SPACE_SIZE * 0.03);
		pieceImage.setTranslateX((Board.SPACE_SIZE - Board.SPACE_SIZE * 0.3125 * 2) / 2);
		pieceImage.setTranslateY((Board.SPACE_SIZE - Board.SPACE_SIZE * 0.26 * 2) / 2);
		
		return pieceImage;
	}
	
	public void setNearBaby(Space[][] board) {
		nearBaby = false;
		
		try {
			if (board[newLocX + 1][newLocY].hasBaby() || 
				board[newLocX][newLocY + 1].hasBaby()) {
				nearBaby = true;
				System.out.println("near baby");
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("Error thrown, may need to recheck");
			newLocY = Board.MAIN_BOARD_HEIGHT - 1;
			if (board[newLocX + 1][newLocY].hasBaby() || 
				board[newLocX][newLocY].hasBaby()) {
				nearBaby = true;
			}
		}
		try {
			if (board[newLocX - 1][newLocY].hasBaby() ||
				board[newLocX][newLocY - 1].hasBaby()) {
				nearBaby = true;
				System.out.println("near baby");
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("Error thrown, may need to recheck");
			newLocY = 0;
			if (board[newLocX - 1][newLocY].hasBaby() || 
				board[newLocX][newLocY].hasBaby()) {
					nearBaby = true;
			}
		}
		
		System.out.println("locationX: " + newLocX);
		System.out.println("locationY: " + newLocY);

		System.out.println("locationX + 1: " + (newLocX + 1));
		System.out.println("locationY + 1: " + (newLocY + 1));
		
		System.out.println("Near Baby Called");
		System.out.println("nearBaby: " + nearBaby);
	}
	
	public void setNearScientist(Space[][] board) {
		nearScientist = false;
		
		try {
			if (board[newLocX + 1][newLocY].hasScientist() || 
				board[newLocX][newLocY + 1].hasScientist()) {
				nearScientist = true;
				System.out.println("near baby");
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("Error thrown, may need to recheck");
			setNewLocY(Board.MAIN_BOARD_HEIGHT - 1);
			if (board[newLocX + 1][newLocY].hasScientist() || 
				board[newLocX][newLocY].hasScientist()) {
				nearScientist = true;
			}
		}
		try {
			if (board[newLocX - 1][newLocY].hasScientist() ||
				board[newLocX][newLocY - 1].hasScientist()) {
				nearScientist = true;
				System.out.println("near baby");
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("Error thrown, may need to recheck");
			setNewLocY(0);
			if (board[newLocX - 1][newLocY].hasScientist() || 
				board[newLocX][newLocY].hasScientist()) {
					nearScientist = true;
			}
		}
		
		System.out.println("locationX: " + newLocX);
		System.out.println("locationY: " + newLocY);

		System.out.println("locationX + 1: " + (newLocX + 1));
		System.out.println("locationY + 1: " + (newLocY + 1));
		
		System.out.println("Near Scientist Called");
		System.out.println("nearScientist: " + nearScientist);
	}
	
	public boolean isNearBaby() {
		return nearBaby;
	}
	
	public boolean isNearScientist() {
		return nearScientist;
	}
	
	/* PIECE GETTER AND SETTERS */
	
	public double getOldX() {
		return oldX;
	}

	public double getOldY() {
		return oldY;
	}
	
	public int getOldLocY() {
		return oldLocY;
	}
	
	public int getOldLocX() {
		return oldLocX;
	}
	public void setNewLocX(int newLocX) {
		this.newLocX = newLocX;
	}
	
	public void setNewLocY(int newLocY) {
		this.newLocY = newLocY;
	}
	
	public int getNewLocY() {
		return newLocY;
	}
	
	public void setImageLink(String imageLink) {
		this.imageLink = imageLink;
		System.out.println("Piece.setImageLink: " + this.imageLink);
	}

	public String getImageLink() {
		System.out.println("Piece.getImageLink: " + imageLink);
		return imageLink;
	}
	
	public ImagePattern getIconImagePattern() {
		return iconImagePattern;
	}

	public void setIconImagePattern(String imageLink) {
		System.out.println("Piece.setIconImagePattern(): " + imageLink);
		this.iconImagePattern = getIconImagePattern(imageLink);
	}

	public ImagePattern getIconImagePattern(String imageLink) {
		System.out.println("Piece.getIconImagePattern: " + imageLink);
		ImagePattern icon = new ImagePattern(new Image(imageLink));
		return icon;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

}

//file:///C:/Users/jesse/Desktop/Java-Workspaces/Raptor_Game/assets/-IMAGE-
