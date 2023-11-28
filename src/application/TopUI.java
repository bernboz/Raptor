package application;

import java.io.File;
import java.net.MalformedURLException;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class TopUI extends HBox{
	
		public String sciHeader = "Scientists";
		public String rapHeader = "Raptors";
		public String titleHeader = "RAPTOR";
		public String actionPointsText = "Action Points: ";
		public static int actionPointsRaptor = 0;
		public static int actionPointsScientist = 0;
		private Space[][] board = Board.getBoard();
		private static Label lblRapPoints = new Label(Integer.toString(actionPointsRaptor));
		private static Label lblSciPoints = new Label(Integer.toString(actionPointsScientist));
		
		
		public TopUI()
		{
			//Creates the boxs that populate the TopUI
			VBox titleBox = new VBox(10);
			HBox buttonBox = new HBox(5);
			VBox scientistBox = new VBox();
			HBox scientistActionBox = new HBox();
			VBox raptorBox = new VBox();
			HBox raptorActionBox = new HBox();
			
			//adds the menubar to the titlebox
			//titleBox.getChildren().add(setUpMenu());
			
			//sets the sizes of the boxes inside the topUI for the raptors and scientists
			raptorBox.setPrefSize(210, 100);
			scientistBox.setPrefSize(210, 100);
			titleBox.setPrefSize(1300, 100);
			
			//sets borders around the raptors and scientist boxes
			raptorBox.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
			scientistBox.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
			
			raptorBox.setAlignment(Pos.CENTER);
			scientistBox.setAlignment(Pos.CENTER);
			setAlignment(Pos.CENTER);
			titleBox.setAlignment(Pos.CENTER);
			buttonBox.setAlignment(Pos.CENTER);
			scientistActionBox.setAlignment(Pos.CENTER);
			raptorActionBox.setAlignment(Pos.CENTER);
			
			//creates the labels for the topUI
			Label lblTitleHeader = new Label(titleHeader);
			Label lblSciHeader = new Label(sciHeader);
			Label lblRapHeader = new Label(rapHeader);
			Label lblActionPointsText = new Label(actionPointsText);
			Label lblActionPointsText2 = new Label(actionPointsText);
			
			
			//creates buttons for the middle of the UI
			Button newBoardButton = new Button("New Board");
			newBoardButton.setPrefSize(100, 50);
			Button continueButton = new Button("Continue");
			continueButton.setPrefSize(100, 50);
			buttonBox.getChildren().addAll(newBoardButton, continueButton);
			
			newBoardButton.setOnAction(e -> {			
				for (int x = 2; x < 13; x++) {
					for (int y = 0; y < 6; y++) {
						if (board[x][y].hasBoulder()) {
							board[x][y].setBoulder(null);
							Board.getPieceGroup().getChildren().clear();
						}
					}
				}
				GameLoop.setPhase(GamePhase.PLACE_BOULDER);
				Boulder.placeBoulders(board);
				
			});
			
			continueButton.setOnAction(e -> {
				GameLoop.setPhase(GamePhase.PLACE_MOTHER);
				titleBox.getChildren().remove(buttonBox);
			});
			
			//changes the style of the raptor title header to a nice design
			lblTitleHeader.setStyle("-fx-font-weight: bold");
			lblTitleHeader.setStyle("-fx-underline: true");
			lblTitleHeader.setStyle("-fx-text-fill: linear-gradient(to right, Orange, red, Green);");
			
			//changes the font sizes of the main labels to be bigger and more prominent
			lblTitleHeader.setFont(new Font(20));
			lblSciHeader.setFont(new Font(20));
			lblRapHeader.setFont(new Font(20));
			
			//adds the labels to each of their respective boxes
			raptorActionBox.getChildren().addAll(lblActionPointsText, lblRapPoints);
			raptorBox.getChildren().addAll(lblRapHeader, raptorActionBox);
			scientistActionBox.getChildren().addAll(lblActionPointsText2, lblSciPoints);
			scientistBox.getChildren().addAll(lblSciHeader, scientistActionBox);
			titleBox.getChildren().addAll(lblTitleHeader, buttonBox);
			
			//adds the boxes to the main topui box
			getChildren().addAll(scientistBox, titleBox, raptorBox);
			
			//various translate x and y's to place the parts in the right spots
			/*
			raptorBox.setTranslateX(1225);
			lblTitleHeader.setTranslateX(610);
			lblActionPointsText.setTranslateY(10);
			lblRapPoints.setTranslateY(10);
			lblActionPointsText2.setTranslateY(10);
			lblSciPoints.setTranslateY(10);
			lblSciPoints.setTranslateX(-36);
			lblActionPointsText2.setTranslateX(-36);
			lblRapPoints.setTranslateX(-30);
			lblActionPointsText.setTranslateX(-30);
			lblRapHeader.setTranslateY(-10);
			lblSciHeader.setTranslateY(-10);
			lblRapHeader.setTranslateX(45);
			lblSciHeader.setTranslateX(45);
			*/
			//sets the labels for the action points
			lblRapPoints.setLabelFor(lblActionPointsText2);
			lblSciPoints.setLabelFor(lblActionPointsText);
			
		}

		public int getActionPoints(String s)
		{
			if(s == "raptors")
				return actionPointsRaptor;
			else
				return actionPointsScientist;
		}
		
		public static void setActionPoints(int x, int y)
		{
			actionPointsScientist = x;
			lblSciPoints.setText(Integer.toString(x));
			actionPointsRaptor = x;
			lblRapPoints.setText(Integer.toString(y));
			
		}
		
		public static VBox setUpMenu()
		{
			MenuBar menuBar = new MenuBar();
			Menu boardRef = new Menu("Board Reference");
			MenuItem reference = new MenuItem("Reference");
			boardRef.getItems().add(reference);
			menuBar.getMenus().addAll(boardRef);
			VBox menuBox = new VBox();
			menuBox.getChildren().add(menuBar);
			
			reference.setOnAction(setMenuRef());
			
			
			
			return menuBox;
		}
		
		public static EventHandler<ActionEvent> setMenuRef()
		{
			//creates the dialog box as a dialog box filled with an image
			Dialog<Image> dialog = new Dialog<Image>();
			//creates the ok button to close the dialog box
			dialog.getDialogPane().getButtonTypes().add(new ButtonType("Ok", ButtonBar.ButtonData.CANCEL_CLOSE));
			return new EventHandler<ActionEvent>()
			{
				public void handle(ActionEvent t)
				{
					//creates the imageview for the dialogpane, will need to edit the string to fit your source
					try 
					{
						dialog.setTitle("Board Reference");
						dialog.setHeight(800);
						ImageView boardReferenceImage = new ImageView(new Image(new File("res\\aid\\BoardReference.jpg").toURI().toURL().toString()));
						boardReferenceImage.setFitHeight(dialog.getHeight());
						dialog.getDialogPane().setContent(boardReferenceImage);
						dialog.show();
					} 
					catch (MalformedURLException e) 
					{
						e.printStackTrace();
					}
					
				}
			};
		}
}
