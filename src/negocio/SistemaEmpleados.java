package negocio;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Queue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import controlador.ControladorCliente;
import controlador.ControladorPersonal;
import modelo.Cliente;




public class SistemaEmpleados extends Observable implements Runnable {
	private static SistemaEmpleados instancia;
	private Socket socket;
	private ObjectOutputStream flujoSalida;
	private ObjectInputStream flujoEntrada;
    private Thread hilo;
    private Queue<Cliente> clientes;
    private String clienteActual="";

    
	
	public void setClienteActual(String clienteActual) {
		this.clienteActual = clienteActual;
	}

	public String getClienteActual() {
		return clienteActual;
	}

	public static SistemaEmpleados getInstancia() {
		if (instancia == null)
			instancia = new SistemaEmpleados();
		return instancia;
	}
	
	public void crearHilo() {
		hilo = new Thread(this);
		hilo.start();
	}

	
	public void siguiente() throws Exception{
		if (this.clienteActual!=null && !this.clienteActual.equalsIgnoreCase("")) 
			throw new Exception ("Primero debes finalizar tu turno actual");
		
			
		try {
			clienteActual = this.clientes.poll().getDNI();	
		} catch (Exception e) {
			throw new Exception("No hay clientes en la Queue");
			
		}

			

		try {
			this.flujoSalida.writeObject("Siguiente");
		} catch (IOException e) {
			System.out.println("Excepcion cuando el empleado hace siguiente "+ e.toString());
		}
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
					Queue<Cliente> clientes = (Queue<Cliente>) object;
					this.clientes= clientes;
					this.setChanged();
					notifyObservers(clientes);
					//ControladorPersonal.getInstancia().printeaLista(clientes);
					//actualizar ventana del empleado con la queue
				} else if (object instanceof Integer) {
					this.setChanged();
					int box = (int) object;
					notifyObservers(box);
				}
				
				
				
			} catch (ClassNotFoundException | IOException e) {
				System.out.println("Excepcion recibiendo la queue "+ e.toString());
			}
    		
		}
		
	}

}
