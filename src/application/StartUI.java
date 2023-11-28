package application;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class StartUI extends BorderPane {
	
	static Button scientistButton = new Button("SCIENTIST");
	static Button raptorButton = new Button("RAPTOR");
	static Button startButton = new Button("START GAME");


	public StartUI() {
		
		// Places elements a bit from the top
		Insets topPadding = new Insets(100);

		//Button size formatting
		scientistButton.setPrefHeight(500);
		scientistButton.setPrefWidth(400);
		
		raptorButton.setPrefHeight(500);
		raptorButton.setPrefWidth(400);
		
		startButton.setPrefHeight(250);
		startButton.setPrefWidth(400);
		
		// Win condition text for each side
		String raptorWinText = "HOW TO WIN AS RAPTORS:\n\n"
				         	 + "1) Eat all Scientists on the board\n\n"
				         	 + "2) THREE(3) Baby Raptors escape";
		
		String scientistWinText = "HOW TO WIN AS SCIENTISTS:\n\n"
								+ "1) Tranqualize Mother Raptor FIVE(5) times\n\n"
								+ "2) Capture THREE(3) Baby Raptors";

		//Raptor win conditions formatting
		Label raptorWin = new Label(raptorWinText);
		raptorWin.setPrefWidth(400);
		raptorWin.setAlignment(Pos.CENTER);
		raptorWin.setPadding(topPadding);
		raptorWin.setWrapText(true);
		raptorWin.setFont(new Font("Impact", 30));
		
		// Scientist win conditions formatting
		Label scientistWin = new Label(scientistWinText);
		scientistWin.setPrefWidth(400);
		scientistWin.setAlignment(Pos.CENTER);
		scientistWin.setPadding(topPadding);
		scientistWin.setWrapText(true);
		scientistWin.setFont(new Font("Impact", 30));
		
		// Creating Boxes to hold the buttons
		HBox selectSideButtonsBox = new HBox(50);
		HBox startButtonBox = new HBox();
		startButtonBox.setPadding(new Insets(50));
		VBox allButtonsBox = new VBox(10);
		
		//Add all buttons to a ButtonBox and format the locations of the buttons
		selectSideButtonsBox.getChildren().addAll(scientistButton, raptorButton);
		selectSideButtonsBox.setAlignment(Pos.CENTER);
		startButtonBox.getChildren().add(startButton);
		startButtonBox.setAlignment(Pos.CENTER);
		allButtonsBox.getChildren().addAll(selectSideButtonsBox, startButtonBox);
		allButtonsBox.setPadding(topPadding);
		
		// Add all elements to the BorderPane
		setLeft(scientistWin);
		setRight(raptorWin);
		setCenter(allButtonsBox);
		
		// Start button is disabled until a side is selected
		startButton.setDisable(true);
		
		raptorButton.setOnAction(e -> {
			//Raptor side is selected
			GameLoop.setScientistPlayer(false);
			startButton.setDisable(false);
		});
		
		scientistButton.setOnAction(e -> {
			// Scientist side is selected
			GameLoop.setScientistPlayer(true);
			startButton.setDisable(false);
		});	
	}

	
	public static Button getStartButton() {
		return startButton;
	}

}
