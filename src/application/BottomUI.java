package application;

import java.util.ArrayList;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;

public class BottomUI extends HBox {

	private static int numInScientistHand;
	private static int numInRaptorHand;
	private static HBox scientistCardsBox = new HBox(15);
	private static HBox raptorCardsBox = new HBox(15);
	private static String actionText;
	private static Label actionLabel = new Label();
	private static ArrayList<Card> scientistDeck = CardDeck.generateScientistDeck();
	private static ArrayList<Card> raptorDeck = CardDeck.generateRaptorDeck();
	
	public BottomUI() {
		
		numInScientistHand = 0;
		while (numInScientistHand != 3) {
			System.out.println("Add card");
			scientistCardsBox.getChildren().add(scientistDeck.get(0));
			scientistDeck.remove(0);
			numInScientistHand++;
		}

		numInRaptorHand = 0;
		while (numInRaptorHand != 3) {
			raptorCardsBox.getChildren().add(raptorDeck.get(0));
			raptorDeck.remove(0);
			numInRaptorHand++;
		}
		
		Pane actionBox = new Pane();
		actionBox.setPrefSize(1000, 210);
		actionText = "placeholder";
		actionLabel.setText(actionText);
		actionBox.getChildren().add(actionLabel);
		
		scientistCardsBox.setAlignment(Pos.CENTER);
		raptorCardsBox.setAlignment(Pos.CENTER);
		actionLabel.setAlignment(Pos.CENTER);
		
		
		
		getChildren().addAll(scientistCardsBox, actionBox, raptorCardsBox);
		
	}
	
	public static void setNumInScientistHand(int numInScientistHand) {
		BottomUI.numInScientistHand = numInScientistHand;
	}
	
	public static int getNumInScientistHand() {
		return numInScientistHand;
	}
	
	public static void setNumInRaptorHand(int numInRaptorHand) {
		BottomUI.numInRaptorHand = numInRaptorHand;
	}
	
	public static int getNumInRaptorHand() {
		return numInRaptorHand;
	}
	
	public static HBox getScientistCardsBox() {
		return scientistCardsBox;
	}
	
	public static HBox getRaptorCardsBox() {
		return raptorCardsBox;
	}

	
	public static ArrayList<Card> getScientistDeck() {
		return scientistDeck;
	}
	
	public static ArrayList<Card> getRaptorDeck() {
		return raptorDeck;
	}
	
	public static void setActionText(String newText) {
		actionText = newText;
		actionLabel.setText(actionText);
		if(GameLoop.getPhase() == GamePhase.PLACE_BOULDER)
		{
			actionLabel.setTranslateY(90);
			actionLabel.setTranslateX(170);
			actionLabel.setFont(new Font(16));
		}
		System.out.println(BottomUI.actionText);
		System.out.println(actionText);
	}
	
	public static void formatActionText(int x, int y)
	{
		actionLabel.setTranslateX(x);
		actionLabel.setTranslateY(y);
	}

}
