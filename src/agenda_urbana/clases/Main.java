package agenda_urbana.clases;


public class Main {
	public static void main(String[] args) {

		Conexion.conectar();

		// Llama a la clase de JavaFX para iniciar la interfaz gráfica
		AppLauncher.runApp(args);
	}
}
