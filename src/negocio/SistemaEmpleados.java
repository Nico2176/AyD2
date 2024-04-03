package negocio;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import controlador.ControladorCliente;
import modelo.Cliente;




public class SistemaEmpleados implements Runnable {
	private static SistemaEmpleados instancia;
	private Socket socket;
	private ObjectOutputStream flujoSalida;
	private ObjectInputStream flujoEntrada;
    private Thread hilo;

	
	public static SistemaEmpleados getInstancia() {
		if (instancia == null)
			instancia = new SistemaEmpleados();
		return instancia;
	}
	
	public void crearHilo() {
		hilo = new Thread(this);
		hilo.start();
	}

	
	public void conectar(String host, int puerto) { 
        try {
            this.socket = new Socket(host, puerto); 
            System.out.println("Empleado conectado con el servidor, puerto del socket: "+ this.socket.getLocalPort());
            this.flujoSalida = new ObjectOutputStream(socket.getOutputStream());
            this.flujoEntrada = new ObjectInputStream(socket.getInputStream());
            
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }






	@Override
	public void run() {
		System.out.println("Ejecutando hilo de empleado");
		while (true) {
			try {
				System.out.println("Empleado escuchando........");
				ObjectInputStream flujo = new ObjectInputStream(this.socket.getInputStream());  ////?????????????????? no tocar, por algun motivo no funciona con el input original y tuve que crear este xD
				Object object =   flujo.readObject();
				System.out.println("Empleado recibió el objeto "+ object.toString());
				if (object instanceof List) { //recibió la queue de clientes actualizada
					//actualizar ventana del empleado con la queue
				}
				
				
				
			} catch (ClassNotFoundException | IOException e) {
				System.out.println("Excepcion recibiendo la queue "+ e.toString());
			}
    		
		}
		
	}

}
