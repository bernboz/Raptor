package application;

public class PlaceScientists {
	
	private static int numScientists;
	private static int numSpawnScientists;
	
	public static void placeInitialScientist(Space[][] board, int x, int y) {
		// Place four initial scientists
		int tileNumber = board[x][y].getSciSpawnNumber();
		if (!board[x][y].isOccupied()) {
			
			if(numSpawnScientists == 0 && tileNumber == 1) {
				generateScientist(board, x, y);
				numSpawnScientists++;
			}
			if(numSpawnScientists == 1 && tileNumber == 2) {
				generateScientist(board, x, y);
				numSpawnScientists++;
			}
			if(numSpawnScientists == 2 && tileNumber == 3) {
				generateScientist(board, x, y);
				numSpawnScientists++;
			}
			if(numSpawnScientists == 3 && tileNumber == 4) {
				generateScientist(board, x, y);
				numSpawnScientists++;
			}
			
			if(numSpawnScientists > 3) {
				GameLoop.setPhase(GamePhase.CARD_SELECT); // Sets the next phase. The testing phase is a placeholder
			}
			System.out.println("Number of Scientists: " + numScientists);
		}
	}
	
	public static void generateScientist(Space[][] board, int spaceX, int spaceY) {
		Piece scientist = new Scientist(spaceX, spaceY);
		scientist.move(spaceX, spaceY);
		board[spaceX][spaceY].setPiece(scientist);
		board[spaceX][spaceY].setScientist((Scientist) scientist);
		Board.getPieceGroup().getChildren().add(scientist);
	}
}
