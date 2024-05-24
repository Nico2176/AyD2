package negocio.State;

import java.io.ObjectInputStream;

import modelo.Cola;
import negocio.SistemaEmpleados;

public class StateSecundarioActivo implements StateServer{

	@Override
	public void manejarEvento(SistemaEmpleados sistema) throws Exception {

		System.out.println("Escuchando al servidor secundario........");
		//Thread.sleep(300);
		ObjectInputStream flujo = new ObjectInputStream(sistema.getSocketSecundario().getInputStream());  
		Object object =   flujo.readObject();
		
		
		if (object instanceof Cola) {
			sistema.setCola((Cola) object);
			sistema.notificarObserver(object);
		} else if (object instanceof Integer) {
			int x = (int) object;
			if (x!=777) {
				sistema.notificarObserver(object);
			} else if (x==777) {
				System.out.println("Volvió el primario, cambiando nuevamente");
				sistema.getSocket().close();
				//SistemaEmpleados.this.socketSecundario.close();
				sistema.getFlujoEntrada().close();
				sistema.getFlujoSalida().close();

				Thread.sleep(200);  //un pequeño delay porque sino no le da tiempo a abrirse al servidor primario
				
				sistema.reconecto("localhost", 1);

				sistema.setEstadoServidor(new StatePrincipalActivo());
				sistema.setPrincipalActivo(true);
	           // System.out.println("Interrumpiendo hilo de escucha");
	            //Thread.currentThread().interrupt();
				return;
			} 
			
		}
	
	}

}
