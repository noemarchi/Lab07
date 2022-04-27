/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.poweroutages;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.poweroutages.model.Black;
import it.polito.tdp.poweroutages.model.Model;
import it.polito.tdp.poweroutages.model.Nerc;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;
    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;
    @FXML // fx:id="cmbNerc"
    private ComboBox<Nerc> cmbNerc; // Value injected by FXMLLoader
    @FXML // fx:id="txtYears"
    private TextField txtYears; // Value injected by FXMLLoader
    @FXML // fx:id="txtHours"
    private TextField txtHours; // Value injected by FXMLLoader
    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    private Model model;
    
    @FXML
    void doRun(ActionEvent event) 
    {
    	// pulizia interfaccia grafica
    	txtResult.clear();
    	
    	// acquisizione e controllo dati
    	
    	Nerc nerc = this.cmbNerc.getValue();
    	if(nerc == null)
    	{
    		this.txtResult.setText("Seleziona un nerc!!");
    		return;
    	}
    	
    	int ore;
    	int anni;
    	
    	try
    	{
    		ore = Integer.parseInt(this.txtHours.getText());
    		anni = Integer.parseInt(this.txtYears.getText());
    	}
    	catch (NumberFormatException e)
    	{
    		this.txtResult.setText("Inserisci solo numeri interi!!");
    		return;
    	}
    	
    	// chiedo al model di eseguire l'operazione
    	List<Black> res = model.getWorst(nerc, anni, ore);
    	
    	// calcolo numero totale di persone e ore
    	int totPersone = 0;
    	int totOre = 0;
    	
    	for(Black b: res)
    	{
    		totPersone = totPersone + b.getN();
    		totOre = totOre + b.getOre();
    	}
    	
    	this.txtResult.appendText("Tot persone coinvolte: " + totPersone + "\n");
    	this.txtResult.appendText("Tot ore di blackout: " + totOre + "\n");
    	this.txtResult.appendText("Elenco blackout: \n");
    	
    	for(Black b: res)
    	{
    		this.txtResult.appendText(b + "\n");
    	}
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert cmbNerc != null : "fx:id=\"cmbNerc\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtYears != null : "fx:id=\"txtYears\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtHours != null : "fx:id=\"txtHours\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";
        
        // Utilizzare questo font per incolonnare correttamente i dati;
        txtResult.setStyle("-fx-font-family: monospace");
    }
    
    public void setModel(Model model) 
    {
    	this.model = model;
    	
    	this.cmbNerc.getItems().clear();
    	
    	List<Nerc> nercList = this.model.getNercList();
    	
    	for(Nerc n: nercList)
    	{
    		this.cmbNerc.getItems().add(n);
    	}
    }
}
