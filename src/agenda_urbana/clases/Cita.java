package agenda_urbana.clases;

import java.time.LocalDateTime;

public class Cita {
	
	private LocalDateTime fecha;
	private String asunto;
	private String lugar;
	
	public Cita () {
		
	}
	
	public Cita (LocalDateTime fecha, String asunto, String lugar) {
		this.fecha = fecha;
		this.asunto = asunto;
		this.lugar = lugar;
	}

	public LocalDateTime getFecha() {
		return fecha;
	}

	public void setFecha(LocalDateTime fecha) {
		this.fecha = fecha;
	}

	public String getAsunto() {
		return asunto;
	}

	public void setAsunto(String asunto) {
		this.asunto = asunto;
	}

	public String getLugar() {
		return lugar;
	}

	public void setLugar(String lugar) {
		this.lugar = lugar;
	}

	
}
