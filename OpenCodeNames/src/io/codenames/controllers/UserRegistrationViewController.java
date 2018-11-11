package io.codenames.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

public class UserRegistrationViewController implements Initializable  {

	/**
	 * Action listener for User Registration button
	 * @param event
	 */
	
	@FXML 
	protected void registerAction(ActionEvent event) {
		try {
				ViewController viewcontroller = ViewController.getInstance();
				viewcontroller.addScreen("LoginView", FXMLLoader.load(getClass().getResource( "/fxml/LoginView.fxml" )));
				viewcontroller.activate("LoginView");
		}
		catch (IOException e) {
			e.printStackTrace();
		}catch (RuntimeException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Action listener for back button
	 * @param event
	 */
	
	@FXML 
	protected void backAction(ActionEvent event) {
		try {
				ViewController viewcontroller = ViewController.getInstance();
				viewcontroller.addScreen("Back", FXMLLoader.load(getClass().getResource( "/fxml/LoginView.fxml" )));
				viewcontroller.activate("Back");
		}
		catch (IOException e) {
			e.printStackTrace();
		}catch (RuntimeException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}
}
