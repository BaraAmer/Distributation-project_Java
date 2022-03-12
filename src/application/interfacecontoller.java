package application;
import java.io.FileNotFoundException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class interfacecontoller {
	GeneticAlgo algo = new GeneticAlgo();
	    @FXML
	    private TextField L1;

	    @FXML
	    private Button B1;

	    @FXML
	    private Button B2;

	    @FXML
	    private ImageView fig1;

	    @FXML
	    void click1(ActionEvent event) throws FileNotFoundException {
	    	
	    	// reading data and trying to genrate close solution
	    	GeneticAlgo.Reading();
	    	

	    }

	    @FXML
	    void click2(ActionEvent event) {
	    	
	    	Stage page2 = new Stage();
	    	try {
	    		FXMLLoader LOADER = new FXMLLoader(getClass().getResource("Solution.fxml"));
	    		Parent root =  LOADER.load();
	    		
				Scene scene = new Scene(root,700,500);
				page2.setScene(scene);
				
				//declare the controller of solution
				Solution sol = LOADER.getController();
				
				String s = "";
				String s2 = "";
				for (int m=0 ; m< GeneticAlgo.bestchromosom.length ; m++) { 
			      s += GeneticAlgo.bestchromosom[m]+"|";
			      if (m == 5 || m==10 || m==15 || m==20 || m==25 || m==30 || m==35)
			    	 s2 += "\n";
			      s2 += "  Group "+(m+1)+":"+GeneticAlgo.bestchromosom[m]+"  |";
				}
				
				sol.display(s);
				sol.displayGroups(s2);
				
				
				page2.show();
			} catch(Exception e) {
				e.printStackTrace();
			}

	    }

	

}
