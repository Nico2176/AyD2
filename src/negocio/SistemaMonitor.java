package negocio;

import java.awt.Toolkit;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
import java.util.Observable;
import java.util.Queue;

import modelo.Cliente;
import modelo.Datos;

public class SistemaMonitor extends Observable implements Runnable {
	private Socket socket;
	private ObjectOutputStream flujoSalida;
	private ObjectInputStream flujoEntrada;
	private Queue<Cliente> clientes;
	private static SistemaMonitor instancia;
	private Thread hilo;
	
	public static SistemaMonitor getInstancia() {
		if (instancia == null)
			instancia = new SistemaMonitor();
		return instancia;
	}
	
	public void identificaMonitor() {
		try {
			int x=2176;
			this.flujoSalida.writeObject(x); //codigo identificador del monitor (en el servidor, el socket que reciba este código será el del monitor)
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void conectar(String host, int puerto) { 
        try {
            this.socket = new Socket(host, puerto); 
            System.out.println("Empleado conectado con el servidor, puerto del socket: "+ this.socket.getLocalPort());
            this.flujoSalida = new ObjectOutputStream(socket.getOutputStream());
            this.flujoEntrada = new ObjectInputStream(socket.getInputStream());
            this.identificaMonitor();
            
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

	public void iniciarHilo() {
		this.hilo= new Thread(this);
		this.hilo.start();
	}


	@Override
	public void run() {
		System.out.println("Ejecutando hilo del monitor");
		
		while (true) {
			try {
				System.out.println("monitor escuchando........");
				ObjectInputStream flujo = new ObjectInputStream(this.socket.getInputStream());  ////?????????????????? no tocar, por algun motivo no funciona con el input original y tuve que crear este xD
				Object object =   flujo.readObject();
				System.out.println("monitor recibió el objeto "+ object.toString());
				if (object instanceof List) { //recibió la queue de clientes actualizada
					Toolkit.getDefaultToolkit().beep();
					Queue<Cliente> clientes = (Queue<Cliente>) object;
					this.clientes= clientes;
					this.setChanged();
					notifyObservers(clientes);
				} else if (object instanceof Datos) {
					this.setChanged();
					Datos datos = (Datos) object;
					notifyObservers(datos);
				}
				
				
				
			} catch (ClassNotFoundException | IOException e) {
				System.out.println("Excepcion recibiendo la queue "+ e.toString());
			}
    		
		}
	}

}
