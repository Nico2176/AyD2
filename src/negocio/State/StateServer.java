package negocio.State;

import negocio.SistemaEmpleados;

public interface StateServer {
	void manejarEvento(SistemaEmpleados sistema) throws Exception;

}
