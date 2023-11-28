package application;

import javafx.scene.shape.Rectangle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;

public class Space extends Rectangle{
	
	private Piece piece;
	private Scientist scientist;
	private MotherRaptor mother;
	private BabyRaptor baby;
	private Boulder boulder;
	private FireToken fireToken;
	private boolean offlimits;
	private boolean babyEscape;
	private boolean occupied;
	private final String woodlandURL = "https://us.123rf.com/450wm/alexzel/alexzel2001/alexzel200100150/137974413-top-view-of-the-countryside-with-forest-grass-with-stones-and-trees.jpg?ver=6";
	ImagePattern imagePattern = new ImagePattern(new Image(woodlandURL));
	private static int spaceX;
	private static int spaceY;
	private Space[][] board = Board.getBoard();
	private int tileNumber;
	private int sciSpawnNumber;
	private boolean sciSpawnTile;
	private boolean sciReinforceSpace;
	private static int jeepMoves;
	
	public Space() {}
	
	public Space(int x, int y) {
		setWidth(Board.SPACE_SIZE);
		setHeight(Board.SPACE_SIZE);
		setOfflimits(false);
		setBabyEscape(false);	
		setOccupied(false);
		relocate(x * Board.SPACE_SIZE, y * Board.SPACE_SIZE); // What does this do?
		setSpaceX(x);
		setSpaceY(y);
		setFill(imagePattern);
		setSciReinforceSpace(false);
		
		// Assign tile numbers
		if (spaceX > 1 && spaceX < 11) 
			tileNumber = Tile.assignTileNumber(spaceX, spaceY);
		
		// Assign scientist initial spawn points
		if (spaceX == 1 || spaceX == 11) 
			sciSpawnNumber = Tile.assignScientistSpawnTile(spaceX,  spaceY);
		
		if (spaceX == 1 && (spaceY > -1 && spaceY < 6) || spaceX == 11 && (spaceY > -1 && spaceY < 6))
			setSciSpawnTile(true);
		
		// Assign scientist reinforcement points
		if (((spaceX > 1 && spaceX < 11) && spaceY == 0) || ((spaceX > 1 && spaceX < 11) && spaceY == 5)) {
			setSciReinforceSpace(true);
		}
		
		setOnMouseClicked(e -> { 
			setSpaceX(Board.toBoard(getLayoutX()));
			setSpaceY(Board.toBoard(getLayoutY()));
			System.out.println("SpaceX: " + spaceX);
			System.out.println("SpaceY: " + spaceY);
			System.out.println("TileNumber: " + tileNumber);
			System.out.println("SciSpawnNumber: " + sciSpawnNumber);
			System.out.println("SciSpawnTile? " + sciSpawnTile);
			System.out.println("SciReinforceSpace? " + sciReinforceSpace);
			System.out.println("SpaceOccupied? " + occupied);
			// Gets the selected piece from Storage to be moved

			GamePhase phase = GameLoop.getPhase();
			CardAction cardAction = GameLoop.getCardAction();
			if (!Storage.pieceStorageEmpty())
				piece = Storage.getFromPieceStorage();
			System.out.println("Piece: " + piece);
			switch (phase) {
				case PLACE_MOTHER: PlaceRaptors.placeMother(board, spaceX, spaceY); break;
				case PLACE_BABY: PlaceRaptors.placeBaby(board, spaceX, spaceY); break;
				case PLACE_INITIAL_SCIENTIST: PlaceScientists.placeInitialScientist(board, spaceX, spaceY); break;
				case REAPPEAR: 
					if (Storage.pieceStorageEmpty())
						RaptorCardAction.reappear(board, spaceX, spaceY); break;
				case RAPTOR_CARD:
					switch(cardAction) {
						case MOTHERS_CALL_1: 
							piece.setNewLocX(spaceX);
							piece.setNewLocY(spaceY);
							RaptorCardAction.motherCall1(piece, board, piece.getOldLocX(), piece.getOldLocY(), spaceX, spaceY); 
						    Storage.removeFromPieceStorage(piece); 
						    break;
						case MOTHERS_CALL_2:
							piece.setNewLocX(spaceX);
							piece.setNewLocY(spaceY);
							RaptorCardAction.motherCall2(piece, board, piece.getOldLocX(), piece.getOldLocY(), spaceX, spaceY); 
						    Storage.removeFromPieceStorage(piece); 
						    break;
					}
				case SCIENTIST_CARD:
					switch(cardAction) {
						case REINFORCE: ScientistCardAction.reinforcements(board, spaceX, spaceY); break;
						case JEEP_1: 
							setJeepMoves(2);
							if (jeepMoves > 0) {
								System.out.println("Num JeepMoves: " + jeepMoves);
								MovementRules.rookMove(piece, board, piece.getOldLocX(), piece.getOldLocY(), spaceX, spaceY);
								jeepMoves--;
								Storage.removeFromPieceStorage(piece);
							} else {
								GameLoop.setPhase(GamePhase.RAPTOR_ACTIONS);
								Storage.removeFromPieceStorage(piece);
							}
						case JEEP_2:
							setJeepMoves(4);
							if (jeepMoves > 0) {
								System.out.println("Num JeepMoves: " + jeepMoves);
								MovementRules.rookMove(piece, board, piece.getOldLocX(), piece.getOldLocY(), spaceX, spaceY);
								jeepMoves--;
								Storage.removeFromPieceStorage(piece);
							} else {
								GameLoop.setPhase(GamePhase.RAPTOR_ACTIONS);
								Storage.removeFromPieceStorage(piece);
							}
						case FIRE_1:
							ScientistCardAction.placeFire(2, board, spaceX, spaceY);
							break;
						case FIRE_2:
							ScientistCardAction.placeFire(3, board, spaceX, spaceY);
							break;
					}
				case RAPTOR_ACTIONS: 
					if (piece instanceof BabyRaptor && !Storage.pieceStorageEmpty()) {
						piece.setNewLocX(spaceX);
						piece.setNewLocY(spaceY);
						MovementRules.orthogonalMove(piece, board, piece.getOldLocX(), piece.getOldLocY(), spaceX, spaceY);
						Storage.removeFromPieceStorage(piece);
						break;
					}
					if (piece instanceof MotherRaptor && !Storage.pieceStorageEmpty()) {
						piece.setNewLocX(spaceX);
						piece.setNewLocY(spaceY);
						MovementRules.rookMove(piece, board, piece.getOldLocX(), piece.getOldLocY(), spaceX, spaceY);
						piece.setNearBaby(board);
						piece.setNearScientist(board);
						Storage.removeFromPieceStorage(piece);
						break;
					}
				case SCIENTIST_ACTIONS:
					if (piece instanceof Scientist && !Storage.pieceStorageEmpty()) {
						piece.setNewLocX(spaceX);
						piece.setNewLocY(spaceY);
						MovementRules.orthogonalMove(piece, board, piece.getOldLocX(), piece.getOldLocY(), spaceX, spaceY);
						piece.setNearBaby(board);
						Storage.removeFromPieceStorage(piece);
						break;
					}
			default:
				Storage.removeFromPieceStorage(piece);
				break;
			}
		});
	}
	
