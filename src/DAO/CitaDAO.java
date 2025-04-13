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

	public static int obtenerIdMasAlto() {
		Connection conexion = Conexion.getConexion();
		int nuevoId = 1;
		// El alias es simplemente para que referenciar el valor en el rs sea mas
		// sencillo
		String query = "SELECT MAX(id) AS max_id FROM citas";
		try (PreparedStatement preparedStatement = conexion.prepareStatement(query);
				ResultSet resultSet = preparedStatement.executeQuery()) {
			
			if(resultSet.next()) {
				nuevoId = resultSet.getInt("max_id") + 1;
			}

		} catch (SQLException e) {
			System.out.println("Error al leer el id mas alto: " + e.getMessage());
		}
		
		return nuevoId;

	}

	public static ArrayList<Cita> leerCitas() {

		Connection conexion = Conexion.getConexion();
		ArrayList<Cita> citas = new ArrayList<Cita>();
		String query = "SELECT * FROM citas";
		try (PreparedStatement preparedStatement = conexion.prepareStatement(query);
				ResultSet resultSet = preparedStatement.executeQuery()) {

			while (resultSet.next()) {
				Cita cita = new Cita(resultSet.getInt("id"), LocalDateTime.parse(resultSet.getString("fecha")), resultSet.getString("asunto"),
						resultSet.getString("lugar"));
				citas.add(cita);
			}
		} catch (SQLException e) {
			System.out.println("Error al leer las citas: " + e.getMessage());
		}

		return citas;
	}

	public static void crearCita(Cita cita) {

		Connection conexion = Conexion.getConexion();
		String query = "INSERT INTO citas (id, fecha, asunto, lugar) VALUES (?,?,?,?)";

		try (PreparedStatement preparedStatement = conexion.prepareStatement(query)) {

			preparedStatement.setInt(1, cita.getId());
			preparedStatement.setString(2, cita.getFecha().toString());
			preparedStatement.setString(3, cita.getAsunto());
			preparedStatement.setString(4, cita.getLugar());

			preparedStatement.executeUpdate();
			System.out.println("Cita añadida correctamente.");

		} catch (SQLException e) {
			System.out.println("Error al registrar la cita: " + e.getMessage());
		}

	}

	public static void borrarCita(int id) {

		Connection conexion = Conexion.getConexion();
		String query = "DELETE FROM citas WHERE id = ?";

		try (PreparedStatement preparedStatement = conexion.prepareStatement(query)) {

			preparedStatement.setInt(1, id);

			preparedStatement.executeUpdate();
			System.out.println("Cita eliminada correctamente.");

		} catch (SQLException e) {
			System.out.println("Error al registrar la cita: " + e.getMessage());
		}

	}

	public static void modificarCita(Cita cita) {

		Connection conexion = Conexion.getConexion();
		String query = "UPDATE citas SET fecha = ?, asunto = ?, lugar = ? WHERE id = ?";
		try (PreparedStatement preparedStatement = conexion.prepareStatement(query)) {
			preparedStatement.setString(1, cita.getFecha().toString());
			preparedStatement.setString(2, cita.getAsunto());
			preparedStatement.setString(3, cita.getLugar());
			preparedStatement.setInt(4, cita.getId());

			preparedStatement.executeUpdate();
			System.out.println("Cita modificada correctamente.");

		} catch (SQLException e) {
			System.out.println("Error al registrar la cita: " + e.getMessage());
		}
	}
}
