package io.codenames.controllers;

import java.io.IOException;
import java.net.URL;
import java.rmi.Naming;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

import io.codenames.serverinterfaces.PlayersHandlerInterface;
import javafx.event.ActionEvent;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import javax.swing.*;

public class LoginViewController implements Initializable {

    private PlayersHandlerInterface playerhandler;
    private Preferences pref;

    @FXML
    private TextField userName;

    @FXML
    private PasswordField userPass;
	/**
	 * Action listener for login button
	 * @param event
	 */
	@FXML 
	protected void handleLoginAction(ActionEvent event) {
		try {
		    if(playerhandler.authenticate(userName.getText(),userPass.getText())) {
                this.pref.put("userName", userName.getText());
                ViewController viewcontroller = ViewController.getInstance();
                viewcontroller.addScreen("Menu", FXMLLoader.load(getClass().getResource("/fxml/MenuView.fxml")));
                viewcontroller.activate("Menu");
            }else{
                JOptionPane.showMessageDialog(new JFrame(), "User Name and Password incorrect", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
		} catch (IOException e) {
			e.printStackTrace();
		}catch (RuntimeException e) {
			e.printStackTrace();
		}catch (Exception e){
            e.printStackTrace();
        }
	}
	
	/**
	 * Action listener for login button
	 * @param event
	 */
	@FXML 
	protected void handleRegistrationAction(ActionEvent event) {
		try {
			ViewController viewcontroller = ViewController.getInstance();
			viewcontroller.addScreen("RegistrationView", FXMLLoader.load(getClass().getResource( "/fxml/UserRegistrationView.fxml" )));
			viewcontroller.activate("RegistrationView");
		} catch (IOException e) {
			e.printStackTrace();
		}catch (RuntimeException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
        try{
            this.pref = Preferences.userNodeForPackage(io.codenames.Main.class);
            playerhandler = (PlayersHandlerInterface) Naming.lookup("rmi://"+pref.get("rmiUri", "localhost")+"/PlayersHandler");
        }catch (Exception e){
            System.out.println(e.getMessage());
            
        }
	}
}
