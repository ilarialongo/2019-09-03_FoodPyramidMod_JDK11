package it.polito.tdp.food;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.food.model.Adiacenza;
import it.polito.tdp.food.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {
	
	private Model model;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField txtCalorie;

    @FXML
    private TextField txtPassi;

    @FXML
    private Button btnAnalisi;

    @FXML
    private Button btnCorrelate;

    @FXML
    private Button btnCammino;

    @FXML
    private ComboBox<String> boxPorzioni;

    @FXML
    private TextArea txtResult;

    @FXML
    void doCammino(ActionEvent event) {

    }

    @FXML
    void doCorrelate(ActionEvent event) {
    	txtResult.clear();
    	String porzione= boxPorzioni.getValue();
    	if (porzione==null) {
    		txtResult.appendText("ERRORE");
    		return;
    	}
    	else {
    	List<Adiacenza> adiacenze = new ArrayList<>(this.model.getPorzioniConnesse(porzione));
    	for (Adiacenza a: adiacenze) {
    		txtResult.appendText(a.getTipo1()+" "+a.getTipo2()+" "+a.getPeso()+"\n");
    	}
    	
    	}
    }

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	txtResult.clear();
    	int c;
    	try {
    		c= Integer.parseInt(txtCalorie.getText());
    		List<String> tipologie= this.model.creaGrafo(c);
    		boxPorzioni.getItems().clear();
    		boxPorzioni.getItems().addAll(tipologie);
    	} catch (NumberFormatException e) {
    		txtResult.appendText("Inserire numero valido");
    		return; 
    	}
    	this.model.creaGrafo(c);
    	txtResult.appendText(String.format("Grafo creato! %d vertici %d archi", this.model.vertici(),this.model.archi()));
    }

    @FXML
    void initialize() {
        assert txtCalorie != null : "fx:id=\"txtCalorie\" was not injected: check your FXML file 'Food.fxml'.";
        assert txtPassi != null : "fx:id=\"txtPassi\" was not injected: check your FXML file 'Food.fxml'.";
        assert btnAnalisi != null : "fx:id=\"btnAnalisi\" was not injected: check your FXML file 'Food.fxml'.";
        assert btnCorrelate != null : "fx:id=\"btnCorrelate\" was not injected: check your FXML file 'Food.fxml'.";
        assert btnCammino != null : "fx:id=\"btnCammino\" was not injected: check your FXML file 'Food.fxml'.";
        assert boxPorzioni != null : "fx:id=\"boxPorzioni\" was not injected: check your FXML file 'Food.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Food.fxml'.";

    }

	public void setModel(Model model) {
		this.model = model;
	}
}
