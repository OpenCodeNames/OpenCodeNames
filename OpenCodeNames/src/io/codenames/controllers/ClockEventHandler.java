package io.codenames.controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;

public class ClockEventHandler implements EventHandler<ActionEvent> {
	int count = 31;
	Label countDown;
	long starTime;
	
	
	public ClockEventHandler(Label countDown) {
		this.countDown = countDown;
		this.starTime= System.currentTimeMillis();
	}
	
	public ClockEventHandler(int count, Label countDown) {
		this(countDown);
		this.count = count;
	}



	@Override
	public void handle(ActionEvent actionEvent) {
		count--;
		countDown.setText("0:"+count);
	}

}
