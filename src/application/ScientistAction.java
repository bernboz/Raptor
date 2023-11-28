package application;

import java.util.ArrayList;

public class ScientistAction {
	private static int scientistActionPoints;
	private static ArrayList<Piece> storage = Storage.getPieceStorage();
	private static CardAction cardAction = GameLoop.getCardAction();
	private static int numBullets = 0;
	private static int babiesSleep = 0;
	
	public static void tranqBaby(Piece scientist, Piece baby) {
		if (scientist.isNearBaby() && ((Scientist) scientist).hasOffensiveAP() && scientistActionPoints > 0 && !((Scientist) scientist).isAfraid()) {
			System.out.println("Go to sleep, baby");
			((BabyRaptor) baby).setAsleep(true);
			((Scientist) scientist).setOffensiveAP(false);
			storage.clear();
			scientistActionPoints--;
			
			
			ScientistAction.setScientistActionPoints(scientistActionPoints);
			if (scientistActionPoints == 0) 
				if (cardAction == CardAction.DIS_AND_OBS) {
					GameLoop.setPhase(GamePhase.REAPPEAR);
				} else {
					GameLoop.setPhase(GamePhase.CARD_SELECT);
				}
		} else {
			storage.clear();
		}
	}
	
	public static void captureBaby(Scientist scientist, BabyRaptor baby, Space[][] board) {
		
		//scientist = (Scientist) scientist;
		
		if (scientist.isNearBaby() && scientist.hasOffensiveAP() && scientistActionPoints > 0 && !((Scientist) scientist).isAfraid()) {
			System.out.println("I got you, baby");
			int locX = baby.getOldLocX();
			int locY = baby.getOldLocY();
			System.out.println("locX: " + locX);
			System.out.println("locY: " + locY);
			board[locX][locY].setPiece(null);
			board[locX][locY].setBaby(null);
			Board.getPieceGroup().getChildren().remove(baby);
			storage.clear();
			scientist.setOffensiveAP(false);
			scientistActionPoints--;
			
			System.out.println("entering capturebaby");
			//adds capturedbabys to the sideuis
			SideUI leftUI = UI.getLeftUI();
			UI.changeBabySquares(leftUI, babiesSleep);
			
			ScientistAction.setScientistActionPoints(scientistActionPoints);
			if (scientistActionPoints == 0) 
				if (cardAction == CardAction.DIS_AND_OBS) {
					GameLoop.setPhase(GamePhase.REAPPEAR);
				} else {
					GameLoop.setPhase(GamePhase.CARD_SELECT);
				}
		} else {
			storage.clear();
		}	
	}
	
	public static void tranqMother(Scientist scientist, MotherRaptor mother, Space[][] board, int sciLocX, int sciLocY, int momLocX, int momLocY) {
		int i;
		boolean lineOfSight = true;
		System.out.println("ScientistX: " + sciLocX);
		System.out.println("ScientistY: " + sciLocY);
		System.out.println("MotherX: " + momLocX);
		System.out.println("MotherY: " + momLocY);
		// Check that horizontal Line of Sight is clear (can see through fire token)
		// TODO: Be able to see over scared scientists and baby raptors
		if (scientist.hasOffensiveAP() && scientistActionPoints > 0 && !((Scientist) scientist).isAfraid()) {
			if (sciLocX != momLocX) {
				int directionX = (sciLocX < momLocX) ? 1 : -1;
				System.out.println("DirectionX: " + directionX);
				for (i = sciLocX + directionX; i != momLocX; i += directionX) {
					if (board[i][momLocY].isOccupied()){
						System.out.println("iX: " + i);
						lineOfSight = false;
						System.out.println("Line of sightX: " + lineOfSight);
					}
				}
			}
			// Check that vertical line of sight is clear (can see through fire token)
			// TODO: Be able to see over scared scientists and baby raptors
			if (sciLocY != momLocY) {
				int directionY = (sciLocY < momLocY) ? 1 : -1;
				System.out.println("DirectionY: " + directionY);
				for (i = sciLocY + directionY; i != momLocY; i += directionY) {
					System.out.println("Hello?");
					if (board[sciLocX][i].isOccupied()){
						System.out.println("iY: " + i);
						lineOfSight = false;
						System.out.println("Line of sightY: " + lineOfSight);
					}
				}
			}
			System.out.println("Check Line of sight: " + lineOfSight);
			if (lineOfSight) {
				mother.addTranqHit();
				storage.clear();
				scientist.setOffensiveAP(false);
				System.out.println("Mother hits: " + MotherRaptor.getTranqHits());
				scientistActionPoints--;
				
				//adds the bullets to the individual sideuis used for testing
				SideUI leftUI = UI.getLeftUI();
				SideUI rightUI = UI.getRightUI();
				System.out.println("entering tranqmother");
				UI.setUpBullets(leftUI, numBullets);
				UI.setUpBullets(rightUI, numBullets);
				System.out.println("numBullets = " + numBullets);
				numBullets++;
				System.out.println("numBullets = " + numBullets);
				
				ScientistAction.setScientistActionPoints(scientistActionPoints);
				if (scientistActionPoints == 0) 
					if (cardAction == CardAction.DIS_AND_OBS) {
						GameLoop.setPhase(GamePhase.REAPPEAR);
					} else {
						GameLoop.setPhase(GamePhase.CARD_SELECT);
					}
			} else {
				storage.clear();
			}
		}
	}
	
	public static void unFearScientist(Piece scientist) {
		System.out.println("No worries, we got this");
		if (((Scientist) scientist).isAfraid() && scientistActionPoints > 0) {
			((Scientist) scientist).setAfraid(false);
			scientistActionPoints--;
			if (scientistActionPoints == 0) 
				if (cardAction == CardAction.DIS_AND_OBS) {
					GameLoop.setPhase(GamePhase.REAPPEAR);
				} else {
					GameLoop.setPhase(GamePhase.CARD_SELECT);
				}
			storage.clear();
		} else {
			storage.clear();
		}
	}

	public static int getScientistActionPoints() {
		return scientistActionPoints;
	}


	public static void setScientistActionPoints(int actionPoints) {
		ScientistAction.scientistActionPoints = actionPoints;
	}
	
	public static void setNumBullets(int x)
	{
		ScientistAction.numBullets = x;
	}
	
	public static int getNumBullets()
	{
		return ScientistAction.numBullets;
	}
}

