package io.codenames.controllers;

import java.util.HashMap;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;

public class ViewController {

	/**
	 * Scene Graph
	 */
	private static HashMap<String, Pane> viewMap = new HashMap<>();
	
	/**
	 * Root node of stage
	 */
    private static Scene mainView;

    public ViewController(Scene main) {
        ViewController.mainView = main;
    }
    /**
     * Implement Singleton
     */
	private static ViewController single_instance = null;
	
    public static ViewController getInstance(Scene main) {
    	if (single_instance == null || mainView == null) 
            single_instance = new ViewController(main);  
        return single_instance; 
    }
    
    public static ViewController getInstance() {
    	if (single_instance == null || mainView == null) 
            throw new RuntimeException("View Controller Not Initialised");
        return single_instance; 
    }
    
    
    /**
     * Add new scene to scene graph
     * @param name Name of Scene
     * @param pane panel created through fxml
     */
    protected void addScreen(String name, Pane pane){
         viewMap.put(name, pane);
    }

    /**
     * Remove a scene from scene graph
     * @param name Scene Name
     */
    protected void removeScreen(String name){
        viewMap.remove(name);
    }

    /**
     * Set scene as main active scene 
     * @param name Scene Name
     */
    protected void activate(String name){
        mainView.setRoot( viewMap.get(name) );
    }
}
