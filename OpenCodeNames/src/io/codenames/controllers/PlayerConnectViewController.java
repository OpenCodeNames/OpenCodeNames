package io.codenames.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import io.codenames.data.Game;
import io.codenames.data.GameHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class PlayerConnectViewController implements Initializable {
	@FXML
	private TableView<Game> gamesTable;
	
	@FXML
	private TableColumn<Game, String> creatorCol;
	
	@FXML
	private TableColumn<Game, String> gameNameCol;
	
	@FXML
	private TableColumn<Game, String> seatAvailableCol;
	
	
	
	@FXML
	private JFXButton joinbtn;
	
	@FXML
	private JFXButton resetBtn;
	
	@FXML 
	private Label creatorLabel;
	
	@FXML 
	private Label creatorName;
	

	@FXML 
	private Label gameLabel;
	
	@FXML 
	private Label gameName;
	
	/**
	 * action listener for table row select
	 * @param oldSelection
	 * @param newSelection
	 */
	public void handleGameSelected(Game newSelection) {
		if(joinbtn.isVisible() == false) {
			joinbtn.setVisible(true);
			creatorLabel.setVisible(true);
			creatorName.setVisible(true);
			gameLabel.setVisible(true);
			gameName.setVisible(true);
			resetBtn.setVisible(true);
		}
		
		creatorName.setText(newSelection.getCreator());
		gameName.setText(newSelection.getName());
	}
	
	/**
	 * Action listener for back button
	 * @param event
	 */
	@FXML 
	public void backAction(ActionEvent event) {
		try {
			ViewController viewcontroller = ViewController.getInstance();
			viewcontroller.addScreen("Back", FXMLLoader.load(getClass().getResource( "/fxml/MenuView.fxml" )));
			viewcontroller.activate("Back");
		}
		catch (IOException e) {
			e.printStackTrace();
		}catch (RuntimeException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Initialize Player Connection View 
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		joinbtn.setVisible(false);
		creatorLabel.setVisible(false);
		creatorName.setVisible(false);
		gameLabel.setVisible(false);
		gameName.setVisible(false);
		resetBtn.setVisible(false);

		gameNameCol.setCellValueFactory(new PropertyValueFactory<Game, String>("name"));
		creatorCol.setCellValueFactory(new PropertyValueFactory<Game, String>("creator"));
		seatAvailableCol.setCellValueFactory(new PropertyValueFactory<Game, String>("seatsAvailable"));
		GameHandler gamehandler = GameHandler.getInstance();
		
		gamesTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> handleGameSelected((Game)newSelection));
		gamesTable.setItems(gamehandler.getGamesList());
	}
}
