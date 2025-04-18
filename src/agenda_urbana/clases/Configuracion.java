package agenda_urbana.clases;

public class Configuracion {

	private boolean avisar;
	private int frecuenciaAviso;
	private int numDiasPreviosAviso;
	
	public Configuracion() {}
	
	public Configuracion(boolean avisar, int frecuenciaAviso, int numDiasPreviosAviso) {
		this.avisar = avisar;
		this.frecuenciaAviso = frecuenciaAviso;
		this.numDiasPreviosAviso = numDiasPreviosAviso;
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
