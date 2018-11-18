package io.codenames.controllers;

import io.codenames.serverinterfaces.GamesHandlerInterface;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;


import java.net.URL;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

public class LoadingViewController implements Initializable {


    private GamesHandlerInterface gamehandler;
    private Preferences pref;

    protected boolean loadGame() {
        try {
            ViewController viewcontroller = ViewController.getInstance();
            viewcontroller.addScreen("Game", FXMLLoader.load(getClass().getResource( "/fxml/GameView.fxml" )));
            viewcontroller.activate("Game");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			ClientCommandInvoker cci = ClientCommandInvoker.getInstance();
			cci.setLoadScreen(this);
            this.pref = Preferences.userNodeForPackage(io.codenames.Main.class);
            gamehandler = (GamesHandlerInterface) Naming.lookup("rmi://"+pref.get("rmiUri", "localhost")+"/GamesHandler");

        } catch (RemoteException e) {
			e.printStackTrace();
		}catch (Exception e) {
            System.out.println(e.getMessage());
        }

	}

}
