package io.codenames.controllers;

import java.net.URL;
import java.rmi.RemoteException;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;

public class GameViewController implements Initializable {
	
	@FXML
	private JFXButton chatSend;
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			ClientCommandInvoker cci = ClientCommandInvoker.getInstance();
			cci.setGameScreen(this);

		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
}
