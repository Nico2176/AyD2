package modelo;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class Cola {
	//se podria usar un arraylist de arraylist de clientes asi cada "sub"arraylist representa cada grupo, seria mas legible PERO esto complicaría el printero en las pantallas según cómo está implementado ahora. 
	//aunque cambiando el método de printeo podria quedar mas bonito. que envíe la clase Cola y qué arraylist usar al método de printeo y luego printee uno seguido del otro.
	//creoq ue va a ser mejor el arraylist de arraylist por los patrones y la escalabilidad. mañana lo sigo.
	private Queue<Cliente> clientes = new LinkedList<>(); //por orden de llegada
	private ArrayList<Cliente> clientesxAfinidad = new ArrayList<>(); //arraylist por grupo de afinidad (supongase gold, platinum, premium y VIP)
	private ArrayList<Cliente> clientesxEdad = new ArrayList<>();      //arraylist por edades, (supongase 18-30, 30-45, 45-60, 60+)
	private byte delimitadorA1=0, delimitadorA2=0, delimitadorA3=0; //delimitadores que marcarán en qué posición del arraylist termina cada afinidad para no realizar busquedas costosas 
	private byte delimitadorE1=0, delimitadorE2=0, delimitadorE3=0; //delimitadores que marcarán en qué posición del arraylist termina cada grupo etario para no realizar busquedas costosas 
	
	//////////////	despues implementamos los patrones, esto es provisorio XD //////////////	//////////////	//////////////	//////////////	//////////////	//////////////	//////////////	//////////////	//////////////	//////////////	//////////////
	
	public void agregarAfinidad(Cliente cliente) { 
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
	}
	
	public void agregarxDefecto(Cliente cliente) {
		this.clientes.add(cliente);
	}
	
	//////////////	//////////////	//////////////	//////////////	//////////////	//////////////	//////////////	//////////////	//////////////	//////////////	//////////////
}
