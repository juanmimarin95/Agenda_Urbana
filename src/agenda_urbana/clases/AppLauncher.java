package agenda_urbana.clases;

import agenda_urbana.controladores.ControladorPrincipal;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class AppLauncher extends Application {
	
	private ControladorPrincipal controlador;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("/agenda_urbana/vistas/vistaPrincipal.fxml"));
		primaryStage.setTitle("Agenda Urbana");
		primaryStage.getIcons().add(new Image(getClass().getResource("/iconoAgendaUrbana.png").toString()));
		primaryStage.setScene(new Scene(root));
		primaryStage.show();
	}
	
	 @Override
	    public void stop() throws Exception {
	        // Detener el Timeline al cerrar la aplicación
	        if (controlador != null) {
	            controlador.detenerTimeline();
	        }
	        System.out.println("Recursos liberados y aplicación cerrada.");
	        super.stop();
	    }

	public static void runApp(String[] args) {
		launch(args); // Método para iniciar la aplicación JavaFX
	}
}
