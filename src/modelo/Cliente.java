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
	
	
	

	public Cliente(String dNI, String afinidad, byte edad) {
		super();
		DNI = dNI;
		this.afinidad = afinidad;
		this.edad = edad;
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
	
	

	public void setAfinidad(String afinidad) {
		this.afinidad = afinidad;
	}




	public void setEdad(byte edad) {
		this.edad = edad;
	}




	@Override
	public String toString() {
		return "Cliente [DNI=" + DNI + ", afinidad=" + afinidad + ", edad=" + edad + "]";
	}


	@Override
    public boolean equals(Object o) {  //reescribo equals para que cuando busque en las listas los considere iguales si tienen DNI igual.
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cliente cliente = (Cliente) o;
        return DNI.equalsIgnoreCase(cliente.getDNI());
    }


	
	

}
