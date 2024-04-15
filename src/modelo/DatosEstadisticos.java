package modelo;

import java.io.Serializable;

public class DatosEstadisticos implements Serializable{
	private int segundosAtendiendo;
	private int segundosDesocupado;

	
	
	
	public int getSegundosAtendiendo() {
		return segundosAtendiendo;
	}

	public void setSegundosAtendiendo(int segundosAtendiendo) {
		this.segundosAtendiendo = segundosAtendiendo;
	}

	public int getSegundosDesocupado() {
		return segundosDesocupado;
	}

	public void setSegundosDesocupado(int segundosDesocupado) {
		this.segundosDesocupado = segundosDesocupado;
	}

	@Override
	public String toString() {
		return "DatosEstadisticos [segundosAtendiendo=" + segundosAtendiendo + ", segundosDesocupado="
				+ segundosDesocupado + "]";
	}

	
	
}
