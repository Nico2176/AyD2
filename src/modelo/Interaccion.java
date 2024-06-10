package modelo;

public class Interaccion {
	private String fechayHora;
	private String DNI;
	private String interaccion;
	
	
	public Interaccion(String fechayHora, String dNI, String interaccion) {
		super();
		this.fechayHora = fechayHora;
		DNI = dNI;
		this.interaccion = interaccion;
	}
	
	
	
	
	public String getFechayHora() {
		return fechayHora;
	}




	public String getDNI() {
		return DNI;
	}




	public String getInteraccion() {
		return interaccion;
	}




	public String toString() {
		return fechayHora+"|"+DNI+"|"+interaccion;
	}
	

}
