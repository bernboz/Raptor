package application;

public class MovementRules {
	private static boolean validMove;
	private static CardAction cardAction = GameLoop.getCardAction();
	
	public static boolean offLimitsCheck(Space[][] board, int x, int y) {
		// Checks if piece is within non-blacked our spaces and on the board
		validMove = true;
		if ((x < 0 || x > 12) || (y < 0 || y > 5) || (board[x][y].isOfflimits())) {
			validMove = false;
		}
		return validMove;
	}
	
	public static boolean occupiedCheck(Space[][]board, int x, int y) {
		// Checks if piece lands on an occupied space
		validMove = true;
		if (board[x][y].isOccupied()) {
			validMove = false;
		}
		return validMove;
	}
	
	public static boolean escapeSpaceCheck(Space[][] board, int x, int y) {
		// Checks if piece lands on an escape space; can only be landed on by BabyRaptor
		validMove = true;
		if(board[x][y].isBabyEscape()) {
			validMove = false;
		}
		return validMove;
	}
	
	public static boolean orthogonalMoveCheck(int oldX, int oldY, int newX, int newY) {
		// Checks if piece makes a one space/straight line movement
		validMove = true;
		if ((oldX - newX > 1) || (oldX - newX < -1) ||
			(oldY - newY > 1) || (oldY - newY < -1) ||
			(oldX != newX) && (oldY != newY)) {
			validMove = false;
		}
		return validMove;
	}
	
	public static boolean rookMoveCheck(Space[][] board, int oldX, int oldY, int newX, int newY) {
		// Checks if the rook move has any obstacles in the way
		validMove = true;
		if ((oldX != newX) && (oldY != newY)) {
			validMove = false;
		}
		
		int i;
		// Check that horizontal movement is clear
		if (oldX != newX) {
			int directionX = (oldX < newX) ? 1 : -1;
			for (i = oldX + directionX; i != newX; i += directionX) {
				if (board[i][newY].isOccupied()) {
					validMove = false;
				}
			}
		}
		// Check that vertical movement is clear
		if (oldY != newY) {
			int directionY = (oldY < newY) ? 1 : -1;
			for (i = oldY + directionY; i != newY; i += directionY) {
				if (board[newX][i].isOccupied()) {
					validMove = false;
				}
			}
		}		
		return validMove;
	}
	
	public static void orthogonalMove(Piece piece, Space[][] board, int oldX, int oldY, int newX, int newY) {
		// USED IN SPACE CLASS
		// Method to move a Scientist or BabyRaptor 
		System.out.println("Moving: " + piece);
		int raptorActionPoints = RaptorAction.getRaptorActionPoints();
		int scientistActionPoints = ScientistAction.getScientistActionPoints();
		System.out.println("Raptor actions: " + raptorActionPoints);
		validMove = true;
		// Valid move checks
		while(validMove) {
			validMove = offLimitsCheck(board, newX, newY);
			if(!validMove)
				break;
			validMove = occupiedCheck(board, newX, newY);
			if(!validMove)
				break;
			if(piece instanceof Scientist) {
				validMove = escapeSpaceCheck(board, newX, newY);
				if(!validMove)
					break;
				// Special check that sees if scientist has been frightened.
				// If scientist is afraid, no movement allowed
				validMove = !((Scientist) piece).isAfraid();
				if(!validMove)
					break;
			}
			if(piece instanceof Scientist || piece instanceof BabyRaptor) {
				validMove = orthogonalMoveCheck(oldX, oldY, newX, newY);
				if(!validMove)
					break;
			}
			if(piece instanceof BabyRaptor) {
				// Special check that sees if BabyRaptor is asleep before it can move
				// If it is asleep, no movement allowed
				validMove = !((BabyRaptor) piece).isAsleep();
				if(!validMove)
					break;
			}
//			if(piece instanceof Scientist) {
//				// Special check that sees if scientist has been frightened.
//				// If scientist is afraid, no movement allowed
//				validMove = !((Scientist) piece).isAfraid();
//				if(!validMove)
//					break;
//			}
			break;
		}
		// If validMove is false, piece will be returned to original position
		if(!validMove) {
			newX = oldX;
			newY = oldY;
		}
		
		// Moves piece to new location
		piece.move(newX, newY);
		
		// Old location is set to null. Piece is set to new space.
		// Piece is used for checking obstacles.
		board[oldX][oldY].setPiece(null);
		board[newX][newY].setPiece(piece);
		
		// The following removes piece from old space and sets piece in new space
		if (piece instanceof Scientist) {
			//piece.setNearBaby(board);                               
			board[oldX][oldY].setScientist(null);            
			board[newX][newY].setScientist((Scientist) piece);
			piece.setSelected(false);
			scientistActionPoints--;
			ScientistAction.setScientistActionPoints(scientistActionPoints);
			if (scientistActionPoints == 0) 
				if (cardAction == CardAction.DIS_AND_OBS) {
					GameLoop.setPhase(GamePhase.REAPPEAR);
				} else {
					GameLoop.setPhase(GamePhase.CARD_SELECT);
				}
			
		}else if (piece instanceof BabyRaptor) {
			board[oldX][oldY].setBaby(null);
			board[newX][newY].setBaby((BabyRaptor) piece);
			piece.setSelected(false);
			raptorActionPoints--;
			RaptorAction.setRaptorActionPoints(raptorActionPoints);
			if (raptorActionPoints == 0) 
				GameLoop.setPhase(GamePhase.CARD_SELECT);
			
		}
		
	}
	
	public static void rookMove(Piece piece, Space[][] board, int oldX, int oldY, int newX, int newY) {
		// USED IN SPACE CLASS
		// Method used to move mother raptor or when a scientist uses a jeep card
		System.out.println("Moving: " + piece);
		int raptorActionPoints = RaptorAction.getRaptorActionPoints();
		System.out.println("Raptor actions: " + raptorActionPoints);
		validMove = true;
		
		// Valid move checks
		while(validMove) {
			validMove = offLimitsCheck(board, newX, newY);
			if(!validMove)
				break;
			validMove = occupiedCheck(board, newX, newY);
			if(!validMove)
				break;
			validMove = escapeSpaceCheck(board, newX, newY);
			if(!validMove)
				break;
			validMove = rookMoveCheck(board, oldX, oldY, newX, newY);
			if(!validMove)
				break;
			break;
		}
		
		// If validMove is false, piece will be returned to original position 
		if(!validMove || raptorActionPoints < 1) {
			newX = oldX;
			newY = oldY;
		}
		
		// Moves piece to new location
		piece.move(newX, newY);
		
		// Old location is set to null. Piece is set to new space.
		// Piece is used for checking obstacles.
		board[oldX][oldY].setPiece(null);
		board[newX][newY].setPiece(piece);
		
		// Removes MotherRaptor from old space and sets it in new space
		if (piece instanceof MotherRaptor) {
			board[oldX][oldY].setMother(null);
			board[newX][newY].setMother((MotherRaptor) piece);
			piece.setSelected(false);
			raptorActionPoints--;
			RaptorAction.setRaptorActionPoints(raptorActionPoints);
			if (raptorActionPoints == 0) 
				GameLoop.setPhase(GamePhase.CARD_SELECT);
		}
		
		if(piece instanceof Scientist) {
			board[oldX][oldY].setScientist(null);
			board[newX][newY].setScientist((Scientist) piece);
			piece.setSelected(false);
		}
	}
		
}
