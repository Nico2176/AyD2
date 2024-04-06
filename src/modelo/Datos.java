package modelo;

import java.io.Serializable;
import java.util.Queue;

public class Datos implements Serializable {
	private Queue<Cliente> clientes;
	private int box;
	private boolean siguiente=false;
	private String DNISig;
	
	
	
	
	public String getDNISig() {
		return DNISig;
	}



	public void setDNISig(String dNISig) {
		DNISig = dNISig;
	}



	public boolean isSiguiente() {
		return siguiente;
	}


	

	public Datos(int box, String dNISig) {
		super();
		this.box = box;
		this.DNISig = dNISig;
	}



	public void setSiguiente(boolean siguiente) {
		this.siguiente = siguiente;
	}


	

	public Datos(Queue<Cliente> clientes, int box, boolean siguiente, String DNISig) {
		super();
		this.clientes = clientes;
		this.box = box;
		this.siguiente=siguiente;
		this.DNISig=DNISig;
	}
	
	
	
	public Datos(Queue<Cliente> clientes) {
		super();
		this.clientes = clientes;
	}



	public Queue<Cliente> getClientes() {
		return clientes;
	}
	public void setClientes(Queue<Cliente> clientes) {
		this.clientes = clientes;
	}
	public int getBox() {
		return box;
	}
	public void setBox(int box) {
		this.box = box;
	}



	@Override
	public String toString() {
		return "Datos [clientes=" + clientes + ", box=" + box + ", siguiente=" + siguiente + ", DNISig=" + DNISig + "]";
	}
	
	
	

}
