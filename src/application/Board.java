package application;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class Board extends Pane {
	public final static int SPACE_SIZE = 100;
	public final static int MAIN_BOARD_HEIGHT = 6;
	public final static int MAIN_BOARD_WIDTH = 13;
	
	private static Space[][] board = new Space[MAIN_BOARD_WIDTH][MAIN_BOARD_HEIGHT];
	private static Space space;
	
	private static Group spaceGroup = new Group();
	private static Group pieceGroup = new Group();
	
	public Board() {
		
		//Set the size of the board, doesn't work right now
		setPrefSize(MAIN_BOARD_WIDTH * SPACE_SIZE, MAIN_BOARD_HEIGHT * SPACE_SIZE);
		
		// Add components to the board
		getChildren().addAll(spaceGroup, pieceGroup);
		for (int y = 0; y < MAIN_BOARD_HEIGHT; y++) {
			for (int x = 0; x < MAIN_BOARD_WIDTH; x++) {
				Space space = new Space(x, y);
				board[x][y] = space;

				
				//sets the off limits rectangles to black
				if(x == 0 && y > 0 && y < 5 || x == 12 && y < 2 || x == 12 && y > 3) // Modified for game
				{
					space.setFill(Color.BLACK);
					space.setStroke(Color.BLACK);
					space.setOfflimits(true);
				}
				
				//sets a different stroke for the spaces where the raptors can leave
				//we should change this later to something that would look better
				//just set up as a placeholder
				else if(x == 0 && y == 0 || x == 0 && y == 5 || x == 12 && y == 2 || x == 12 && y == 3) // Modified for game
				{
					space.setStroke(Color.RED);
					//space.setStrokeWidth(5);
					space.setBabyEscape(true);
				}
				//otherwise sets the woodland image to the remaining rectangles
				else
				{
					space.setStroke(Color.BLACK);
				}

				spaceGroup.getChildren().add(space);			
			}
		}
		
	}
	
	public static Space[][] getBoard() {
		return board;
	}
	
	public static int toBoard(double pixel) {
		return (int)(pixel + SPACE_SIZE / 2) / SPACE_SIZE;
	}
	
	public static Group getPieceGroup() {
		return pieceGroup;
	}

	public static Space getSpace() {
		return space;
	}

	public static void setSpace(Space space) {
		Board.space = space;
	}


}
