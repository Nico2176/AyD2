package modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Queue;

public class Pedido implements Serializable {
	private ArrayList<Cliente> clientes;
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


	

	public Pedido(int box, String dNISig) {
		super();
		this.box = box;
		this.DNISig = dNISig;
	}



	public void setSiguiente(boolean siguiente) {
		this.siguiente = siguiente;
	}


	

	public Pedido(ArrayList<Cliente> clientes, int box, boolean siguiente, String DNISig) {
		super();
		this.clientes = clientes;
		this.box = box;
		this.siguiente=siguiente;
		this.DNISig=DNISig;
	}
	
	
	
	public Pedido(ArrayList<Cliente> clientes) {
		super();
		this.clientes = clientes;
	}



	public ArrayList<Cliente> getClientes() {
		return clientes;
	}
	public void setClientes(ArrayList<Cliente> clientes) {
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
