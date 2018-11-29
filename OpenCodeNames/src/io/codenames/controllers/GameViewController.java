package io.codenames.controllers;

import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

import com.jfoenix.controls.JFXButton;

import io.codenames.serverinterfaces.GamesHandlerInterface;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class GameViewController implements Initializable {
	private GamesHandlerInterface gamehandler;
	private Preferences pref;
    private ArrayList<JFXButton> buttonList;

    @FXML
    private AnchorPane gameBoard;

	@FXML
	private JFXButton chatSend;
	
	@FXML
	private Label redScore;
	
	@FXML
	private Label blueScore;
	

	protected boolean handleScores(int score, char team){
		if(team == 1) {
			redScore.setText(score+ "/8");
		}else if(team == 2) {
			blueScore.setText(score+ "/8");
		}
		return true;
	}	
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			ClientCommandInvoker cci = ClientCommandInvoker.getInstance();
			cci.setGameScreen(this);
			this.pref = Preferences.userNodeForPackage(io.codenames.Main.class);
			gamehandler = (GamesHandlerInterface) Naming.lookup("rmi://"+pref.get("rmiUri", "localhost")+"/GamesHandler");
            ArrayList<String> codeNames = gamehandler.getCardsArray(this.pref.get("gameID", ""), this.pref.get("userName", ""));
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

            }
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}
	
}
