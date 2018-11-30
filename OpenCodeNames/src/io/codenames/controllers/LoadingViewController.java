package io.codenames.controllers;

import io.codenames.serverinterfaces.GamesHandlerInterface;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;


import java.net.URL;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class LoadingViewController implements Initializable {


    private GamesHandlerInterface gamehandler;
    private Preferences pref;
    private boolean loadingDone = false;

    protected boolean loadGame() {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
			        try {
			            ViewController viewcontroller = ViewController.getInstance();
			            viewcontroller.addScreen("Game", FXMLLoader.load(getClass().getResource( "/fxml/GameView.fxml" )));
			            viewcontroller.activate("Game");
			        } catch (Exception e) {
			            e.printStackTrace();
			        }
	            };
	    });
        return true;
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			ClientCommandInvoker cci = ClientCommandInvoker.getInstance();
			cci.setLoadScreen(this);
            this.pref = Preferences.userNodeForPackage(io.codenames.Main.class);
            gamehandler = (GamesHandlerInterface) Naming.lookup("rmi://"+pref.get("rmiUri", "localhost")+"/GamesHandler");
            if(!gamehandler.joinGameQueue(this.pref.get("gameID", ""), this.pref.get("userName", ""), cci)){
            	JOptionPane.showMessageDialog(new JFrame(), "Unable to Join game", "Error",
                        JOptionPane.ERROR_MESSAGE);
            	/**
            	 * Return to Game List 
            	 */
            	ViewController viewcontroller = ViewController.getInstance();
				viewcontroller.addScreen("JoinGame", FXMLLoader.load(getClass().getResource( "/fxml/PlayerConnectView.fxml" )));
				viewcontroller.activate("JoinGame");
            }

        } catch (RemoteException e) {
			e.printStackTrace();
		}catch (Exception e) {
            System.out.println(e.getMessage());
        }
        if(this.loadingDone) {
        	ViewController viewcontroller = ViewController.getInstance();
        	viewcontroller.activate("Game");
        }
	}

}
