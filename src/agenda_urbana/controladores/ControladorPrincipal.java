package agenda_urbana.controladores;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

import DAO.CitaDAO;
import agenda_urbana.clases.Cita;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class ControladorPrincipal {

	private Cita citaSeleccionada;

	@FXML
	private Label labelInfo;
	@FXML
	private Button botonRegistrarCita, botonBorrarCita, botonModificarCita, botonLlenarCitas;
	@FXML
	private DatePicker datePickerFecha;
	@FXML
	private TableView<Cita> tableViewTablaCitas;
	@FXML
	private TableColumn<Cita, LocalDateTime> colFecha;
	@FXML
	private TableColumn<Cita, String> colAsunto;
	@FXML
	private TableColumn<Cita, String> colLugar;
	@FXML
	private TableColumn<Cita, Integer> colId;
	@FXML
	private TextField inputHoras, inputMinutos, inputLugar, inputAsunto;

	public ControladorPrincipal() {

	}

	@FXML
	public void initialize() {
		leerCitas();

		tableViewTablaCitas.setOnMouseClicked(event -> {
			citaSeleccionada = tableViewTablaCitas.getSelectionModel().getSelectedItem();
		});
	}

	@FXML
	public void handleSubmit() {

		LocalTime horasyminutos = LocalTime.of(Integer.parseInt(inputHoras.getText()),
				Integer.parseInt(inputMinutos.getText()));
		LocalDateTime fecha = LocalDateTime.of(datePickerFecha.getValue(), horasyminutos);

		Cita cita = new Cita(CitaDAO.obtenerIdMasAlto(), fecha, inputAsunto.getText(), inputLugar.getText());

		CitaDAO.crearCita(cita);

		leerCitas();
	}

	@FXML
	public void leerCitas() {

		ArrayList<Cita> citas = CitaDAO.leerCitas();

		ObservableList<Cita> observableCitas = FXCollections.observableArrayList(citas);

		colId.setCellValueFactory(new PropertyValueFactory<>("id"));
		colFecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));
		colAsunto.setCellValueFactory(new PropertyValueFactory<>("asunto"));
		colLugar.setCellValueFactory(new PropertyValueFactory<>("lugar"));

		tableViewTablaCitas.setItems(observableCitas);
	}

	@FXML
	public void borrarCita() {
		

		if (citaSeleccionada != null) {
			CitaDAO.borrarCita(citaSeleccionada.getId());
		} else {
			labelInfo.setText("Selecciona un registro");
		}
		
		leerCitas();
	}

	@FXML
	public void modificarCita() {
		
		if (citaSeleccionada != null) {
			LocalTime horasyminutos = LocalTime.of(Integer.parseInt(inputHoras.getText()),
					Integer.parseInt(inputMinutos.getText()));
			LocalDateTime fecha = LocalDateTime.of(datePickerFecha.getValue(), horasyminutos);
			
			Cita citaModificada = new Cita(citaSeleccionada.getId(), fecha, inputAsunto.getText(), inputLugar.getText()); 
			CitaDAO.modificarCita(citaModificada);
		} else {
			labelInfo.setText("Selecciona un registro");
		}
		
		leerCitas();
	}

}
