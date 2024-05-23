package negocio.Strategies;

import modelo.Cliente;
import modelo.Cola;

public class EstrategiaEdad implements IEstrategia{


	@Override
	public void agregar(Cola cola, Cliente cliente) {
			
		if (cliente.getEdad()>=60) 
			cola.getClientes().get(0).add(cliente);
	else if(cliente.getEdad()>45 && cliente.getEdad()<=60) 
			cola.getClientes().get(1).add(cliente);
	else if (cliente.getEdad()>30 && cliente.getEdad()<=45) 
			cola.getClientes().get(2).add(cliente);
	 else if (cliente.getEdad()>=18 && cliente.getEdad()<=30) 
			cola.getClientes().get(3).add(cliente);
	 else
		 cola.getClientes().get(3).add(cliente);
		
	cola.updateLista();
	}
	
	
	

	}

