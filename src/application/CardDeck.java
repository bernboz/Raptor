package application;

import java.util.ArrayList;
import java.util.Collections;

public class CardDeck {
	
	// Three raptor decks: the deck as a whole, the raptor's hand, and the raptor's discard pile
	
	public static ArrayList<Card> generateRaptorDeck() {
		
		ArrayList<Card> raptorDeck = new ArrayList<>();
		
		Card raptor1 = new Card(1, "raptor1", "raptor");
		Card raptor2 = new Card(2, "raptor2", "raptor");
		Card raptor3 = new Card(3, "raptor3", "raptor");
		Card raptor4 = new Card(4, "raptor4", "raptor");
		Card raptor5 = new Card(5, "raptor5", "raptor");
		Card raptor6 = new Card(6, "raptor6", "raptor");
		Card raptor7 = new Card(7, "raptor7", "raptor");
		Card raptor8 = new Card(8, "raptor8", "raptor");
		Card raptor9 = new Card(9, "raptor9", "raptor");
		
		raptorDeck.add(raptor1);
		raptorDeck.add(raptor2);
		raptorDeck.add(raptor3);
		raptorDeck.add(raptor4);
		raptorDeck.add(raptor5);
		raptorDeck.add(raptor6);
		raptorDeck.add(raptor7);
		raptorDeck.add(raptor8);
		raptorDeck.add(raptor9);
		
		//Collections.shuffle(raptorDeck);
		
		return raptorDeck;
	}
	
	public static ArrayList<Card> generateRaptorHand() {
		
		ArrayList<Card> raptorHand = new ArrayList<>();
		return raptorHand;
		
	}
	
	public static ArrayList<Card> generateRaptorDiscard() {
		
		ArrayList<Card> raptorDiscard = new ArrayList<>();
		return raptorDiscard;
		
	}
	
	// Three scientist decks: the deck as a whole, the scientist's hand, and the scientist discard pile
	public static ArrayList<Card> generateScientistDeck() {
		
		ArrayList<Card> scientistDeck = new ArrayList<>();
		
		Card scientist1 = new Card(1, "scientist1", "scientist");
		Card scientist2 = new Card(2, "scientist2", "scientist");
		Card scientist3 = new Card(3, "scientist3", "scientist");
		Card scientist4 = new Card(4, "scientist4", "scientist");
		Card scientist5 = new Card(5, "scientist5", "scientist");
		Card scientist6 = new Card(6, "scientist6", "scientist");
		Card scientist7 = new Card(7, "scientist7", "scientist");
		Card scientist8 = new Card(8, "scientist8", "scientist");
		Card scientist9 = new Card(9, "scientist9", "scientist");
		
		scientistDeck.add(scientist1);
		scientistDeck.add(scientist2);
		scientistDeck.add(scientist3);	
		scientistDeck.add(scientist4);
		scientistDeck.add(scientist5);
		scientistDeck.add(scientist6);	
		scientistDeck.add(scientist7);
		scientistDeck.add(scientist8);
		scientistDeck.add(scientist9);
		
		//Collections.shuffle(scientistDeck);
		
		return scientistDeck;
	}
	
	public static ArrayList<Card> generateScientistHand() {
		
		ArrayList<Card> scientistHand = new ArrayList<>();
		return scientistHand;
		
	}
	
	public static ArrayList<Card> generateScientistDiscard() {
		
		ArrayList<Card> scientistDiscard = new ArrayList<>();
		return scientistDiscard;
		
	}
	
}
