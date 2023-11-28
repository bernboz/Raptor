package application;

public class RaptorCardAction {
	
	private static int babyCalls;
	private static int recoveryPoints;
	private static int numFears;
	
	public static void motherCall1(Piece piece, Space[][] board, int oldX, int oldY, int newX, int newY) {
		
		System.out.println("Mothers Call 1 fired");
		
		piece.move(newX, newY);
		
		if (piece instanceof BabyRaptor) {
			board[oldX][oldY].setPiece(null);
			board[newX][newY].setPiece(piece);
			board[oldX][oldY].setBaby(null);
			board[newX][newY].setBaby((BabyRaptor) piece);
			piece.setSelected(false);
		}
		
		GameLoop.setPhase(GamePhase.SCIENTIST_ACTIONS);
	}
	
	public static void disappear(Piece piece, Space[][] board, int oldX, int oldY) {
		
		if (piece instanceof MotherRaptor) {
			board[oldX][oldY].setPiece(null);
			board[oldX][oldY].setMother(null);
			Board.getPieceGroup().getChildren().remove(piece);	
		}
		GameLoop.setPhase(GamePhase.SCIENTIST_ACTIONS);
	}
	
	public static void reappear(Space[][] board, int newX, int newY) {
		
		if (!board[newX][newY].isOccupied()) {
			Piece mother = new MotherRaptor(newX, newY);
			mother.move(newX, newY);
			board[newX][newY].setPiece(mother);
			board[newX][newY].setMother((MotherRaptor) mother);
			Board.getPieceGroup().getChildren().add(mother);	
		}
		
		GameLoop.setPhase(GamePhase.CARD_SELECT);
	}
	
	public static void fear1(Piece scientist) {
		if (scientist instanceof Scientist) {
			((Scientist) scientist).setAfraid(true);
		}
		
		GameLoop.setPhase(GamePhase.SCIENTIST_ACTIONS);
	}
	
	public static void fear2(Piece scientist) {
		
		if (numFears < 2) {
			System.out.println("Dude got scared");
			if (scientist instanceof Scientist && !((Scientist) scientist).isAfraid()) {
				((Scientist) scientist).setAfraid(true);
				numFears++;
				System.out.println("Fears: " + numFears);
			}
		} 
		
		if (numFears == 2) {
			GameLoop.setPhase(GamePhase.SCIENTIST_ACTIONS);
			System.out.println(GameLoop.getPhase());
			setNumFears(0);
		}
	}
	
	public static void motherCall2(Piece piece, Space[][] board, int oldX, int oldY, int newX, int newY) {
		
		System.out.println("Mothers Call 2 fired");
		
		piece.move(newX, newY);
		
		if (babyCalls < 1) {
			if (piece instanceof BabyRaptor) {
				board[oldX][oldY].setPiece(null);
				board[newX][newY].setPiece(piece);
				board[oldX][oldY].setBaby(null);
				board[newX][newY].setBaby((BabyRaptor) piece);
				piece.setSelected(false);
				babyCalls++;
			}		
		} else {
			GameLoop.setPhase(GamePhase.SCIENTIST_ACTIONS);
			setBabyCalls(0);
		}
	}
	
	public static void recovery(Piece piece, int recoveryPoints) {
		setRecoveryPoints(recoveryPoints);
		int tranqHits = MotherRaptor.getTranqHits();
		
		if (recoveryPoints > 0) {
			if (piece instanceof BabyRaptor) {
				if (((BabyRaptor) piece).isAsleep()){
					((BabyRaptor) piece).setAsleep(false);
					recoveryPoints--;
				}
			}
		
			if (piece instanceof MotherRaptor) {
				if (tranqHits > 0) {
					tranqHits--;
					MotherRaptor.setTranqHits(tranqHits);
					recoveryPoints--;
				}
			}
		} else {
			GameLoop.setPhase(GamePhase.SCIENTIST_ACTIONS);
			setRecoveryPoints(0);
		}
	}

	public static void setBabyCalls(int babyCalls) {
		RaptorCardAction.babyCalls = babyCalls;
	}

	public static void setRecoveryPoints(int recoveryPoints) {
		RaptorCardAction.recoveryPoints = recoveryPoints;
	}

	public static int getNumFears() {
		return numFears;
	}

	public static void setNumFears(int numFears) {
		RaptorCardAction.numFears = numFears;
	}
}
