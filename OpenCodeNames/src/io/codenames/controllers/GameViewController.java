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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import javax.swing.JOptionPane;
import javax.swing.JFrame;

import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.animation.Animation;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import javafx.util.Duration;

public class GameViewController implements Initializable {
	private GamesHandlerInterface gamehandler;
	private Preferences pref;
    private LinkedHashMap<String, JFXButton> buttonList = new LinkedHashMap<String, JFXButton>();

    int team;
    int turn;
    int turnCount;
    int role;
    String gameID;
    String playerName;

    @FXML
    private AnchorPane gameBoard;

	@FXML
	private JFXButton chatSend;
	
	@FXML
	private Label redScore;
	
	@FXML
	private Label blueScore;
	
	@FXML
	private Label countDown;


	protected boolean handleScores(int score, int team){
		if(team == 0) {
			redScore.setText(score+ "/8");
		}else if(team == 1) {
			blueScore.setText(score+ "/8");
		}
		return true;
	}

	protected boolean inputLocked() {
		return (turn!=team);
	}

	protected void timeOver() {

	}

	protected void startCountDown() {
		Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0),new ClockEventHandler(countDown, this)), new KeyFrame(Duration.seconds(1)));
		timeline.setCycleCount(Animation.INDEFINITE);
		timeline.play();
	}
	

	@FXML 
	protected boolean gameDrop() {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
			        try {
			            ViewController viewcontroller = ViewController.getInstance();
			            viewcontroller.addScreen("GameDrop", FXMLLoader.load(getClass().getResource( "/fxml/MenuView.fxml" )));
			            viewcontroller.activate("GameDrop");
			        } catch (Exception e) {
			            e.printStackTrace();
			        }
	            };
	    });
        return true;
	}
	
	

	protected void handleCardClick(MouseEvent event){
		if(team!=turn){
			JOptionPane.showMessageDialog(new JFrame(), "Not your turn", "Error", JOptionPane.ERROR_MESSAGE);
		}
		JFXButton button = (JFXButton) event.getSource();
		try {
			if(!gamehandler.cardSelected(gameID, turnCount, button.getText(),playerName)){
				JOptionPane.showMessageDialog(new JFrame(), "Couldn't Turn Card", "Error", JOptionPane.ERROR_MESSAGE);
			}
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
		startCountDown();
	}
	
}
