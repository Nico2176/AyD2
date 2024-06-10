package negocio.State;

import java.io.IOException;
import java.io.ObjectInputStream;

import modelo.Cola;
import negocio.SistemaEmpleados;

public class StatePrincipalActivo implements StateServer {
	
	
	private String pre ="[STATE_PRINCIPAL]";
	@Override
	public void manejarEvento(SistemaEmpleados sistema) throws Exception {

			
			
				System.out.println(pre+"Escuchando al servidor principal........");
				//Thread.sleep(300);
				
				ObjectInputStream flujo = new ObjectInputStream(sistema.getSocket().getInputStream());  
				Object object =   flujo.readObject();
			
				
				System.out.println(pre+"Recibió el objeto "+ object.toString());
				if (object instanceof Integer) 
					sistema.notificarObserver(object);
		    	else if (object instanceof Cola) {
					sistema.setCola((Cola) object);
					sistema.notificarObserver(object);
				}
			
			
	}

	
	

} 
