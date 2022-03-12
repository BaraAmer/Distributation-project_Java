package application;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class Solution {
	
	@FXML
    Label l2;
	
	 @FXML
	 Label l3;
	
	

	public void display(String s) {
		l2.setText(s);
	}
 
	
	public void displayGroups(String s) {
		l3.setText(s);
	}
	
	
	

}
