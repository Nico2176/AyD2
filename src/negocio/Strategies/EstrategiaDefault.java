package negocio.Strategies;

import modelo.Cliente;
import modelo.Cola;

public class EstrategiaDefault implements IEstrategia {


	@Override
	public void agregar(Cola cola, Cliente cliente) {
		cola.getClientes().get(0).add(cliente);
		
	}

}
