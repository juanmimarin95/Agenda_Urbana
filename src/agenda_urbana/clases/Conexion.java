package agenda_urbana.clases;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Conexion {
    
		private Conexion () {
			
		}
		
		public static void conectar () {
			
			String url = "jdbc:sqlite:agenda_urbana.db"; // Nombre o ruta de tu base de datos

	        try (Connection conexion = DriverManager.getConnection(url)) {
	            System.out.println("Conexión establecida con SQLite.");
	            
	            // Crear tabla de ejemplo
	            String sql = "CREATE TABLE IF NOT EXISTS citas ("
	                       + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
	                       + "fecha TEXT NOT NULL, "
	                       + "asunto TEXT NOT NULL,"
	                       + "lugar TEXT NOT NULL)";
	            
	            Statement stmt = conexion.createStatement();
	            stmt.execute(sql);
	            System.out.println("Conectado correctamente.");
	        } catch (SQLException e) {
	            System.out.println("Error de conexión: " + e.getMessage());
	        }
			
		}
    
}
