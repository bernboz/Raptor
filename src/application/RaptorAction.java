package application;

import java.util.ArrayList;

public class RaptorAction {
	private static int raptorActionPoints;
	private static ArrayList<Piece> storage = Storage.getPieceStorage();

	public static void eatScientist(Piece mother, Piece scientist, Space[][] board, int locX, int locY) {
		// If mother raptor is near scientist, mother raptor can eat scientist
		if(mother.isNearScientist() && raptorActionPoints > 0) {
			System.out.println("Food time started");
			locX = scientist.getOldLocX();
			locY = scientist.getOldLocY();
			board[locX][locY].setPiece(null);
			board[locX][locY].setScientist(null);
			Board.getPieceGroup().getChildren().remove(scientist);
			storage.clear();
			raptorActionPoints--;
			if (raptorActionPoints == 0) 
				GameLoop.setPhase(GamePhase.CARD_SELECT);
		} else {
			storage.clear();
		}
	}
	
	public static void wakeBaby(Piece mother, Piece baby) {
		System.out.println("Wake up, baby");
		if (mother.isNearBaby() && ((BabyRaptor) baby).isAsleep() && raptorActionPoints > 0) {
			((BabyRaptor) baby).setAsleep(false);
			storage.clear();
			raptorActionPoints--;
			if (raptorActionPoints == 0) 
				GameLoop.setPhase(GamePhase.CARD_SELECT);
		} else {
			storage.clear();
		}
	}

	public static int getRaptorActionPoints() {
		return raptorActionPoints;
	}

	public static void setRaptorActionPoints(int raptorActionPoints) {
		System.out.println("Set called" + raptorActionPoints);
		RaptorAction.raptorActionPoints = raptorActionPoints;
	}
	
}
