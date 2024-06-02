package negocio;

import java.io.IOException;
import java.net.Socket;

public class Verificador {
	
	private Socket socket;
	
	
	public int verificaServidores() {
		try {
			this.socket = new Socket("localhost", 1);
		} catch (IOException e) {
			return 1;			
		} 
		
		try {
			this.socket = new Socket("localhost",2);
		} catch (IOException e1) {
			 return 2;
		}
		
		return 0;
		
	}
	

}
