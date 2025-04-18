package agenda_urbana.clases;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class Conexion {
	
		private static Connection conexion = null;
    
		private Conexion () {}
		
		public static void conectar () {
			
			String url = "jdbc:sqlite:agenda_urbana.db"; // Nombre o ruta de tu base de datos

	        try {
	        	conexion = DriverManager.getConnection(url);
	            System.out.println("Conexión establecida con SQLite.");
	            
	            // Crear tabla de ejemplo
	            String tablaCitas = "CREATE TABLE IF NOT EXISTS citas ("
						+ "id INTEGER PRIMARY KEY AUTOINCREMENT, "
	                       + "fecha TEXT NOT NULL, "
	                       + "asunto TEXT NOT NULL,"
	                       + "lugar TEXT NOT NULL)";
	            
	            Statement stmt = conexion.createStatement();
	            stmt.execute(tablaCitas);
	            
	            String tablaConfiguracion = "CREATE TABLE IF NOT EXISTS configuracion ("
	            		+ "id INTEGER PRIMARY KEY AUTOINCREMENT,"
	            		+ "AVISAR INTEGER NOT NULL,"
	            		+ "FRECUENCIA_AVISO INTEGER NOT NULL,"
	            		+ "NUM_DIAS_PREVIOS_CITA INTEGER)";
	            
	            Statement stmt2 = conexion.createStatement();
	            stmt2.execute(tablaConfiguracion);
	            
	            String defaultConfig = "INSERT INTO configuracion (AVISAR, FRECUENCIA_AVISO, NUM_DIAS_PREVIOS_CITA) values (?,?,?)";
	            
	            PreparedStatement pstmt = conexion.prepareStatement(defaultConfig);
	            
	            pstmt.setInt(1, 1);
	            pstmt.setInt(2, 3);
	            pstmt.setInt(3, 3);
	            
	            pstmt.executeUpdate();
	            System.out.println("Conectado correctamente.");
	        } catch (SQLException e) {
	            System.out.println("Error de conexión: " + e.getMessage());
	        }
			
		}
		
		public static Connection getConexion() {
			if (conexion == null) {
				conectar();
			}
			return conexion;
		}
		
		public static void cerrarConexion() {
			
			if (conexion != null) {
				try{
					conexion.close();
					System.out.println("Conexión cerrada.");
	                conexion = null;
				}catch(SQLException e){
					System.out.println("Error al cerrar la conexión: " + e.getMessage());
				}
			}
		}
    
}
