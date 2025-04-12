package agenda_urbana.clases;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AppLauncher extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/agenda_urbana/vistas/vistaPrincipal.fxml"));
        primaryStage.setTitle("Aplicación JavaFX");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public static void runApp(String[] args) {
        launch(args); // Método para iniciar la aplicación JavaFX
    }
}

