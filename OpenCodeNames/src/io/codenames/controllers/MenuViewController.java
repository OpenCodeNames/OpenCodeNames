package io.codenames.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import javafx.event.ActionEvent;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.stage.Stage;

public class MenuViewController implements Initializable {
	
	/**
	 * Action listener for Statistic button
	 * @param event
	 */
	@FXML 
	protected void playerStatsAction(ActionEvent event) {
		try {
				ViewController viewcontroller = ViewController.getInstance();
				viewcontroller.addScreen("PlayerStats", FXMLLoader.load(getClass().getResource( "/fxml/PlayerStatsView.fxml" )));
				viewcontroller.activate("PlayerStats");
		}
		catch (IOException e) {
			e.printStackTrace();
		}catch (RuntimeException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Action listener for Join game button
	 * @param event
	 */
	
	@FXML 
	protected void joinGameAction(ActionEvent event) {
		try {
				ViewController viewcontroller = ViewController.getInstance();
				viewcontroller.addScreen("JoinGame", FXMLLoader.load(getClass().getResource( "/fxml/PlayerConnectView.fxml" )));
				viewcontroller.activate("JoinGame");
		}
		catch (IOException e) {
			e.printStackTrace();
		}catch (RuntimeException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Action listener for Create Game button
	 * @param event
	 */

	@FXML
	protected void createGameAction(ActionEvent event) {
		try {
				ViewController viewcontroller = ViewController.getInstance();
				viewcontroller.addScreen("PlayerStats", FXMLLoader.load(getClass().getResource( "/fxml/CreateGame.fxml" )));
				viewcontroller.activate("PlayerStats");
		}
		catch (IOException e) {
			e.printStackTrace();
		}catch (RuntimeException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Action listener for Quit Game button
	 * @param event
	 */
	
	@FXML
	private void closeButtonAction(){
		ViewController.closeGame();
	}

	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}
}
