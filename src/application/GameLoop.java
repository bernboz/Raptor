package application;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

public class GameLoop {
	private static boolean isScientistPlayer;
	private static boolean scientistTurn;
	private static boolean scientistVictory;
	private static boolean raptorVictory;
	private static int xLoc;
	private static int yLoc;

	private static GamePhase phase;
	private static CardAction cardAction;
	
	private static int numMother;
	private static int numBabies;
	private static int numScientists;
	
	public static void gameLoop() {
		
		Space[][] board = Board.getBoard();
		System.out.println("Is Scientist player? " + isScientistPlayer);
		setNumMother(0);
		setNumBabies(0);
		setNumScientists(0);
		BottomUI.setActionText("Click 'New Board' to randomly generate rocks. Click 'Continue' to place pieces");
		// Add boulders to the board in randomly generated locations
		setPhase(GamePhase.PLACE_BOULDER);
		Boulder.placeBoulders(board);
		
		
		System.out.println("Num Mothers: " + getNumMother());
		System.out.println("Num Babies: " + getNumBabies());
		System.out.println("Num Scientists: " + getNumScientists());

	}

	public boolean isScientistTurn() {
		return scientistTurn;
	}

	public boolean isScientistVictory() {
		return scientistVictory;
	}

	public boolean isRaptorVictory() {
		return raptorVictory;
	}

	public static boolean isScientistPlayer() {
		return isScientistPlayer;
	}

	public static void setScientistPlayer(boolean isScientistPlayer) {
		GameLoop.isScientistPlayer = isScientistPlayer;
	}

	public static GamePhase getPhase() {
		return phase;
	}

	public static void setPhase(GamePhase phase) {
		GameLoop.phase = phase;
	}

	public static int getNumMother() {
		return numMother;
	}

	public static void setNumMother(int numMother) {
		GameLoop.numMother = numMother;
	}
	
	public static int getNumBabies() {
		return numBabies;
	}
	
	public static void setNumBabies(int numBabies) {
		GameLoop.numBabies = numBabies;
	}

	public static int getNumScientists() {
		return numScientists;
	}

	public static void setNumScientists(int numScientists) {
		GameLoop.numScientists = numScientists;
	}

	public static CardAction getCardAction() {
		return cardAction;
	}

	public static void setCardAction(CardAction cardAction) {
		GameLoop.cardAction = cardAction;
	}


}
