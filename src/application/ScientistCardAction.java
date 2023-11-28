package application;

public class ScientistCardAction {
	
	private static int numScientists;
	private static int gasFires;
	private static int numFires;
	
	public static void sleepingGas1(Piece baby) {
		System.out.println("Sleeping gas 1 activated");
		
		if (!((BabyRaptor) baby).isAsleep()) 
			((BabyRaptor) baby).setAsleep(true);
		
		GameLoop.setPhase(GamePhase.RAPTOR_ACTIONS);
	}
	
	public static void sleepingGas2(Piece baby) {
		
		if (gasFires < 2) {
			if (!((BabyRaptor) baby).isAsleep()) {
				((BabyRaptor) baby).setAsleep(true);
				gasFires++;
			}
		} else {
			GameLoop.setPhase(GamePhase.RAPTOR_ACTIONS);
			gasFires = 0;
			System.out.println("Gasfires: " + gasFires);
		}
	}
	
	public static void reinforcements(Space[][] board, int x, int y) {
		System.out.println("Numscientists" + numScientists);
		if (numScientists < 2) {
			if (board[x][y].isSciReinforceSpace() && !board[x][y].isOccupied()) {
				PlaceScientists.generateScientist(board, x, y);
				numScientists++;
			}
		} else {
			GameLoop.setPhase(GamePhase.RAPTOR_ACTIONS);
			numScientists = 0;
		}
	}
	
	public static void placeFire(int maxFires, Space[][] board, int x, int y) {
	
		if(numFires < maxFires) {
			if (!board[x][y].isSciSpawnTile()) {
				System.out.println("Placing fire");
				FireToken.PlaceFireToken(board, x, y);
				numFires++;
			}
		}
		
		if(numFires == maxFires) {
			GameLoop.setPhase(GamePhase.RAPTOR_ACTIONS);
			numFires = 0;
		}
	}
	
	public static int getNumScientists() {
		return numScientists;
	}
	
	public static void setNumScientists(int numScientists) {
		ScientistCardAction.numScientists = numScientists;
	}

	public static int getGasFires() {
		return gasFires;
	}

	public static void setGasFires(int gasFires) {
		ScientistCardAction.gasFires = gasFires;
	}
	
	public static void setNumFires(int numFires) {
		ScientistCardAction.numFires = numFires;
	}
}
