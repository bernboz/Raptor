package application;

import java.util.ArrayList;

public class Storage {
	// When an object is clicked, it will be added to a storage list. If it is a piece, it will go in piece storage. If it's a card,
	// it'll go to card storage. 
	
	private static ArrayList<Piece> pieceStorage = new ArrayList<>();
	private static ArrayList<Card> cardStorage = new ArrayList<>();
	private static Piece piece;
	private static Card card;
	
	public static void addToPieceStorage() {
		// When a piece is clicked, that piece is added to the pieceStorage ArrayList 
		// so it can be used in the Space class
		System.out.println("Add to pieceStorage: " + piece);
		System.out.println("PieceStorage size: " + pieceStorage.size());
		pieceStorage.add(piece);
	}
	
	public static void removeFromPieceStorage(Piece piece) {
		// After the piece has been used, the piece will be removed from storage
		System.out.println("Remove from pieceStorage: " + piece);
		pieceStorage.remove(piece);
		System.out.println("PieceStorage size: " + pieceStorage.size());
	}
	
	public static Piece getFromPieceStorage() {
		// Get the piece from storage so it can be used
		piece = pieceStorage.get(0);
		System.out.println("PieceStorage size: " + pieceStorage.size());
		return piece;
	}
	
	public static void setPiece(Piece piece) {
		// Sets the piece to be used by above methods
		System.out.println("PieceStorage size: " + pieceStorage.size());
		Storage.piece = piece;
	}
	
	public static boolean pieceStorageEmpty() {
		// Checks if pieceStorage is empty
		System.out.println("PieceStorage size: " + pieceStorage.size());
		return pieceStorage.isEmpty();
	}
	
	public static ArrayList<Piece> getPieceStorage() {
		System.out.println("PieceStorage size: " + pieceStorage.size());
		return pieceStorage;
	}
	
	public static void addToCardStorage() {
		// When a card is clicked, the card will be added to the card storage ArrayList
		cardStorage.add(card);
		System.out.println("CardStorage size: " + cardStorage.size());
	}
	
	public static Card getFromCardStorage() {
		// Get the card from card storage so it can be used
		card = cardStorage.get(0);
		return card;
	}
	
	public static void setCard(Card card) {
		Storage.card = card;
	}
	
	public static boolean cardStorageFull() {
		return cardStorage.isEmpty();
	}
	
	public static ArrayList<Card> getCardStorage() {
		return cardStorage;
	}
}
