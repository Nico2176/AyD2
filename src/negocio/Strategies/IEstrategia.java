package negocio.Strategies;

import modelo.Cliente;
import modelo.Cola;

public interface IEstrategia {
	public void agregar(Cola cola, Cliente cliente);

}
