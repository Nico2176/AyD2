package modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class Cola implements Serializable{

	private final int cantGruposAfinidad = 4;
	private final int cantGruposEtarios = 4;
	private int criterio = 1;


	
	private ArrayList<ArrayList<Cliente>> clientes = new ArrayList<>();       //un arraylist de arraylist de clientes asi tengo un arraylist según cada tipo
	private ArrayList<Cliente> lista = new ArrayList<>();

	
	/*private ArrayList<Cliente> clientesVIP = new ArrayList<>();
	private ArrayList<Cliente> clientesPREMIUM = new ArrayList<>();
	private ArrayList<Cliente> clientesPLATINUM = new ArrayList<>();
	private ArrayList<Cliente> clientesGOLD = new ArrayList<>();
	
	private ArrayList<Cliente> clientes18_30 = new ArrayList<>();
	private ArrayList<Cliente> clientes30_45 = new ArrayList<>();
	private ArrayList<Cliente> clientes45_60 = new ArrayList<>();
	private ArrayList<Cliente> clientes60p = new ArrayList<>(); */
	
	public Cola() { //según el criterio que uso creo la cantidad de arraylist que necesitaré
		if (criterio==0) {
			clientes.add(new ArrayList<Cliente>());
		} else if (criterio==1) {
			for (int i=0; i<cantGruposAfinidad; i++) {
				clientes.add(new ArrayList<Cliente>());
			}
		}
		else if (criterio==2) {
			for (int i=0; i<cantGruposEtarios; i++) {
				clientes.add(new ArrayList<Cliente>());
			}
		}
		
	}

	public ArrayList<ArrayList<Cliente>> getClientes() {
		return clientes;
	}
	
	
	public void updateLista(){        //paso todos los datos a un solo arraylist
		lista.clear();
		if (criterio==0) {
			lista = clientes.get(0);
		} else if (criterio==1) {
			for (int i=0; i<cantGruposAfinidad;i++) {
				lista.addAll(clientes.get(i));
			}
		}
		else if (criterio==2) {
			for (int i=0; i<cantGruposEtarios;i++) {
				lista.addAll(clientes.get(i));
			}
		}
		System.out.println(this.toString());
	}
	
	public void removeAtendido(Cliente cliente) {
		System.out.println("Al remove atendido le llegó "+ cliente.toString());
		for (int i=0; i<clientes.size();i++) {
			System.out.println("Removi cliente de la lista "+ i + ": "+ clientes.get(i).remove(cliente));	
		}
		System.out.println("Removi cliente de la lista: "+ this.lista.remove(cliente));	
		
	}

	public ArrayList<Cliente> getLista() {
		return lista;
	}

	@Override
	public String toString() {
		return "Cola [clientes=" + clientes + "\n, lista=" + lista + "]";
	}

	public void setLista(ArrayList<Cliente> lista) {
		this.lista = lista;
	}
	
	
	

	

		
		
	
		
	/*
	 * 	private ArrayList<Cliente> clientesxAfinidad = new ArrayList<>(); //arraylist por grupo de afinidad (supongase gold, platinum, premium y VIP)
	private ArrayList<Cliente> clientesxEdad = new ArrayList<>();      //arraylist por edades, (supongase 18-30, 30-45, 45-60, 60+)
	private byte delimitadorA1=0, delimitadorA2=0, delimitadorA3=0; //delimitadores que marcarán en qué posición del arraylist termina cada afinidad para no realizar busquedas costosas 
	private byte delimitadorE1=0, delimitadorE2=0, delimitadorE3=0; //delimitadores que marcarán en qué posición del arraylist termina cada grupo etario para no realizar busquedas costosas 
	 
	 * public void agregarAfinidad(Cliente cliente) { 
		switch(cliente.getAfinidad()) {
		case "VIP": clientesxAfinidad.add(delimitadorA1++, cliente);
		delimitadorA2++;
		delimitadorA3++;
		break;
		case "PREMIUM": clientesxAfinidad.add(delimitadorA2++, cliente); 
		delimitadorA3++;
		break;
		case "PLATINUM": clientesxAfinidad.add(delimitadorA3++, cliente); break;
		case "GOLD": clientesxAfinidad.add (cliente);
		}
	}
	
	public void agregarEdad(Cliente cliente) {
		if (cliente.getEdad()>=18 && cliente.getEdad()<=30) {
			clientesxEdad.add(cliente);
		}else if (cliente.getEdad()>30 && cliente.getEdad()<=45) {
			clientesxEdad.add(delimitadorE3++, cliente);
		}else if(cliente.getEdad()>45 && cliente.getEdad()<=60) {
			clientesxEdad.add(delimitadorE2++, cliente);
			delimitadorE3++;
		}else if (cliente.getEdad()>=60) {
			clientesxEdad.add(delimitadorE1++, cliente);
			delimitadorE2++;
			delimitadorE3++;
		}
	} */
	
	






	
	
	//////////////	//////////////	//////////////	//////////////	//////////////	//////////////	//////////////	//////////////	//////////////	//////////////	//////////////
}
