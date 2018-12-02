package io.codenames.controllers;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import java.util.prefs.Preferences;
import javafx.application.Platform;

import com.jfoenix.controls.JFXButton;

import io.codenames.serverinterfaces.GamesHandlerInterface;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import javax.swing.JOptionPane;
import javax.swing.JFrame;

import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.animation.Animation;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class GameViewController implements Initializable {
	private GamesHandlerInterface gamehandler;
	private Preferences pref;
    private LinkedHashMap<String, JFXButton> buttonList = new LinkedHashMap<String, JFXButton>();

    int team;
    int turn;
    int turnCount;
    int role;
    int redScoreVal=0;
    int blueScoreVal=0;
    String gameID;
    String playerName;

    ClockEventHandler gameClock;
    
    @FXML
    private AnchorPane gameBoard;

	@FXML
	private JFXButton chatSend;
	
	@FXML
	private TextField messageTextBox;

	@FXML
    private VBox messageBoard;
	@FXML
	private Label redScore;
	
	@FXML
	private Label blueScore;
	
	@FXML
	private Label countDown;
	
	@FXML
	private Label roleDisplay;

	protected void handleScores(){
		redScore.setText(redScoreVal+ "/8");
		blueScore.setText(blueScoreVal+ "/8");
	}

	protected boolean inputLocked() {
		return (turn!=team);
	}

	protected void addChatMessage(String chat,int team,int type){
		 Platform.runLater(new Runnable() {
			 @Override
	            public void run() {
				    Label chatMessage = new Label(chat);
				    String teamString="";
				    String typeString="";
				    switch (team){
				        case(0):
				            teamString = "red-";
				            break;
				        case(1):
				            teamString = "blue-";
				            break;
				        default:
				            break;
				    }
				    switch (type){
				        case(0):
				            typeString = "player";
				            break;
				        case(1):
				            typeString = "spymaster";
				            break;
				        default:
				            typeString = "gameMessage";
				            break;
				    }
				    chatMessage.getStyleClass().add(teamString+typeString);
				    messageBoard.getChildren().add(chatMessage);
			 }
		 });
    }
	protected void timeOver() {
		try {
            gamehandler.passTurnInGame(gameID, playerName, turnCount);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
	}

	protected void turnChange(){
	    if(turn == 0){
	        turn = 1;
        }else{
	        turn = 0;
        }
	    this.gameClock.startClock();
    }

    protected void incrimentTurnCount(){
	    turnCount++;
    }

    protected void revealCard(String code, boolean turnChange){
        Platform.runLater(new Runnable() {
            String code;
            GameViewController gameView;
            boolean turnChange;
            public Runnable init(String code, boolean turnChange, GameViewController gameView) {
                this.code=code;
                this.gameView = gameView;
                this.turnChange = turnChange;
                return(this);
            }

            @Override
            public void run() {
                if(turnChange)
                    this.gameView.turnChange();
                this.gameView.incrimentTurnCount();
                this.gameView.revealCard(code);
            }
        }.init(code,turnChange,this));
    }

    protected void revealCard(String code){
        JFXButton btn = buttonList.get(code);
        try {
            int type = gamehandler.getTypeOfCardInGame(gameID,code,playerName);
            btn.getStyleClass().remove("card-Hidden");
            switch (type){
                case 0:
                    btn.getStyleClass().add("card-Red");
                    this.redScoreVal++;
                    break;
                case 1:
                    btn.getStyleClass().add("card-Blue");
                    this.blueScoreVal++;
                    break;
                case 2:
                    btn.getStyleClass().add("card-Neutral");
                    break;
                case 3:
                    btn.getStyleClass().add("card-Death");
                    break;
                default:
            }
            handleScores();
            this.gameClock.startClock();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

	protected void startCountDown() {
		gameClock = new ClockEventHandler(countDown, this);
		Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0), gameClock), new KeyFrame(Duration.seconds(1)));
		timeline.setCycleCount(Animation.INDEFINITE);
		timeline.play();
	}
	
 
	protected boolean gameDrop() {
		gameOver("A Player forfited the game",true,"Game Droped");
        return true;
	}
	
	protected void gameOver(String message,boolean isError,String messageTitle) {
		int paneType;
		if(isError){
			paneType = JOptionPane.ERROR_MESSAGE;
		}else {
			paneType = JOptionPane.INFORMATION_MESSAGE;
		}
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
						JOptionPane.showMessageDialog(new JFrame(), message, messageTitle,
								paneType);
			            ViewController viewcontroller = ViewController.getInstance();
			            viewcontroller.activate("Menu");  
	       }
	    
		});
	}

	protected void gameWon(int wonBy, boolean byDeathCard){
	    String message;
	    String title;
        String by;
	    if(team==wonBy){
	        if(byDeathCard){
                title = "You Lost";
                by = "Your Team";
            }else{
                title = "You Won";
                by = "Opponent";
            }


        }else{
            if(byDeathCard){
                title = "You Won";
                by = "Opponent";
            }else{
                title = "You Lost";
                by = "Your Team";
            }

        }
        String reason;
        if(byDeathCard)
            reason = " By "+by+" clicking on";
        else
            reason = " By "+by+" clearing the board";

        message = title+reason;
	    this.gameOver(message,false,title);
    }
	
	
	private boolean spyMasterLock() {
		return (role==1);
	}
	
	protected void handleCardClick(MouseEvent event){
		if(inputLocked()){
			JOptionPane.showMessageDialog(new JFrame(), "Not your turn", "Error", JOptionPane.ERROR_MESSAGE);
		}else if(spyMasterLock()){
			JOptionPane.showMessageDialog(new JFrame(), "Spy master cant reveal cards", "Error", JOptionPane.ERROR_MESSAGE);
		}else{
            JFXButton button = (JFXButton) event.getSource();
            try {
                if (!gamehandler.cardSelected(gameID, turnCount, button.getText(), playerName)) {
                    JOptionPane.showMessageDialog(new JFrame(), "Couldn't Turn Card", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
	}
	
	@FXML
	protected void sendMessage(ActionEvent event) {
		String messages = messageTextBox.getText();
		try {
			gamehandler.placeChatMessage(gameID, playerName, messages);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			ClientCommandInvoker cci = ClientCommandInvoker.getInstance();
			cci.setGameScreen(this);
			this.pref = Preferences.userNodeForPackage(io.codenames.Main.class);
			gamehandler = (GamesHandlerInterface) Naming.lookup("rmi://"+pref.get("rmiUri", "localhost")+"/GamesHandler");
			gameID = this.pref.get("gameID", "");
			playerName = this.pref.get("userName", "");
            team = gamehandler.getTeamOfPlayerInGame(gameID,playerName);
            turn = gamehandler.getTurnOfGame(gameID,playerName);
            turnCount = gamehandler.getTurnCountOfGame(gameID,playerName);
            role = gamehandler.getRoleOfPlayerInGame(gameID,playerName);
            ArrayList<String> codeNames = gamehandler.getCardsArray(gameID, playerName);
            double xpos = 51.0;
            double ypos = 54.0;
            int i = 0;
            for(String cardtext: codeNames){
                i++;
                JFXButton btn = new JFXButton();
                btn.getStyleClass().add("card");
                btn.getStyleClass().add("card-Hidden");
                btn.setLayoutX(xpos);
                btn.setLayoutY(ypos);
                btn.setPrefHeight(66.0);
                btn.setPrefWidth(107.0);
                btn.setFocusTraversable(false);
                btn.setText(cardtext);
                gameBoard.getChildren().add(btn);
                xpos+=117;
                if(i%5==0){
                    ypos+=81;
                    xpos = 51;
                }
				btn.setOnMouseClicked(actionEvent -> {this.handleCardClick(actionEvent);});
				buttonList.put(cardtext,btn);
            }
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		String teamString = "";
		String typeString = "";
		switch (team){
	        case(0):
	            teamString = "Red";
	            break;
	        case(1):
	            teamString = "Blue";
	            break;
	        default:
	            break;
	    }
	    switch (role){
	        case(0):
	            typeString = "player";
	            break;
	        case(1):
	            typeString = "spymaster";
	            break;
	        default:
	            break;
	    }
	    roleDisplay.setText(teamString+" "+typeString); 
		startCountDown();
	}
	
}
