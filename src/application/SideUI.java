package application;

import java.io.File;
import java.net.MalformedURLException;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class SideUI extends VBox {
	
	private Boolean isHuman;
	private String babyText;
	private String tranqText;
	private String aidButtonText;
	private Button endButton;
	

	private final int CIRCLE_RADIUS = 30;
	private final int SQUARE_SIZE = 60;
	
	private Circle tranqSpace1 = new Circle(CIRCLE_RADIUS);
	private Circle tranqSpace2 = new Circle(CIRCLE_RADIUS);
	private Circle tranqSpace3 = new Circle(CIRCLE_RADIUS);
	private Circle tranqSpace4 = new Circle(CIRCLE_RADIUS);
	private Circle tranqSpace5 = new Circle(CIRCLE_RADIUS);
	private Rectangle babySquare1 = new Rectangle(SQUARE_SIZE, SQUARE_SIZE);
	private Rectangle babySquare2 = new Rectangle(SQUARE_SIZE, SQUARE_SIZE);
	private Rectangle babySquare3 = new Rectangle(SQUARE_SIZE, SQUARE_SIZE);
	
	public SideUI(Boolean isHuman) {
		
		setBabyText(isHuman);
		setTranqText(isHuman);
		setAidButtonText(isHuman);
		
		// Tranq Box setup
		VBox tranqBox = new VBox(5);	
		
		String tranqText = getTranqText();
		Label tranqLabel = new Label(tranqText);
		GridPane tranqGrid = new GridPane();
		tranqGrid.setAlignment(Pos.CENTER);
		
	    // set up tranq circles
	    //Circle tranqSpace1 = new Circle(CIRCLE_RADIUS);
	    tranqSpace1.setStroke(Color.BLACK);
	    tranqSpace1.setFill(Color.WHITE);
	    tranqSpace2.setStroke(Color.BLACK);
	    tranqSpace2.setFill(Color.WHITE);
	    tranqSpace3.setStroke(Color.BLACK);
	    tranqSpace3.setFill(Color.WHITE);
	    tranqSpace4.setStroke(Color.BLACK);
	    tranqSpace4.setFill(Color.WHITE);
	    tranqSpace5.setStroke(Color.BLACK);
	    tranqSpace5.setFill(Color.WHITE);
	    
	    // Set up tranqGrid
	    tranqGrid.add(tranqSpace1, 0, 1);
	    tranqGrid.add(tranqSpace2, 0, 3);
	    tranqGrid.add(tranqSpace3, 1, 2);
	    tranqGrid.add(tranqSpace4, 2, 1);
	    tranqGrid.add(tranqSpace5, 2, 3);
	    
	    tranqBox.setAlignment(Pos.CENTER);
	    tranqBox.getChildren().addAll(tranqLabel, tranqGrid);
	    
		// Baby Box set up
		
		VBox babyBox = new VBox(5);
		
		String babyText = getBabyText();
		Label babyLabel = new Label(babyText);
		HBox babyHBox = new HBox(6.5);
		babyHBox.setAlignment(Pos.CENTER);


	    // set up baby capture/escape squares
	    babySquare1.setStroke(Color.BLACK);
	    babySquare1.setFill(Color.WHITE);
	    
	    babySquare2.setStroke(Color.BLACK);
	    babySquare2.setFill(Color.WHITE);
	    
	    babySquare3.setStroke(Color.BLACK);
	    babySquare3.setFill(Color.WHITE);
	    
	    babyBox.setAlignment(Pos.CENTER);
	    babyBox.getChildren().addAll(babyLabel, babyHBox);
	    
	    // Button Box setup
		VBox buttonVBox = new VBox(10);
		
		endButton = new Button("END TURN");
		endButton.setPrefHeight(200);
		endButton.setPrefWidth(300);
		endButton.setOnAction(doneClick(isHuman));
		
		Button aidButton = new Button(aidButtonText);
		aidButton.setPrefHeight(100);
		aidButton.setPrefWidth(300);
		buttonVBox.getChildren().addAll(endButton, aidButton);
		buttonVBox.setAlignment(Pos.CENTER);
		setMargin(buttonVBox, new Insets(10));
		
		//calls the setAidButton method when clicked which returns an eventlistener that differentiates
		//between the scientist and raptor button to display the correct aid card
		aidButton.setOnAction(setAidButton(isHuman));

		// Add all elements to the UI
		babyHBox.getChildren().addAll(babySquare1, babySquare2, babySquare3);
		setPadding(new Insets(5, 5, 5, 5));
		getChildren().addAll(tranqBox, babyBox, buttonVBox);
	}
	
	public Boolean getIsHuman() {
		return isHuman;
	}
	
	// do I need this?
	public void setIsHuman(Boolean isHuman) {
		this.isHuman = isHuman;
	}
	
	public String getBabyText() {
		return babyText;
	}
	
	public void setBabyText(Boolean isHuman) {
		if (isHuman) {
			this.babyText = "Captured Baby Raptors";
		} else {
			this.babyText = "Escaped Baby Raptors";
		}
	}
	
	public String getTranqText() {
		return tranqText;
	}
	
	public void setTranqText(Boolean isHuman) {
		if (isHuman) {
			this.tranqText = "Ammunition Against the Mother";
		}
		else {
			this.tranqText = "Damages and Effect on Movement";
		}
	}
	
	public String getAidButtonText() {
		return aidButtonText;
	}
	
	public void setAidButtonText(boolean isHuman) {
		if (isHuman) {
			this.aidButtonText = "  Scientist\nPlayer Aid";
		} else {
			this.aidButtonText = "  Raptor\nPlayer Aid";
		}
	}
	
	public static EventHandler<ActionEvent> doneClick(boolean isHuman) {
		
		GamePhase phase = GameLoop.getPhase();
		
		return new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				if(isHuman) {
					if (phase == GamePhase.SCIENTIST_ACTIONS) {
						ScientistAction.setScientistActionPoints(0);
						GameLoop.setPhase(GamePhase.CARD_SELECT);
					}
					if (phase == GamePhase.SCIENTIST_CARD) {
						ScientistCardAction.setNumScientists(0);
						ScientistCardAction.setGasFires(0);
						ScientistCardAction.setNumFires(0);
						Space.setJeepMoves(0);
						GameLoop.setPhase(GamePhase.RAPTOR_ACTIONS);
					}
				} else {
					if (phase == GamePhase.RAPTOR_ACTIONS) {
						RaptorAction.setRaptorActionPoints(0);
						GameLoop.setPhase(GamePhase.CARD_SELECT);
					}
					if (phase == GamePhase.RAPTOR_CARD) {
						RaptorCardAction.setBabyCalls(0);
						RaptorCardAction.setNumFears(0);
						RaptorCardAction.setRecoveryPoints(0);
						GameLoop.setPhase(GamePhase.SCIENTIST_ACTIONS);
					}
				}
				
				
			}
		};
	}
	
	//Eventhandler method to differentiate the two sideUI buttons being clicked,
	//changed to a method to make the code cleaner
	public static EventHandler<ActionEvent> setAidButton(boolean isHuman)
	{
		//creates the dialog box as a dialog box filled with an image
		Dialog<Image> dialog = new Dialog<Image>();
		//creates the ok button to close the dialog box
		dialog.getDialogPane().getButtonTypes().add(new ButtonType("Ok", ButtonBar.ButtonData.CANCEL_CLOSE));
		return new EventHandler<ActionEvent>()
		{
			public void handle(ActionEvent t)
			{
				if(isHuman)
				{
					//sets the title
					dialog.setTitle("Scientist Actions");
					//creates the imageview for the dialogpane, will need to edit the string to fit your source
					ImageView ruleView;
					try {
						ruleView = new ImageView(new Image(new File("res\\aid\\scientist_rules.png").toURI().toURL().toString()));
						dialog.getDialogPane().setContent(ruleView);
						dialog.show();
					} catch (MalformedURLException e) {
						e.printStackTrace();
					}
					//may need to change depending on how the image is saved on the computer, this fit for me
					//ruleView.setRotate(180);
					//sets the content of the dialog pane to the scientist aid image
					
				}
				else
				{
					//sets the title
					dialog.setTitle("Raptor Actions");
					//creates the imageview for the dialogpane, will need to edit the string to fit your source
					ImageView ruleView;
					try {
						ruleView = new ImageView(new Image(new File("res\\aid\\raptor_rules.png").toURI().toURL().toString()));
						dialog.getDialogPane().setContent(ruleView);
						dialog.show();
					} catch (MalformedURLException e) {
						e.printStackTrace();
					}
					//may need to change depending on how the image is saved on the computer, this fit for me
					//ruleView.setRotate(270);
					//may need to change depending on how image is loading for user, this width/height worked for me
					//ruleView.setFitWidth(900);
					//ruleView.setFitHeight(900);
					
					//dialog.getDialogPane().setPrefSize(ruleView.getFitHeight() + 50, ruleView.getFitHeight());
					//sets the content of the dialogpane to the raptor aid image
					
				}
			}
		};
	}

	public Button getEndButton() {
		return endButton;
	}
	

	public Circle getTranqSpace(int i) 
	{
		if(i == 1)
			return tranqSpace1;
		if(i == 2)
			return tranqSpace2;
		if(i == 3)
			return tranqSpace3;
		if(i == 4)
			return tranqSpace4;
		else
			return tranqSpace5;
	}
	
	public Rectangle getBabySquare(int i)
	{
		if(i == 1)
			return babySquare1;
		if(i == 2)
			return babySquare2;
		else
			return babySquare3;
	}

	public void setTranqSpace1(Circle tranqSpace1) {
		this.tranqSpace1 = tranqSpace1;
	}


}