	// check if Space has piece in it
	public boolean hasPiece() {
		return piece != null;
	}
	
	public boolean hasFire()
	{
		return fireToken != null;
	}
	
	public boolean hasScientist() {
		return scientist != null;
	}
	
	public boolean hasMother() {
		return mother != null;
	}
	
	public boolean hasBaby() {
		return baby != null;
	}
	
	public boolean hasBoulder() {
		return boulder != null;
	}
	
	public void setPiece(Piece piece) {
		if (piece != null) {
			setOccupied(true);
		} else {
			setOccupied(false);
		}
		this.piece = piece;
	}
	
	public void setFireToken(FireToken fireToken) 
	{
		if (fireToken != null) 
		{
			setOccupied(true);
		} 
		else 
		{
			setOccupied(false);
		}
		this.fireToken = fireToken;
	}
	
	public void setScientist(Scientist scientist) {
		this.scientist = scientist;
	}

	public void setMother(MotherRaptor mother) {
		this.mother = mother;
		
	}
	
	public void setBaby(BabyRaptor baby) {
		this.baby = baby;
	}
	
	public boolean isOfflimits() {
		return offlimits;
	}

	public void setOfflimits(boolean offlimits) {
		this.offlimits = offlimits;
	}

	public boolean isBabyEscape() {
		return babyEscape;
	}

	public void setBabyEscape(boolean babyEscape) {
		this.babyEscape = babyEscape;
	}

	public boolean isOccupied() {
		return occupied;
	}

	public void setOccupied(boolean occupied) {
		this.occupied = occupied;
	}

	public Boulder getBoulder() {
		return boulder;
	}

	public void setBoulder(Boulder boulder) {
		if (boulder != null) {
			setOccupied(true);
		} else {
			setOccupied(false);
		}
		this.boulder = boulder;
	}
	
	public int getSpaceX() {
		System.out.println("spaceX: " + spaceX);
		return spaceX;
	}

	public void setSpaceX(int spaceX) {
		Space.spaceX = spaceX;
	}

	public int getSpaceY() {
		System.out.println("spaceY: " + spaceY);
		return spaceY;
	}

	public void setSpaceY(int spaceY) {
		Space.spaceY = spaceY;
	}

	public int getTileNumber() {
		return tileNumber;
	}

	public void setTileNumber(int tileNumber) {
		this.tileNumber = tileNumber;
	}

	public int getSciSpawnNumber() {
		return sciSpawnNumber;
	}

	public void setSciSpawnNumber(int sciSpawnNumber) {
		this.sciSpawnNumber = sciSpawnNumber;
	}

	public boolean isSciReinforceSpace() {
		return sciReinforceSpace;
	}

	public void setSciReinforceSpace(boolean sciReinforceSpace) {
		this.sciReinforceSpace = sciReinforceSpace;
	}

	public static int getJeepMoves() {
		return jeepMoves;
	}

	public static void setJeepMoves(int jeepMoves) {
		Space.jeepMoves = jeepMoves;
	}

	public boolean isSciSpawnTile() {
		return sciSpawnTile;
	}

	public void setSciSpawnTile(boolean sciSpawnTile) {
		this.sciSpawnTile = sciSpawnTile;
	}

}	
