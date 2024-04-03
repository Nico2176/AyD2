package modelo;

import java.io.Serializable;

import negocio.SistemaEmpleados;

public class Cliente implements Serializable {
	private String DNI;
	
	
	
	public Cliente(String dNI) {
		super();
		DNI = dNI;
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
