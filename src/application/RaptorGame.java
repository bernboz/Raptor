package application;
	
import java.io.File;
import java.net.MalformedURLException;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.image.*;

// MAIN CLASS OF RAPTOR
public class RaptorGame extends Application {

	final int APP_HEIGHT = 915;
	final int APP_WIDTH = 1719;
	public static Menu fileMenu = new Menu("File");
	public MenuItem newGame = new MenuItem("New Game");
	
	@Override
	public void start(Stage primaryStage) {
		GameLoop.setPhase(GamePhase.PLACE_BOULDER);
		Button startButton = StartUI.getStartButton();
		StartUI startUI = new StartUI();
		UI ui = new UI();
		
		
		
		fileMenu.getItems().add(newGame);
		
		
		newGame.setOnAction(event ->
		{
			Platform.runLater(() ->
			{
				try
				{
					start(new Stage());
					primaryStage.close();
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
			});
		}); 
		
		ui.setTop(setUpMenu());
		
		Scene startScene = new Scene(startUI, APP_WIDTH, APP_HEIGHT);		
		Scene gameScene = new Scene(ui, APP_WIDTH, APP_HEIGHT);
		

		startButton.setOnAction(e -> {
			primaryStage.setScene(gameScene);
			GameLoop.gameLoop();
		});
		primaryStage.setTitle("RAPTOR"); // Set the stage title
		primaryStage.setScene(startScene);
		primaryStage.show();
		
		//TempGameLoop.gameLoop();
	}
	public static void main(String[] args) {
		launch(args);
	}
	
	public static VBox setUpMenu()
	{
		MenuBar menuBar = new MenuBar();
		Menu boardRef = new Menu("Board Reference");
		MenuItem reference = new MenuItem("Reference");
		boardRef.getItems().addAll(reference);
		menuBar.getMenus().addAll(boardRef, fileMenu);
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
