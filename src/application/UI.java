package application;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.MalformedURLException;

import javafx.geometry.Insets;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
	

// Temporary UI used to hold the pieces
public class UI extends BorderPane {
	
	public static SideUI leftUI = new SideUI(true);
	public static SideUI rightUI = new SideUI(false);
	
	public UI() {
		// Create a pane to hold the pieces
		HBox gameTopPart = new HBox();
		VBox leftPart = new VBox();
		VBox rightPart = new VBox();
		HBox bottomPart = new HBox();
		Pane centerPart = new Pane();
		
		
		// Top BorderPane portion
		TopUI gameTopUI = new TopUI();		
		gameTopPart.setStyle("-fx-border-color: black");
		gameTopPart.getChildren().add(gameTopUI);
		
		// Left BorderPane portion
		leftPart.setStyle("-fx-border-color: orange");
		leftPart.setPrefWidth(200);
		//SideUI leftUI = new SideUI(true);
		leftPart.getChildren().add(leftUI);	
		
		
		// Right BorderPane portion
		rightPart.setStyle("-fx-border-color: green");
		rightPart.setPrefWidth(200);
		//SideUI rightUI = new SideUI(false);
		rightPart.getChildren().add(rightUI);
		
		// Bottom BorderPane portion
		bottomPart.setStyle("-fx-border-color: black");
		BottomUI cardsBox = new BottomUI();
		bottomPart.getChildren().add(cardsBox);
		
		
		Board board = new Board();
		centerPart.setStyle("-fx-border-color: black");
		centerPart.setPrefHeight((Board.MAIN_BOARD_HEIGHT * Board.SPACE_SIZE));
		centerPart.setPrefWidth((Board.MAIN_BOARD_WIDTH * Board.SPACE_SIZE));
		centerPart.getChildren().add(board);
		
		// Add all the parts to the UI borderpane
		setTop(gameTopPart);
		setLeft(leftPart);
		setRight(rightPart);
		setBottom(bottomPart);
		setCenter(centerPart);

	}
	
	public static void setUpBullets(SideUI sideUI, int x)
	{
		System.out.println("entering setupbullets from ui");
		sideUI.getTranqSpace(x).setFill(Color.BLACK);
	}
	
	public static void removeBullets(SideUI sideUI, int x)
	{
		sideUI.getTranqSpace(x).setFill(Color.WHITE);
	}
	
	public static void changeBabySquares(SideUI sideUI, int x)
	{
		sideUI.getBabySquare(x).setFill(Color.BLACK);
	}
	
	public static SideUI getLeftUI()
	{
		return leftUI;
	}
	
	public static SideUI getRightUI()
	{
		return rightUI;
	}
}
