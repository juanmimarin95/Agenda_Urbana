package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import agenda_urbana.clases.Conexion;
import agenda_urbana.clases.Configuracion;

public class ConfiguracionDAO {

	private boolean avisar;
	private int frecuenciaAviso;
	private int numDiasPreviosAviso;
	
	public static Configuracion leerConfiguracion() {
		
		Connection conexion = Conexion.getConexion();
		String query = "SELECT * FROM configuracion";
		Configuracion configuracion = new Configuracion(true,3,3);
		
		try(PreparedStatement pstmt = conexion.prepareStatement(query);
			ResultSet rs = pstmt.executeQuery()){
			
			if (rs.next()) {
				configuracion.setAvisar(rs.getInt("avisar") == 1);
				configuracion.setFrecuenciaAviso(rs.getInt("frecuencia_aviso"));
				configuracion.setNumDiasPreviosAviso(rs.getInt("num_dias_previos_cita"));
			}
			
		}catch(SQLException e) {
			System.out.println("Error al ejecutar la query: " + e.getMessage());
			
		}
		
		return configuracion;
	}
	
	public static void modificarConfiguracion(Configuracion configuracion) {
		
		Connection conexion = Conexion.getConexion();
		String query ="UPDATE configuracion SET AVISAR = ?, FRECUENCIA_AVISO = ?, NUM_DIAS_PREVIOS_CITA = ? WHERE id = 1";
		int avisar;
		
		if(configuracion.isAvisar()) {
			avisar = 1;
		}else {
			avisar = 0;
		}
        
        try {
        PreparedStatement pstmt = conexion.prepareStatement(query);
        
        pstmt.setInt(1, avisar);
        pstmt.setInt(2, configuracion.getFrecuenciaAviso());
        pstmt.setInt(3, configuracion.getNumDiasPreviosAviso());
        
        pstmt.executeUpdate();
        
        }catch(SQLException e) {
        	System.out.println("Error al modificar la configuración: " + e.getMessage());
        }
	}
	
	public boolean isAvisar() {
		return avisar;
	}
	public void setAvisar(boolean avisar) {
		this.avisar = avisar;
	}
	public int getFrecuenciaAviso() {
		return frecuenciaAviso;
	}
	public void setFrecuenciaAviso(int frecuenciaAviso) {
		this.frecuenciaAviso = frecuenciaAviso;
	}
	public int getNumDiasPreviosAviso() {
		return numDiasPreviosAviso;
	}
	public void setNumDiasPreviosAviso(int numDiasPreviosAviso) {
		this.numDiasPreviosAviso = numDiasPreviosAviso;
	}
	
}
