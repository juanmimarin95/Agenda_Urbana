package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;

import agenda_urbana.clases.Cita;
import agenda_urbana.clases.Conexion;

	public class CitaDAO {


	
	public static ArrayList<Cita> leerCitas() {
		
		Connection conexion = Conexion.getConexion();
		ArrayList<Cita> citas = new ArrayList<Cita>();
		String query = "SELECT * FROM citas";
		try(PreparedStatement preparedStatement = conexion.prepareStatement(query);
			ResultSet resultSet = preparedStatement.executeQuery()){
			
			while (resultSet.next()) {
				Cita cita = new Cita(LocalDateTime.parse(resultSet.getString("fecha")), resultSet.getString("asunto"), resultSet.getString("lugar"));
				citas.add(cita);
			}
		}catch(SQLException e) {
			System.out.println("Error al leer las citas: " + e.getMessage());
		}
		
		return citas;
	}
	
	public static void crearCita(Cita cita) {
		
		Connection conexion = Conexion.getConexion();
		String query = "INSERT INTO citas (fecha, asunto, lugar) VALUES (?,?,?)";
		
		try(PreparedStatement preparedStatement = conexion.prepareStatement(query)){
			
			preparedStatement.setString(1, cita.getFecha().toString());
			preparedStatement.setString(2, cita.getAsunto());
			preparedStatement.setString(3, cita.getLugar());
			
			preparedStatement.executeUpdate();
			System.out.println("Cita añadida correctamente.");
			
		}catch(SQLException e) {
			System.out.println("Error al registrar la cita: " + e.getMessage());
		}
		
	}
	
	public static void borrarCita() {
		
		Connection conexion = Conexion.getConexion();
		
	}
	public static void modificarCita() {
		
		Connection conexion = Conexion.getConexion();
		
	}
}
