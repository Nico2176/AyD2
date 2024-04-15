package negocio;

public class Estadisticas {
	private int segundosTotales;
	private int segundosAtendiendo;
	private int segundosDesocupado;
	private int clientesAtendidos;
	
	public Estadisticas() {
		this.segundosAtendiendo=0;
		this.segundosTotales=0;
		this.segundosDesocupado=0;
		this.clientesAtendidos=0;
	}
		
	public float getPromedioxHora() {
		System.out.println(this.toString());
		if (segundosTotales!=0)
			return (float) clientesAtendidos/((float) segundosTotales/3600);
		else
			return 0;
	}
	
	public int getPromedioTiempoAtencion() {
		if (clientesAtendidos!=0)
			return segundosAtendiendo/clientesAtendidos;
		else 
			return 0;
	}
	
	public int getSegundosTotales() {
		return segundosTotales;
	}
	public int getSegundosAtendiendo() {
		return segundosAtendiendo;
	}
	public int getSegundosDesocupado() {
		return segundosDesocupado;
	}
	public int getClientesAtendidos() {
		return clientesAtendidos;
	}
	
	
	
	@Override
	public String toString() {
		return "Estadisticas [segundosTotales=" + segundosTotales + "\n segundosAtendiendo=" + segundosAtendiendo
				+ "\n segundosDesocupado=" + segundosDesocupado + "\n clientesAtendidos=" + clientesAtendidos + "]";
	}

	public void clienteAtendido(int segAtendiendo, int segDesocupado) {
		this.segundosTotales+=segAtendiendo+segDesocupado;
		this.segundosAtendiendo+=segAtendiendo;
		this.segundosDesocupado+=segDesocupado;
		this.clientesAtendidos++;
	}
	
}
