package io.codenames.controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;

public class ClockEventHandler implements EventHandler<ActionEvent> {
	int count = 31;
	Label countDown;
	long starTime;
	boolean stoped=false;
	GameViewController gameview;
	
	public ClockEventHandler(Label countDown, GameViewController gameViweController) {
		this.countDown = countDown;
		gameview = gameViweController;
		this.starTime= System.currentTimeMillis();
	}
	
	public ClockEventHandler(int count, Label countDown, GameViewController gameViweController) {
		this(countDown, gameViweController);
		this.count = count;
	}

	public void stopClock() {
		this.stoped=true;
	}
	public void startClock() {
		this.stoped=false;
		count=30;
	}
	@Override
	public void handle(ActionEvent actionEvent) {
		if(!stoped) {
			count--;
			if(count == 0) {
				this.stopClock();
			}
		}
		else {
			if(count == 0) {
				this.gameview.timeOver();	
			}
			
		}
		countDown.setText("0:"+count);
	}

}
