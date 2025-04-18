package agenda_urbana.controladores;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import DAO.CitaDAO;
import agenda_urbana.clases.Cita;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;


public class ControladorPrincipal {

	private Cita citaSeleccionada;
	private Timeline timeline;
	private boolean alertMostrado = false;
	
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

	public ControladorPrincipal() {}

	@FXML
	public void initialize() {

		leerCitas();
		
		tableViewTablaCitas.setOnMouseClicked(event -> {
			citaSeleccionada = tableViewTablaCitas.getSelectionModel().getSelectedItem();
		});
		
		periodicidadAlert();
		Platform.runLater(() ->{
			refrescarCitasYNotificaciones();
		});
	}
	
	public void refrescarCitasYNotificaciones () {
		if (!alertMostrado) {
			StringBuilder listaCitas = new StringBuilder();
			
			leerCitas();
			
			if(tableViewTablaCitas.getItems() != null && !tableViewTablaCitas.getItems().isEmpty()) {
				for (Cita cita : tableViewTablaCitas.getItems()) {
					if (cita != null) {
						if(compararFechas(cita)) {
							listaCitas.append(cita.toString() + "\n");
						}
					}
				}
				if(listaCitas.length() > 0) {
					lanzarAlertCitasProximas(listaCitas);
					alertMostrado = true;
					
				}
				
			}else {
				System.out.println("No se pueden comparar las fechas, la tabla esta vacia");
			}
		}
	}
	
	public void lanzarAlertCitasProximas (StringBuilder citasProximas) {
		
		String rutaSonidoAlerta = getClass().getResource("/notification-alert.mp3").toExternalForm();
		AudioClip clip = new AudioClip(rutaSonidoAlerta);
		
		Alert alertaCitas = new Alert(AlertType.INFORMATION);
		
		alertaCitas.setTitle("Recordatorio de citas próximas");
		alertaCitas.setHeaderText(null);
		alertaCitas.setContentText(citasProximas.toString());
		clip.play();
		alertaCitas.show();
		alertaCitas.setOnHidden(event -> {
			alertMostrado = false;
		});
		
	}
	
	public boolean compararFechas(Cita cita) {
		boolean citaProxima = false;
	    long diasDiferencia = ChronoUnit.DAYS.between(LocalDateTime.now(), cita.getFecha());

	    if (diasDiferencia <= 3 && diasDiferencia >= 0) {
	    	citaProxima = true;
	    }
	    
	    return citaProxima;
	}
	
	public void periodicidadAlert() {
		
	    // Crear el Timeline con un intervalo de 30 segundos
        timeline = new Timeline(new KeyFrame(Duration.hours(3), event -> {
            refrescarCitasYNotificaciones();
        }));

        // Configurar para que se repita indefinidamente
        timeline.setCycleCount(Timeline.INDEFINITE);

        // Iniciar el Timeline
        timeline.play();
    }

    public void detenerTimeline() {
        if (timeline != null) {
            timeline.stop();
            System.out.println("Timeline detenido correctamente.");
        }
    }
	
	@FXML
	public void resetarEstiloBordeRojo(){
		
		datePickerFecha.setStyle("");
		inputHoras.setStyle("");
		inputMinutos.setStyle("");
		inputAsunto.setStyle("");
		inputLugar.setStyle("");
	}
	
	@FXML
	public boolean validarFormulario() {
		
		StringBuilder str = new StringBuilder();
		boolean comprobacion = true;
		
		resetarEstiloBordeRojo();
		
		if (datePickerFecha.getEditor().getText().isBlank() || datePickerFecha.getValue() == null ) {
			str.append("Por favor seleccione la fecha de la cita\n");
			comprobacion = false;
			datePickerFecha.setStyle("-fx-border-color: #bf0f23; -fx-border-width: 2px;");
		}
		
		if (inputHoras.getText() == null || inputHoras.getText().isBlank()) {
			
			str.append("Por favor introduzca la hora de la cita\n");
			comprobacion = false;
			inputHoras.setStyle("-fx-border-color: #bf0f23; -fx-border-width: 2px;");
			
		}else {
			try {
				int hora = Integer.parseInt(inputHoras.getText());
				if(hora < 0 || hora > 23) {
					comprobacion = false;
					str.append("Introduzca un valor entre 0 y 23\n");
					inputHoras.setStyle("-fx-border-color: #bf0f23; -fx-border-width: 2px;");
					
				}
				
			}catch(NumberFormatException e) {
				str.append("Introduzca un valor numérico\n");
				inputHoras.setStyle("-fx-border-color: #bf0f23; -fx-border-width: 2px;");
			}
		}
		
		if (inputMinutos.getText() == null || inputMinutos.getText().isBlank()) {
			str.append("Introduzca los minutos de la cita\n");
			inputMinutos.setStyle("-fx-border-color: #bf0f23; -fx-border-width: 2px;");
			comprobacion = false;
		}else {
			try {
				
				int minutos = Integer.parseInt(inputMinutos.getText());
				
				if(minutos < 0 || minutos > 59) {
					str.append("Introduzca un valor entre 0 y 59\n");
					inputMinutos.setStyle("-fx-border-color: #bf0f23; -fx-border-width: 2px;");
					comprobacion = false;
					
				}
				
			}catch(NumberFormatException e) {
				str.append("Introduzca un valor numérico\n");
				inputMinutos.setStyle("-fx-border-color: #bf0f23; -fx-border-width: 2px;");
				comprobacion = false;
			}
		}
		if (inputAsunto.getText() == null || inputAsunto.getText().isBlank()) {
			str.append("Por favor introduzca el asunto de la cita\n");
			inputAsunto.setStyle("-fx-border-color: #bf0f23; -fx-border-width: 2px;");
			comprobacion = false;
		}
		if (inputLugar.getText() == null || inputLugar.getText().isBlank()) {
			str.append("Por favor introduzca el lugar de la cita\n");
			inputLugar.setStyle("-fx-border-color: #bf0f23; -fx-border-width: 2px;");
			comprobacion = false;
		}
		
		labelInfo.setTextFill(Color.web("#bf0f23"));
		labelInfo.setText(str.toString());
		labelInfo.setStyle("");
		return comprobacion;
	}

	@FXML
	public void handleSubmit() {

		if(validarFormulario()) {
			LocalTime horasyminutos = LocalTime.of(Integer.parseInt(inputHoras.getText()),
					Integer.parseInt(inputMinutos.getText()));
			LocalDateTime fecha = LocalDateTime.of(datePickerFecha.getValue(), horasyminutos);

			Cita cita = new Cita(CitaDAO.obtenerIdMasAlto(), fecha, inputAsunto.getText(), inputLugar.getText());

			CitaDAO.crearCita(cita);

			refrescarCitasYNotificaciones();
		}
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
		
		if (validarFormulario()) {
			if (citaSeleccionada != null) {
				LocalTime horasyminutos = LocalTime.of(Integer.parseInt(inputHoras.getText()),
						Integer.parseInt(inputMinutos.getText()));
				LocalDateTime fecha = LocalDateTime.of(datePickerFecha.getValue(), horasyminutos);
				
				Cita citaModificada = new Cita(citaSeleccionada.getId(), fecha, inputAsunto.getText(), inputLugar.getText()); 
				CitaDAO.modificarCita(citaModificada);
			} else {
				labelInfo.setText("Selecciona un registro");
			}
			
			refrescarCitasYNotificaciones();
		}
	}

}
