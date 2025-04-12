package agenda_urbana.controladores;

import agenda_urbana.clases.Cita;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class ControladorPrincipal {
	@FXML
	private Label labelInfo;
	@FXML
	private Button botonRegistrarCita, botonBorrarCita, botonModificarCita;
	@FXML
	private DatePicker datePickerFecha;
	@FXML
	private TableView<Cita> tableViewTablaCitas;
	@FXML
	private TextField inputHoras, inputMinutos, inputLugar, inputAsunto;
	
	
	public ControladorPrincipal () {
		
	}
	
	@FXML
	public void handleSubmit () {
		
	}

}
