package application;

import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.layout.Pane.*;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Ellipse;

public class FireToken extends StackPane
{
	private static String link = "https://png.pngtree.com/png-clipart/20201208/original/pngtree-orange-fire-ball-flame-clip-art-png-image_5566544.jpg";
	private Image fireImage = new Image(link);
	protected Space[][] board = Board.getBoard();
	private Space space;
	private int x, y;
	private double clickX, clickY;
	private double oldX, oldY;
	private int numFireTokens = 0;
	private static boolean nearScientist = false;
	private static boolean nearFire = false;
	//need a scientistaction object variable
	
	public FireToken()
	{
		
	}
	
	//firetoken constructor, might need to change up depending on the way we load the image
	public FireToken(String link, int x, int y)
	{
		this.link = link;
		this.x = x;
		this.y = y;
		prefWidth(Board.SPACE_SIZE);
		prefHeight(Board.SPACE_SIZE);
		getChildren().add(CreateFireToken());
		moveFire(x, y);
		
		setOnMouseClicked(e -> {
			System.out.println(this);
		});
	}
	
	//creates the 'firetoken' image by displaying an ellipse as the firetoken object
	public Ellipse CreateFireToken()
	{
		Ellipse fireToken = new Ellipse(Board.SPACE_SIZE * .35, Board.SPACE_SIZE * .35);
		fireToken.setFill(new ImagePattern(fireImage));
		fireToken.setStroke(Color.BLACK);
		
		return fireToken;
	}
	
	//method to place the firetoken on the board, checks to make sure the space isn't occupied
	public static void PlaceFireToken(Space[][] board, int x, int y)
	{
		FireToken fireToken = new FireToken(link, x, y);
		if (!board[x][y].isOccupied() && (isNearScientist(board, x, y) || isNearFire(board, x, y)) && !board[x][y].isSciSpawnTile()) //needs a condition based off the cards actions
		{
			System.out.print(isNearScientist(board, x, y));
			System.out.print(isNearFire(board, x, y));
			//adds the counter to the number of fire tokens displayed, needs to be set on a limit depending on the card used
			
			board[x][y].setFireToken(fireToken);
			fireToken.setX(x);
			fireToken.setY(y);
			Board.getPieceGroup().getChildren().add(fireToken);
			//displays the firetoken in the center of the space
			fireToken.setTranslateX((Board.SPACE_SIZE - Board.SPACE_SIZE * 0.35 * 2) / 2);
			fireToken.setTranslateY((Board.SPACE_SIZE - Board.SPACE_SIZE * 0.35 * 2) / 2);
			//fireToken.moveFire(x, y);
		}	
	}
	
	//may be unnecessary depending on the way we load the image
	public String getImageLink()
	{
		return link;
	}
	
	//movepiece method for the firetoken method
	public void moveFire(int x, int y)
	{
		oldX = x * Board.SPACE_SIZE;
		oldY = y * Board.SPACE_SIZE;
		relocate(oldX, oldY);
	}
	
	//work in progress
	public boolean IsObstructed()
	{
		boolean isObstructed = false;
		
		if(board[x][y] == board[this.x][this.y])
			isObstructed = true;
		
		return isObstructed;
	}
	
	//getter for x, might not be used depending on how the firetoken is implented in the action classes
	public int getX()
	{
		return x;
	}
	
	//getter for y, same as x
	public int getY()
	{
		return y;
	}
	
	public int getNumTokens()
	{
		return this.numFireTokens;
	}
	
	//various setters that may or may not be used depending on action classes
	public void setX(int x)
	{
		this.x = x;
	}
	
	public void setLink(String s)
	{
		this.link = link;
	}
	
	public void setY(int y)
	{
		this.y = y;
	}
	
	public void setTokenCounter(int i, int y)
	{
		i = y;
	}
	
	public void setTokens(int x)
	{
		this.numFireTokens = x;
	}
	
	public static boolean isNearFire(Space[][] board, int x, int y)
	{
		nearFire = false;
		try
		{
			if (board[x + 1][y].hasFire() || board[x][y + 1].hasFire()) 
			{
				nearFire = true;
			}
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
			y = Board.MAIN_BOARD_HEIGHT - 1;
			if (board[x + 1][y].hasFire() || board[x][y].hasFire()) 
			{
				nearFire = true;
			}
		}
		try
		{
			if (board[x - 1][y].hasFire() || board[x][y - 1].hasFire()) 
			{
				nearFire = true;
			}
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
			y = 0;
			if (board[x - 1][y].hasFire() || board[x][y].hasFire()) 
			{
				nearFire = true;
			}
		}
		
		return nearFire;
	}
	
	//needed for condition to place firetoken checks to see if there is a space next to where
	//the user clicks occupied by a scientist piece
	public static boolean isNearScientist(Space[][] board, int x, int y) 
	{
		nearScientist = false;
		try
		{	
			if (board[x + 1][y].hasScientist() || board[x][y + 1].hasScientist()) 
			{
				nearScientist = true;
			}
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
			y = Board.MAIN_BOARD_HEIGHT - 1;
			if (board[x + 1][y].hasScientist() || board[x][y].hasScientist()) 
			{
				nearScientist = true;
			}
		}
		try
		{
			if (board[x - 1][y].hasScientist() || board[x][y - 1].hasScientist()) 
			{
				nearScientist = true;
			}
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
			y = 0;
			if (board[x - 1][y].hasScientist() || board[x][y].hasScientist()) 
			{
				nearScientist = true;
			}
		}
		
		return nearScientist;
	}
	
	public void removeFireToken(FireToken fireToken)
	{
		fireToken.x = fireToken.getX();
		fireToken.y = fireToken.getY();
		Board.getPieceGroup().getChildren().remove(fireToken);
		board[x][y].setFireToken(fireToken);
	}

}
