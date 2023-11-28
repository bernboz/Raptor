package application;

import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Collections;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class Card extends Rectangle {

	private int cardValue;
	private String imageLink;
	private ImagePattern cardImage;
	private String team;
	private final int CARD_HEIGHT = 200;
	private final int CARD_WIDTH = 125;
	private ArrayList<Card> cardStorage = Storage.getCardStorage();
	ArrayList<Card> scientistDiscard = new ArrayList<>();
	ArrayList<Card> raptorDiscard = new ArrayList<>();
	
	public Card(int cardValue, String imageLink, String team) {
		setWidth(CARD_WIDTH);
		setHeight(CARD_HEIGHT);
		this.cardValue = cardValue;
		this.imageLink = imageLink;
		this.team = team;
		try {
			cardImage = getCardImage(imageLink);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		setFill(cardImage);
		
		setOnMouseClicked(e -> {
			GamePhase phase = GameLoop.getPhase();
			HBox raptorBox = BottomUI.getRaptorCardsBox();
			HBox scientistBox = BottomUI.getScientistCardsBox();
			ArrayList<Card> scientistDeck = BottomUI.getScientistDeck();
			ArrayList<Card> raptorDeck = BottomUI.getRaptorDeck();
			System.out.println("Card value: " + cardValue);
			System.out.println("Team: " + team);
			
			if (phase == GamePhase.CARD_SELECT) {
				Storage.setCard(this);
				Storage.addToCardStorage();
				
				while (cardStorage.size() == 2) {
					Card card1 = cardStorage.get(0);
					System.out.println("Card1 team: " + card1.team);
					Card card2 = cardStorage.get(1);
					System.out.println("Card2 team: " + card2.team);
					
					if (card1.team != "scientist" || card2.team != "raptor") {
						cardStorage.clear();
						continue;
					}
					
					if (card1.cardValue > card2.cardValue) {
						int scientistActionPoints = calculateActionPoints(card1, card2);
						ScientistAction.setScientistActionPoints(scientistActionPoints);
						GameLoop.setPhase(GamePhase.RAPTOR_CARD);
						setCardAction(card2);
						CardAction card = GameLoop.getCardAction();
						if (card == CardAction.DIS_AND_OBS) {
							GameLoop.setPhase(GamePhase.DISAPPEAR);
						}
						setActionText();
						System.out.println("CardPhase: " + card);
						cardStorage.clear();
						scientistBox.getChildren().remove(card1);
						if (scientistDeck.size() != 0) {
							scientistBox.getChildren().add(scientistDeck.get(0));
							scientistDeck.remove(0);
							scientistDiscard.add(card1);
							raptorBox.getChildren().remove(card2);
						} else {
							discardToDeck(scientistDeck, scientistDiscard);
							scientistBox.getChildren().add(scientistDeck.get(0));
							scientistDeck.remove(0);
							scientistDiscard.add(card1);
							raptorBox.getChildren().remove(card2);
						}
						if (raptorDeck.size() != 0) {
							raptorBox.getChildren().add(raptorDeck.get(0));
							raptorDeck.remove(0);
							raptorDiscard.add(card2);
							phase = GameLoop.getPhase();
							System.out.println("Phase: " + phase);
							break;
						} else {
							discardToDeck(raptorDeck, raptorDiscard);
							raptorBox.getChildren().add(raptorDeck.get(0));
							raptorDeck.remove(0);
							raptorDiscard.add(card2);
							phase = GameLoop.getPhase();
							System.out.println("Phase: " + phase);
							break;
						}
						
						
					} else if (card1.cardValue < card2.cardValue) {
						int raptorActionPoints = calculateActionPoints(card1, card2);
						RaptorAction.setRaptorActionPoints(raptorActionPoints);
						GameLoop.setPhase(GamePhase.SCIENTIST_CARD);
						setCardAction(card1);
						CardAction card = GameLoop.getCardAction();
						setActionText();
						System.out.println("CardPhase: " + card);
						cardStorage.clear();
						scientistBox.getChildren().remove(card1);
						System.out.println("Scientist Deck size: " + scientistDeck.size());
						if (scientistDeck.size() != 0) {
							scientistBox.getChildren().add(scientistDeck.get(0));
							scientistDeck.remove(0);
							scientistDiscard.add(card1);
						} else {
							discardToDeck(scientistDeck, scientistDiscard);
							scientistBox.getChildren().add(scientistDeck.get(0));
							scientistDeck.remove(0);
							scientistDiscard.add(card1);
						}
						raptorBox.getChildren().remove(card2);
						if (raptorDeck.size() != 0) {
							raptorBox.getChildren().add(raptorDeck.get(0));
							raptorDeck.remove(0);
							raptorDiscard.add(card2);
							phase = GameLoop.getPhase();
							System.out.println("Phase: " + phase);
							break;
						} else {
							discardToDeck(raptorDeck, raptorDiscard);
							raptorBox.getChildren().add(raptorDeck.get(0));
							raptorDeck.remove(0);
							raptorDiscard.add(card2);
							phase = GameLoop.getPhase();
							System.out.println("Phase: " + phase);
							break;
						}
					} else {
						cardStorage.clear();
						scientistBox.getChildren().remove(card1);
						System.out.println("Scientist Deck size: " + scientistDeck.size());
						if (scientistDeck.size() != 0) {
							scientistBox.getChildren().add(scientistDeck.get(0));
							scientistDiscard.add(card1);
							scientistDeck.remove(0);
						} else {
							discardToDeck(scientistDeck, scientistDiscard);
							scientistBox.getChildren().add(scientistDeck.get(0));
							scientistDiscard.add(card1);
							scientistDeck.remove(0);
						}
						if (raptorDeck.size() != 0) {
							raptorBox.getChildren().remove(card2);
							raptorBox.getChildren().add(raptorDeck.get(0));
							raptorDiscard.add(card2);
							raptorDeck.remove(0);
							break;
						} else {
							discardToDeck(raptorDeck, raptorDiscard);
							raptorBox.getChildren().remove(card2);
							raptorBox.getChildren().add(raptorDeck.get(0));
							raptorDiscard.add(card2);
							raptorDeck.remove(0);
							break;
						}
					}
				}
			}
		});
	}

	public int getCardValue() {
		return cardValue;
	}

	public void setCardValue(int value) {
		this.cardValue = value;
	}

	public String getImageLink() {
		return imageLink;
	}

	public void setImageLink(String imageLink) {
		this.imageLink = imageLink;
	}

	public ImagePattern getCardImage(String imageLink) throws MalformedURLException {	
		ImagePattern cardImage = new ImagePattern(new Image(new File("res\\cards\\" + imageLink + ".png").toURI().toURL().toString()));
		return cardImage;
	}

	public String getTeam() {
		return team;
	}

	public void setTeam(String team) {
		this.team = team;
	}
	
	public static int calculateActionPoints(Card scientistCard, Card raptorCard) {
		int actionPoints = 0;
		int sciCardValue = scientistCard.cardValue;
		int rapCardValue = raptorCard.cardValue;
		
		if (sciCardValue > rapCardValue) {
			actionPoints = sciCardValue - rapCardValue;
		} else
			actionPoints = rapCardValue - sciCardValue;
		
		System.out.println("Action points: " + actionPoints);
		return actionPoints;
	}
	
	public static void setCardAction(Card card) {
		if (card.team == "scientist") {
			switch (card.cardValue) {
				case 1: GameLoop.setCardAction(CardAction.SLEEPING_GAS_1); break;
				case 2: GameLoop.setCardAction(CardAction.REINFORCE); break;
				case 3: GameLoop.setCardAction(CardAction.JEEP_1); break;
				case 4: GameLoop.setCardAction(CardAction.SLEEPING_GAS_2); break;
				case 5: GameLoop.setCardAction(CardAction.FIRE_1); break;
				case 6: GameLoop.setCardAction(CardAction.REINFORCE); break;
				case 7: GameLoop.setCardAction(CardAction.FIRE_2); break;
				case 8: GameLoop.setCardAction(CardAction.JEEP_2); break;
			}
		}
		
		if (card.team == "raptor") {
			switch (card.cardValue) {
				case 1: GameLoop.setCardAction(CardAction.MOTHERS_CALL_1); break;
				case 2: GameLoop.setCardAction(CardAction.DIS_AND_OBS); break;
				case 3: GameLoop.setCardAction(CardAction.FEAR_1); break;
				case 4: GameLoop.setCardAction(CardAction.MOTHERS_CALL_2); break;
				case 5: GameLoop.setCardAction(CardAction.RECOVERY_1); break;
				case 6: GameLoop.setCardAction(CardAction.DIS_AND_OBS); break;
				case 7: GameLoop.setCardAction(CardAction.RECOVERY_2); break;
				case 8: GameLoop.setCardAction(CardAction.FEAR_2); break;
			}
		}
	}
	
	public static void discardToDeck(ArrayList<Card> deck, ArrayList<Card> discard) {
		System.out.println("Discard size: " + discard.size());
		Card insertCard;
		for (int i = 0; i < discard.size(); i++) {
			insertCard = discard.get(0);
			deck.add(insertCard);
			discard.remove(0);
		}
		
		Collections.shuffle(deck);
	}
	
	public static void setActionText()
	{
		//Sets the action text box in the middle of the bottom ui for the scientist card actions
		if(GameLoop.getCardAction() == CardAction.SLEEPING_GAS_1)
		{
			BottomUI.setActionText("Put one baby raptor to sleep. The baby raptor must be located on the same tile as a \r\n"
					+ "scientist or on a neighboring tile. Lay down the baby raptor figurine.");
		}
		else if(GameLoop.getCardAction() == CardAction.REINFORCE)
		{
			BottomUI.setActionText("Place one or two scientists from your reserve (if you have any left) on empty spaces \r\n"
					+ "located alongside the long edges of the board");
		}
		else if(GameLoop.getCardAction() == CardAction.JEEP_1)
		{
			BottomUI.setActionText("Move 1 or 2 scientists as many spaces as you like in a straight line as long as they don't \r\n"
					+ "run into an obstacle. If a scientist drives over a fire token, the fire is put down and the token is removed.");
		}
		else if(GameLoop.getCardAction() == CardAction.SLEEPING_GAS_2)
		{
			BottomUI.setActionText("Put one or two baby raptors to sleep. The baby raptors must be located on the same tile as a \r\n"
					+ "scientist or on a neighboring tile.");
		}
		else if(GameLoop.getCardAction() == CardAction.FIRE_1)
		{
			BottomUI.setActionText("Place 2 fire tokens on free spaces of the board. A fire token can only be placed adjacent to a \r\n"
					+ "scientist or to an existing fire token.");
		}
		else if(GameLoop.getCardAction() == CardAction.FIRE_2)
		{
			BottomUI.setActionText("Place 3 fire tokens on free spaces of the board. A fire token can only be placed adjacent to a \r\n"
					+ "scientist or to an existing fire token.");
		}
		else if(GameLoop.getCardAction() == CardAction.JEEP_2)
		{
			BottomUI.setActionText("Move up to 4 scientists as many spaces as you like in a straight line as long as they don't \r\n"
					+ "run into an obstacle. If a scientist drives over a fire token, the fire is put down and the token is removed.");
		}
		//sets the action text for the raptor team card actions
		else if(GameLoop.getCardAction() == CardAction.MOTHERS_CALL_1)
		{
			BottomUI.formatActionText(60, 70);
			BottomUI.setActionText("Move a baby onto a free space of your choice on the tile where the mother is located. \r\n"
					+ "This move must be carried out without crossing spaces that are occupied or on which there is a fire token.");
		}
		else if(GameLoop.getCardAction() == CardAction.DIS_AND_OBS)
		{
			BottomUI.formatActionText(60, 70);
			BottomUI.setActionText("Remove the mother from the board. Then after the Scientist player has used up all of their \r\n"
					+ "action points, place her back on a free space of your choice.");
		}
		else if(GameLoop.getCardAction() == CardAction.FEAR_1)
		{
			BottomUI.setActionText("A scientist of your choice is frightened. This scientist becomes inactive until he is set back \r\n"
					+ "upright again.");
		}
		else if(GameLoop.getCardAction() == CardAction.MOTHERS_CALL_2)
		{
			BottomUI.setActionText("Move 1 or 2 babies onto a free space on the tile where the mother is located. This move must be \r\n"
					+ "carried out without crossing spaces that are occupied or on which there is a fire.");
		}
		else if(GameLoop.getCardAction() == CardAction.RECOVERY_1)
		{
			BottomUI.setActionText("Remove 2 sleep tokens from the mother, or wake up 2 baby raptors (or remove one sleep token and \r\n"
					+ "wake up one baby.)");
		}
		else if(GameLoop.getCardAction() == CardAction.RECOVERY_2)
		{
			BottomUI.setActionText("Remove 3 sleep tokens from the mother, or wake up 3 baby raptors (or do a combination of both.)");
		}
		else if(GameLoop.getCardAction() == CardAction.FEAR_2)
		{
			BottomUI.setActionText("Up to 2 scientists of your choice are frightened. These scientists become inactive until they are \r\n"
					+ "set back upright.");
		}
	}
	
}
