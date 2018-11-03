package io.codenames.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

public class LoginViewController implements Initializable {
	
	@FXML 
	protected void handleLoginAction(ActionEvent event) {
		try {
			ViewController viewcontroller = ViewController.getInstance();
			viewcontroller.addScreen("Menu", FXMLLoader.load(getClass().getResource( "/fxml/MenuView.fxml" )));
			viewcontroller.activate("Menu");
		} catch (IOException e) {
			e.printStackTrace();
		}catch (RuntimeException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}
}
