package application;

import java.util.ArrayList;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class Boulder extends Rectangle {
	
	private final String BOULDER_LINK = "https://i.imgur.com/fuPonEn.jpg";
	private ImagePattern imagePattern = new ImagePattern(new Image(BOULDER_LINK));
	private static int randomX;
	private static int randomY;
	
	public Boulder(int x, int y) {
		setWidth(Board.SPACE_SIZE);
		setHeight(Board.SPACE_SIZE);
		setFill(imagePattern);
		setStroke(Color.BLACK);
		relocate(x * Board.SPACE_SIZE, y * Board.SPACE_SIZE);
	}
	

	public static void placeBoulders(Space[][] board) {
		/* 
		 * Every non-L tile gets at least one boulder.
		 * This method ensures that each of those tiles gets one boulder in a random space.
		 */
		int numBoulders = 0;
	
		for (int spaceX = 2; spaceX < 11; spaceX++) {
			for (int spaceY = 0; spaceY < 6; spaceY++) {
				
				int tileNumber = board[spaceX][spaceY].getTileNumber();
				
				// Create boulder1
				if (numBoulders == 0 && tileNumber == 1) {
					randomX = RandomCalcs.randomXLoc(2, 4);
					randomY = RandomCalcs.randomYLoc(0, 2);
					generateBoulder(board, randomX, randomY);
					numBoulders++;
				}
				
				// Create boulder2
				if (numBoulders == 1 && tileNumber == 2) {
					randomX = RandomCalcs.randomXLoc(2, 4);
					randomY = RandomCalcs.randomYLoc(3, 5);
					generateBoulder(board, randomX, randomY);
					numBoulders++;
				}
				
				// Create boulder3
				if (numBoulders == 2 && tileNumber == 3) {
					randomX = RandomCalcs.randomXLoc(5, 7);
					randomY = RandomCalcs.randomYLoc(0, 2);
					generateBoulder(board, randomX, randomY);
					numBoulders++;
				}
				
				// Create boulder4
				if (numBoulders == 3 && tileNumber == 4) {
					randomX = RandomCalcs.randomXLoc(5, 7);
					randomY = RandomCalcs.randomYLoc(3, 5);
					generateBoulder(board, randomX, randomY);
					numBoulders++;
				}
				
				// Create boulder5
				if (numBoulders == 4 && tileNumber == 5) {
					randomX = RandomCalcs.randomXLoc(8, 10);
					randomY = RandomCalcs.randomYLoc(0, 2);
					generateBoulder(board, randomX, randomY);
					numBoulders++;
				}
				
				// Create boulder6
				if (numBoulders == 5 && tileNumber == 6) {
					randomX = RandomCalcs.randomXLoc(8, 10);
					randomY = RandomCalcs.randomYLoc(3, 5);
					generateBoulder(board, randomX, randomY);
					numBoulders++;
				}
			}
		}
		placeDoubleBoulder(board);
	}
	
	public static void placeDoubleBoulder(Space[][] board) {
		/*
		 * Three additional boulders are placed in three different tiles; 1 per tile.
		 * A tile cannot have more than two boulders in it.
		 */
		Integer extraBoulder;		
		ArrayList<Integer> boulderTile = new ArrayList<>();
		
		/*
		 * This for loop populates an ArrayList with three randomized values.
		 * The values represent the tile number. If a tile number is already in the list,
		 * a "reroll" will happen to ensure that the new roll is not already in the list.
		 */
		
		int numBoulders = 0;
		
		while(numBoulders < 3) {
			extraBoulder = RandomCalcs.randomTile();
			if (boulderTile.contains(extraBoulder)) {
				continue;
			} else {
				boulderTile.add(extraBoulder);
				numBoulders++;
				System.out.println("extraBoulder: " + extraBoulder);
			}
		}

		/*
		 * Initializes minX, maxX, minY, and maxY.
		 * These values are used when a random tile is selected, as each tile
		 * has their own unique combination of min and max values.
		 */
		int xLocMin = 0;
		int xLocMax = 0;
		int yLocMin = 0;
		int yLocMax = 0;

		/*
		 * While loop that generates a new boulder based on the randomly rolled tile.
		 * The min and max values will be assigned based on tile number, and the boulder will generate
		 * within the min and max value range.
		 * 
		 * The randomly generated tile is selected from the ArrayList, starting at index 0.
		 * After the boulder has been placed on the board, the ArrayList then removes index 0 so the next
		 * randomly chosen tile is selected.
		 */
		while(boulderTile.size() != 0) {
			for (int spaceX = 2; spaceX < 11; spaceX++) {
				for (int spaceY = 0; spaceY < 6; spaceY++) {
					int tileNumber = board[spaceX][spaceY].getTileNumber();
					try {
						int randomTile = boulderTile.get(0);
						switch (randomTile) {
							case 1: xLocMin = 2; xLocMax = 4; yLocMin = 0; yLocMax = 2; break;
							case 2: xLocMin = 2; xLocMax = 4; yLocMin = 3; yLocMax = 5; break;
							case 3: xLocMin = 5; xLocMax = 7; yLocMin = 0; yLocMax = 2; break;
							case 4: xLocMin = 5; xLocMax = 7; yLocMin = 3; yLocMax = 5; break;
							case 5: xLocMin = 8; xLocMax = 10; yLocMin = 0; yLocMax = 2; break;
							case 6: xLocMin = 8; xLocMax = 10; yLocMin = 3; yLocMax = 5; break;
						}
						if (tileNumber == randomTile) {
							randomX = RandomCalcs.randomXLoc(xLocMin, xLocMax);
							randomY = RandomCalcs.randomYLoc(yLocMin, yLocMax);
							if (!board[randomX][randomY].isOccupied()) {
								generateBoulder(board, randomX, randomY);
								boulderTile.remove(0);
							}
						}
					} catch(IndexOutOfBoundsException e) {
						break;
					}	
				}
			}
		}
	}
	
	public static void generateBoulder(Space[][] board, int spaceX, int spaceY) {
		/*
		 * Method that instances a new boulder and places it on the board
		 */
		Boulder boulder = new Boulder(spaceX, spaceY);
		board[spaceX][spaceY].setBoulder(boulder);
		Board.getPieceGroup().getChildren().add(boulder);
	}

}
