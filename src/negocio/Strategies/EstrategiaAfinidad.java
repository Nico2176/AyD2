package negocio.Strategies;

import modelo.Cliente;
import modelo.Cola;

public class EstrategiaAfinidad implements IEstrategia{

	@Override
	public void agregar(Cola cola, Cliente cliente) {
			 if (cliente.getAfinidad()==null)
				 cola.getClientes().get(3).add(cliente); 
			 else {
				 switch(cliente.getAfinidad()) {
					case "VIP": cola.getClientes().get(0).add(cliente);
					break;
					case "Premium":  cola.getClientes().get(1).add(cliente);
					break;
					case "Platinum":  cola.getClientes().get(2).add(cliente); 
					break;
					case "Gold":   cola.getClientes().get(3).add(cliente);
					break;
					
					}
			 }
			 cola.updateLista();
			 System.out.println("La cola quedó así: "+ cola.getLista().toString());
			
	}

}
