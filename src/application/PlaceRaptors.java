package application;

public class PlaceRaptors {
	private static int numBabies;
	private static int numMother;
	private static int motherTile;
	private static int tileNumber;
	 
	public static void placeMother(Space[][] board, int x, int y) {
		//TODO: Highlight spaces on board that mother raptor can be placed
		// Place one mother raptor on the board
		numMother = GameLoop.getNumMother(); // May be able to delete this. 
		tileNumber = board[x][y].getTileNumber();
		
		if (!board[x][y].isOccupied() && (tileNumber == 3 || tileNumber == 4)) {
			Piece mother = new MotherRaptor(x, y);
			board[x][y].setPiece(mother);
			Board.getPieceGroup().getChildren().add(mother);
			GameLoop.setNumMother(numMother++);
			if (tileNumber == 3) {
				setMotherTile(3); 
			} else {
				setMotherTile(4);
			}
			GameLoop.setPhase(GamePhase.PLACE_BABY); // Sets the next phase of the placements
		}
	}
	
	public static void placeBaby(Space[][] board, int x, int y) {
		//TODO: Highlight spaces on board that babies can be placed
		// Place five baby raptors on the board	
		motherTile = getMotherTile(); // If tile selected is not motherTile, valid placement
		tileNumber = board[x][y].getTileNumber();
		System.out.println("NumBabies: " + numBabies);
		
		if (!board[x][y].isOccupied() && tileNumber != motherTile) {			
			if (numBabies == 0 && tileNumber == 1) {
				generateBaby(board, x, y);
				numBabies++;
			}
			if (numBabies == 1 && tileNumber == 2) {
				generateBaby(board, x, y);
				numBabies++;
			}
			if (numBabies == 2 && (tileNumber == 3 || tileNumber == 4)) {
				generateBaby(board, x, y);
				numBabies++;
			}
			if (numBabies == 3 && tileNumber == 5) { 
				generateBaby(board, x, y);
				numBabies++;
			}
			if (numBabies == 4 && tileNumber == 6) { 
				generateBaby(board, x, y);
				numBabies++;
			}
			
			if (numBabies > 4) 
				GameLoop.setPhase(GamePhase.PLACE_INITIAL_SCIENTIST);
		}
	}

	// Used for future card, don't delete
	public static int getMotherTile() {
		return motherTile;
	}

	public static void setMotherTile(int motherTile) {
		PlaceRaptors.motherTile = motherTile;
	}
	
	public static void generateBaby(Space[][] board, int spaceX, int spaceY) {
		Piece baby = new BabyRaptor(spaceX, spaceY);
		board[spaceX][spaceY].setPiece(baby);
		Board.getPieceGroup().getChildren().add(baby);
	}
}
