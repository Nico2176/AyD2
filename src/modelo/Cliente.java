package modelo;

import java.io.Serializable;

import negocio.SistemaEmpleados;

public class Cliente implements Serializable {
	private String DNI;
	private String afinidad;
	private byte edad;
	
	
	
	public Cliente(String dNI) {
		super();
		this.DNI = dNI;
	}
	
	

	public String getAfinidad() {
		return afinidad;
	}



	public byte getEdad() {
		return edad;
	}



	public String getDNI() {
		return DNI;
	}

	public void setDNI(String DNI) {
		DNI = DNI;
	}

	@Override
	public String toString() {
		return "Cliente [DNI=" + DNI + "]";
	}
	
	

}
