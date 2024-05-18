package negocio;

import modelo.Cliente;
import modelo.Cola;
import modelo.IEstrategia;

public class EstrategiaDefault implements IEstrategia {


	@Override
	public void agregar(Cola cola, Cliente cliente) {
		cola.getClientes().get(0).add(cliente);
		
	}

}
